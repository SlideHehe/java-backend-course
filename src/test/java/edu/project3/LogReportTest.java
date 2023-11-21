package edu.project3;

import edu.project3.cliargs.CliInput;
import edu.project3.loading.LogParser;
import edu.project3.record.LogRecord;
import edu.project3.record.Request;
import edu.project3.report.LogReport;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LogReportTest {

    private static List<LogRecord> logRecords;

    @BeforeAll
    static void setUp() {
        logRecords = Arrays.asList(
            createLogRecord(
                "93.180.71.3",
                "17/May/2015:08:05:32 +0000",
                new Request(Request.Method.GET, "/downloads/product_1", Request.Version.HTTP11),
                304,
                0,
                "-"
            ),
            createLogRecord(
                "192.168.0.1",
                "18/May/2015:12:30:45 +0000",
                new Request(Request.Method.GET, "/downloads/product_2", Request.Version.HTTP10),
                200,
                1024,
                "http://donotclick.com"
            ),
            createLogRecord(
                "10.0.0.1",
                "19/May/2015:15:45:10 +0000",
                new Request(Request.Method.GET, "/downloads/product_2", Request.Version.HTTP11),
                404,
                2048,
                "-"
            ),
            createLogRecord(
                "172.16.0.1",
                "20/May/2015:20:10:23 +0000",
                new Request(Request.Method.GET, "/downloads/product_3", Request.Version.HTTP11),
                200,
                512,
                "http://donotclick.com"
            ),
            createLogRecord(
                "192.168.1.1",
                "21/May/2015:22:55:01 +0000",
                new Request(Request.Method.POST, "/downloads/product_2", Request.Version.HTTP20),
                304,
                1024,
                "http://donotclick.com"
            ),
            createLogRecord(
                "10.0.0.2",
                "22/May/2015:01:10:30 +0000",
                new Request(Request.Method.GET, "/downloads/product_1", Request.Version.HTTP11),
                200,
                2048,
                "-"
            ),
            createLogRecord(
                "172.16.0.2",
                "23/May/2015:03:25:55 +0000",
                new Request(Request.Method.POST, "/downloads/product_2", Request.Version.HTTP11),
                404,
                512,
                "http://donotclick.com"
            ),
            createLogRecord(
                "192.168.1.2",
                "24/May/2015:05:40:20 +0000",
                new Request(Request.Method.GET, "/downloads/product_3", Request.Version.HTTP11),
                200,
                1024,
                "http://donotclick.com"
            ),
            createLogRecord(
                "10.0.0.3",
                "25/May/2015:07:55:45 +0000",
                new Request(Request.Method.GET, "/downloads/product_1", Request.Version.HTTP11),
                304,
                2048,
                "-"
            ),
            createLogRecord(
                "172.16.0.3",
                "26/May/2015:10:10:10 +0000",
                new Request(Request.Method.PUT, "/downloads/product_2", Request.Version.HTTP11),
                200,
                512,
                "http://donotclick.com"
            ),
            createLogRecord(
                "192.168.1.3",
                "27/May/2015:12:25:35 +0000",
                new Request(Request.Method.DELETE, "/downloads/product_3", Request.Version.HTTP11),
                404,
                1024,
                "http://donotclick.com"
            ),
            createLogRecord(
                "10.0.0.4",
                "28/May/2015:14:40:00 +0000",
                new Request(Request.Method.GET, "/downloads/product_1", Request.Version.HTTP11),
                200,
                2048,
                "-"
            ),
            createLogRecord(
                "172.16.0.4",
                "29/May/2015:17:55:25 +0000",
                new Request(Request.Method.GET, "/downloads/product_2", Request.Version.HTTP11),
                304,
                512,
                "http://donotclick.com"
            ),
            createLogRecord(
                "192.168.1.4",
                "30/May/2015:20:10:50 +0000",
                new Request(Request.Method.GET, "/downloads/product_3", Request.Version.HTTP11),
                200,
                1024,
                "http://donotclick.com"
            )
        );
    }

    @Test
    @DisplayName("Создание объекта Log Report с пустым списком логов")
    void logReportInitWithEmptyLogsList() {
        // expect
        assertThatThrownBy(() -> new LogReport(List.of(), new CliInput("path", null, null, null)))
            .isInstanceOf(RuntimeException.class)
            .hasMessage("Nothing to report");
    }

    @Test
    @DisplayName("Получение начальной даты")
    void logReportStartDate() {
        // given
        LogReport logReport = new LogReport(logRecords, new CliInput("path", null, null, null));

        // when
        ZonedDateTime startDate = logReport.getStartDate();

        // then
        assertThat(startDate).isEqualTo(ZonedDateTime.parse("2015-05-17T08:05:32Z"));
    }

    @Test
    @DisplayName("Получение конечной даты")
    void logReportEndDate() {
        // given
        LogReport logReport = new LogReport(logRecords, new CliInput("path", null, null, null));

        // when
        ZonedDateTime endDate = logReport.getEndDate();

        // then
        assertThat(endDate).isEqualTo(ZonedDateTime.parse("2015-05-30T20:10:50Z"));
    }

    @Test
    @DisplayName("Получение количества запросов")
    void logReportNumberOfRequests() {
        // given
        LogReport logReport = new LogReport(logRecords, new CliInput("path", null, null, null));

        // when
        long numberOfRequests = logReport.getNumberOfRequests();

        // then
        assertThat(numberOfRequests).isEqualTo(14);
    }

    @Test
    @DisplayName("Получение среднего размера ответа")
    void logReportAverageResponseSize() {
        // given
        LogReport logReport = new LogReport(logRecords, new CliInput("path", null, null, null));

        // when
        int averageRequestSize = (int) logReport.getAverageResponseSize();

        // then
        assertThat(averageRequestSize).isEqualTo(1097);
    }

    @Test
    @DisplayName("Получение наиболее частых ресурсов")
    void logReportMostFrequentResources() {
        // given
        LogReport logReport = new LogReport(logRecords, new CliInput("path", null, null, null));

        // when
        Map<String, Long> mostFrequentResources = logReport.getMostFrequentResources();

        // then
        assertThat(mostFrequentResources).hasSize(3)
            .containsEntry("/downloads/product_1", 4L)
            .containsEntry("/downloads/product_2", 6L)
            .containsEntry("/downloads/product_3", 4L);
    }

    @Test
    @DisplayName("Получение наиболее частых кодов ответа")
    void logReportMostFrequentStatusCodes() {
        // given
        LogReport logReport = new LogReport(logRecords, new CliInput("path", null, null, null));

        // when
        Map<Integer, Long> mostFrequentStatusCodes = logReport.getMostFrequentStatusCodes();

        // then
        assertThat(mostFrequentStatusCodes).hasSize(3)
            .containsEntry(304, 4L)
            .containsEntry(200, 7L)
            .containsEntry(404, 3L);
    }

    @Test
    @DisplayName("Получение наиболее частых методов запроса")
    void logReportMostFrequentRequestMethods() {
        // given
        LogReport logReport = new LogReport(logRecords, new CliInput("path", null, null, null));

        // when
        Map<Request.Method, Long> mostFrequentResources = logReport.getMostFrequentRequestMethods();

        // then
        assertThat(mostFrequentResources).hasSize(4)
            .containsEntry(Request.Method.GET, 10L)
            .containsEntry(Request.Method.POST, 2L)
            .containsEntry(Request.Method.PUT, 1L)
            .containsEntry(Request.Method.DELETE, 1L);
    }

    @Test
    @DisplayName("Получение наиболее частых версий http")
    void logReportMostFrequentHttpVersion() {
        // given
        LogReport logReport = new LogReport(logRecords, new CliInput("path", null, null, null));

        // when
        Map<Request.Version, Long> mostFrequentResources = logReport.getMostFrequentHttpVersion();

        // then
        assertThat(mostFrequentResources).hasSize(3)
            .containsEntry(Request.Version.HTTP11, 12L)
            .containsEntry(Request.Version.HTTP20, 1L)
            .containsEntry(Request.Version.HTTP10, 1L);
    }

    private static LogRecord createLogRecord(
        String remoteAddr,
        String dateTime,
        Request request,
        int status,
        long bytesSent,
        String referer
    ) {
        return new LogRecord(
            remoteAddr,
            "-",
            ZonedDateTime.parse(dateTime, LogParser.DATE_TIME_FORMATTER),
            request,
            status,
            bytesSent,
            referer,
            "AbobaBrowser"
        );
    }
}
