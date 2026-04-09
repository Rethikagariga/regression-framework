# \# Regression Test Automation Framework

# 

# A complete regression test automation framework built using Java and Spring Boot.

# 

# \## Tech Stack

# \- Java 17

# \- Spring Boot

# \- MySQL

# \- Selenium WebDriver

# \- REST-Assured

# \- Maven

# 

# \## Features

# \- Web UI testing using Selenium

# \- API testing using REST-Assured

# \- Parallel test execution

# \- Automated scheduling

# \- HTML, CSV, JUnit reports

# \- Failure logs with screenshots

# \- Analytics and pass rate tracking

# \- REST API support

# 

# \## How to Run

# 1\. Ensure MySQL is running with `regression\_db`

# 2\. Build project:

# &#x20;  mvn clean package

# 3\. Run:

# &#x20;  java -jar target/regression-framework-0.0.1-SNAPSHOT.jar

# 

# \## API Endpoints

# \- POST /tests/integrate

# \- POST /tests/execute

# \- GET /analytics/summary

# \- GET /reports/generate/html

# \- POST /schedule/run

