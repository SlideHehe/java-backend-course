package edu.hw9.task2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FilesUtilsTest {
    @Test
    @DisplayName("Проверка поиска папок с n-ым количеством файлов")
    void directoriesWithManyFiles() throws IOException {
        // given
        Path root = Files.createTempDirectory("root");
        Path dir1 = Files.createDirectory(root.resolve("dir1"));
        Path dir2 = Files.createDirectory(root.resolve("dir2"));
        Files.createFile(dir1.resolve("test1.txt"));
        Files.createFile(dir1.resolve("test2.txt"));
        Files.createFile(dir2.resolve("test1.txt"));

        // when
        List<Path> directoriesWithMultipleFiles = FilesUtils.getDirectoriesWithManyFiles(root, 2);

        // then
        assertThat(directoriesWithMultipleFiles)
            .hasSize(1)
            .contains(dir1)
            .doesNotContain(dir2);
    }

    @Test
    @DisplayName("Проверка поиска папок с n-ым количеством файлов с null вместо root")
    void directoriesWithManyFilesNullRoot() {
        // expect
        assertThatThrownBy(() -> FilesUtils.getDirectoriesWithManyFiles(null, 5))
            .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Проверка поиска папок с n-ым количеством файлов с недопустимым количеством файлов")
    void directoriesWithManyFilesZeroNum() {
        // expect
        assertThatThrownBy(() -> FilesUtils.getDirectoriesWithManyFiles(Path.of("."), 0))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Illegal number of files (Can't be less than 1)");
    }

    @Test
    @DisplayName("Проверка поиска файлов по расширению")
    void filesByExtension() throws IOException {
        // given
        Path root = Files.createTempDirectory("root");
        Path dir1 = Files.createDirectory(root.resolve("dir1"));
        Path dir2 = Files.createDirectory(root.resolve("dir2"));
        Files.createFile(dir1.resolve("test1.txt"));
        Files.createFile(dir1.resolve("test2.txt"));
        Files.createFile(dir2.resolve("test1.txt"));
        Files.createFile(dir2.resolve("test2.csv"));

        // when
        List<Path> filesWithExtension = FilesUtils.getFilesByExtension(root, "txt");

        // then
        assertThat(filesWithExtension)
            .hasSize(3)
            .contains(dir1.resolve("test1.txt"))
            .contains(dir1.resolve("test2.txt"))
            .contains(dir2.resolve("test1.txt"));
    }

    @Test
    @DisplayName("Проверка поиска файлов по расширению c null вместо root")
    void filesByExtensionNullRoot() {
        // expect
        assertThatThrownBy(() -> FilesUtils.getFilesByExtension(null, "a"))
            .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Проверка поиска файлов по расширению с null вместо extension")
    void filesByExtensionExtensionNull() {
        // expect
        assertThatThrownBy(() -> FilesUtils.getFilesByExtension(Path.of("."), null))
            .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Проверка поиска файлов больше чем n байт")
    void filesLargerThan() throws IOException {
        // given
        Path root = Files.createTempDirectory("root");
        Path dir1 = Files.createDirectory(root.resolve("dir1"));
        Path dir2 = Files.createDirectory(root.resolve("dir2"));
        Path file1 = Files.createFile(dir1.resolve("test1.txt"));
        Path file2 = Files.createFile(dir1.resolve("test2.txt"));
        Path file3 = Files.createFile(dir2.resolve("test1.txt"));
        Path file4 = Files.createFile(dir2.resolve("test2.csv"));
        Files.writeString(file1, "a".repeat(101));
        Files.writeString(file2, "a".repeat(1));
        Files.writeString(file3, "a".repeat(100));
        Files.writeString(file4, "a".repeat(150));

        // when
        List<Path> filesLargerThan = FilesUtils.getFilesLargerThan(root, 100);

        // then
        assertThat(filesLargerThan)
            .hasSize(2)
            .contains(file1)
            .contains(file4);
    }

    @Test
    @DisplayName("Проверка поиска файлов больше чем n байт с null вместо root")
    void filesLargerThanNullRoot() {
        // expect
        assertThatThrownBy(() -> FilesUtils.getFilesLargerThan(null, 5))
            .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Проверка поиска файлов больше чем n байт c недопустимым размером")
    void filesLargerThanNegativeSize() {
        // expect
        assertThatThrownBy(() -> FilesUtils.getFilesLargerThan(Path.of("."), -1))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("File size can't be less than 0");
    }

    @Test
    @DisplayName("Проверка поиска файлов меньше чем n байт")
    void filesLessThan() throws IOException {
        // given
        Path root = Files.createTempDirectory("root");
        Path dir1 = Files.createDirectory(root.resolve("dir1"));
        Path dir2 = Files.createDirectory(root.resolve("dir2"));
        Path file1 = Files.createFile(dir1.resolve("test1.txt"));
        Path file2 = Files.createFile(dir1.resolve("test2.txt"));
        Path file3 = Files.createFile(dir2.resolve("test1.txt"));
        Path file4 = Files.createFile(dir2.resolve("test2.csv"));
        Files.writeString(file1, "a".repeat(100));
        Files.writeString(file2, "a".repeat(1));
        Files.writeString(file3, "a".repeat(99));
        Files.writeString(file4, "a".repeat(150));

        // when
        List<Path> filesLessThan = FilesUtils.getFilesLessThan(root, 100);

        // then
        assertThat(filesLessThan)
            .hasSize(2)
            .contains(file2)
            .contains(file3);
    }

    @Test
    @DisplayName("Проверка поиска файлов меньше чем n байт с null вместо root")
    void filesLessThanNullRoot() {
        // expect
        assertThatThrownBy(() -> FilesUtils.getFilesLessThan(null, 5))
            .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Проверка поиска файлов меньше чем n байт c недопустимым размером")
    void filesLessThanNegativeSize() {
        // expect
        assertThatThrownBy(() -> FilesUtils.getFilesLessThan(Path.of("."), -1))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("File size can't be less than 0");
    }
}
