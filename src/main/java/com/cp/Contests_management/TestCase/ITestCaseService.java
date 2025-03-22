package com.cp.Contests_management.TestCase;

import java.util.List;

public interface ITestCaseService {
    TestCase getTestCaseById(Long id);
    List<TestCase> getTestCasesByProblemId(Long problemId);
    TestCase addTestCase(TestCaseRequest request);
    TestCase updateTestCase(TestCaseRequest request, Long id);


    void deleteTestCase(Long id);

    TestCase toggleTestCaseVisibility(Long id);

    List<TestCase> getHiddenTestCases(Long problemId);

    List<TestCaseDTO> getConvertedTestCases(List<TestCase> testCases);

    List<TestCase> getVisibleTestCases(Long problemId);

    TestCaseDTO convertToDto(TestCase testCase);
}
