package com.cp.Contests_management.Problem;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProblemRepository extends JpaRepository<Problem, Integer> {
    List<Problem> findByName(String name);

    List<Problem> findByCompetitionId(Integer competitionId);
}
