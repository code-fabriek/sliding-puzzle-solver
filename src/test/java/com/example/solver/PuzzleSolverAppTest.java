package com.example.solver;

import com.example.solver.domain.MoveDirection;
import com.example.solver.domain.PuzzleBoard;
import com.example.solver.domain.PuzzleBoardException;
import com.example.solver.domain.PuzzleBoardTest;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class PuzzleSolverAppTest {

    PuzzleBoard testBoard;
    PuzzleSolverApp testApp;

    @Before
    public void setUp() throws PuzzleBoardException {
        this.testBoard = new PuzzleBoard(3, PuzzleBoardTest.BOARD_TILES_3x3);
        this.testApp = new PuzzleSolverApp(this.testBoard);
    }

    @Test public void testGenerateBoardStates() {
        int[] moveDownBoard = new int[] {
                1, 3, 7,
                0, 2, 5,
                4, 6, 8
        };
        int[] moveRightBoard = new int[] {
                3, 0, 7,
                1, 2, 5,
                4, 6, 8
        };

        List<PuzzleBoard> newBoards = this.testApp.generateBoardStates(this.testBoard);
        for (PuzzleBoard board : newBoards) {
            if (board.getLastMove() == MoveDirection.DOWN) {
                assertArrayEquals(moveDownBoard, board.getTiles());
            } else if (board.getLastMove() == MoveDirection.RIGHT) {
                assertArrayEquals(moveRightBoard, board.getTiles());
            }
        }

    }

    @Test public void testSolve3x3Board() {
        PuzzleBoard solvedBoard = this.testApp.solve();
        assertNotNull(solvedBoard);
        assertTrue(solvedBoard.isSolved());
    }

}
