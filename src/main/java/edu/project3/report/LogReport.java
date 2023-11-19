package edu.project3.report;

import edu.project3.cliargs.CliInput;
import edu.project3.record.LogRecord;
import edu.project3.record.Request;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LogReport {
    private final Supplier<Stream<LogRecord>> logRecordSupplier;
    private final CliInput cliInput;

    public LogReport(List<LogRecord> logRecords, CliInput cliInput) {
        Objects.requireNonNull(logRecords);
        Objects.requireNonNull(cliInput);

        this.logRecordSupplier = logRecords::stream;

        if (logRecordSupplier.get().findAny().isEmpty()) {
            throw new RuntimeException("Nothing to report");
        }

        this.cliInput = cliInput;
    }

    public CliInput getCliInput() {
        return cliInput;
    }

    public ZonedDateTime getStartDate() {
        return logRecordSupplier.get()
            .map(LogRecord::dateTime)
            .min(Comparator.naturalOrder())
            .orElse(null);
    }

    public ZonedDateTime getEndDate() {
        return logRecordSupplier.get()
            .map(LogRecord::dateTime)
            .max(Comparator.naturalOrder())
            .orElse(null);
    }

    public long getNumberOfRequests() {
        return logRecordSupplier.get().count();
    }

    public double getAverageResponseSize() {
        return logRecordSupplier.get()
            .mapToLong(LogRecord::bytesSent)
            .average()
            .orElse(0.0);
    }

    private  <T> Map<T, Long> getMostFrequentMetrics(Function<LogRecord, T> keyExtractor, long limit) {
        return logRecordSupplier.get()
            .collect(Collectors.groupingBy(keyExtractor, Collectors.counting()))
            .entrySet().stream()
            .sorted(Map.Entry.<T, Long>comparingByValue().reversed())
            .limit(limit)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (x, y) -> y, LinkedHashMap::new));
    }

    private  <T> Map<T, Long> getMostFrequentMetrics(Function<LogRecord, T> keyExtractor) {
        return getMostFrequentMetrics(keyExtractor, Long.MAX_VALUE);
    }

    public Map<Integer, Long> getMostFrequentStatusCodes(long limit) {
        return getMostFrequentMetrics(LogRecord::status, limit);
    }

    public Map<Integer, Long> getMostFrequentStatusCodes() {
        return getMostFrequentMetrics(LogRecord::status);
    }

    public Map<String, Long> getMostFrequentResources(long limit) {
        return getMostFrequentMetrics(logRecord -> logRecord.request().resource(), limit);
    }

    public Map<String, Long> getMostFrequentResources() {
        return getMostFrequentMetrics(logRecord -> logRecord.request().resource());
    }

    // Добавление доп. статистик

    public Map<Request.Method, Long> getMostFrequentRequestMethods(long limit) {
        return getMostFrequentMetrics(logRecord -> logRecord.request().requestMethod(), limit);
    }

    public Map<Request.Method, Long> getMostFrequentRequestMethods() {
        return getMostFrequentMetrics(logRecord -> logRecord.request().requestMethod());
    }

    public Map<Request.Version, Long> getMostFrequentHttpVersion(long limit) {
        return getMostFrequentMetrics(logRecord -> logRecord.request().version(), limit);
    }

    public Map<Request.Version, Long> getMostFrequentHttpVersion() {
        return getMostFrequentMetrics(logRecord -> logRecord.request().version());
    }
}
