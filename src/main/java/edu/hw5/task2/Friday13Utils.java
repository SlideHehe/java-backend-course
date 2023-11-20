package edu.hw5.task2;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Friday13Utils {
    private static final int MONTHS_IN_YEAR = 12;
    private static final int THIRTEEN = 13;
    private static final long DAYS_IN_WEEK = 7;
    private static final TemporalAdjuster NEXT_FRIDAY_13TH = TemporalAdjusters.ofDateAdjuster(date -> {
        LocalDate nextFriday = date.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));

        while (nextFriday.getDayOfMonth() != THIRTEEN) {
            nextFriday = nextFriday.plusDays(DAYS_IN_WEEK);
        }

        return nextFriday;
    });

    private Friday13Utils() {
    }

    public static List<LocalDate> getAllFriday13thInYear(int year) {
        if (year < 0) {
            throw new IllegalArgumentException();
        }

        List<LocalDate> fridays = new ArrayList<>();

        for (int month = 1; month <= MONTHS_IN_YEAR; month++) {
            LocalDate date = LocalDate.of(year, month, THIRTEEN);

            if (date.getDayOfWeek().equals(DayOfWeek.FRIDAY)) {
                fridays.add(date);
            }
        }

        return fridays;
    }

    public static LocalDate getClosestFriday13th(LocalDate date) {
        Objects.requireNonNull(date);

        return date.with(NEXT_FRIDAY_13TH);
    }
}
