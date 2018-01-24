package com.example.solver.domain;

import com.google.common.base.Objects;
import com.google.common.primitives.Ints;

import java.util.*;
import java.util.stream.IntStream;

public class PuzzleBoard {

    private int[] tiles;
    private int size;
    private int emptyTilePosition;
    private List<MoveDirection> moves = new ArrayList<>();

    /**
     * Initializes a new sliding puzzle game board.
     *
     * @param size the size of the game board (e.g. size = 3 means the board is 3x3)
     * @param tiles an array representing the game board tile values
     * @throws PuzzleBoardException if the puzzle board initial state is invalid or cannot be solved
     */
    public PuzzleBoard(int size, int[] tiles) throws PuzzleBoardException {
        if (size < 2) {
            throw new IllegalArgumentException("Puzzle board size must be at least 2 x 2 tiles!");
        }
        if (tiles == null || tiles.length == 0) {
            throw new IllegalArgumentException("An initial set of board values must be provided!");
        }
        this.size = size;
        this.tiles = tiles.clone();
        this.emptyTilePosition = Ints.indexOf(this.tiles, 0);

        this.validateInitialBoard();

    }

    /**
     * Initializes a new copy of the given sliding puzzle game board.
     *
     * @param board the puzzle board instance to copy
     */
    public PuzzleBoard(PuzzleBoard board) {
        if (board == null) {
            throw new IllegalArgumentException("An existing puzzle board instance must be provided!");
        }

        this.size = board.getSize();
        this.tiles = board.getTiles().clone();
        this.emptyTilePosition = board.getEmptyTilePosition();
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

    public List<MoveDirection> getMoves() {
        return moves;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PuzzleBoard that = (PuzzleBoard) o;
        return Objects.equal(tiles, that.tiles);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(tiles);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append(String.format("\n === Board Moves: %d === \n", this.moves.size()));
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
     * Checks if the current tile arrangement is a solved state.
     *
     * @return true if all tile values are sorted in ascending order, false otherwise
     */
    public boolean isSolved() {
        return this.getInversionCount() == 0;
    }


    /**
     * Returns a set of valid moves given the current empty tile position.
     *
     * @return the set of currently available moves
     */
    public Set<MoveDirection> getAvailableMoves() {
        EnumSet<MoveDirection> validMoves = EnumSet.noneOf(MoveDirection.class);

        if (this.emptyTilePosition / this.size > 0) {
            validMoves.add(MoveDirection.UP);
        }
        if (this.emptyTilePosition / this.size < (this.size - 1)) {
            validMoves.add(MoveDirection.DOWN);
        }
        if (this.emptyTilePosition % this.size != 0) {
            validMoves.add(MoveDirection.LEFT);
        }
        if ((this.emptyTilePosition + 1) % this.size != 0) {
            validMoves.add(MoveDirection.RIGHT);
        }

        return validMoves;
    }

    /**
     * Swaps the tile in the specified direction with the empty tile.
     *
     * @param direction the direction to move the empty tile
     */
    public void moveTile(MoveDirection direction) {
        int swapPosition;
        switch (direction) {
            case UP:
                swapPosition = this.emptyTilePosition - this.size;
                break;
            case DOWN:
                swapPosition = this.emptyTilePosition + this.size;
                break;
            case LEFT:
                swapPosition = this.emptyTilePosition - 1;
                break;
            case RIGHT:
                swapPosition = this.emptyTilePosition + 1;
                break;
            default:
                throw new IllegalArgumentException("Invalid move direction");
        }

        this.tiles[this.emptyTilePosition] = this.tiles[swapPosition];
        this.tiles[swapPosition] = 0;
        this.emptyTilePosition = swapPosition;
        this.moves.add(direction);

    }

    /**
     * Counts the number of permutation inversions in the puzzle board tile values.
     *
     * @return the sum of all permutation inversions on the board
     * @see <a href="http://mathworld.wolfram.com/PermutationInversion.html">Wolfram Mathworld</a>
     */
    private int getInversionCount() {
        int inversions = 0;

        // walk through tiles counting the number of tiles to the right with a lower value
        for (int i = 0; i < this.tiles.length - 1; i++) {
            // skip the empty tile
            if (i == this.emptyTilePosition) {
                continue;
            }

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
