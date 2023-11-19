package edu.project3;

import edu.project3.loading.LogParser;
import edu.project3.record.LogRecord;
import edu.project3.record.Request;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import static edu.project3.loading.LogParser.DATE_TIME_FORMATTER;
import static org.assertj.core.api.Assertions.assertThat;

class LogParserTest {
    @Test
    @DisplayName("Передача правильного формата лога")
    void parseValidString() {
        // given
        String logString =
            "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"";

        // when
        LogRecord logRecord = LogParser.parseLog(logString);

        // then
        assertThat(logRecord).isNotNull();
        assertThat(logRecord.remoteAddr()).isEqualTo("93.180.71.3");
        assertThat(logRecord.remoteUser()).isEqualTo("-");
        assertThat(logRecord.dateTime()).isEqualTo(ZonedDateTime.parse(
            "17/May/2015:08:05:32 +0000",
            DATE_TIME_FORMATTER
        ));
        assertThat(logRecord.request()).isEqualTo(new Request(
            Request.Method.GET,
            "/downloads/product_1",
            Request.Version.HTTP11
        ));
        assertThat(logRecord.status()).isEqualTo(304);
        assertThat(logRecord.bytesSent()).isEqualTo(0);
        assertThat(logRecord.referer()).isEqualTo("-");
        assertThat(logRecord.userAgent()).isEqualTo("Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Передача пустой и null строки")
    void parseNullAndEmptyLog(String log) {
        // expect
        assertThat(LogParser.parseLog(log)).isNull();
    }

    @Test
    @DisplayName("Передача недопустимого формата лога")
    void parseInvalidLog() {
        // given
        String logString =
            "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"INVALID_METHOD /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"";

        // expect
        assertThat(LogParser.parseLog(logString)).isNull();
    }
}
