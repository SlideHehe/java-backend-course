package edu.project2.renderers;

import edu.project2.mazecomponents.Coordinate;
import edu.project2.mazecomponents.Maze;
import java.util.List;

public interface Renderer {
    String render(Maze maze);

    String render(Maze maze, List<Coordinate> path);
}
