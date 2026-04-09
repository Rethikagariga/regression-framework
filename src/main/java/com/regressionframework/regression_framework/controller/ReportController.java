package com.regressionframework.regression_framework.controller;

import com.regressionframework.regression_framework.reporting.LogCollector;
import com.regressionframework.regression_framework.reporting.ReportGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private ReportGenerator reportGenerator;

    @Autowired
    private LogCollector logCollector;

    // Generate HTML report
    @GetMapping("/generate/html")
    public ResponseEntity<String> generateHtmlReport() {
        try {
            String fileName = reportGenerator.generateHtmlReport();
            return ResponseEntity.ok("HTML Report generated successfully: " + fileName);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error generating HTML report: " + e.getMessage());
        }
    }

    // Generate CSV report
    @GetMapping("/generate/csv")
    public ResponseEntity<String> generateCsvReport() {
        try {
            String fileName = reportGenerator.generateCsvReport();
            return ResponseEntity.ok("CSV Report generated successfully: " + fileName);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error generating CSV report: " + e.getMessage());
        }
    }

    // Generate JUnit XML report
    @GetMapping("/generate/junit")
    public ResponseEntity<String> generateJUnitReport() {
        try {
            String fileName = reportGenerator.generateJUnitReport();
            return ResponseEntity.ok("JUnit Report generated successfully: " + fileName);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error generating JUnit report: " + e.getMessage());
        }
    }

    // Collect failure logs
    @PostMapping("/logs/collect")
    public ResponseEntity<String> collectFailureLogs() {
        try {
            String fileName = logCollector.collectFailureLogs();
            return ResponseEntity.ok("Failure logs collected successfully: " + fileName);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error collecting logs: " + e.getMessage());
        }
    }

    // Collect logs for specific test case
    @PostMapping("/logs/collect/{testCaseId}")
    public ResponseEntity<String> collectLogsForTestCase(@PathVariable Long testCaseId) {
        try {
            String fileName = logCollector.collectLogsForTestCase(testCaseId);
            return ResponseEntity.ok("Logs collected for test case " + testCaseId + ": " + fileName);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error collecting logs: " + e.getMessage());
        }
    }
}