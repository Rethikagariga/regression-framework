package com.regressionframework.regression_framework.executor;

import com.regressionframework.regression_framework.entity.ExecutionResult;
import com.regressionframework.regression_framework.entity.TestCase;

public interface TestExecutor {
    ExecutionResult execute(TestCase testCase);
}