package com.regressionframework.regression_framework.service;

import com.regressionframework.regression_framework.engine.TestExecutionEngine;
import com.regressionframework.regression_framework.entity.ExecutionResult;
import com.regressionframework.regression_framework.entity.TestCase;
import com.regressionframework.regression_framework.repository.ExecutionResultRepository;
import com.regressionframework.regression_framework.repository.TestCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TestCaseService {

    @Autowired
    private TestCaseRepository testCaseRepository;

    @Autowired
    private ExecutionResultRepository executionResultRepository;

    @Autowired
    private TestExecutionEngine testExecutionEngine;

    public TestCase createTestCase(TestCase testCase) {
        return testCaseRepository.save(testCase);
    }

    public List<TestCase> getAllTestCases() {
        return testCaseRepository.findAll();
    }

    public TestCase getTestCaseById(Long id) {
        return testCaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TestCase not found: " + id));
    }

    public void deleteTestCase(Long id) {
        testCaseRepository.deleteById(id);
    }

    public ExecutionResult executeTest(Long testCaseId) {
        TestCase testCase = getTestCaseById(testCaseId);
        return testExecutionEngine.runTest(testCase);
    }

    public List<ExecutionResult> getResultsByTestCaseId(Long testCaseId) {
        return executionResultRepository.findByTestCaseId(testCaseId);
    }

    public List<ExecutionResult> getAllResults() {
        return executionResultRepository.findAll();
    }
}