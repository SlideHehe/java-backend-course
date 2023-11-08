package edu.project2.renderers;

import edu.project2.mazecomponents.Cell;
import edu.project2.mazecomponents.Coordinate;
import edu.project2.mazecomponents.Maze;
import java.util.List;
import static edu.project2.Utils.validateMaze;

public class UglyRenderer implements Renderer {
    private static final char PATH_SYMBOL = '●';
    private static final char PASSAGE_SYMBOL = ' ';
    private static final char WALL_SYMBOL = '█';

    @Override
    public String render(Maze maze) {
        validateMaze(maze);

        StringBuilder stringBuilder = new StringBuilder();
        var grid = maze.grid();

        for (int row = 0; row < maze.height(); row++) {
            for (int col = 0; col < maze.width(); col++) {
                Cell currentCell = grid[row][col];
                if (currentCell.equals(Cell.PASSAGE)) {
                    stringBuilder.append(PASSAGE_SYMBOL);
                } else if (currentCell.equals(Cell.WALL)) {
                    stringBuilder.append(WALL_SYMBOL);
                }
            }
            stringBuilder.append('\n');
        }

        return stringBuilder.toString();
    }

    @Override
    public String render(Maze maze, List<Coordinate> path) {
        StringBuilder stringBuilder = new StringBuilder(render(maze));
        int width = maze.width();

        for (Coordinate coordinate : path) {
            int row = coordinate.row();
            int col = coordinate.col();

            stringBuilder.setCharAt((row * (width + 1) + col), PATH_SYMBOL);
        }

        return stringBuilder.toString();
    }
}
