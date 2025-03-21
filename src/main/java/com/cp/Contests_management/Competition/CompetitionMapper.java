package com.cp.Contests_management.Competition;

public class CompetitionMapper {
    public static CompetitionDTO toDTO(Competition competition) {
        CompetitionDTO dto = new CompetitionDTO();
        dto.setId(competition.getId());
        dto.setName(competition.getName());
        dto.setDuration(competition.getDuration());
        dto.setStartTime(competition.getStartTime());
        dto.setEndTime(competition.getEndTime());
        dto.setPenalty(competition.getPenalty());
        dto.setAppUserId(competition.getAppUser().getId()); // Assuming AppUser is a related entity
        return dto;
    }
}
