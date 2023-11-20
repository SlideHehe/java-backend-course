package edu.hw5.task3.parsers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.Optional;

public final class DateFormatParser implements DateParser {
    private final DateTimeFormatter dateTimeFormatter;

    public DateFormatParser(String pattern) {
        Objects.requireNonNull(pattern);
        dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
    }

    @Override
    public boolean canParse(String date) {
        if (Objects.isNull(date)) {
            return false;
        }

        try {
            dateTimeFormatter.parse(date);
            return true;
        } catch (DateTimeParseException ignored) {
            return false;
        }
    }

    @Override
    public Optional<LocalDate> parse(String date) {
        if (Objects.isNull(date)) {
            return Optional.empty();
        }

        try {
            LocalDate localDate = LocalDate.parse(date, dateTimeFormatter);
            return Optional.of(localDate);
        } catch (DateTimeParseException ignored) {
            return Optional.empty();
        }
    }
}
