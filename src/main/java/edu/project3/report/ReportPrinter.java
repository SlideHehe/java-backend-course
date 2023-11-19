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
    private static final String MARKDOWN = "markdown";
    private static final String MD = "md";
    private static final String ADOC = "adoc";
    private static final String MARKDOWN_TWO_COLUMNS_TABLE_HEADER = "--- | ---";
    private static final String MARKDOWN_THREE_COLUMNS_TABLE_HEADER = "--- | --- | ---";
    private static final String ADOC_TABLE_HEADER = "|====";
    private static final String TABLE_SEPARATOR = "|";
    private final String titleLabel;
    private final String format;
    private final LogReport logReport;

    public ReportPrinter(LogReport logReport) {
        Objects.requireNonNull(logReport);

        String formatName = logReport.getCliInput().format();

        this.format = formatName.equals(MARKDOWN) ? MD : ADOC;
        this.titleLabel = formatName.equals(MARKDOWN) ? "###" : "===";
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

        String columnNames = "| Метрика | Значение";
        if (format.equals(MD)) {
            genInfoTable.append(columnNames).append(System.lineSeparator());
            genInfoTable.append(MARKDOWN_TWO_COLUMNS_TABLE_HEADER).append(System.lineSeparator());
        } else {
            genInfoTable.append(ADOC_TABLE_HEADER).append(System.lineSeparator());
            genInfoTable.append(columnNames).append(System.lineSeparator());
        }

        addFilesToMetrics(genInfoTable);
        addDatesToMetrics(genInfoTable);
        addNumberOfRequestsAndAverageResponseSizeToMetrics(genInfoTable);

        if (format.equals(ADOC)) {
            genInfoTable.append(ADOC_TABLE_HEADER).append(System.lineSeparator());
        }

        return genInfoTable.toString();
    }

    private void addFilesToMetrics(StringBuilder stringBuilder) {
        List<String> paths = getPathNames();
        assert paths != null;

        stringBuilder.append(TABLE_SEPARATOR)
            .append("Файл(-ы)")
            .append(TABLE_SEPARATOR)
            .append(paths.get(0))
            .append(System.lineSeparator());

        for (int i = 1; i < paths.size(); i++) {
            stringBuilder.append(TABLE_SEPARATOR)
                .append(".")
                .append(TABLE_SEPARATOR)
                .append(paths.get(i))
                .append(System.lineSeparator());
        }
    }

    private void addDatesToMetrics(StringBuilder stringBuilder) {
        stringBuilder.append(TABLE_SEPARATOR)
            .append("Начальная дата")
            .append(TABLE_SEPARATOR)
            .append(logReport.getStartDate().format(DateTimeFormatter.ISO_LOCAL_DATE))
            .append(System.lineSeparator());
        stringBuilder.append(TABLE_SEPARATOR)
            .append("Конечная дата")
            .append(TABLE_SEPARATOR)
            .append(logReport.getEndDate().format(DateTimeFormatter.ISO_LOCAL_DATE))
            .append(System.lineSeparator());
    }

    private void addNumberOfRequestsAndAverageResponseSizeToMetrics(StringBuilder stringBuilder) {
        stringBuilder.append(TABLE_SEPARATOR)
            .append("Количество запросов")
            .append(TABLE_SEPARATOR)
            .append(logReport.getNumberOfRequests())
            .append(System.lineSeparator());
        stringBuilder.append(TABLE_SEPARATOR)
            .append("Средний размер ответа")
            .append(TABLE_SEPARATOR)
            .append("%.0f".formatted(logReport.getAverageResponseSize()))
            .append("b")
            .append(System.lineSeparator());
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

        String columnNames = "| Ресурс | Количество";
        if (format.equals(MD)) {
            resourcesTable.append(columnNames).append(System.lineSeparator());
            resourcesTable.append(MARKDOWN_TWO_COLUMNS_TABLE_HEADER).append(System.lineSeparator());
        } else {
            resourcesTable.append(ADOC_TABLE_HEADER).append(System.lineSeparator());
            resourcesTable.append(columnNames).append(System.lineSeparator());
        }

        for (var entry : resources.entrySet()) {
            resourcesTable.append(TABLE_SEPARATOR)
                .append(entry.getKey())
                .append(TABLE_SEPARATOR)
                .append(entry.getValue())
                .append(System.lineSeparator());
        }

        if (format.equals(ADOC)) {
            resourcesTable.append(ADOC_TABLE_HEADER).append(System.lineSeparator());
        }

        return resourcesTable.toString();
    }

    private String generateMostCommonCodes() {
        var codes = logReport.getMostFrequentStatusCodes(LIMIT);
        StringBuilder codesTable = new StringBuilder();

        codesTable.append(titleLabel).append(" Коды ответа").append(System.lineSeparator());

        String columnNames = "| Код | Имя | Количество";
        if (format.equals(MD)) {
            codesTable.append(columnNames).append(System.lineSeparator());
            codesTable.append(MARKDOWN_THREE_COLUMNS_TABLE_HEADER).append(System.lineSeparator());
        } else {
            codesTable.append(ADOC_TABLE_HEADER).append(System.lineSeparator());
            codesTable.append(columnNames).append(System.lineSeparator());
        }

        for (var entry : codes.entrySet()) {
            codesTable.append(TABLE_SEPARATOR)
                .append(entry.getKey())
                .append(TABLE_SEPARATOR)
                .append(LookupTable.getCode(entry.getKey()))
                .append(TABLE_SEPARATOR)
                .append(entry.getValue())
                .append(System.lineSeparator());
        }

        if (format.equals(ADOC)) {
            codesTable.append(ADOC_TABLE_HEADER).append(System.lineSeparator());
        }

        return codesTable.toString();
    }
}
