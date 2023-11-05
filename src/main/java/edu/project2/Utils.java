package edu.project2;

import edu.project2.mazecomponents.Cell;
import edu.project2.mazecomponents.Maze;
import java.util.Objects;

public class Utils {
    private static final int MIN_DIMENSION_SIZE = 3;

    private Utils() {
    }

    public static void validateMaze(Maze maze) {
        Objects.requireNonNull(maze);
        Objects.requireNonNull(maze.grid());

        if (maze.height() < MIN_DIMENSION_SIZE
            || maze.width() < MIN_DIMENSION_SIZE
            || maze.grid().length != maze.height()) {
            throw new IllegalArgumentException();
        }

        for (Cell[] row : maze.grid()) {
            Objects.requireNonNull(row);

            if (row.length != maze.width()) {
                throw new IllegalArgumentException();
            }

            for (Cell col : row) {
                Objects.requireNonNull(col);
            }
        }
    }
}
