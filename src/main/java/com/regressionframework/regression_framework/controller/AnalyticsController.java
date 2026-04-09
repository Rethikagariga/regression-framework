package com.regressionframework.regression_framework.controller;

import com.regressionframework.regression_framework.analytics.ResultAnalytics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {

    @Autowired
    private ResultAnalytics resultAnalytics;

    // Get overall stats
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Long>> getOverallStats() {
        return ResponseEntity.ok(resultAnalytics.getOverallStats());
    }

    // Get pass rate
    @GetMapping("/passrate")
    public ResponseEntity<Map<String, Object>> getPassRate() {
        return ResponseEntity.ok(resultAnalytics.getPassRate());
    }

    // Get average execution time
    @GetMapping("/avgtime")
    public ResponseEntity<Map<String, Object>> getAverageTime() {
        return ResponseEntity.ok(resultAnalytics.getAverageExecutionTime());
    }

    // Get most failing tests
    @GetMapping("/failing")
    public ResponseEntity<List<Map<String, Object>>> getMostFailingTests() {
        return ResponseEntity.ok(resultAnalytics.getMostFailingTests());
    }

    // Get results by suite
    @GetMapping("/results/{suiteId}")
    public ResponseEntity<Map<String, Object>> getResultsBySuite(@PathVariable Long suiteId) {
        return ResponseEntity.ok(resultAnalytics.getResultsBySuiteId(suiteId));
    }

    // Get trends
    @GetMapping("/trends")
    public ResponseEntity<Map<String, Object>> getTrends() {
        return ResponseEntity.ok(resultAnalytics.getTrends());
    }
}