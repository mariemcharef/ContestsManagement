package com.cp.Contests_management.TestCase;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestCaseRepository extends JpaRepository<TestCase, Integer> {
    List<TestCase> findByProblemId(Integer problemId);

    List<TestCase> findByProblemIdAndHiddenTrue(Integer problemId);

    List<TestCase> findByProblemIdAndHiddenFalse(Integer problemId);
}
