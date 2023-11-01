package edu.hw2.task3;

import edu.hw2.task3.managers.ConnectionManager;
import edu.hw2.task3.managers.DefaultConnectionManager;
import edu.hw2.task3.managers.FaultyConnectionManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import java.util.stream.Stream;
import static org.assertj.core.api.AssertionsForClassTypes.*;

public class CommandExecutorTest {
    static Stream<Arguments> invalidConstructorArgsProvider() {
        return Stream.of(
            Arguments.of(null, 2),
            Arguments.of(new DefaultConnectionManager(), 0),
            Arguments.of(new FaultyConnectionManager(), -1)
        );
    }

    @DisplayName("Проверка недопустимых аргументов конструктора")
    @ParameterizedTest(name = "{index}) input = {0}, {1}")
    @MethodSource("invalidConstructorArgsProvider")
    void invalidConstructorArgs(ConnectionManager connectionManager, int maxAttempts) {
        // expect
        assertThatThrownBy(() -> new PopularCommandExecutor(connectionManager, maxAttempts))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("Проверка FaultyConnectionManager")
    @Test
    void checkFaultyConnectionManager() {
        // given
        ConnectionManager connectionManager = new FaultyConnectionManager();
        PopularCommandExecutor executor = new PopularCommandExecutor(
            connectionManager,
            1
        ); // При ограничении в 1 попытку, каждая вторая команда будет выкидывать ConnectionException

        // expect
        assertThatThrownBy(executor::updatePackages).isInstanceOf(ConnectionException.class);
        assertThatNoException().isThrownBy(executor::updatePackages);
        assertThatThrownBy(() -> executor.makeDir("java")).isInstanceOf(ConnectionException.class);
        assertThatNoException().isThrownBy(() -> executor.makeDir("java"));
        assertThatThrownBy(() -> executor.installPackage("ffmpeg")).isInstanceOf(ConnectionException.class);
        assertThatNoException().isThrownBy(() -> executor.installPackage("ffmpeg"));
    }

    @DisplayName("Проверка DefaultConnectionManager")
    @Test
    void checkDefaultConnectionManager() {
        // given
        ConnectionManager connectionManager = new DefaultConnectionManager();
        PopularCommandExecutor executor = new PopularCommandExecutor(
            connectionManager,
            1
        ); // При ограничении в 1 попытку, каждый второй раз будет выдаваться FaultyConnection
        // (который выбрасывает FaultyConnection каждый второй раз) -> каждый 4-ый раз должен ловится exception

        // expect
        assertThatThrownBy(executor::updatePackages).isInstanceOf(ConnectionException.class);
        assertThatNoException().isThrownBy(executor::updatePackages);
        assertThatNoException().isThrownBy(() -> executor.makeDir("java"));
        assertThatNoException().isThrownBy(() -> executor.installPackage("ffmpeg"));
        assertThatThrownBy(() -> executor.installPackage("ant")).isInstanceOf(ConnectionException.class);
    }

    @DisplayName("Проверка недопустимых строк в методах installPackage и makeDir")
    @ParameterizedTest
    @NullAndEmptySource
    void checkInvalidStrings(String input) {
        // given
        ConnectionManager connectionManager = new DefaultConnectionManager();
        PopularCommandExecutor executor = new PopularCommandExecutor(connectionManager, 5);

        // expect
        assertThatThrownBy(() -> executor.makeDir(input)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> executor.installPackage(input)).isInstanceOf(IllegalArgumentException.class);
    }
}
