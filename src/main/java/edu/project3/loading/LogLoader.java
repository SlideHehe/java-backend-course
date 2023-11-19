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
import java.util.stream.Stream;

public class LogLoader {
    private static final String ERROR_MSG_FROM_URI = "Unable to get logs from URI";
    private static final String ERROR_MSG_FROM_GLOB = "Unable to get logs from glob";

    public static List<LogRecord> loadLogs(CliInput cliInput) {
        Stream<LogRecord> logRecordStream;

        if (cliInput.path().startsWith("http")) {
            URI uri = URI.create(cliInput.path());
            logRecordStream = loadLogsFromUri(uri);
        } else {
            logRecordStream = loadLogsFromGlob(cliInput.path());
        }

        return logRecordStream
            .filter(Objects::nonNull)
            .filter(logRecord -> filterWithDate(logRecord, cliInput))
            .toList();
    }

    private static Stream<LogRecord> loadLogsFromUri(URI uri) {
        HttpRequest httpRequest = HttpRequest.newBuilder()
            .uri(uri)
            .GET()
            .build();

        try (HttpClient httpClient = HttpClient.newHttpClient()) {
            HttpResponse<String> resp = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            String logs = resp.body();

            return Arrays.stream(logs.split("\n"))
                .map(LogParser::parseLog);

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(ERROR_MSG_FROM_URI);
        }
    }

    private static Stream<LogRecord> loadLogsFromGlob(String glob) {
        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:" + glob);
        try (Stream<Path> walkedFiles = Files.walk(Paths.get(""))) {
            return walkedFiles
                .filter(pathMatcher::matches)
                .flatMap(LogLoader::loadLogsFromFile);
        } catch (IOException e) {
            throw new RuntimeException(ERROR_MSG_FROM_GLOB);
        }
    }

    private static Stream<LogRecord> loadLogsFromFile(Path path) {
        try (Stream<String> lines = Files.lines(path)) {
            return lines
                .map(LogParser::parseLog);

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
