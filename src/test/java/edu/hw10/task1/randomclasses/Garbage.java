package edu.hw10.task1.randomclasses;

import edu.hw10.task1.annotations.Max;
import edu.hw10.task1.annotations.Min;
import edu.hw10.task1.annotations.NotNull;

public record Garbage(
    @Min("1")
    @Max("3")
    int intValue,
    @Max("0.0")
    double doubleValue,

    @Min("a")
    char charValue,

    @NotNull
    String stringValue
) {
}
