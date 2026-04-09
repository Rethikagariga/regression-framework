package com.regressionframework.regression_framework.controller;

import com.regressionframework.regression_framework.entity.TestCase;
import com.regressionframework.regression_framework.repository.TestCaseRepository;
import com.regressionframework.regression_framework.scheduler.TestScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedule")
public class SchedulerController {

    @Autowired
    private TestScheduler testScheduler;

    @Autowired
    private TestCaseRepository testCaseRepository;

    // Manually trigger all active tests to run in parallel
    @PostMapping("/run")
    public ResponseEntity<String> runAllActiveTests() {
        List<TestCase> activeTests = testCaseRepository.findByStatus("ACTIVE");
        if (activeTests.isEmpty()) {
            return ResponseEntity.ok("No active tests found!");
        }
        testScheduler.runTestsInParallel(activeTests);
        return ResponseEntity.ok("Started running " + activeTests.size() + " tests in parallel!");
    }

    // Get execution status - how many tests are active
    @GetMapping("/status")
    public ResponseEntity<String> getStatus() {
        List<TestCase> activeTests = testCaseRepository.findByStatus("ACTIVE");
        return ResponseEntity.ok("Total active tests: " + activeTests.size());
    }
}