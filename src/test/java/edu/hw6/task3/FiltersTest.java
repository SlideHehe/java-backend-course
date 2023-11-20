package edu.hw6.task3;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FiltersTest {
    private static final Path RESOURCES_DIR = Path.of("src", "test", "resources");
    private static final List<Path> paths = new ArrayList<>();

    @BeforeAll
    static void createSomeFiles() {
        paths.clear();

        for (int i = 1; i <= 3; i++) {
            Path path = RESOURCES_DIR.resolve("aboba" + i + ".txt");

            try {
                Files.createFile(path);
                paths.add(path);
            } catch (IOException ignored) {
            }
        }
    }

    @Test
    @DisplayName("Передача null в and")
    void andNullPtrEx() {
        // given
        AbstractFilter filter = FilterUtils.regularFile;

        // expect
        assertThatThrownBy(() -> filter.and(null)).isInstanceOf(NullPointerException.class);
    }


    @Test
    @DisplayName("Передача null в globMatches")
    void globMatchesNullPtrEx() {
        // expect
        assertThatThrownBy(() -> FilterUtils.globMatches(null)).isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Передача null в regexContains")
    void regexContainsNullPtrEx() {
        // expect
        assertThatThrownBy(() -> FilterUtils.regexContains(null)).isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Проверка фильтров largerThan, magicNumber, globMatches")
    void largerThanMagicNumberGlobMatches() {
        // given
        AbstractFilter largerThan = FilterUtils.largerThan(15_000);
        DirectoryStream.Filter<Path> filter = largerThan
            .and(FilterUtils.magicNumber(0x89, 'P', 'N', 'G'))
            .and(FilterUtils.globMatches("**/*.png"));

        // when
        List<Path> filteredPaths = new ArrayList<>();
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(RESOURCES_DIR, filter)) {
            entries.forEach(filteredPaths::add);
        } catch (IOException ignored) {
        }

        // then
        assertThat(filteredPaths).containsExactly(RESOURCES_DIR.resolve("tinkoff_bank.png"));
    }

    @Test
    @DisplayName("Проверка фильтров readable, regularFile, regexContains")
    void readableRegularFileRegexContains() {
        // given
        AbstractFilter readable = FilterUtils.readable;
        DirectoryStream.Filter<Path> filter = readable
            .and(FilterUtils.regularFile)
            .and(FilterUtils.regexContains(".*[2-3].*"));

        // when
        List<Path> filteredPaths = new ArrayList<>();
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(RESOURCES_DIR, filter)) {
            entries.forEach(filteredPaths::add);
        } catch (IOException ignored) {
        }

        // then
        assertThat(filteredPaths).doesNotContain(paths.get(0));
        assertThat(filteredPaths).containsExactly(paths.get(1), paths.get(2));
    }

    @AfterAll
    static void clearFiles() {
        try {
            for (Path path : paths) {
                Files.delete(path);
            }
        } catch (IOException ignored) {
        }
    }
}
