package edu.hw5.task3.parsers;

import java.time.LocalDate;
import java.util.Optional;

public sealed interface DateParser permits DateFormatParser, KeyWordParser, SomeDaysAgoParser {
    boolean canParse(String date);

    Optional<LocalDate> parse(String date);
}
