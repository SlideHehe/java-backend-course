package edu.project2.solvers;

import edu.project2.mazecomponents.Coordinate;
import edu.project2.mazecomponents.Direction;
import edu.project2.mazecomponents.Maze;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import static edu.project2.solvers.SolverUtils.isValidMove;
import static edu.project2.solvers.SolverUtils.validateGenerateArgs;

public class BacktrackerSolver implements Solver {
    private final Stack<Coordinate> stack = new Stack<>();

    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        validateGenerateArgs(maze, start, end);

        stack.clear();
        List<Coordinate> path = new ArrayList<>();
        boolean[][] visited = new boolean[maze.height()][maze.width()];

        stack.push(start);

        while (!stack.isEmpty()) {
            if (isSolutionFound(end)) {
                path.addAll(stack);
                break;
            }
            processCell(maze, visited);
        }

        return path;
    }

    private boolean isSolutionFound(Coordinate end) {
        Coordinate current = stack.peek();
        return current.equals(end);
    }

    private void processCell(Maze maze, boolean[][] visited) {
        Coordinate current = stack.peek();

        visited[current.row()][current.col()] = true;
        processNeighbors(maze, current, visited);
    }

    private void processNeighbors(Maze maze, Coordinate current, boolean[][] visited) {
        for (Direction direction : Direction.values()) {
            int newRow = current.row() + direction.rowOffset();
            int newCol = current.col() + direction.colOffset();

            Coordinate next = new Coordinate(newRow, newCol);

            if (isValidMove(maze, next) && !visited[newRow][newCol]) {
                stack.push(next);
                return;
            }
        }

        stack.pop();
    }
}
