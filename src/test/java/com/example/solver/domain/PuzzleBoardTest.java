package com.example.solver.domain;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class PuzzleBoardTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test public void testCreateValidBoard() throws PuzzleBoardException {
        int[] tiles = new int[]{
                0, 3, 7,
                1, 2, 5,
                4, 6, 8
        };
        PuzzleBoard puzzleBoard = new PuzzleBoard(3, tiles);
        assertNotNull(puzzleBoard);
        assertEquals(3, puzzleBoard.getSize());
        assertArrayEquals(tiles, puzzleBoard.getTiles());
    }

    @Test public void testBoardInversionCount() throws PuzzleBoardException {
        int[] tiles = new int[]{
                0, 3, 7,
                1, 2, 5,
                4, 6, 8
        };
        PuzzleBoard puzzleBoard = new PuzzleBoard(3, tiles);
        assertNotNull(puzzleBoard);
        assertArrayEquals(tiles, puzzleBoard.getTiles());
        assertEquals(3, puzzleBoard.getSize());
        assertEquals(8, puzzleBoard.getInversionCount());
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
}
