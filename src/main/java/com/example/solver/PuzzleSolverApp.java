package com.example.solver;

import com.example.solver.domain.MoveDirection;
import com.example.solver.domain.PuzzleBoard;
import com.example.solver.domain.PuzzleBoardException;
import com.example.solver.io.PuzzleLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * This class loads and attempts to solve an 8-puzzle / 15-puzzle board layout.
 *
 * @author Mark Schilling
 * @see <a href="https://github.com/code-fabriek/sliding-puzzle-solver">Github project</a>
 */
public class PuzzleSolverApp {
    private static final Logger logger = LogManager.getLogger();

    private PriorityQueue<PuzzleBoard> queue;

    public PuzzleSolverApp(PuzzleBoard puzzleBoard) {
        // TODO add manhattan distance heuristic
        this.queue = new PriorityQueue<>((a,b) -> a.getMoves().size() - b.getMoves().size());
        this.queue.add(puzzleBoard);
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Sliding puzzle solver app");
            System.out.println("Usage: java -jar sliding-puzzle-solver.jar [FILE]");
            System.out.println("[FILE] = initial puzzle board values");
            Runtime.getRuntime().exit(1);
        }

        String boardData = args[0];
        try {

            PuzzleBoard puzzleBoard = PuzzleLoader.createPuzzleFromTextFile(boardData);
            logger.info("Loaded initial board layout");
            logger.info(puzzleBoard.toString());

            PuzzleSolverApp app = new PuzzleSolverApp(puzzleBoard);
            PuzzleBoard solvedBoard = app.solve();

            if (solvedBoard != null) {
                logger.info("Solved board layout");
                logger.info(solvedBoard.toString());
                logger.info("List of moves for solution: " + solvedBoard.getMoves());
            }

        } catch (PuzzleBoardException e) {
            logger.error("Invalid game board data - exiting");
        }
    }

    /**
     * Generates a list of next possible board states given the current board.
     *
     * @param currentBoard the current board layout
     * @return a list containing the next possible board layouts for all valid moves
     */
    public List<PuzzleBoard> generateBoardStates(PuzzleBoard currentBoard) {
        MoveDirection lastMove = null;
        if (!currentBoard.getMoves().isEmpty()) {
            lastMove = currentBoard.getLastMove();
        }
        Set<MoveDirection> availableMoves = currentBoard.getAvailableMoves();
        logger.debug("Found available moves: " + availableMoves);
        List<PuzzleBoard> nextBoards = new ArrayList<>(availableMoves.size());

        for (MoveDirection move: availableMoves) {
            // check that we don't backtrack to the previous move / board layout
            if (lastMove == null || move != lastMove.getOppositeDirection()) {
                PuzzleBoard newBoard = new PuzzleBoard(currentBoard);
                newBoard.moveTile(move);
                nextBoards.add(newBoard);
            }
        }

        return nextBoards;
    }

    /**
     * Searches for a solution to the initial board layout.
     *
     * @return the solved puzzle board
     */
    public PuzzleBoard solve() {
        Set<PuzzleBoard> visitedBoardStates = new HashSet<>();
        int iterations = 0;
        int maxIterations = 300000;
        while (this.queue.size() > 0) {
            iterations++;
            if (iterations > maxIterations) {
                logger.error("Unable to find solution before max iterations reached");
                break;
            }

            PuzzleBoard currentBoard = this.queue.poll();
            if (currentBoard.isSolved()) {
                return currentBoard;
            } else {
                visitedBoardStates.add(currentBoard);
                for (PuzzleBoard nextBoard : this.generateBoardStates(currentBoard)) {
                    if (!visitedBoardStates.contains(nextBoard)) {
                        this.queue.add(nextBoard);
                    }
                }
            }
        }
        return null;
    }

}
