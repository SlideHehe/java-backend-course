package edu.project2.solvers;

import edu.project2.mazecomponents.Cell;
import edu.project2.mazecomponents.Coordinate;
import edu.project2.mazecomponents.Maze;
import java.util.Objects;
import static edu.project2.Utils.validateMaze;

class SolverUtils {
    private SolverUtils() {
    }

    static void validateSolverArgs(Maze maze, Coordinate start, Coordinate end) {
        validateMaze(maze);
        validateCoordinates(maze, start, end);
    }

    static void validateCoordinates(Maze maze, Coordinate start, Coordinate end) {
        Objects.requireNonNull(start);
        Objects.requireNonNull(end);

        if (!isValidMove(maze, start) || !isValidMove(maze, end)) {
            throw new IllegalArgumentException();
        }
    }

    static boolean isValidMove(Maze maze, Coordinate coordinate) {
        return coordinate.row() >= 0
            && coordinate.row() < maze.height()
            && coordinate.col() >= 0
            && coordinate.col() < maze.width()
            && maze.grid()[coordinate.row()][coordinate.col()] != Cell.WALL;
    }
}
