package com.cp.Contests_management.Clarification;

import java.util.List;

public interface IClarificationService {
    Clarification addClarification(ClarificationAddRequest request);

    Clarification getClarificationById(Long id);

    void deleteClarification(Long id);

    List<Clarification> getAllClarifications();

    List<Clarification> getClarificationByProblem(Long competitionId);

    List<ClarificationDTO> getConvertedClarifications(List<Clarification> clarifications);

    ClarificationDTO convertToDto(Clarification clarification);
}
