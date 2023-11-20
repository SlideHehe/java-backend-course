package edu.project2.generators;

import edu.project2.mazecomponents.Cell;
import edu.project2.mazecomponents.Coordinate;
import edu.project2.mazecomponents.Direction;
import edu.project2.mazecomponents.Maze;
import java.util.Objects;
import static edu.project2.generators.GeneratorUtils.connectWithNeighbor;
import static edu.project2.generators.GeneratorUtils.generateRandomCoordinateWithinBounds;
import static edu.project2.generators.GeneratorUtils.getNeighborFromDirection;
import static edu.project2.generators.GeneratorUtils.getNeighbors;
import static edu.project2.generators.GeneratorUtils.initializeGrid;
import static edu.project2.generators.GeneratorUtils.isUnvisited;
import static edu.project2.generators.GeneratorUtils.isValidCoordinate;
import static edu.project2.generators.GeneratorUtils.validateDimension;

public class HuntAndKillGenerator implements Generator {
    private Cell[][] grid;

    @Override
    public Maze generate(int height, int width) {
        validateDimension(height, width);

        int actualHeight = height % 2 == 0 ? height + 1 : height;
        int actualWidth = width % 2 == 0 ? width + 1 : width;

        grid = initializeGrid(actualHeight, actualWidth);

        Coordinate startCoordinate = generateRandomCoordinateWithinBounds(actualHeight, actualWidth);
        generateWithHuntAndKillAlgorithm(startCoordinate);

        return new Maze(actualHeight, actualWidth, grid);
    }

    private void generateWithHuntAndKillAlgorithm(Coordinate startCoordinate) {
        Coordinate current = startCoordinate;

        while (Objects.nonNull(current)) {
            grid[current.row()][current.col()] = Cell.PASSAGE;

            current = walk(current);

            if (Objects.isNull(current)) {
                current = hunt();
            }
        }
    }

    private Coordinate walk(Coordinate current) {
        for (Direction direction : getNeighbors()) {
            Coordinate neighbor = getNeighborFromDirection(current, direction);

            if (isValidCoordinate(grid, neighbor) && isUnvisited(grid, neighbor)) {
                connectWithNeighbor(grid, neighbor, direction);
                return neighbor;
            }
        }

        return null;
    }

    private Coordinate hunt() {
        for (int row = 1; row < grid.length - 1; row += 2) {
            for (int col = 1; col < grid[0].length - 1; col += 2) {
                Coordinate coordinate = new Coordinate(row, col);

                if (isUnvisited(grid, coordinate) && hasVisitedNeighbor(coordinate)) {
                    return new Coordinate(row, col);
                }
            }
        }

        return null;
    }

    private boolean hasVisitedNeighbor(Coordinate coordinate) {
        for (Direction direction : getNeighbors()) {
            Coordinate neighbor = getNeighborFromDirection(coordinate, direction);

            if (isValidCoordinate(grid, neighbor) && !isUnvisited(grid, neighbor)) {
                connectWithNeighbor(grid, neighbor, direction);
                return true;
            }
        }

        return false;
    }
}
