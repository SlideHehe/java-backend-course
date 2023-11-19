package edu.project3.cliargs;

import java.time.ZonedDateTime;
import java.util.Objects;

public record CliInput(
    String path,
    ZonedDateTime from,
    ZonedDateTime to,
    String format
) {
    public CliInput {
        if (Objects.isNull(format) || !format.equals("adoc")) {
            format = "markdown";
        }
    }
}
