package edu.hw6.task2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CloningUtilsTest {
    private static final String FILE_NAME = "dummyFile";
    private static final String EXTENSION = ".txt";
    private static final Path DUMMY_FILE = Path.of(FILE_NAME + EXTENSION);

    @BeforeAll
    static void createDummyFile() {
        try {
            Files.createFile(DUMMY_FILE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Проверка, что при передаче несуществуещего пути файл не создается и возвращается null")
    void invalidPath() {
        // given
        Path path = Path.of("somethingweird.md5");

        // when
        Path copy = CloningUtils.cloneFile(path);

        // then
        assertThat(copy).isNull();
    }

    @Test
    @DisplayName("Проверка создания единичной копии")
    void singleCopyCreation() {
        // when
        Path copy = CloningUtils.cloneFile(DUMMY_FILE);

        // then
        assertThat(copy).isNotNull();
        assertThat(copy.toString()).isEqualTo(FILE_NAME + " — копия" + EXTENSION);
        deleteFile(copy);
    }

    @Test
    @DisplayName("Проверка создания трех копий")
    void tripleCopyCreation() {
        // when
        Path copy1 = CloningUtils.cloneFile(DUMMY_FILE);
        Path copy2 = CloningUtils.cloneFile(DUMMY_FILE);
        Path copy3 = CloningUtils.cloneFile(DUMMY_FILE);

        // then
        assertThat(copy2).isNotNull();
        assertThat(copy3).isNotNull();
        assertThat(copy2.toString()).isEqualTo(FILE_NAME + " — копия (2)" + EXTENSION);
        assertThat(copy3.toString()).isEqualTo(FILE_NAME + " — копия (3)" + EXTENSION);
        deleteFile(copy1);
        deleteFile(copy2);
        deleteFile(copy3);
    }

    @AfterAll
    static void deleteDummyFile() {
        deleteFile(DUMMY_FILE);
    }

    static void deleteFile(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
