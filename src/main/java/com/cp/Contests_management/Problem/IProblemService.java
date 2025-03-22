package com.cp.Contests_management.Problem;


import java.util.List;

public interface IProblemService {
    Problem getProblemById(Long id);
    List<Problem> getProblemByName(String name);
    List<Problem> getAllProblems();
    Problem addProblem(ProblemAddRequest request);
    Problem updateProblem(ProblemUpdateRequest request, Long id);
    void deleteProblem(Long id);

    List<ProblemDTO> getConvertedProblems(List<Problem> problems);

    ProblemDTO convertToDto(Problem problem);

    List<Problem> getProblemsByCompetitionId(Long competitionId);
}

