package edu.hw6.task6;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThatCode;

public class SocketScanTest {
    @Test
    @DisplayName("Проверка, что logAvailableSocketsInfo не ломается")
    void logAvailableSocketsInfo() {
        assertThatCode(SocketScan::logAvailableSocketsInfo).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Проверка, что logTakenSocketsInfo не ломается")
    void logTakenSocketsInfo() {
        assertThatCode(SocketScan::logTakenSocketsInfo).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Проверка, что logAvailableSocketsInfo не ломается")
    void logAllSocketsInfo() {
        assertThatCode(SocketScan::logAllSocketsInfo).doesNotThrowAnyException();
    }
}
