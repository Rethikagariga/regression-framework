package com.regressionframework.regression_framework.engine;

import com.regressionframework.regression_framework.entity.ExecutionResult;
import com.regressionframework.regression_framework.entity.TestCase;
import com.regressionframework.regression_framework.executor.ApiTestExecutor;
import com.regressionframework.regression_framework.executor.SeleniumTestExecutor;
import com.regressionframework.regression_framework.repository.ExecutionResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestExecutionEngine {

    @Autowired
    private SeleniumTestExecutor seleniumTestExecutor;

    @Autowired
    private ApiTestExecutor apiTestExecutor;

    @Autowired
    private ExecutionResultRepository executionResultRepository;

    public ExecutionResult runTest(TestCase testCase) {
        ExecutionResult result;

        if ("WEB".equalsIgnoreCase(testCase.getType())) {
            result = seleniumTestExecutor.execute(testCase);
        } else if ("API".equalsIgnoreCase(testCase.getType())) {
            result = apiTestExecutor.execute(testCase);
        } else {
            result = new ExecutionResult();
            result.setTestCase(testCase);
            result.setResult("ERROR");
            result.setErrorMessage("Unknown test type: " + testCase.getType());
        }

        return executionResultRepository.save(result);
    }
}