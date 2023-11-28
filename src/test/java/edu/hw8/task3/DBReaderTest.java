package edu.hw8.task3;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DBReaderTest {

    @Test
    @DisplayName("Чтение пар значений из файла")
    void readFromFile() throws IOException {
        // given
        Path tempFile = Files.createTempFile("tmp", ".txt");
        String data = """
            key1 value1
            key2 value2
            key3 value3
            """;
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile.toFile()));
        writer.write(data);
        writer.close();
        Map<String, String> expected = new HashMap<>();
        expected.put("value1", "key1");
        expected.put("value2", "key2");
        expected.put("value3", "key3");

        // when
        Map<String, String> result = DBReader.readFromFile(tempFile.toString());

        // then
        assertThat(result).isEqualTo(expected);
        Files.delete(tempFile);
    }

    @Test
    @DisplayName("Чтение пустого файла")
    void readFromEmptyFile() throws IOException {
        // given
        Path tempFile = Files.createTempFile("tmp", ".txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile.toFile()));
        writer.close();
        Map<String, String> expected = new HashMap<>();

        // when
        Map<String, String> result = DBReader.readFromFile(tempFile.toString());

        // then
        assertThat(result).isEqualTo(expected);
        Files.delete(tempFile);
    }

    @Test
    @DisplayName("Передача null в readFromFile")
    void readFromNull() {
        // expect
        assertThatThrownBy(() -> DBReader.readFromFile(null)).isInstanceOf(NullPointerException.class);
    }

}
