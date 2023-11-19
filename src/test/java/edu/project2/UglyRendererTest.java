package edu.project2;

import edu.project2.mazecomponents.Coordinate;
import edu.project2.mazecomponents.Maze;
import edu.project2.renderers.Renderer;
import edu.project2.renderers.UglyRenderer;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.NullSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UglyRendererTest {
    private final Renderer renderer = new UglyRenderer();

    @ParameterizedTest
    @ArgumentsSource(InvalidMazesProvider.class)
    @DisplayName("Проверка вызова render с недопустимыми лабиринтами")
    void invalidMazesRender(Maze maze) {
        // expect
        assertThatThrownBy(() -> renderer.render(maze)).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("Проверка вызова render с null вместо maze")
    void nullMazeRender(Maze maze) {
        // expect
        assertThatThrownBy(() -> renderer.render(maze)).isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Проверка вызова render с лабиринтом и null сеткой")
    void nullGridRender() {
        // given
        Maze maze = new Maze(5, 5, null);

        // expect
        assertThatThrownBy(() -> renderer.render(maze)).isInstanceOf(NullPointerException.class);
    }

    @ParameterizedTest
    @ArgumentsSource(SimpleMazeProvider.class)
    @DisplayName("Проверка отрисовки лабиринта")
    void renderMaze(Maze maze) {
        // when
        String actual = renderer.render(maze);

        // then
        assertThat(actual).isEqualTo(
            """
                █████████
                █   █   █
                ███ █ █ █
                █   █ █ █
                █ █████ █
                █ █   █ █
                █ █ █ █ █
                █   █   █
                █████████
                """
        );
    }

    @ParameterizedTest
    @ArgumentsSource(SimpleMazeProvider.class)
    @DisplayName("Проверка отрисовки лабиринта с путем")
    void renderMazeWithPath(Maze maze, List<Coordinate> path) {
        // when
        String actual = renderer.render(maze, path);

        // then
        assertThat(actual).isEqualTo(
            """
                █████████
                █●●●█●●●█
                ███●█●█●█
                █●●●█●█●█
                █●█████●█
                █●█●●●█●█
                █●█●█●█●█
                █●●●█●●●█
                █████████
                """
        );
    }
}
