package com.cp.Contests_management.TestCase;

import com.cp.Contests_management.Problem.Problem;
import com.cp.Contests_management.Problem.ProblemRepository;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestCaseService  {

    private final TestCaseRepository testCaseRepository;

    private final ProblemRepository problemRepository;

    private final ModelMapper modelMapper;

    public TestCase getTestCaseById(Integer id) {
        return testCaseRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"TestCase not found"));
    }

    public List<TestCase> getTestCasesByProblemId(Integer problemId) {
        return testCaseRepository.findAll().stream().filter(t -> t.getProblem().getId().equals(problemId)).toList();
    }

    public TestCase addTestCase(TestCaseRequest request,Integer problemId) {
        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Problem not found"));
        TestCase testCase = new TestCase();
        testCase.setProblem(problem);
        testCase.setInput(request.getInput());
        testCase.setOutput(request.getOutput());
        testCase.setHidden(request.isHidden());
        testCase.setExplanation(request.getExplanation());
        return testCaseRepository.save(testCase);
    }

    public TestCase updateTestCase(TestCaseRequest request, Integer id) {
        TestCase testCase = testCaseRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"TestCase not found"));
        if(!request.getInput().isEmpty()){
            testCase.setInput(request.getInput());
        }
        if(!request.getOutput().isEmpty()){
            testCase.setOutput(request.getOutput());
        }
        testCase.setHidden(request.isHidden());
        if(!request.getExplanation().isEmpty()){
            testCase.setExplanation(request.getExplanation());
        }

        return testCaseRepository.save(testCase);
    }


    public void deleteTestCase(Integer id) {
        testCaseRepository.findById(id).ifPresentOrElse(testCaseRepository::delete,()->{
            throw new RuntimeException("TestCase not found");
        } );
    }

    public TestCase toggleTestCaseVisibility(Integer id) {
        TestCase testCase = testCaseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Test case not found with id: " + id));

        testCase.setHidden(!testCase.isHidden());
        return testCaseRepository.save(testCase);
    }

    public List<TestCase> getHiddenTestCases(Integer problemId) {
        return testCaseRepository.findByProblemIdAndHiddenTrue(problemId);
    }

    public List<TestCaseDTO> getConvertedTestCases(List<TestCase> testCases) {
        return testCases.stream().map(this::convertToDto).toList();
    }
    public List<TestCase> getVisibleTestCases(Integer problemId) {
        return testCaseRepository.findByProblemIdAndHiddenFalse(problemId);
    }
    public TestCaseDTO convertToDto(TestCase testCase) {

        return  modelMapper.map(testCase, TestCaseDTO.class);
    }
}
