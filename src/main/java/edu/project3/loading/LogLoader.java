package edu.project3.loading;

import edu.project3.cliargs.CliInput;
import edu.project3.record.LogRecord;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LogLoader {
    private static final String ERROR_MSG_FROM_URI = "Unable to get logs from URI";
    private static final String ERROR_MSG_FROM_GLOB = "Unable to get logs from glob";

    private LogLoader() {
    }

    public static List<LogRecord> loadLogs(CliInput cliInput) {
        Stream<LogRecord> logRecordStream;

        if (cliInput.path().startsWith("http")) {
            return loadLogsFromUri(cliInput);
        }

        return loadLogsFromGlob(cliInput);
    }

    private static List<LogRecord> loadLogsFromUri(CliInput cliInput) {
        URI uri = URI.create(cliInput.path());

        HttpRequest httpRequest = HttpRequest.newBuilder()
            .uri(uri)
            .GET()
            .build();

        try (HttpClient httpClient = HttpClient.newHttpClient()) {
            HttpResponse<String> resp = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            String logs = resp.body();

            return Arrays.stream(logs.split("\n"))
                .map(LogParser::parseLog)
                .filter(Objects::nonNull)
                .filter(logRecord -> filterWithDate(logRecord, cliInput))
                .collect(Collectors.toList());

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(ERROR_MSG_FROM_URI);
        }
    }

    private static List<LogRecord> loadLogsFromGlob(CliInput cliInput) {
        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:" + cliInput.path());
        try (Stream<Path> walkedFiles = Files.walk(Paths.get(""))) {
            return walkedFiles
                .filter(pathMatcher::matches)
                .flatMap(path -> {
                    try (Stream<String> linesStream = Files.lines(path)) {
                        List<LogRecord> linesList = linesStream.map(LogParser::parseLog).toList();
                        return linesList.stream();
                    } catch (IOException e) {
                        throw new RuntimeException(ERROR_MSG_FROM_GLOB);
                    }
                })
                .filter(Objects::nonNull)
                .filter(logRecord -> filterWithDate(logRecord, cliInput))
                .toList();
        } catch (IOException e) {
            throw new RuntimeException(ERROR_MSG_FROM_GLOB);
        }
    }

    private static boolean filterWithDate(LogRecord logRecord, CliInput cliInput) {
        if (Objects.isNull(cliInput.from()) && Objects.isNull(cliInput.to())) {
            return true;
        }

        if (Objects.isNull(cliInput.from())) {
            return logRecord.dateTime().isBefore(cliInput.to());
        }

        if (Objects.isNull(cliInput.to())) {
            return logRecord.dateTime().isAfter(cliInput.from());
        }

        return logRecord.dateTime().isAfter(cliInput.from()) && logRecord.dateTime().isBefore(cliInput.to());
    }
}
