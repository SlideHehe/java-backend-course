package edu.hw9.task3;

import edu.project2.mazecomponents.Coordinate;
import edu.project2.mazecomponents.Direction;
import edu.project2.mazecomponents.Maze;
import edu.project2.solvers.Solver;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import static edu.project2.solvers.SolverUtils.isValidMove;
import static edu.project2.solvers.SolverUtils.validateSolverArgs;

public class MultithreadedBacktrackerSolver implements Solver {
    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        validateSolverArgs(maze, start, end);

        Set<Coordinate> path = new LinkedHashSet<>();

        try (ForkJoinPool forkJoinPool = ForkJoinPool.commonPool()) {
            forkJoinPool.invoke(new SolverTask(maze, start, end, path));
        }

        return path.stream().toList();
    }

    private static class SolverTask extends RecursiveTask<Boolean> {
        private final Maze maze;
        private final Coordinate current;
        private final Coordinate end;
        private final Set<Coordinate> path;

        SolverTask(Maze maze, Coordinate current, Coordinate end, Set<Coordinate> path) {
            this.maze = maze;
            this.current = current;
            this.end = end;
            this.path = path;
        }

        @Override
        protected Boolean compute() {
            path.add(current);

            if (current.equals(end)) {
                return true;
            }

            List<SolverTask> tasks = new ArrayList<>();

            for (Direction direction : Direction.values()) {
                int newRow = current.row() + direction.rowOffset();
                int newCol = current.col() + direction.colOffset();

                Coordinate next = new Coordinate(newRow, newCol);

                if (isValidMove(maze, next) && !path.contains(next)) {
                    SolverTask task = new SolverTask(maze, next, end, new LinkedHashSet<>(path));
                    tasks.add(task);
                    task.fork();
                }
            }

            for (SolverTask task : tasks) {
                if (task.join()) {
                    path.addAll(task.path);
                    return true;
                }
            }

            path.clear();
            return false;
        }
    }
}
