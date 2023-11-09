package edu.hw5.task3.parsers;

import java.time.LocalDate;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class SomeDaysAgoParser implements DateParser {
    private static final Pattern PATTERN = Pattern.compile("(\\d+) day(s)? ago");

    @Override
    public boolean canParse(String date) {
        return PATTERN.matcher(date).matches();
    }

    @Override
    public Optional<LocalDate> parse(String date) {
        Matcher matcher = PATTERN.matcher(date);
        if (matcher.matches()) {
            LocalDate now = LocalDate.now();

            int days = Integer.parseInt(matcher.group(1));

            return Optional.of(now.minusDays(days));
        }

        return Optional.empty();
    }
}
