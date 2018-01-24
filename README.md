# sliding-puzzle-solver
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
gradlew build
gradlew run 8-puzzle-example.txt
```
