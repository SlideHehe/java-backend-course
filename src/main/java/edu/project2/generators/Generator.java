package edu.project2.generators;

import edu.project2.mazecomponents.Maze;

public interface Generator {
    Maze generate(int height, int width);
}
