package com.regressionframework.regression_framework.executor;

import com.regressionframework.regression_framework.entity.ExecutionResult;
import com.regressionframework.regression_framework.entity.TestCase;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class SeleniumTestExecutor implements TestExecutor {

    @Override
    public ExecutionResult execute(TestCase testCase) {
        ExecutionResult result = new ExecutionResult();
        result.setTestCase(testCase);

        WebDriver driver = null;
        long startTime = System.currentTimeMillis();

        try {
            WebDriverManager.chromedriver().setup();

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");

            driver = new ChromeDriver(options);
            driver.get(testCase.getTargetUrl());

            String title = driver.getTitle();
            if (title != null && !title.isEmpty()) {
                result.setResult("PASS");
                result.setErrorMessage(null);
            } else {
                result.setResult("FAIL");
                result.setErrorMessage("Page title was empty");
                saveScreenshot(driver, result, testCase.getId());
            }

        } catch (Exception e) {
            result.setResult("FAIL");
            result.setErrorMessage(e.getMessage());
            if (driver != null) {
                saveScreenshot(driver, result, testCase.getId());
            }
        } finally {
            if (driver != null) {
                driver.quit();
            }
            result.setExecutionTimeMs(System.currentTimeMillis() - startTime);
        }

        return result;
    }

    private void saveScreenshot(WebDriver driver, ExecutionResult result, Long testCaseId) {
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String screenshotDir = "screenshots/";
            Path dirPath = Paths.get(screenshotDir);
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }
            String fileName = screenshotDir + "test_" + testCaseId + "_" + System.currentTimeMillis() + ".png";
            Files.copy(screenshot.toPath(), Paths.get(fileName));
            result.setScreenshotPath(fileName);
        } catch (IOException e) {
            System.out.println("Could not save screenshot: " + e.getMessage());
        }
    }
}