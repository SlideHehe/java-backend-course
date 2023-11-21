package edu.project3;

import edu.project3.cliargs.CliInput;
import edu.project3.cliargs.CliInputParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CliInputParserTest {

    @Test
    @DisplayName("Передача верных аргументов, без начальной и конечной дат")
    void parseValidInputWithoutDates() {
        // given
        String[] args = {"-p", "path/to/file", "-f", "2023-01-01", "-t", "2023-02-01", "-fmt", "markdown"};

        // when
        CliInput cliInput = CliInputParser.parse(args);

        // then
        assertThat(cliInput.path()).isEqualTo("path/to/file");
        assertThat(cliInput.from()).isNotNull();
        assertThat(cliInput.to()).isNotNull();
        assertThat(cliInput.format()).isEqualTo("markdown");
    }

    @Test
    @DisplayName("Передача null вместо входных аргументов")
    void parseNullInput() {
        // expect
        assertThatThrownBy(() -> CliInputParser.parse(null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Missing --path arg");
    }

    @Test
    @DisplayName("Передача аргументов без пути")
    void parseInputWithoutPath() {
        // given
        String[] args = {"-f", "2023-01-01"};

        // expect
        assertThatThrownBy(() -> CliInputParser.parse(args))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Missing --path arg");
    }

    @Test
    @DisplayName("Передача даты в неверном формате")
    void parseDateInInvalidFormat() {
        // given
        String[] args = {"-p", "path/to/file", "-f", "invalid-date-format"};

        // expect
        assertThatThrownBy(() -> CliInputParser.parse(args))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Date was provided in wrong format");
    }

    @Test
    @DisplayName("Передача неизвестного аргум")
    void parseUnknownArg() {
        // given
        String[] args = {"-p", "path/to/file", "-invalid"};

        // expect
        assertThatThrownBy(() -> CliInputParser.parse(args))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Unknown argument");
    }
}
