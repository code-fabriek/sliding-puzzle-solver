package com.example.solver.domain;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class PuzzleBoardTest {

    static final int[] BOARD_TILES_3x3 = new int[] {
            0, 3, 7,
            1, 2, 5,
            4, 6, 8
    };

    static final int[] BOARD_TILES_4x4 = new int[] {
            12, 1, 10, 2,
            7, 11, 4, 14,
            5, 0, 9, 15,
            8, 13, 6, 3
    };

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test public void testCreate3x3Board() throws PuzzleBoardException {
        PuzzleBoard puzzleBoard = new PuzzleBoard(3, BOARD_TILES_3x3);
        assertNotNull(puzzleBoard);
        assertEquals(3, puzzleBoard.getSize());
        assertArrayEquals(BOARD_TILES_3x3, puzzleBoard.getTiles());
    }

    @Test public void testCreate4x4Board() throws PuzzleBoardException {
        PuzzleBoard puzzleBoard = new PuzzleBoard(4, BOARD_TILES_4x4);
        assertNotNull(puzzleBoard);
        assertEquals(4, puzzleBoard.getSize());
        assertArrayEquals(BOARD_TILES_4x4, puzzleBoard.getTiles());
    }

    @Test public void testCreateNoEmptyTileBoardThrowsError() throws PuzzleBoardException {
        thrown.expect(PuzzleBoardException.class);
        thrown.expectMessage("Invalid board data - there must be one empty tile on the board!");

        int[] tiles = new int[]{
                9, 3, 7,
                1, 2, 5,
                4, 6, 8
        };
        new PuzzleBoard(3, tiles);
    }

    @Test public void testCreateDuplicateValuesBoardThrowsError() throws PuzzleBoardException {
        thrown.expect(PuzzleBoardException.class);
        thrown.expectMessage("Invalid board data - each board tile must have a unique value!");

        int[] tiles = new int[]{
                0, 3, 7,
                1, 2, 5,
                4, 5, 8
        };
        new PuzzleBoard(3, tiles);
    }

    @Test public void testCreateUnsolvableBoardThrowsError() throws PuzzleBoardException {
        thrown.expect(PuzzleBoardException.class);
        thrown.expectMessage("Invalid board data - there is no solution for the given board layout!");

        int[] unsolvableTiles = new int[] {
                3, 9, 1, 15,
                14, 11, 4, 6,
                13, 0, 10, 12,
                2, 7, 8, 5
        };
        new PuzzleBoard(4, unsolvableTiles);
    }

}
