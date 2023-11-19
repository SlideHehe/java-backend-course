package edu.project2.generators;

import edu.project2.mazecomponents.Cell;
import edu.project2.mazecomponents.Coordinate;
import edu.project2.mazecomponents.Direction;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

class GeneratorUtils {
    private static final int MIN_DIMENSION_SIZE = 3;
    private static final Random RANDOM = new Random();

    private GeneratorUtils() {
    }

    static void validateDimension(int height, int width) {
        if (height <= MIN_DIMENSION_SIZE || width <= MIN_DIMENSION_SIZE) {
            throw new IllegalArgumentException();
        }
    }

    static Cell[][] initializeGrid(int height, int width) {
        var grid = new Cell[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (row % 2 == 0 || col % 2 == 0) {
                    grid[row][col] = Cell.WALL;
                }
            }
        }

        return grid;
    }

    static Coordinate generateRandomCoordinateWithinBounds(int height, int width) {
        return new Coordinate(
            generateRandomNumberWithinBound(height),
            generateRandomNumberWithinBound(width)
        );
    }

    private static int generateRandomNumberWithinBound(int bound) {
        int number = RANDOM.nextInt(1, bound - 2);
        number = number % 2 == 0 ? number + 1 : number;
        return number;
    }

    static List<Direction> getNeighbors() {
        var directions = Arrays.asList(Direction.values());
        Collections.shuffle(directions);
        return directions;
    }

    static Coordinate getNeighborFromDirection(Coordinate coordinate, Direction direction) {
        int newRow = coordinate.row() + direction.cellRowOffset();
        int newCol = coordinate.col() + direction.cellColOffset();

        return new Coordinate(newRow, newCol);
    }

    static boolean isValidCoordinate(Cell[][] grid, Coordinate coordinate) {
        return coordinate.row() > 0
            && coordinate.row() < grid.length - 1
            && coordinate.col() > 0
            && coordinate.col() < grid[coordinate.row()].length - 1;
    }

    static boolean isUnvisited(Cell[][] grid, Coordinate coordinate) {
        return Objects.isNull(grid[coordinate.row()][coordinate.col()]);
    }

    static void connectWithNeighbor(Cell[][] grid, Coordinate neighbor, Direction direction) {
        grid[neighbor.row() - direction.rowOffset()][neighbor.col() - direction.colOffset()] = Cell.PASSAGE;
    }
}
