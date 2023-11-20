package edu.hw6.task1;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DiskMapTest {
    private static final String FOLDER_NAME = "test";

    @Test
    @DisplayName("Получение NPE при создании мапы")
    void nullArg() {
        // expect
        assertThatThrownBy(() -> new DiskMap(null)).isInstanceOf(NullPointerException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\n", "\t"})
    @DisplayName("Получение Illegal Argument Exception при создании мапы")
    void illegalArgs(String s) {
        // expect
        assertThatThrownBy(() -> new DiskMap(s)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Проверка на создание каталога")
    void folderCreation() {
        // given
        new DiskMap(FOLDER_NAME);

        // expect
        File file = new File(FOLDER_NAME);
        assertThat(file.exists() && file.isDirectory()).isTrue();
    }

    @Test
    @DisplayName("Проверка на создание файла при добавлении элементов")
    void fileCreation() {
        // given
        DiskMap diskMap = new DiskMap(FOLDER_NAME);

        // when
        diskMap.put("test", "test");

        // then
        File file = Path.of(FOLDER_NAME, "test").toFile();
        assertThat(file.exists() && file.isFile()).isTrue();
    }

    @Test
    @DisplayName("Проверка на удаление файла из мапы")
    void fileDeletion() {
        // given
        DiskMap diskMap = new DiskMap(FOLDER_NAME);

        // when
        diskMap.put("test", "test");
        diskMap.remove("test");

        // then
        File file = Path.of(FOLDER_NAME, "test").toFile();
        assertThat(file.exists()).isFalse();
    }

    @Test
    @DisplayName("Проверка на получение значения из мапы")
    void getValue() {
        // given
        DiskMap diskMap = new DiskMap(FOLDER_NAME);

        // when
        diskMap.put("test", "test");

        // then
        assertThat(diskMap.get("test")).isEqualTo("test");
    }

    @Test
    @DisplayName("Проверка удаления всех entry в каталоге")
    void entriesClear() {
        // given
        DiskMap diskMap = new DiskMap(FOLDER_NAME);

        // when
        diskMap.clear();

        // then
        File folder = Path.of(FOLDER_NAME).toFile();
        assertThat(folder.list()).isEmpty();
    }

    @Test
    @DisplayName("Проверка подрузки элементов из существующего каталога")
    void loadingEntries() {
        // given
        DiskMap diskMap = new DiskMap(FOLDER_NAME);
        diskMap.put("test1", "test1");
        diskMap.put("test2", "test2");
        diskMap.put("test3", "test3");

        // when
        DiskMap newDiskMap = new DiskMap(FOLDER_NAME);

        // then
        assertThat(newDiskMap.size()).isEqualTo(diskMap.size());
        assertThat(newDiskMap.keySet()).isEqualTo(diskMap.keySet());
        assertThat(newDiskMap.values()).isEqualTo(diskMap.values());
    }

    @AfterAll
    static void rmFolder() {
        File folder = Path.of(FOLDER_NAME).toFile();
        File[] files = folder.listFiles();

        if (Objects.nonNull(files)) {
            for (File file : files) {
                try {
                    Files.delete(file.toPath());
                } catch (IOException ignored) {
                }
            }
        }

        try {
            Files.delete(folder.toPath());
        } catch (IOException ignored) {
        }
    }
}
