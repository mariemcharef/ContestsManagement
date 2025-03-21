package com.cp.Contests_management.Competition;

import java.util.List;

public interface ICompetitionService {
    Competition getCompetitionById(Long id);
    List<Competition> getCompetitionByName(String name);
    List<Competition> getAllCompetitions();
    Competition addCompetition(CompetitionAddRequest request);
    Competition updateCompetition(CompetitionUpdateRequest request,Long id);
    void deleteCompetition(Long id);

    List<CompetitionDTO> getConvertedCompetitions(List<Competition> competitions);

    CompetitionDTO convertToDto(Competition competition);
}
