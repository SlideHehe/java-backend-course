package edu.hw5.task3.parsers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

public final class DateFormatParser implements DateParser {
    private final DateTimeFormatter dateTimeFormatter;

    public DateFormatParser(String patter) {
        dateTimeFormatter = DateTimeFormatter.ofPattern(patter);
    }

    @Override
    public boolean canParse(String date) {
        try {
            dateTimeFormatter.parse(date);
            return true;
        } catch (DateTimeParseException ignored) {
            return false;
        }
    }

    @Override
    public Optional<LocalDate> parse(String date) {
        try {
            LocalDate localDate = LocalDate.parse(date, dateTimeFormatter);
            return Optional.of(localDate);
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
    }
}
