package edu.project3;

import edu.project3.cliargs.CliInput;
import edu.project3.loading.LogLoader;
import edu.project3.record.LogRecord;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class LogLoaderTest {

    private static final String VALID_URI =
        "https://raw.githubusercontent.com/elastic/examples/master/Common%20Data%20Formats/nginx_logs/nginx_logs";
    private static final String VALID_GLOB = "src/**/*.log";

    @Test
    @DisplayName("Получение логов с рабочей ссылки по http")
    void loadLogsFromValidURI() {
        // given
        CliInput cliInput = new CliInput(VALID_URI, null, null, null);

        // when
        List<LogRecord> logRecords = LogLoader.loadLogs(cliInput);

        // then
        assertThat(logRecords).hasSize(51_462);
    }

    @Test
    @DisplayName("Получение логов с ссылки с пустым телом по http")
    void loadLogsFromURIWithEmptyBody() {
        // given
        CliInput cliInput = new CliInput(VALID_URI + "81852a919512", null, null, null);

        // when
        List<LogRecord> logRecords = LogLoader.loadLogs(cliInput);

        // then
        assertThat(logRecords).isEmpty();
    }

    @Test
    @DisplayName("Получение логов из существующего файла")
    void loadLogsFromValidGlob() {
        // given
        CliInput cliInput = new CliInput(VALID_GLOB, null, null, null);

        // when
        List<LogRecord> logRecords = LogLoader.loadLogs(cliInput);

        // then
        assertThat(logRecords).hasSize(10_000);
    }

    @Test
    @DisplayName("Получение логов из отсутствующего файла")
    void loadLogsFromUnknownGlob() {
        // given
        CliInput cliInput = new CliInput(VALID_GLOB + "txttxttxt", null, null, null);

        // when
        List<LogRecord> logRecords = LogLoader.loadLogs(cliInput);

        // then
        assertThat(logRecords).hasSize(0);
    }
}
