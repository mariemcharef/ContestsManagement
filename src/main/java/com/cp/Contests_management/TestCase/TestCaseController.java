package com.cp.Contests_management.TestCase;

import com.cp.Contests_management.ApiResponse;
import com.cp.Contests_management.Competition.*;
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

    @GetMapping("/{id}/testcase")
    public ResponseEntity<ApiResponse> getTestCaseById(@PathVariable long id) {
        try {
            TestCase testCase = testCaseService.getTestCaseById(id);
            var testCaseDto = testCaseService.convertToDto(testCase);

            return ResponseEntity.ok(new ApiResponse("success", testCaseDto));
        } catch (TestCaseNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
    @GetMapping("{problemId}/all")
    public ResponseEntity<ApiResponse> getTestCasesByProblemId(@PathVariable long problemId) {
        List<TestCase> TestCases = testCaseService.getTestCasesByProblemId(problemId);
        List<TestCaseDTO> convertedTestCases = testCaseService.getConvertedTestCases(TestCases);
        return ResponseEntity.ok(new ApiResponse("success", convertedTestCases));
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addTestCase(@RequestBody TestCaseRequest request) {
        try {
            TestCase theTestcase = testCaseService.addTestCase(request);
            var theTestcaseDto = testCaseService.convertToDto(theTestcase);
            return ResponseEntity.ok(new ApiResponse("TestCase added successfully", theTestcaseDto));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }
    @PutMapping("/{Id}/update")
    public ResponseEntity<ApiResponse> updateTestCase(@RequestBody TestCaseRequest request, @PathVariable Long Id){
        try {
            TestCase testCase = testCaseService.updateTestCase(request,Id);
            var testcaseDto = testCaseService.convertToDto(testCase);

            return ResponseEntity.ok(new ApiResponse("TestCase updated successfully", testcaseDto));
        } catch (TestCaseNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("/{Id}/delete")
    public ResponseEntity<ApiResponse> deleteTestCase(@PathVariable Long Id){
        try {
            testCaseService.deleteTestCase(Id);
            return ResponseEntity.ok(new ApiResponse("TestCase deleted successfully", null));
        } catch (TestCaseNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PatchMapping("/{id}/toggle-visibility")//PATCH allows the client to send only the fields that need to be updated
    public ResponseEntity<ApiResponse> toggleTestCaseVisibility(
            @PathVariable Long problemId,
            @PathVariable Long id) {
        try {
            TestCase testCase = testCaseService.toggleTestCaseVisibility(id);
            TestCaseDTO testCaseDto = testCaseService.convertToDto(testCase);
            return ResponseEntity.ok(new ApiResponse("success", testCaseDto));
        }  catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));

        }
    }
    @GetMapping("/hidden")
    public ResponseEntity<ApiResponse> getHiddenTestCases(@PathVariable Long problemId) {
        try {
            List<TestCase> hiddenTestCases = testCaseService.getHiddenTestCases(problemId);
            List<TestCaseDTO> convertedTestCases = testCaseService.getConvertedTestCases(hiddenTestCases);
            return ResponseEntity.ok(new ApiResponse("success", convertedTestCases));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
    @GetMapping("/visible")
    public ResponseEntity<ApiResponse> getVisibleTestCases(@PathVariable Long problemId) {
        try {
            List<TestCase> visibleTestCases = testCaseService.getVisibleTestCases(problemId);
            List<TestCaseDTO> convertedTestCases = testCaseService.getConvertedTestCases(visibleTestCases);
            return ResponseEntity.ok(new ApiResponse("success", convertedTestCases));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
        }

}
