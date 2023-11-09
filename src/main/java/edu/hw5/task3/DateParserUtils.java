package edu.hw5.task3;

import edu.hw5.task3.parsers.DateFormatParser;
import edu.hw5.task3.parsers.DateParser;
import edu.hw5.task3.parsers.KeyWordParser;
import edu.hw5.task3.parsers.SomeDaysAgoParser;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class DateParserUtils {
    private static final List<DateParser> PARSERS = List.of(
        new DateFormatParser("yyyy-MM-dd"),
        new DateFormatParser("yyyy-MM-d"),
        new DateFormatParser("d/M/yyyy"),
        new DateFormatParser("d/M/yy"),
        new SomeDaysAgoParser(),
        new KeyWordParser()
    );

    private DateParserUtils() {
    }

    public static Optional<LocalDate> parseDate(String string) {
        Objects.requireNonNull(string);

        return PARSERS.stream()
            .filter(parser -> parser.canParse(string))
            .findAny()
            .flatMap(parser -> parser.parse(string));
    }
}
