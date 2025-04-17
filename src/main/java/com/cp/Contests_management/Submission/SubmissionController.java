package com.cp.Contests_management.Submission;

import com.cp.Contests_management.ApiResponse;
import com.cp.Contests_management.Participant.Participant;
import com.cp.Contests_management.Participant.ParticipantRepository;
import com.cp.Contests_management.Problem.Problem;
import com.cp.Contests_management.Problem.ProblemAddRequest;
import com.cp.Contests_management.Problem.ProblemDTO;
import com.cp.Contests_management.Problem.ProblemRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}")
public class SubmissionController {
    private final SubmissionService submissionService;
    private final ProblemRepository problemRepository;
    private final ParticipantRepository participantRepository;


    @PostMapping("/submissions/{problem_id}/{participant_name}/add")
    public SubmissionDTO addSubmission(@Valid @RequestBody SubmissionAddRequest submission ,@PathVariable("problem_id")Integer problem_id,@PathVariable("participant_name")String participant_name ) {
        Submission thesubmission = submissionService.addSubmission(submission,problem_id,participant_name);
        return submissionService.convertToDto(thesubmission);
    }

    @GetMapping("/public/submissions/all")
    public List<SubmissionDTO> getAllSubmissions() {
        List<Submission> Submissions = submissionService.getAllSubmissions();
        return submissionService.getConvertedSubmissions(Submissions);
    }

    @GetMapping("/submissions/result/{submissionId}")//check the result
    public String getResult(@PathVariable Integer submissionId) {
        Submission submission = submissionService.getSubmissionById(submissionId);
        return submissionService.checkSubmissionResult(submission);
    }

    @GetMapping("/submissions/checkAllResults")
    public List<SubmissionDTO> checkAllResults() {
        List<Submission> Submissions = submissionService.getResultsNotChecked();
       return submissionService.getConvertedSubmissions(Submissions);

    }

    @GetMapping("/submissions/ByParticipant/{participant_name}")
    public List<SubmissionDTO> getSubmissionByParticipantId(@PathVariable String participant_name){
            List<Submission> Submissions = submissionService.getSubmissionByParticipantName(participant_name);
            return submissionService.getConvertedSubmissions(Submissions);
    }

}