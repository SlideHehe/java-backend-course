package edu.project3;

import edu.project3.cliargs.CliInput;
import edu.project3.loading.LogParser;
import edu.project3.record.LogRecord;
import edu.project3.record.Request;
import edu.project3.report.LogReport;
import edu.project3.report.ReportPrinter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.ZonedDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReportPrinterTest {
    @Test
    @DisplayName("Передача null в конструктор ReportPrinter")
    void reportPrinterNullArg() {
        // expect
        assertThatThrownBy(() -> new ReportPrinter(null))
            .isInstanceOf(NullPointerException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {
        "markdown, md",
        "adoc, adoc"
    })
    @DisplayName("Проверка, что при вызове функции создается файл")
    void writeToFileCreatesFiles(String format, String extension) {
        // given
        List<LogRecord> logRecords = List.of(
            createLogRecord("192.168.0.1", "17/May/2015:08:05:32 +0000", "/downloads/product_1", 304, 0, "-"),
            createLogRecord("93.180.71.3", "17/May/2015:08:05:32 +0000", "/downloads/product_2", 200, 4, "-")
        );
        CliInput cliInput = new CliInput("src/**/*.txt", null, null, format);
        LogReport logReport = new LogReport(logRecords, cliInput);
        ReportPrinter reportPrinter = new ReportPrinter(logReport);

        // when
        reportPrinter.writeToFile();

        // then
        Path metricsPath = Path.of("metrics." + extension);
        assertThat(Files.exists(metricsPath)).isTrue();
        try {
            assertThat(Files.readString(metricsPath)).isNotEmpty();
            Files.deleteIfExists(metricsPath);
        } catch (IOException ignored) {
        }
    }

    private static LogRecord createLogRecord(
        String remoteAddr,
        String dateTime,
        String resource,
        int status,
        long bytesSent,
        String referer
    ) {
        return new LogRecord(
            remoteAddr,
            "-",
            ZonedDateTime.parse(dateTime, LogParser.DATE_TIME_FORMATTER),
            new Request(Request.Method.GET, resource, Request.Version.HTTP11),
            status,
            bytesSent,
            referer,
            "AbobaBrowser"
        );
    }
}
