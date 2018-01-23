package com.example.solver.io;

import com.example.solver.domain.PuzzleBoard;
import com.example.solver.domain.PuzzleBoardException;
import com.google.common.base.CharMatcher;
import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.io.Resources;
import com.google.common.primitives.Ints;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class PuzzleLoader {

    /**
     * Loads a text file containing the initial board state into a 2-dimensional array of tile values.
     *
     * The text file can be comma/semicolon/space delimited, every line represents one row of the game board and each
     * tile value must be a non-repeating integer.  A value of 0 indicates the empty tile.
     *
     * @param filePath the text data file to load
     * @return a 2-dimensional array representing the board puzzle tile values
     * @throws IOException if an error occurs reading the data file
     */
    private static int[][] loadBoardValues(String filePath) throws IOException {
        URL file = Resources.getResource(filePath);
        ImmutableList<String> lines = Resources.asCharSource(file, Charsets.UTF_8).readLines();

        int[][] tileGrid = new int[lines.size()][];
        for (int i = 0; i < lines.size(); i++) {
            List<String> lineValues =
                    Splitter.on(CharMatcher.anyOf(";, ")).trimResults().omitEmptyStrings()
                            .splitToList(lines.get(i));
            tileGrid[i] = lineValues.stream().mapToInt(Integer::parseInt).toArray();
        }

        return tileGrid;
    }

    /**
     * Checks if the board has the same number of rows/columns.
     *
     * @param tileGrid a 2-dimensional array representing puzzle board tile values
     * @return true if the board is square, false otherwise
     */
    private static boolean isSquare(int[][] tileGrid) {
        for (int i = 0; i < tileGrid.length; i++) {
            if (tileGrid.length != tileGrid[i].length) {
                return false;
            }
        }
        return true;
    }

    /**
     * Factory method to initialize a new PuzzleBoard from a data file containing the board layout
     *
     * @param filePath the text data file to load
     * @return a PuzzleBoard instance initialized with tile values from the data file
     * @throws PuzzleBoardException if an error occurs creating the puzzle board
     */
    public static PuzzleBoard createPuzzleFromTextFile(String filePath) throws PuzzleBoardException {
        try {
            int[][] tileGrid = loadBoardValues(filePath);

            if (!isSquare(tileGrid)) {
                throw new PuzzleBoardException("Invalid puzzle board data - game board must be square!");
            }

            // flatten 2-dimensional grid into one array (top left to lower right)
            int[] tiles = Ints.concat(tileGrid);

            return new PuzzleBoard(tileGrid.length, tiles);

        } catch (IOException e) {
            throw new PuzzleBoardException("Unable to load puzzle board data file", e);
        } catch (NumberFormatException e) {
            throw new PuzzleBoardException("Invalid puzzle board tile values (must be numeric)", e);
        }

    }

}
