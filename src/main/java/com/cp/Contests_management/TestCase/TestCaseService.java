package com.cp.Contests_management.TestCase;

import com.cp.Contests_management.Competition.CompetitionNotFoundException;
import com.cp.Contests_management.Problem.Problem;
import com.cp.Contests_management.Problem.ProblemNotFoundException;
import com.cp.Contests_management.Problem.ProblemRepository;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestCaseService implements ITestCaseService {

    private final TestCaseRepository testCaseRepository;

    private final ProblemRepository problemRepository;

    private final ModelMapper modelMapper;

    @Override
    public TestCase getTestCaseById(Long id) {
        return testCaseRepository.findById(id).orElseThrow(()->new TestCaseNotFoundException("TestCase not found"));
    }

    @Override
    public List<TestCase> getTestCasesByProblemId(Long problemId) {
        return testCaseRepository.findByProblemId(problemId);
    }
    @Override
    public TestCase addTestCase(TestCaseRequest request) {
        return testCaseRepository.save(createTestCase(request));
    }

    public TestCase createTestCase(TestCaseRequest request) {
        Problem problem = problemRepository.findById(request.getProblemId()).orElseThrow(()->new ProblemNotFoundException("Problem not found"));
        TestCase testCase = new TestCase();
        testCase.setProblem(problem);
        testCase.setInput(request.getInput());
        testCase.setOutput(request.getOutput());
        testCase.setHidden(request.isHidden());
        testCase.setExplanation(request.getExplanation());
        return testCaseRepository.save(testCase);
    }

    public TestCase updateExistingTestCase(TestCaseRequest request, Long id) {
        TestCase testCase = testCaseRepository.findById(id).orElseThrow(()->new TestCaseNotFoundException("TestCase not found"));
        testCase.setInput(request.getInput());
        testCase.setOutput(request.getOutput());
        testCase.setHidden(request.isHidden());
        testCase.setExplanation(request.getExplanation());
        return testCase;
    }

    @Override
    public TestCase updateTestCase(TestCaseRequest request, Long id) {
        return testCaseRepository.findById(id).map(old -> updateExistingTestCase(request,id)).map(testCaseRepository :: save).orElseThrow(()->new TestCaseNotFoundException("TestCase Not Found"));

    }

    @Override
    public void deleteTestCase(Long id) {
        testCaseRepository.findById(id).ifPresentOrElse(testCaseRepository::delete,()->{
            throw new CompetitionNotFoundException("TestCase not found");
        } );
    }
    @Override
    public TestCase toggleTestCaseVisibility(Long id) {
        TestCase testCase = testCaseRepository.findById(id)
                .orElseThrow(() -> new TestCaseNotFoundException("Test case not found with id: " + id));

        testCase.setHidden(!testCase.isHidden());
        return testCaseRepository.save(testCase);
    }
    @Override
    public List<TestCase> getHiddenTestCases(Long problemId) {
        return testCaseRepository.findByProblemIdAndHiddenTrue(problemId);
    }
    @Override
    public List<TestCaseDTO> getConvertedTestCases(List<TestCase> testCases) {
        return testCases.stream().map(this::convertToDto).toList();
    }
    @Override
    public List<TestCase> getVisibleTestCases(Long problemId) {
        return testCaseRepository.findByProblemIdAndHiddenFalse(problemId);
    }
    @Override
    public TestCaseDTO convertToDto(TestCase testCase) {
        return  modelMapper.map(testCase, TestCaseDTO.class);
    }
}
