package com.example.solver;

import com.example.solver.domain.PuzzleBoard;
import com.example.solver.domain.PuzzleBoardException;
import com.example.solver.io.PuzzleLoader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class PuzzleSolverApp {
    private static final Logger logger = LogManager.getLogger();

    private PuzzleBoard puzzleBoard;

    public PuzzleSolverApp(PuzzleBoard puzzleBoard) {
        this.puzzleBoard = puzzleBoard;
    }

    public void findSolution() {
        // TODO create graph and use A* search + manhattan distance
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Sliding puzzle solver app\n");
            System.out.println("Usage: java -jar sliding-puzzle-solver.jar [FILE]\n");
            System.out.println("[FILE] = initial puzzle board values");
        }

        String boardData = args[0];
        try {

            PuzzleBoard puzzleBoard = PuzzleLoader.createPuzzleFromTextFile(boardData);

            PuzzleSolverApp app = new PuzzleSolverApp(puzzleBoard);
            app.findSolution();

        } catch (PuzzleBoardException e) {
            logger.error("Invalid game board data - exiting");
        }
    }

}
