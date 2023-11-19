package edu.project3.report;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class ReportPrinter {
    private static final int LIMIT = 3;
    private static final String MARKDOWN_TWO_COLUMNS_TABLE_HEADER = "--- | ---";
    private static final String MARKDOWN_THREE_COLUMNS_TABLE_HEADER = "--- | --- | ---";
    private static final String ADOC_TABLE_HEADER = "|====";
    private static final String TABLE_SEPARATOR = "|";
    private final String titleLabel;
    private final String format;
    private final LogReport logReport;

    public ReportPrinter(LogReport logReport) {
        Objects.requireNonNull(logReport);

        String format = logReport.getCliInput().format();

        this.format = format.equals("markdown") ? "md" : "adoc";
        this.titleLabel = format.equals("markdown") ? "###" : "===";
        this.logReport = logReport;
    }

    public void writeToFile() {
        Path metrics = Path.of("metrics." + format);
        try {
            Files.writeString(metrics, generateContent());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateContent() {
        return generateGeneralInfo() + generateMostRequestedResources() + generateMostCommonCodes();
    }

    private String generateGeneralInfo() {
        StringBuilder genInfoTable = new StringBuilder();

        genInfoTable.append(titleLabel).append(" Общая информация").append(System.lineSeparator());

        if (format.equals("md")) {
            genInfoTable.append("| Метрика | Значение").append(System.lineSeparator());
            genInfoTable.append(MARKDOWN_TWO_COLUMNS_TABLE_HEADER).append(System.lineSeparator());
        } else {
            genInfoTable.append(ADOC_TABLE_HEADER).append(System.lineSeparator());
            genInfoTable.append("| Метрика | Значение").append(System.lineSeparator());
        }

        List<String> paths = getPathNames();
        assert paths != null;

        genInfoTable.append(TABLE_SEPARATOR)
            .append("Файл(-ы)")
            .append(TABLE_SEPARATOR)
            .append(paths.get(0))
            .append(System.lineSeparator());

        for (int i = 1; i < paths.size(); i++) {
            genInfoTable.append(TABLE_SEPARATOR)
                .append(".")
                .append(TABLE_SEPARATOR)
                .append(paths.get(i))
                .append(System.lineSeparator());
        }

        genInfoTable.append(TABLE_SEPARATOR)
            .append("Начальная дата")
            .append(TABLE_SEPARATOR)
            .append(logReport.getStartDate().format(DateTimeFormatter.ISO_LOCAL_DATE))
            .append(System.lineSeparator());
        genInfoTable.append(TABLE_SEPARATOR)
            .append("Конечная дата")
            .append(TABLE_SEPARATOR)
            .append(logReport.getEndDate().format(DateTimeFormatter.ISO_LOCAL_DATE))
            .append(System.lineSeparator());

        genInfoTable.append(TABLE_SEPARATOR)
            .append("Количество запросов")
            .append(TABLE_SEPARATOR)
            .append(logReport.getNumberOfRequests())
            .append(System.lineSeparator());
        genInfoTable.append(TABLE_SEPARATOR)
            .append("Средний размер ответа")
            .append(TABLE_SEPARATOR)
            .append("%.0f".formatted(logReport.getAverageRequestSize()))
            .append("b")
            .append(System.lineSeparator());


        if (format.equals("adoc")) {
            genInfoTable.append(ADOC_TABLE_HEADER).append(System.lineSeparator());
        }

        return genInfoTable.toString();
    }

    private List<String> getPathNames() {
        if (logReport.getCliInput().path().contains("http")) {
            return List.of(logReport.getCliInput().path());
        }

        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:" + logReport.getCliInput().path());
        try (Stream<Path> walkedFiles = Files.walk(Paths.get(""))) {
            return walkedFiles
                .filter(path -> Files.isRegularFile(path) && pathMatcher.matches(path))
                .map(path -> path.getFileName().toString())
                .toList();
        } catch (IOException ignored) {
            return null;
        }
    }

    private String generateMostRequestedResources() {
        var resources = logReport.getMostFrequentResources(LIMIT);
        StringBuilder resourcesTable = new StringBuilder();

        resourcesTable.append(titleLabel).append(" Запрашиваемые ресурсы").append(System.lineSeparator());

        if (format.equals("md")) {
            resourcesTable.append("| Ресурс | Количество").append(System.lineSeparator());
            resourcesTable.append(MARKDOWN_TWO_COLUMNS_TABLE_HEADER).append(System.lineSeparator());
        } else {
            resourcesTable.append(ADOC_TABLE_HEADER).append(System.lineSeparator());
            resourcesTable.append("| Ресурс | Количество").append(System.lineSeparator());
        }

        for (var entry : resources.entrySet()) {
            resourcesTable.append(TABLE_SEPARATOR)
                .append(entry.getKey())
                .append(TABLE_SEPARATOR)
                .append(entry.getValue())
                .append(System.lineSeparator());
        }

        if (format.equals("adoc")) {
            resourcesTable.append(ADOC_TABLE_HEADER).append(System.lineSeparator());
        }

        return resourcesTable.toString();
    }

    private String generateMostCommonCodes() {
        var codes = logReport.getMostFrequentStatusCodes(LIMIT);
        StringBuilder codesTable = new StringBuilder();

        codesTable.append(titleLabel).append(" Коды ответа").append(System.lineSeparator());

        if (format.equals("md")) {
            codesTable.append("| Код | Имя | Количество").append(System.lineSeparator());
            codesTable.append(MARKDOWN_THREE_COLUMNS_TABLE_HEADER).append(System.lineSeparator());
        } else {
            codesTable.append(ADOC_TABLE_HEADER).append(System.lineSeparator());
            codesTable.append("| Код | Имя | Количество").append(System.lineSeparator());
        }


        for (var entry : codes.entrySet()) {
            codesTable.append(TABLE_SEPARATOR)
                .append(entry.getKey())
                .append(TABLE_SEPARATOR)
                .append(LookupTable.STATUS_CODES.get(entry.getKey()))
                .append(TABLE_SEPARATOR)
                .append(entry.getValue())
                .append(System.lineSeparator());
        }


        if (format.equals("adoc")) {
            codesTable.append(ADOC_TABLE_HEADER).append(System.lineSeparator());
        }

        return codesTable.toString();
    }
}
