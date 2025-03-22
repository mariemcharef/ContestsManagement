package com.cp.Contests_management.TestCase;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestCaseRepository extends JpaRepository<TestCase, Long> {
    List<TestCase> findByProblemId(Long problemId);

    List<TestCase> findByProblemIdAndHiddenTrue(Long problemId);

    List<TestCase> findByProblemIdAndHiddenFalse(Long problemId);
}
