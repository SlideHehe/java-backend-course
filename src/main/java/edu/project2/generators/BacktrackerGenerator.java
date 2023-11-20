package edu.project2.generators;

import edu.project2.mazecomponents.Cell;
import edu.project2.mazecomponents.Coordinate;
import edu.project2.mazecomponents.Direction;
import edu.project2.mazecomponents.Maze;
import java.util.Stack;
import static edu.project2.generators.GeneratorUtils.connectWithNeighbor;
import static edu.project2.generators.GeneratorUtils.generateRandomCoordinateWithinBounds;
import static edu.project2.generators.GeneratorUtils.getNeighborFromDirection;
import static edu.project2.generators.GeneratorUtils.getNeighbors;
import static edu.project2.generators.GeneratorUtils.initializeGrid;
import static edu.project2.generators.GeneratorUtils.isUnvisited;
import static edu.project2.generators.GeneratorUtils.isValidCoordinate;
import static edu.project2.generators.GeneratorUtils.validateDimension;

public class BacktrackerGenerator implements Generator {
    private final Stack<Coordinate> stack = new Stack<>();
    private Cell[][] grid;

    @Override
    public Maze generate(int height, int width) {
        validateDimension(height, width);

        int actualHeight = height % 2 == 0 ? height + 1 : height;
        int actualWidth = width % 2 == 0 ? width + 1 : width;

        grid = initializeGrid(actualHeight, actualWidth);
        stack.clear();

        Coordinate startCoordinate = generateRandomCoordinateWithinBounds(actualHeight, actualWidth);
        stack.push(startCoordinate);
        generateWithBacktrackerAlgorithm();

        return new Maze(actualHeight, actualWidth, grid);
    }

    private void generateWithBacktrackerAlgorithm() {
        while (!stack.empty()) {
            Coordinate current = stack.peek();
            grid[current.row()][current.col()] = Cell.PASSAGE;
            processNeighbors(current);
        }
    }

    private void processNeighbors(Coordinate current) {
        for (Direction direction : getNeighbors()) {
            Coordinate neighbor = getNeighborFromDirection(current, direction);

            if (isValidCoordinate(grid, neighbor) && isUnvisited(grid, neighbor)) {
                connectWithNeighbor(grid, neighbor, direction);
                stack.push(neighbor);
                return;
            }
        }

        stack.pop();
    }
}
