package edu.hw5.task3.parsers;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public final class KeyWordParser implements DateParser {
    private static final String TOMORROW = "tomorrow";
    private static final String YESTERDAY = "yesterday";
    private static final String TODAY = "today";
    private static final Set<String> KEY_WORDS = new HashSet<>();

    static {
        KEY_WORDS.add(TOMORROW);
        KEY_WORDS.add(YESTERDAY);
        KEY_WORDS.add(TODAY);
    }

    @Override
    public boolean canParse(String date) {
        return KEY_WORDS.contains(date);
    }

    @Override
    public Optional<LocalDate> parse(String date) {
        LocalDate now = LocalDate.now();

        switch (date) {
            case TOMORROW -> {
                return Optional.of(now.plusDays(1));
            }
            case YESTERDAY -> {
                return Optional.of(now.minusDays(1));
            }
            case TODAY -> {
                return Optional.of(now);
            }
            default -> {
                return Optional.empty();
            }
        }
    }
}
