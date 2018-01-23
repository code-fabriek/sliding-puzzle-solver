package com.example.solver.domain;

import com.google.common.primitives.Ints;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

public class PuzzleBoard {

    private int[] tiles;
    private int size;
    private int emptyTilePosition;

    /**
     * Initializes a new sliding puzzle game board.
     *
     * @param size the size of the game board (e.g. size = 3 means the board is 3x3)
     * @param tiles an array representing the game board tile values
     * @throws PuzzleBoardException if the puzzle board initial state is invalid or cannot be solved
     */
    public PuzzleBoard(int size, int[] tiles) throws PuzzleBoardException {
        if (size < 2) {
            throw new IllegalArgumentException("Puzzle board size must be at least 2 x 2 tiles");
        }
        if (tiles == null || tiles.length == 0) {
            throw new IllegalArgumentException("An initial set of board values must be provided");
        }
        this.size = size;
        this.tiles = tiles;
        this.emptyTilePosition = Ints.indexOf(this.tiles, 0);

        this.validateInitialBoard();

    }

    public int[] getTiles() {
        return tiles;
    }

    public int getSize() {
        return size;
    }

    public int getEmptyTilePosition() {
        return emptyTilePosition;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append("\n === Board Values === \n");
        for (int i = 0; i < tiles.length; i++) {
            sb.append(tiles[i]);
            if (i % this.size != 0) {
                sb.append(" | ");
            } else {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * Counts the number of permutation inversions in the puzzle board tile values.
     *
     * @return the sum of all permutation inversions on the board
     * @see <a href="http://mathworld.wolfram.com/PermutationInversion.html">Wolfram Mathworld</a>
     */
    public int getInversionCount() {
        int inversions = 0;

        // walk through tiles counting the number of tiles to the right with a lower value
        for (int i = 0; i < this.tiles.length - 1; i++) {
            for (int j = i + 1; j < this.tiles.length; j++) {
                if (this.tiles[i] > this.tiles[j]) {
                    inversions++;
                }
            }
        }

        return inversions;

    }

    /**
     * Validates the initial puzzle game board state.
     *
     * @throws PuzzleBoardException if the board tile values contain invalid data
     */
    private void validateInitialBoard() throws PuzzleBoardException {
        if (!this.containsOneEmptyTile()) {
            throw new PuzzleBoardException("Invalid board data - there must be one empty tile on the board!");
        }

        if (!this.containsUniqueTiles()) {
            throw new PuzzleBoardException("Invalid board data - each board tile must have a unique value!");
        }

        if (!this.isSolvable()) {
            throw new PuzzleBoardException("Invalid board data - there is no solution for the given board layout!");
        }

    }



    /**
     * Checks that one tile is empty (value == 0) on the board.
     *
     * @return true if there is one empty tile, false otherwise
     */
    private boolean containsOneEmptyTile() {
        return IntStream.of(this.tiles).filter(x -> x == 0).count() == 1;
    }

    /**
     * Checks that all tile values are unique on the board.
     *
     * @return true if all tile values are unique, false otherwise
     */
    private boolean containsUniqueTiles() {
        Set<Integer> uniqueBoardValues = new HashSet<>();
        for (int value : this.tiles) {
            if (!uniqueBoardValues.add(value)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the given board layout can actually be solved.
     *
     * @return true if a solution can be found for the board, false otherwise
     */
    private boolean isSolvable() {
        if (this.size % 2 == 0) {
            // when board size is even, parity of empty tile row position from bottom must
            // be the inverse of inversion count parity
            int emptyTileRow = (((this.size * this.size) - this.emptyTilePosition + 1) / this.size) + 1;
            return (emptyTileRow % 2 != this.getInversionCount() % 2);

        } else {
            // when board size is odd, inversions must be even
            return this.getInversionCount() % 2 == 0;
        }
    }

}
