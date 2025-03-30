package com.cp.Contests_management.Submission;

import com.cp.Contests_management.ApiResponse;
import com.cp.Contests_management.Participant.Participant;
import com.cp.Contests_management.Participant.ParticipantRepository;
import com.cp.Contests_management.Problem.Problem;
import com.cp.Contests_management.Problem.ProblemAddRequest;
import com.cp.Contests_management.Problem.ProblemDTO;
import com.cp.Contests_management.Problem.ProblemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/submissions")
public class SubmissionController {
    private final SubmissionService submissionService;
    private final ProblemRepository problemRepository;
    private final ParticipantRepository participantRepository;


    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addSubmission(@RequestBody SubmissionAddRequest submission) {
        try {
            Submission thesubmission = submissionService.addSubmission(submission);
            var submissionDto = submissionService.convertToDto(thesubmission);

            return ResponseEntity.ok(new ApiResponse("Submission added successfully", submissionDto));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllSubmissions() {
        List<Submission> Submissions = submissionService.getAllSubmissions();
        List<SubmissionDTO> convertedSubmissions = submissionService.getConvertedSubmissions(Submissions);
        return ResponseEntity.ok(new ApiResponse("success", convertedSubmissions));
    }

    @GetMapping("/result/{submissionId}")//check the result
    public String getResult(@PathVariable Long submissionId) {
        Submission submission = submissionService.getSubmissionById(submissionId);
        return submissionService.checkSubmissionResult(submission);
    }
    @GetMapping("/checkAllResults")
    public ResponseEntity<ApiResponse> checkAllResults() {
        List<Submission> Submissions = submissionService.getResultsNotChecked();
        List<SubmissionDTO> convertedSubmissions = submissionService.getConvertedSubmissions(Submissions);
        return ResponseEntity.ok(new ApiResponse("success", convertedSubmissions));

    }
    @GetMapping("/ByParticipant/{participantId}")
    public ResponseEntity<ApiResponse> getSubmissionByParticipantId(@PathVariable Long participantId){

        try {

            List<Submission> Submissions = submissionService.getSubmissionByParticipantId(participantId);
            List<SubmissionDTO> convertedSubmissions = submissionService.getConvertedSubmissions(Submissions);
            return ResponseEntity.ok(new ApiResponse("success", convertedSubmissions));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}