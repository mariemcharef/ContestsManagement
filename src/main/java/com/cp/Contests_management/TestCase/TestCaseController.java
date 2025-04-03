package com.cp.Contests_management.TestCase;

import com.cp.Contests_management.ApiResponse;
import com.cp.Contests_management.Competition.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/testCases")
public class TestCaseController {
    private final TestCaseService testCaseService;

    @GetMapping("/{testCase_id}/testcase")
    public TestCaseDTO getTestCaseById(@PathVariable Integer testCase_id) {
            TestCase testCase = testCaseService.getTestCaseById(testCase_id);
            return testCaseService.convertToDto(testCase);
    }

    @GetMapping("/{problemId}/all")
    public List<TestCaseDTO> getTestCasesByProblemId(@PathVariable Integer problemId) {
        List<TestCase> TestCases = testCaseService.getTestCasesByProblemId(problemId);
        return testCaseService.getConvertedTestCases(TestCases);
    }

    @PostMapping("/{problem_id}/add")
    public TestCaseDTO addTestCase(@Valid @RequestBody TestCaseRequest request,@PathVariable Integer problem_id) {
            TestCase theTestcase = testCaseService.addTestCase(request,problem_id);
            return testCaseService.convertToDto(theTestcase);
    }

    @PutMapping("/{testCase_id}/update")
    public TestCaseDTO updateTestCase(@Valid @RequestBody TestCaseRequest request, @PathVariable Integer testCase_id){
        TestCase testCase = testCaseService.updateTestCase(request,testCase_id);
        return testCaseService.convertToDto(testCase);

    }

    @DeleteMapping("/{testCase_id}/delete")
    public void deleteTestCase(@PathVariable Integer testCase_id){
            testCaseService.deleteTestCase(testCase_id);
    }


    @GetMapping("/{problem_id}/hidden")
    public List<TestCaseDTO> getHiddenTestCases(@PathVariable Integer problem_id) {
        List<TestCase> hiddenTestCases = testCaseService.getHiddenTestCases(problem_id);
        return testCaseService.getConvertedTestCases(hiddenTestCases);

    }
    @GetMapping("/{problem_id}/visible")
    public List<TestCaseDTO> getVisibleTestCases(@PathVariable Integer problem_id) {
            List<TestCase> visibleTestCases = testCaseService.getVisibleTestCases(problem_id);
             return testCaseService.getConvertedTestCases(visibleTestCases);

    }
    @PatchMapping("/{testcase_id}")
    public TestCaseDTO toggleTestCaseVisibility(@PathVariable Integer testcase_id) {
        TestCase testCase = testCaseService.toggleTestCaseVisibility(testcase_id);
        return testCaseService.convertToDto(testCase);
    }

}
