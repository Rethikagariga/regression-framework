package com.regressionframework.regression_framework.analytics;

import com.regressionframework.regression_framework.entity.ExecutionResult;
import com.regressionframework.regression_framework.repository.ExecutionResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ResultAnalytics {

    @Autowired
    private ExecutionResultRepository executionResultRepository;

    // Get overall pass/fail/error counts
    public Map<String, Long> getOverallStats() {
        List<ExecutionResult> results = executionResultRepository.findAll();

        long passCount = results.stream()
                .filter(r -> "PASS".equals(r.getResult())).count();
        long failCount = results.stream()
                .filter(r -> "FAIL".equals(r.getResult())).count();
        long errorCount = results.stream()
                .filter(r -> "ERROR".equals(r.getResult())).count();

        Map<String, Long> stats = new LinkedHashMap<>();
        stats.put("total", (long) results.size());
        stats.put("pass", passCount);
        stats.put("fail", failCount);
        stats.put("error", errorCount);
        return stats;
    }

    // Get pass rate percentage
    public Map<String, Object> getPassRate() {
        List<ExecutionResult> results = executionResultRepository.findAll();
        if (results.isEmpty()) {
            Map<String, Object> empty = new HashMap<>();
            empty.put("passRate", 0.0);
            empty.put("message", "No results found");
            return empty;
        }

        long passCount = results.stream()
                .filter(r -> "PASS".equals(r.getResult())).count();
        double passRate = (double) passCount / results.size() * 100;

        Map<String, Object> rate = new LinkedHashMap<>();
        rate.put("totalTests", results.size());
        rate.put("passCount", passCount);
        rate.put("passRate", String.format("%.2f%%", passRate));
        return rate;
    }

    // Get average execution time
    public Map<String, Object> getAverageExecutionTime() {
        List<ExecutionResult> results = executionResultRepository.findAll();
        if (results.isEmpty()) {
            Map<String, Object> empty = new HashMap<>();
            empty.put("averageTimeMs", 0);
            return empty;
        }

        double avgTime = results.stream()
                .mapToLong(ExecutionResult::getExecutionTimeMs)
                .average()
                .orElse(0.0);

        Map<String, Object> timeStats = new LinkedHashMap<>();
        timeStats.put("averageExecutionTimeMs", String.format("%.2f ms", avgTime));
        timeStats.put("totalExecutions", results.size());
        return timeStats;
    }

    // Get most failing tests
    public List<Map<String, Object>> getMostFailingTests() {
        List<ExecutionResult> failedResults = executionResultRepository.findByResult("FAIL");

        Map<String, Long> failCounts = failedResults.stream()
                .collect(Collectors.groupingBy(
                        r -> r.getTestCase().getName(),
                        Collectors.counting()
                ));

        return failCounts.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .map(entry -> {
                    Map<String, Object> map = new LinkedHashMap<>();
                    map.put("testName", entry.getKey());
                    map.put("failCount", entry.getValue());
                    return map;
                })
                .collect(Collectors.toList());
    }

    // Get results by suite ID
    public Map<String, Object> getResultsBySuiteId(Long suiteId) {
        List<ExecutionResult> results = executionResultRepository.findAll()
                .stream()
                .filter(r -> r.getTestSuite() != null &&
                        r.getTestSuite().getId().equals(suiteId))
                .collect(Collectors.toList());

        long passCount = results.stream()
                .filter(r -> "PASS".equals(r.getResult())).count();
        long failCount = results.stream()
                .filter(r -> "FAIL".equals(r.getResult())).count();

        Map<String, Object> suiteStats = new LinkedHashMap<>();
        suiteStats.put("suiteId", suiteId);
        suiteStats.put("totalTests", results.size());
        suiteStats.put("pass", passCount);
        suiteStats.put("fail", failCount);
        return suiteStats;
    }

    // Get trends - results grouped by date
    public Map<String, Object> getTrends() {
        List<ExecutionResult> results = executionResultRepository.findAll();

        Map<String, Long> passByDate = results.stream()
                .filter(r -> "PASS".equals(r.getResult()))
                .collect(Collectors.groupingBy(
                        r -> r.getExecutedAt().toLocalDate().toString(),
                        Collectors.counting()
                ));

        Map<String, Long> failByDate = results.stream()
                .filter(r -> "FAIL".equals(r.getResult()))
                .collect(Collectors.groupingBy(
                        r -> r.getExecutedAt().toLocalDate().toString(),
                        Collectors.counting()
                ));

        Map<String, Object> trends = new LinkedHashMap<>();
        trends.put("passByDate", passByDate);
        trends.put("failByDate", failByDate);
        return trends;
    }
}