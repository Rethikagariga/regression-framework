package com.regressionframework.regression_framework.repository;

import com.regressionframework.regression_framework.entity.ExecutionResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ExecutionResultRepository extends JpaRepository<ExecutionResult, Long> {
    List<ExecutionResult> findByResult(String result);
    List<ExecutionResult> findByTestCaseId(Long testCaseId);
}