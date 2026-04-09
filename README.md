\# Regression Test Automation Framework



A complete regression test automation framework built with Java and Spring Boot.



\## Tech Stack

\- Java 17

\- Spring Boot 3.x

\- MySQL 8.0

\- Selenium WebDriver 4.18

\- REST-Assured 5.4

\- Maven



\## Features

\- Web UI testing using Selenium

\- API testing using REST-Assured

\- Parallel test execution with multithreading

\- Automated scheduling every 60 minutes

\- HTML, CSV, JUnit report generation

\- Failure log collection with screenshots

\- Result analytics and pass rate tracking

\- REST API for all operations



\## How to Run

1\. Make sure MySQL is running with regression\_db database

2\. Run: java -jar target/regression-framework-0.0.1-SNAPSHOT.jar



\## API Endpoints

\- POST /tests/integrate - Create test case

\- POST /tests/execute - Execute a test

\- GET /analytics/summary - View analytics

\- GET /reports/generate/html - Generate report

\- POST /schedule/run - Run all tests in parallel

