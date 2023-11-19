package edu.project2;

import edu.project2.generators.BacktrackerGenerator;
import edu.project2.generators.Generator;
import edu.project2.generators.HuntAndKillGenerator;
import edu.project2.mazecomponents.Cell;
import edu.project2.mazecomponents.Maze;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class GeneratorTest {

    static Stream<Generator> generatorProvider() {
        return Stream.of(
            new HuntAndKillGenerator(),
            new BacktrackerGenerator()
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 3})
    @DisplayName("Проверка generate с передачей недопустимых размеров")
    void generateInvalidDims(int dimension) {
        generatorProvider().forEach(generator ->
            // expect
            assertThatThrownBy(() -> generator.generate(dimension, dimension))
                .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 10, 20})
    @DisplayName("Проверка generate с четными размерами")
    void generateEvenDims(int dimension) {
        generatorProvider().forEach(generator -> {
            // when
            Maze maze = generator.generate(dimension, dimension);

            // then
            assertThat(maze.width()).isEqualTo(dimension + 1);
            assertThat(maze.height()).isEqualTo(dimension + 1);
        });
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 11, 21})
    @DisplayName("Проверка generate с нечетными размерами")
    void generateOddDims(int dimension) {
        generatorProvider().forEach(generator -> {
            // when
            Maze maze = generator.generate(dimension, dimension);

            // then
            assertThat(maze.width()).isEqualTo(dimension);
            assertThat(maze.height()).isEqualTo(dimension);
        });
    }

    @Test
    @DisplayName("Проверка, что созданный лабиринт содержит только стены и проходы")
    void cellTypesTest() {
        generatorProvider().forEach(generator -> {
            // when
            Maze maze = generator.generate(5, 5);
            var grid = maze.grid();

            // then
            for (Cell[] row : grid) {
                for (Cell col : row) {
                    assertThat(col).isNotNull();
                }
            }
        });
    }
}
