package edu.hw6.task4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class QuoteUtilsTest {
    @Test
    @DisplayName("Передача null в writeBrianKernighanQuoteToFile")
    void writeBrianKernighanQuoteToFileNullPtrEx() {
        // expect
        assertThatThrownBy(() -> QuoteUtils.writeBrianKernighanQuoteToFile(null))
            .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Проверка правильности работы writeBrianKernighanQuoteToFile")
    void writeBrianKernighanQuoteToFileCheck() {
        // given
        Path path = Path.of("test.txt");

        // when
        QuoteUtils.writeBrianKernighanQuoteToFile(path);

        // then
        try {
            String actual = Files.readString(path);
            assertThat(actual).isEqualTo("Programming is learned by writing programs. ― Brian Kernighan");
            Files.delete(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
