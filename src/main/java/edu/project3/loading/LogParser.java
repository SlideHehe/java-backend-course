package edu.project3.loading;

import edu.project3.record.LogRecord;
import edu.project3.record.Request;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogParser {
    private static final Pattern PATTERN = Pattern.compile(
        "^(.*) - (.+) \\[(.+)] \"([A-Z]+) (.+) (HTTP/\\d.\\d)\" (\\d{3}) (\\d+) \"(.+)\" \"(.+)\"$"
    );
    public static final DateTimeFormatter DATE_TIME_FORMATTER =
        DateTimeFormatter.ofPattern("dd/LLL/yyyy:HH:mm:ss Z").withLocale(Locale.ENGLISH);
    private static final int REMOTE_ADDR_GROUP = 1;
    private static final int REMOTE_USER_GROUP = 2;
    private static final int TIME_LOCAL_GROUP = 3;
    private static final int REQUEST_METHOD_GROUP = 4;
    private static final int REQUEST_RESOURCE_GROUP = 5;
    private static final int REQUEST_VERSION_GROUP = 6;
    private static final int STATUS_CODE_GROUP = 7;
    private static final int BODY_BYTES_SENT_GROUP = 8;
    private static final int HTTP_REFERER_GROUP = 9;
    private static final int HTTP_USER_AGENT_GROUP = 10;

    private LogParser() {
    }

    public static LogRecord parseLog(String logString) {
        if (Objects.isNull(logString) || logString.isBlank()) {
            return null;
        }

        Matcher matcher = PATTERN.matcher(logString);

        if (!matcher.matches()) {
            return null;
        }

        return parseGroups(matcher);
    }

    private static LogRecord parseGroups(Matcher matcher) {
        String remoteAddr = matcher.group(REMOTE_ADDR_GROUP);
        String remoteUser = matcher.group(REMOTE_USER_GROUP);
        ZonedDateTime timeLocal = ZonedDateTime.parse(matcher.group(TIME_LOCAL_GROUP), DATE_TIME_FORMATTER);
        Request request = new Request(
            Request.Method.valueOf(matcher.group(REQUEST_METHOD_GROUP)),
            matcher.group(REQUEST_RESOURCE_GROUP),
            Request.Version.fromString(matcher.group(REQUEST_VERSION_GROUP))
        );
        int statusCode = Integer.parseInt(matcher.group(STATUS_CODE_GROUP));
        long bytesSent = Long.parseLong(matcher.group(BODY_BYTES_SENT_GROUP));
        String httpReferer = matcher.group(HTTP_REFERER_GROUP);
        String httpUserAgent = matcher.group(HTTP_USER_AGENT_GROUP);

        return new LogRecord(
            remoteAddr,
            remoteUser,
            timeLocal,
            request,
            statusCode,
            bytesSent,
            httpReferer,
            httpUserAgent
        );
    }
}
