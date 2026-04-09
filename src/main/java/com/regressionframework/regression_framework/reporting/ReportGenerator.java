package com.regressionframework.regression_framework.reporting;

import com.regressionframework.regression_framework.entity.ExecutionResult;
import com.regressionframework.regression_framework.repository.ExecutionResultRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Component
public class ReportGenerator {

    @Autowired
    private ExecutionResultRepository executionResultRepository;

    // Generate HTML Report
    public String generateHtmlReport() throws IOException {
        List<ExecutionResult> results = executionResultRepository.findAll();

        String reportDir = "reports/";
        if (!Files.exists(Paths.get(reportDir))) {
            Files.createDirectories(Paths.get(reportDir));
        }

        String fileName = reportDir + "report_" + System.currentTimeMillis() + ".html";

        StringBuilder html = new StringBuilder();
        html.append("<html><head><title>Test Report</title>");
        html.append("<style>");
        html.append("body { font-family: Arial, sans-serif; margin: 20px; }");
        html.append("table { border-collapse: collapse; width: 100%; }");
        html.append("th { background-color: #4CAF50; color: white; padding: 10px; }");
        html.append("td { border: 1px solid #ddd; padding: 8px; }");
        html.append("tr:nth-child(even) { background-color: #f2f2f2; }");
        html.append(".PASS { color: green; font-weight: bold; }");
        html.append(".FAIL { color: red; font-weight: bold; }");
        html.append(".ERROR { color: orange; font-weight: bold; }");
        html.append("</style></head><body>");
        html.append("<h1>Regression Test Report</h1>");
        html.append("<p>Total Tests: " + results.size() + "</p>");

        // Count pass/fail
        long passCount = results.stream().filter(r -> "PASS".equals(r.getResult())).count();
        long failCount = results.stream().filter(r -> "FAIL".equals(r.getResult())).count();
        long errorCount = results.stream().filter(r -> "ERROR".equals(r.getResult())).count();

        html.append("<p>✅ Pass: " + passCount + " | ❌ Fail: " + failCount + " | ⚠️ Error: " + errorCount + "</p>");
        html.append("<table>");
        html.append("<tr><th>ID</th><th>Test Name</th><th>Type</th><th>Result</th><th>Execution Time</th><th>Error</th><th>Executed At</th></tr>");

        for (ExecutionResult result : results) {
            html.append("<tr>");
            html.append("<td>" + result.getId() + "</td>");
            html.append("<td>" + result.getTestCase().getName() + "</td>");
            html.append("<td>" + result.getTestCase().getType() + "</td>");
            html.append("<td class='" + result.getResult() + "'>" + result.getResult() + "</td>");
            html.append("<td>" + result.getExecutionTimeMs() + " ms</td>");
            html.append("<td>" + (result.getErrorMessage() != null ? result.getErrorMessage() : "-") + "</td>");
            html.append("<td>" + result.getExecutedAt() + "</td>");
            html.append("</tr>");
        }

        html.append("</table></body></html>");

        FileWriter writer = new FileWriter(fileName);
        writer.write(html.toString());
        writer.close();

        System.out.println("HTML Report generated: " + fileName);
        return fileName;
    }

    // Generate CSV Report
    public String generateCsvReport() throws IOException {
        List<ExecutionResult> results = executionResultRepository.findAll();

        String reportDir = "reports/";
        if (!Files.exists(Paths.get(reportDir))) {
            Files.createDirectories(Paths.get(reportDir));
        }

        String fileName = reportDir + "report_" + System.currentTimeMillis() + ".csv";

        FileWriter writer = new FileWriter(fileName);
        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                .withHeader("ID", "Test Name", "Type", "Result", "Execution Time (ms)", "Error Message", "Executed At"));

        for (ExecutionResult result : results) {
            csvPrinter.printRecord(
                    result.getId(),
                    result.getTestCase().getName(),
                    result.getTestCase().getType(),
                    result.getResult(),
                    result.getExecutionTimeMs(),
                    result.getErrorMessage() != null ? result.getErrorMessage() : "-",
                    result.getExecutedAt()
            );
        }

        csvPrinter.flush();
        csvPrinter.close();

        System.out.println("CSV Report generated: " + fileName);
        return fileName;
    }

    // Generate JUnit XML Report
    public String generateJUnitReport() throws IOException {
        List<ExecutionResult> results = executionResultRepository.findAll();

        String reportDir = "reports/";
        if (!Files.exists(Paths.get(reportDir))) {
            Files.createDirectories(Paths.get(reportDir));
        }

        String fileName = reportDir + "report_" + System.currentTimeMillis() + ".xml";

        long passCount = results.stream().filter(r -> "PASS".equals(r.getResult())).count();
        long failCount = results.stream().filter(r -> "FAIL".equals(r.getResult())).count();

        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        xml.append("<testsuite name=\"RegressionTests\" tests=\"" + results.size() + "\" failures=\"" + failCount + "\" errors=\"0\">");

        for (ExecutionResult result : results) {
            xml.append("<testcase name=\"" + result.getTestCase().getName() + "\" time=\"" + (result.getExecutionTimeMs() / 1000.0) + "\">");
            if ("FAIL".equals(result.getResult())) {
                xml.append("<failure message=\"" + (result.getErrorMessage() != null ? result.getErrorMessage() : "Test Failed") + "\"/>");
            }
            xml.append("</testcase>");
        }

        xml.append("</testsuite>");

        FileWriter writer = new FileWriter(fileName);
        writer.write(xml.toString());
        writer.close();

        System.out.println("JUnit Report generated: " + fileName);
        return fileName;
    }
}