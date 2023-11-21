package edu.project3.record;

import java.time.ZonedDateTime;

public record LogRecord(
    String remoteAddr,
    String remoteUser,
    ZonedDateTime dateTime,
    Request request,
    int status,
    long bytesSent,
    String referer,
    String userAgent
) {
}
