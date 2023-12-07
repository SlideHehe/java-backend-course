package edu.hw9.task3;

import edu.project2.InvalidMazesProvider;
import edu.project2.SimpleMazeProvider;
import edu.project2.mazecomponents.Cell;
import edu.project2.mazecomponents.Coordinate;
import edu.project2.mazecomponents.Maze;
import edu.project2.solvers.Solver;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MultithreadedBacktrackerSolverTest {
    @ParameterizedTest
    @ArgumentsSource(InvalidMazesProvider.class)
    @DisplayName("Проверка вызова solve с недопустимыми лабиринтами")
    void invalidMazesSolve(Maze maze) {
        assertThatThrownBy(() ->
            new MultithreadedBacktrackerSolver().solve(maze, new Coordinate(1, 1), new Coordinate(1, 1))
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Проверка вызова solve с null вместо maze")
    void nullMazeSolve() {
        // expect
        assertThatThrownBy(() ->
            new MultithreadedBacktrackerSolver().solve(null, new Coordinate(1, 1), new Coordinate(1, 1))
        ).isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Проверка вызова solve с лабиринтом и null сеткой")
    void nullGridSolve() {
        // given
        Maze maze = new Maze(5, 5, null);

        // expect
        assertThatThrownBy(() ->
            new MultithreadedBacktrackerSolver().solve(maze, new Coordinate(1, 1), new Coordinate(1, 1))
        ).isInstanceOf(NullPointerException.class);
    }

    @ParameterizedTest
    @ArgumentsSource(SimpleMazeProvider.class)
    @DisplayName("Проверка вызова solve с начальными точками в стенах")
    void coordinatesInWallsSolve(Maze maze) {
        // given
        Solver solver = new MultithreadedBacktrackerSolver();

        // expect
        assertThatThrownBy(() -> solver.solve(maze, new Coordinate(0, 0), new Coordinate(1, 1)))
            .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> solver.solve(maze, new Coordinate(1, 1), new Coordinate(0, 0)))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ArgumentsSource(SimpleMazeProvider.class)
    @DisplayName("Проверка вызова solve с null вместо координат")
    void nullCoordinatesSolve(Maze maze) {
        // expect
        assertThatThrownBy(() ->
            new MultithreadedBacktrackerSolver().solve(maze, null, null)
        ).isInstanceOf(NullPointerException.class);
    }

    @ParameterizedTest
    @ArgumentsSource(SimpleMazeProvider.class)
    @DisplayName("Проверка успешного нахождения пути")
    void solveSuccess(Maze maze, List<Coordinate> path) {
        // when
        var actualPath = new MultithreadedBacktrackerSolver().solve(maze, new Coordinate(1, 1), new Coordinate(3, 5));

        // then
        assertThat(actualPath).isEqualTo(path);
    }

    @Test
    @DisplayName("Проверка нахождения пути в лабиринте без реального пути")
    void solveFail() {
        // given
        Maze maze = new Maze(5, 5, new Cell[][] {
            {Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL},
            {Cell.WALL, Cell.PASSAGE, Cell.WALL, Cell.WALL, Cell.WALL},
            {Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL},
            {Cell.WALL, Cell.WALL, Cell.WALL, Cell.PASSAGE, Cell.WALL},
            {Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL}
        });
        Coordinate start = new Coordinate(1, 1);
        Coordinate end = new Coordinate(3, 3);

        // when
        var actual = new MultithreadedBacktrackerSolver().solve(maze, start, end);

        // then
        assertThat(actual).isEmpty();
    }
}
