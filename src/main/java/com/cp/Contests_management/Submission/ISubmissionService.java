package com.cp.Contests_management.Submission;

import com.cp.Contests_management.Competition.Competition;
import com.cp.Contests_management.Competition.CompetitionAddRequest;
import com.cp.Contests_management.Competition.CompetitionDTO;
import com.cp.Contests_management.Competition.CompetitionUpdateRequest;

import java.util.List;

public interface ISubmissionService {
    Submission getSubmissionById(Long id);
    List<Submission> getSubmissionByParticipantId(Long participantId);
    List<Submission> getAllSubmissions();
    Submission addSubmission(SubmissionAddRequest request);

    List<SubmissionDTO> getConvertedSubmissions(List<Submission> submissions);

    SubmissionDTO convertToDto(Submission submission);

    String submitCodeToJudge0(Submission submission);

    String checkSubmissionResult(Submission submission);
}
