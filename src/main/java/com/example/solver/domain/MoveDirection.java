package com.example.solver.domain;

public enum MoveDirection {
    UP, DOWN, LEFT, RIGHT;

    private MoveDirection opposite;

    static {
        UP.opposite = DOWN;
        DOWN.opposite = UP;
        LEFT.opposite = RIGHT;
        RIGHT.opposite = LEFT;
    }

    public MoveDirection getOppositeDirection() {
        return opposite;
    }
}
