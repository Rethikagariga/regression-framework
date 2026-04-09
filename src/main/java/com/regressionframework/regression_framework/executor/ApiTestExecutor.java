package com.regressionframework.regression_framework.executor;

import com.regressionframework.regression_framework.entity.ExecutionResult;
import com.regressionframework.regression_framework.entity.TestCase;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.springframework.stereotype.Component;

@Component
public class ApiTestExecutor implements TestExecutor {

    @Override
    public ExecutionResult execute(TestCase testCase) {
        ExecutionResult result = new ExecutionResult();
        result.setTestCase(testCase);

        long startTime = System.currentTimeMillis();

        try {
            Response response = RestAssured
                    .given()
                    .header("Content-Type", "application/json")
                    .when()
                    .get(testCase.getTargetUrl())
                    .then()
                    .extract()
                    .response();

            int statusCode = response.getStatusCode();

            if (statusCode >= 200 && statusCode < 300) {
                result.setResult("PASS");
                result.setErrorMessage(null);
            } else {
                result.setResult("FAIL");
                result.setErrorMessage("Expected 2xx but got: " + statusCode);
            }

        } catch (Exception e) {
            result.setResult("ERROR");
            result.setErrorMessage(e.getMessage());
        } finally {
            result.setExecutionTimeMs(System.currentTimeMillis() - startTime);
        }

        return result;
    }
}