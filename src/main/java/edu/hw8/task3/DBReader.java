package edu.hw8.task3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DBReader {
    private static final String DELIMITER = "\\s+";

    private DBReader() {
    }

    public static Map<String, String> readFromFile(String fileName) {
        Objects.requireNonNull(fileName);

        try (Stream<String> lineStream = Files.lines(Path.of(fileName))) {
            return lineStream
                .map(line -> line.strip().split(DELIMITER))
                .filter(pair -> pair.length >= 2)
                .collect(Collectors.toMap(pair -> pair[1], pair -> pair[0]));
        } catch (IOException e) {
            return Map.of();
        }
    }
}
