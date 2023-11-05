package edu.project2;

import edu.project2.mazecomponents.Cell;
import edu.project2.mazecomponents.Maze;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import java.util.stream.Stream;

public class InvalidMazesProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
        return Stream.of(
            Arguments.of(
                new Maze(4, 3, new Cell[][] {
                    {Cell.WALL, Cell.WALL, Cell.WALL},
                    {Cell.WALL, Cell.WALL, Cell.WALL},
                    {Cell.WALL, Cell.WALL, Cell.WALL}
                })
            ),
            Arguments.of(
                new Maze(3, 3, new Cell[][] {
                    {Cell.WALL, Cell.WALL, Cell.WALL},
                    {Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL},
                    {Cell.WALL, Cell.WALL, Cell.WALL}
                })
            ),
            Arguments.of(
                new Maze(1, 1, new Cell[][] {
                    {Cell.WALL, Cell.WALL, Cell.WALL},
                    {Cell.WALL, Cell.WALL, Cell.WALL},
                    {Cell.WALL, Cell.WALL, Cell.WALL}
                })
            )
        );
    }
}
