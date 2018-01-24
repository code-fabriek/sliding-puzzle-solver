# sliding-puzzle-solver

[![Build Status](https://travis-ci.org/code-fabriek/sliding-puzzle-solver.svg?branch=master)](https://travis-ci.org/code-fabriek/sliding-puzzle-solver)

A sliding puzzle solver app written in Java that attempts to solve an 
[8-puzzle / 15-puzzle](https://en.wikipedia.org/wiki/15_puzzle) board layout loaded from a text file.  

## Example board layout
The app loads the initial board layout from a text file, each line in the file represents a row on the board and
each tile value separated by a delimiter (space/comma).  The empty tile slot is indicated by a tile value of 0.

For example, a 3x3 board layout file would look like:

```
0 3 7
1 2 5
4 6 8
``` 

## Running the solver

```bash
gradlew installDist
.\build\install\sliding-puzzle-solver\bin\sliding-puzzle-solver 8-puzzle-example.txt
```

## Example output

```
13:34:50.403 [main] INFO  com.example.solver.PuzzleSolverApp - Loaded initial board layout
13:34:50.406 [main] INFO  com.example.solver.PuzzleSolverApp -  === Board Moves: 0 ===
2 | 8 |
4 | 1 | 3
7 | 6 | 5
13:34:50.468 [main] INFO  com.example.solver.PuzzleSolverApp - Solved board layout
13:34:50.469 [main] INFO  com.example.solver.PuzzleSolverApp -  === Board Moves: 14 ===
1 | 2 | 3
4 | 5 | 6
7 | 8 |
13:34:50.469 [main] INFO  com.example.solver.PuzzleSolverApp - List of moves for solution: [DOWN, DOWN, LEFT, LEFT, UP, RIGHT, UP, LEFT, DOWN, DOWN, RIGHT, UP, RIGHT, DOWN]
```
