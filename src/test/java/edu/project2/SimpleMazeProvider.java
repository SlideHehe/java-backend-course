package edu.project2;

import edu.project2.mazecomponents.Cell;
import edu.project2.mazecomponents.Coordinate;
import edu.project2.mazecomponents.Maze;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import java.util.List;
import java.util.stream.Stream;

public class SimpleMazeProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
        return Stream.of(
            Arguments.of(
                new Maze(9, 9, new Cell[][] {
                    {Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL},
                    {Cell.WALL, Cell.PASSAGE, Cell.PASSAGE, Cell.PASSAGE, Cell.WALL, Cell.PASSAGE, Cell.PASSAGE, Cell.PASSAGE, Cell.WALL},
                    {Cell.WALL, Cell.WALL, Cell.WALL, Cell.PASSAGE, Cell.WALL, Cell.PASSAGE, Cell.WALL, Cell.PASSAGE, Cell.WALL},
                    {Cell.WALL, Cell.PASSAGE, Cell.PASSAGE, Cell.PASSAGE, Cell.WALL, Cell.PASSAGE, Cell.WALL, Cell.PASSAGE, Cell.WALL},
                    {Cell.WALL, Cell.PASSAGE, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.PASSAGE, Cell.WALL},
                    {Cell.WALL, Cell.PASSAGE, Cell.WALL, Cell.PASSAGE, Cell.PASSAGE, Cell.PASSAGE, Cell.WALL, Cell.PASSAGE, Cell.WALL},
                    {Cell.WALL, Cell.PASSAGE, Cell.WALL, Cell.PASSAGE, Cell.WALL, Cell.PASSAGE, Cell.WALL, Cell.PASSAGE, Cell.WALL},
                    {Cell.WALL, Cell.PASSAGE, Cell.PASSAGE, Cell.PASSAGE, Cell.WALL, Cell.PASSAGE, Cell.PASSAGE, Cell.PASSAGE, Cell.WALL},
                    {Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL}
                }),
                List.of(
                    new Coordinate(1, 1), new Coordinate(1, 2), new Coordinate(1, 3),
                    new Coordinate(2, 3),
                    new Coordinate(3, 3), new Coordinate(3, 2), new Coordinate(3, 1),
                    new Coordinate(4, 1),
                    new Coordinate(5, 1),
                    new Coordinate(6, 1),
                    new Coordinate(7, 1), new Coordinate(7, 2), new Coordinate(7, 3),
                    new Coordinate(6, 3),
                    new Coordinate(5, 3), new Coordinate(5, 4), new Coordinate(5, 5),
                    new Coordinate(6, 5),
                    new Coordinate(7, 5), new Coordinate(7, 6), new Coordinate(7, 7),
                    new Coordinate(6, 7),
                    new Coordinate(5, 7),
                    new Coordinate(4, 7),
                    new Coordinate(3, 7),
                    new Coordinate(2, 7),
                    new Coordinate(1, 7), new Coordinate(1, 6), new Coordinate(1, 5),
                    new Coordinate(2, 5),
                    new Coordinate(3, 5)
                )
            )
        );
    }
}
