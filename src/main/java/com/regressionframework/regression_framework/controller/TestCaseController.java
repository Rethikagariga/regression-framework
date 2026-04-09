package com.regressionframework.regression_framework.controller;

import com.regressionframework.regression_framework.dto.TestExecutionRequest;
import com.regressionframework.regression_framework.entity.ExecutionResult;
import com.regressionframework.regression_framework.entity.TestCase;
import com.regressionframework.regression_framework.service.TestCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/tests")
public class TestCaseController {

    @Autowired
    private TestCaseService testCaseService;

    @PostMapping("/integrate")
    public ResponseEntity<TestCase> createTest(@RequestBody TestCase testCase) {
        return ResponseEntity.ok(testCaseService.createTestCase(testCase));
    }

    @GetMapping
    public ResponseEntity<List<TestCase>> getAllTests() {
        return ResponseEntity.ok(testCaseService.getAllTestCases());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestCase> getTestById(@PathVariable Long id) {
        return ResponseEntity.ok(testCaseService.getTestCaseById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTest(@PathVariable Long id) {
        testCaseService.deleteTestCase(id);
        return ResponseEntity.ok("TestCase deleted successfully");
    }

    @PostMapping("/execute")
    public ResponseEntity<ExecutionResult> executeTest(@RequestBody TestExecutionRequest request) {
        ExecutionResult result = testCaseService.executeTest(request.getTestCaseId());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}/results")
    public ResponseEntity<List<ExecutionResult>> getResults(@PathVariable Long id) {
        return ResponseEntity.ok(testCaseService.getResultsByTestCaseId(id));
    }

    @GetMapping("/results/all")
    public ResponseEntity<List<ExecutionResult>> getAllResults() {
        return ResponseEntity.ok(testCaseService.getAllResults());
    }
}