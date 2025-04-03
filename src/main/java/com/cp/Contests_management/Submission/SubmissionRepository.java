package com.cp.Contests_management.Submission;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission, Integer> {
    List<Submission> findByParticipantId(Integer participantId);


}
