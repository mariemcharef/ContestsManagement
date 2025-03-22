package com.cp.Contests_management.Problem;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProblemRepository extends JpaRepository<Problem, Long> {
    List<Problem> findByName(String name);

    List<Problem> findByCompetitionId(Long competitionId);
}
