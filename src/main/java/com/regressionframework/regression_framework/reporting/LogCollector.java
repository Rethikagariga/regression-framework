package com.regressionframework.regression_framework.reporting;

import com.regressionframework.regression_framework.entity.ExecutionResult;
import com.regressionframework.regression_framework.repository.ExecutionResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class LogCollector {

    @Autowired
    private ExecutionResultRepository executionResultRepository;

    // Collect and save logs for all failed tests
    public String collectFailureLogs() throws IOException {
        List<ExecutionResult> failedResults = executionResultRepository.findByResult("FAIL");

        String logDir = "logs/";
        if (!Files.exists(Paths.get(logDir))) {
            Files.createDirectories(Paths.get(logDir));
        }

        String fileName = logDir + "failure_log_" + System.currentTimeMillis() + ".txt";

        FileWriter writer = new FileWriter(fileName);
        writer.write("=== FAILURE LOG REPORT ===\n");
        writer.write("Generated at: " + LocalDateTime.now() + "\n");
        writer.write("Total Failures: " + failedResults.size() + "\n");
        writer.write("==========================\n\n");

        for (ExecutionResult result : failedResults) {
            writer.write("Test ID: " + result.getId() + "\n");
            writer.write("Test Name: " + result.getTestCase().getName() + "\n");
            writer.write("Test Type: " + result.getTestCase().getType() + "\n");
            writer.write("Target URL: " + result.getTestCase().getTargetUrl() + "\n");
            writer.write("Result: " + result.getResult() + "\n");
            writer.write("Error: " + (result.getErrorMessage() != null ? result.getErrorMessage() : "No error message") + "\n");
            writer.write("Screenshot: " + (result.getScreenshotPath() != null ? result.getScreenshotPath() : "No screenshot") + "\n");
            writer.write("Executed At: " + result.getExecutedAt() + "\n");
            writer.write("Execution Time: " + result.getExecutionTimeMs() + " ms\n");
            writer.write("--------------------------\n\n");
        }

        writer.close();
        System.out.println("Failure log saved: " + fileName);
        return fileName;
    }

    // Collect logs for a specific test case
    public String collectLogsForTestCase(Long testCaseId) throws IOException {
        List<ExecutionResult> results = executionResultRepository.findByTestCaseId(testCaseId);

        String logDir = "logs/";
        if (!Files.exists(Paths.get(logDir))) {
            Files.createDirectories(Paths.get(logDir));
        }

        String fileName = logDir + "testcase_" + testCaseId + "_log_" + System.currentTimeMillis() + ".txt";

        FileWriter writer = new FileWriter(fileName);
        writer.write("=== TEST CASE LOG ===\n");
        writer.write("Test Case ID: " + testCaseId + "\n");
        writer.write("Generated at: " + LocalDateTime.now() + "\n");
        writer.write("Total Executions: " + results.size() + "\n");
        writer.write("====================\n\n");

        for (ExecutionResult result : results) {
            writer.write("Execution ID: " + result.getId() + "\n");
            writer.write("Result: " + result.getResult() + "\n");
            writer.write("Error: " + (result.getErrorMessage() != null ? result.getErrorMessage() : "None") + "\n");
            writer.write("Executed At: " + result.getExecutedAt() + "\n");
            writer.write("Execution Time: " + result.getExecutionTimeMs() + " ms\n");
            writer.write("--------------------\n\n");
        }

        writer.close();
        System.out.println("Test case log saved: " + fileName);
        return fileName;
    }
}