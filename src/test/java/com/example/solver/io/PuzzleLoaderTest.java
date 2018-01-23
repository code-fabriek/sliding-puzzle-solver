package com.example.solver.io;

import com.example.solver.domain.PuzzleBoard;
import com.example.solver.domain.PuzzleBoardException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class PuzzleLoaderTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test public void testCreateValidBoard() throws PuzzleBoardException {
        int[] expectedTiles = new int[]{0, 3, 7, 1, 2, 5, 4, 6, 8};
        PuzzleBoard puzzleBoard = PuzzleLoader.createPuzzleFromTextFile("example-boards/3x3-board.txt");
        assertNotNull(puzzleBoard);
        assertEquals(3, puzzleBoard.getSize());
        assertArrayEquals(expectedTiles, puzzleBoard.getTiles());
    }

    @Test public void testCreateNonNumericBoardThrowsError() throws PuzzleBoardException {
        thrown.expect(PuzzleBoardException.class);
        thrown.expectMessage("Invalid puzzle board tile values");

        PuzzleLoader.createPuzzleFromTextFile("example-boards/non-numeric-board.txt");
    }

    @Test public void testCreateNonSquareBoardThrowsError() throws PuzzleBoardException {
        thrown.expect(PuzzleBoardException.class);
        thrown.expectMessage("Invalid puzzle board data - game board must be square!");

        PuzzleLoader.createPuzzleFromTextFile("example-boards/non-square-puzzle.txt");
    }

}
