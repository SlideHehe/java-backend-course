package edu.project2;

import edu.project2.mazecomponents.Cell;
import edu.project2.mazecomponents.Coordinate;
import edu.project2.mazecomponents.Maze;
import edu.project2.solvers.BacktrackerSolver;
import edu.project2.solvers.ShortestPathSolver;
import edu.project2.solvers.Solver;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.NullSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SolverTest {

    static Stream<Solver> solversProvider() {
        return Stream.of(
            new BacktrackerSolver(),
            new ShortestPathSolver()
        );
    }

    @ParameterizedTest
    @ArgumentsSource(InvalidMazesProvider.class)
    @DisplayName("Проверка вызова solve с недопустимыми лабиринтами")
    void invalidMazesSolve(Maze maze) { // Я не нашел способа удобно объединить ArgumentsSource и MethodSource через JUnit :(
        solversProvider().forEach(solver ->
            // expect
            assertThatThrownBy(() -> solver.solve(maze, new Coordinate(1, 1), new Coordinate(1, 1)))
                .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("Проверка вызова solve с null вместо maze")
    void nullMazeSolve(Maze maze) {
        solversProvider().forEach(solver ->
            // expect
            assertThatThrownBy(() -> solver.solve(maze, new Coordinate(1, 1), new Coordinate(1, 1)))
                .isInstanceOf(NullPointerException.class)
        );
    }

    @Test
    @DisplayName("Проверка вызова solve с лабиринтом и null сеткой")
    void nullGridSolve() {
        solversProvider().forEach(solver -> {
            // given
            Maze maze = new Maze(5, 5, null);

            // expect
            assertThatThrownBy(() -> solver.solve(maze, new Coordinate(1, 1), new Coordinate(1, 1))).isInstanceOf(
                NullPointerException.class);
        });
    }

    @ParameterizedTest
    @ArgumentsSource(SimpleMazeProvider.class)
    @DisplayName("Проверка вызова solve с точками в стенах")
    void coordinatesInWallsSolve(Maze maze) {
        solversProvider().forEach(solver -> {
            // expect
            assertThatThrownBy(() -> solver.solve(maze, new Coordinate(0, 0), new Coordinate(1, 1)))
                .isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> solver.solve(maze, new Coordinate(1, 1), new Coordinate(0, 0)))
                .isInstanceOf(IllegalArgumentException.class);
        });
    }

    @ParameterizedTest
    @ArgumentsSource(SimpleMazeProvider.class)
    @DisplayName("Проверка вызова solve с null вместо координат")
    void nullCoordinatesSolve(Maze maze) {
        solversProvider().forEach(solver ->
            // expect
            assertThatThrownBy(() -> solver.solve(maze, null, null)).isInstanceOf(NullPointerException.class)
        );
    }

    @ParameterizedTest
    @ArgumentsSource(SimpleMazeProvider.class)
    @DisplayName("Проверка успешного нахождения пути")
    void solveSuccess(Maze maze, List<Coordinate> path) {
        solversProvider().forEach(solver -> {
            // when
            var actualPath = solver.solve(maze, new Coordinate(1, 1), new Coordinate(3, 5));

            // then
            assertThat(actualPath).isEqualTo(path);
        });
    }

    @Test
    @DisplayName("Проверка нахождения пути в лабиринте без реального пути")
    void solveFail() {
        solversProvider().forEach(solver -> {
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
            var actual = solver.solve(maze, start, end);

            // then
            assertThat(actual).isEmpty();
        });
    }
}
