package edu.project2.solvers;

import edu.project2.mazecomponents.Coordinate;
import edu.project2.mazecomponents.Direction;
import edu.project2.mazecomponents.Maze;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import static edu.project2.solvers.SolverUtils.isValidMove;
import static edu.project2.solvers.SolverUtils.validateGenerateArgs;

public class ShortestPathSolver implements Solver {
    private final Queue<Coordinate> queue = new ArrayDeque<>();
    private final Map<Coordinate, Coordinate> parentMap = new HashMap<>();

    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        validateGenerateArgs(maze, start, end);

        queue.clear();
        parentMap.clear();

        queue.add(start);

        while (!queue.isEmpty()) {
            if (isSolutionFound(end)) {
                return reconstructPath(parentMap, start, end);
            }
            processCell(maze);
        }

        return Collections.emptyList();
    }

    private boolean isSolutionFound(Coordinate end) {
        Coordinate current = queue.peek();
        return Objects.equals(current, end);
    }

    private void processCell(Maze maze) {
        Coordinate current = queue.poll();

        assert current != null;

        addNeighborsToQueue(maze, current);
    }

    private void addNeighborsToQueue(Maze maze, Coordinate current) {
        for (Direction direction : Direction.values()) {
            int newRow = current.row() + direction.rowOffset();
            int newCol = current.col() + direction.colOffset();

            Coordinate next = new Coordinate(newRow, newCol);

            if (isValidMove(maze, next) && !parentMap.containsKey(next)) {
                queue.add(next);
                parentMap.put(next, current);
            }
        }
    }

    private List<Coordinate> reconstructPath(Map<Coordinate, Coordinate> parentMap, Coordinate start, Coordinate end) {
        List<Coordinate> path = new ArrayList<>();
        Coordinate current = end;

        while (!current.equals(start)) {
            path.add(current);
            current = parentMap.get(current);
        }

        path.add(start);
        Collections.reverse(path);

        return path;
    }
}
