package com.example.solver.domain;

public class PuzzleBoardException extends Exception {

    public PuzzleBoardException(String message) {
        super(message);
    }

    public PuzzleBoardException(String message, Throwable cause) {
        super(message, cause);
    }
}
