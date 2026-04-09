package com.regressionframework.regression_framework.dto;

public class TestExecutionRequest {

    private Long testCaseId;
    private Long suiteId;

    public Long getTestCaseId() { return testCaseId; }
    public void setTestCaseId(Long testCaseId) { this.testCaseId = testCaseId; }

    public Long getSuiteId() { return suiteId; }
    public void setSuiteId(Long suiteId) { this.suiteId = suiteId; }
}