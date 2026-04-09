package com.regressionframework.regression_framework.repository;

import com.regressionframework.regression_framework.entity.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TestCaseRepository extends JpaRepository<TestCase, Long> {
    List<TestCase> findByType(String type);
    List<TestCase> findByStatus(String status);
}