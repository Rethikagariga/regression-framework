package com.regressionframework.regression_framework.scheduler;

import com.regressionframework.regression_framework.entity.TestCase;
import com.regressionframework.regression_framework.repository.TestCaseRepository;
import com.regressionframework.regression_framework.service.TestCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
@EnableScheduling
public class TestScheduler {

    @Autowired
    private TestCaseService testCaseService;

    @Autowired
    private TestCaseRepository testCaseRepository;

    // Thread pool with 5 threads for parallel execution
    private final ExecutorService executorService = Executors.newFixedThreadPool(5);

    // Runs automatically every 60 minutes
    @Scheduled(fixedRate = 3600000)
    public void scheduleAllActiveTests() {
        System.out.println("Scheduler triggered - running all active tests...");
        List<TestCase> activeTests = testCaseRepository.findByStatus("ACTIVE");
        runTestsInParallel(activeTests);
    }

    // Run a list of tests in parallel using multithreading
    public void runTestsInParallel(List<TestCase> testCases) {
        System.out.println("Running " + testCases.size() + " tests in parallel...");

        for (TestCase testCase : testCases) {
            executorService.submit(() -> {
                try {
                    System.out.println("Running test: " + testCase.getName());
                    testCaseService.executeTest(testCase.getId());
                    System.out.println("Finished test: " + testCase.getName());
                } catch (Exception e) {
                    System.out.println("Error running test: " + testCase.getName() + " - " + e.getMessage());
                }
            });
        }

        // Wait for all tests to finish
        try {
            executorService.awaitTermination(10, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}