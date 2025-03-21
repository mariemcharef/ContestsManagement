package com.cp.Contests_management.Competition;

import java.util.List;

public interface ICompetitionService {
    CompetitionDTO getCompetitionById(Long id);
    List<CompetitionDTO> getCompetitionByName(String name);
    List<CompetitionDTO> getAllCompetitions();
    Competition addCompetition(CompetitionAddRequest request);
    Competition updateCompetition(CompetitionUpdateRequest request,Long id);
    void deleteCompetition(Long id);
}
