package com.cp.Contests_management.Submission;

import com.cp.Contests_management.Participant.Participant;
import com.cp.Contests_management.Participant.ParticipantRepository;
import com.cp.Contests_management.Problem.Problem;
import com.cp.Contests_management.Problem.ProblemRepository;
import com.cp.Contests_management.TestCase.TestCase;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.cp.Contests_management.Submission.Language.fromJudge0Id;

@Service
@RequiredArgsConstructor
public class SubmissionService {
    private static final String JUDGE0_URL = "https://judge0-ce.p.rapidapi.com/submissions?base64_encoded=false&wait=false";
    private static final String RAPIDAPI_KEY ="f27222a81amsh28a1ddf997c07f6p119dc1jsn5f6c4ca445fd";
    private static final String RAPIDAPI_HOST = "judge0-ce.p.rapidapi.com";
    private static final Logger logger = LoggerFactory.getLogger(SubmissionService.class);    private final ModelMapper modelMapper;
    private final RestTemplate restTemplate;
    private final SubmissionRepository submissionRepository;
    private final ProblemRepository problemRepository;
    private final ParticipantRepository participantRepository;


    public Submission addSubmission(SubmissionAddRequest request,Integer problem_id,String participant_name){
        Problem problem = problemRepository.findById(problem_id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Problem not found"));
        Participant participant=participantRepository.findByName(participant_name);
                if(participant==null){
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Participant not found !!!!!!");
                }
        Submission submission = new Submission();
        submission.setCode(request.getCode());
        submission.setProblem(problem);
        submission.setParticipant(participant);
        submission.setLanguage(fromJudge0Id(request.getLanguageId()));
        submission.setTime(LocalDateTime.now());
        submission.setJudgement("In Queue");
        String judge0Token = submitCodeToJudge0(submission);
        submission.setJudge0Token(judge0Token);
        submission.setProcessed(false);
        return submissionRepository.save(submission);
    }


    public Submission getSubmissionById(Integer id) {
        return submissionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Submission not found"));
    }


    public List<Submission> getSubmissionByParticipantName(String participant_name) {
        getResultsNotChecked();
        Participant participant = participantRepository.findByName(participant_name);
        return submissionRepository.findByParticipantId(participant.getId());
    }


    public List<Submission> getAllSubmissions() {
        getResultsNotChecked();
        return submissionRepository.findAll();
    }


    public SubmissionDTO convertToDto(Submission submission) {
        return  modelMapper.map(submission, SubmissionDTO.class);
    }

    public List<SubmissionDTO> getConvertedSubmissions(List<Submission> submissions) {
        return submissions.stream().map(this::convertToDto).toList();
    }

    public String getTestCaseInput(Submission submission) {
        if ( submission.getProblem() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND," Problem is null");
        }
        Problem problem =submission.getProblem();
        List<TestCase> testCases = problem.getTestCases();
        if (testCases == null || testCases.isEmpty()) {
            throw new IllegalStateException("No test cases found for the problem");
        }
        return testCases.get(0).getInput();//testCase that will be send to judge0 by default

    }

    public String getTestCaseOutput(Submission submission) {
        if (submission.getProblem() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Problem is null");
        }
        Problem problem =submission.getProblem();
        List<TestCase> testCases = problem.getTestCases();
        if (testCases == null || testCases.isEmpty()) {
            throw new IllegalStateException("No test cases found for the problem");
        }
        return testCases.get(0).getOutput();

    }

    public String submitCodeToJudge0(Submission submission) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-rapidapi-host", RAPIDAPI_HOST);
        headers.set("X-rapidapi-key", RAPIDAPI_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);
        SubmissionRequest request = new SubmissionRequest();
        request.setSource_code(submission.getCode());
        request.setLanguage_id(submission.getLanguage().getJudge0Id());
        request.setStdin(getTestCaseInput(submission));
        request.setExpected_output(getTestCaseOutput(submission));
        HttpEntity<SubmissionRequest> requestEntity = new HttpEntity<>(request, headers);
        logger.info("Submitting code to Judge0: {}", request);

        int maxRetries = 5;
        int attempts = 0;
        while (attempts < maxRetries) {
            try {
                ResponseEntity<SubmissionResponse> response=restTemplate.exchange(
                        JUDGE0_URL,
                        HttpMethod.POST,
                        requestEntity,
                        SubmissionResponse.class
                );
                return response.getBody().getToken();
            }catch(Exception e){
                attempts++;
                logger.error("Attempt {} failed: {}", attempts, e.getMessage());
                if (attempts == maxRetries) {
                    throw new RuntimeException("Failed to submit code to Judge0 after " + maxRetries + " attempts", e);//to delete this message after finishing backend
                }
                try {
                    Thread.sleep(1000); // Wait 1 second before retrying
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Interrupted during retry", ie);
                }
            }
        }
        throw new RuntimeException("Unexpected error in submitCodeToJudge0");

    }



    public String checkSubmissionResult(Submission submission) {
        String token=submission.getJudge0Token();
        logger.info("Fetching result for token: {}", token);
        String url = "https://judge0-ce.p.rapidapi.com/submissions/" + token + "?base64_encoded=true";

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Key", RAPIDAPI_KEY);
        headers.set("X-RapidAPI-Host", RAPIDAPI_HOST);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    String.class
            );
            logger.info("Submission result: {}", response.getBody());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.getBody());
            String description = rootNode.path("status").path("description").asText();
            logger.info("Submission result description: {}", description);
            submission.setJudgement(description);
            submission.setProcessed(true);
            submissionRepository.save(submission);
            return description;

        } catch (Exception e) {
            logger.error("Error fetching result: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch submission result: " + e.getMessage(), e);
        }
    }
    //get results of submissions not checked

    public List<Submission> getResultsNotChecked() {
        return submissionRepository.findAll().stream()
                .filter(s -> !s.isProcessed())
                .map(s -> {
                    checkSubmissionResult(s);
                    return s;
                })
                .collect(Collectors.toList());
    }

}
