package edu.hw5.task1;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;

public class AvgDurationCounter {
    // 2022-03-12, 20:20
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm");
    private static final int DATES_IN_SINGLE_STRING = 2;

    private AvgDurationCounter() {
    }

    public static String getAvgSessionDuration(List<String> sessions) {
        Objects.requireNonNull(sessions);

        Duration averageDuration = getTotalDuration(sessions).dividedBy(sessions.size());

        return averageDuration.toHoursPart() + "ч " + averageDuration.toMinutesPart() + "м";
    }

    private static Duration getTotalDuration(List<String> sessions) {
        Duration totalDuration = Duration.ZERO;

        for (String session : sessions) {
            validateSingleSession(session);
            totalDuration = totalDuration.plus(getSingleSessionDuration(session));
        }

        return totalDuration;
    }

    private static void validateSingleSession(String session) {
        Objects.requireNonNull(session);

        if (session.replaceAll("\\s+", "").isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    private static Duration getSingleSessionDuration(String session) {
        String[] dates = session.split(" - ");

        LocalDateTime[] localDateTimes = new LocalDateTime[DATES_IN_SINGLE_STRING];

        for (int i = 0; i < DATES_IN_SINGLE_STRING; i++) {
            try {
                localDateTimes[i] = LocalDateTime.parse(dates[i], FORMATTER);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException();
            }
        }

        return Duration.between(localDateTimes[0], localDateTimes[1]);
    }
}
