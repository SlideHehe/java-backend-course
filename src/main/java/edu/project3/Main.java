package edu.project3;

import edu.project3.cliargs.CliInput;
import edu.project3.cliargs.CliInputParser;
import edu.project3.loading.LogLoader;
import edu.project3.record.LogRecord;
import edu.project3.report.LogReport;
import edu.project3.report.ReportPrinter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Main {
    private Main() {
    }

    public static void main(String[] args) {
        var arr = new String[] {"-p",
//            "https://raw.githubusercontent.com/elastic/examples/master/Common%20Data%20Formats/nginx_logs/nginx_logs",
            "src/main/resources/*.txt",
            "-fmt", "adoc"
        };

        CliInput cliInput = CliInputParser.parse(arr);

        List<LogRecord> logs = LogLoader.loadLogs(cliInput);

        LogReport logReport = new LogReport(logs, cliInput);

        ReportPrinter reportPrinter = new ReportPrinter(logReport);

        reportPrinter.writeToFile();
    }
}
