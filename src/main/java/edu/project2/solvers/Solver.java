package edu.project2.solvers;

import edu.project2.mazecomponents.Coordinate;
import edu.project2.mazecomponents.Maze;
import java.util.List;

public interface Solver {
    List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end);
}
