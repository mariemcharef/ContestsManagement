package com.cp.Contests_management.ParticipantCompetition;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/participant_competitions")
public class ParticipantCompetitionController {
    private final ParticipantCompetitionService participantCompetitionService;
     /*
        This method will allow to a user to register  within
        a competition.

        If the user is the creator of the competition
        he will not be able to compete (participate) .

        and check if the date of participation is before the starting time of the competition
     */
    @PostMapping("")
     public ParticipantCompetitionResponseDto createParticipationInCompetition( @Valid @RequestBody ParticipantCompetitionDTO participantCompetitionDto){
        return participantCompetitionService.createParticipationInCompetition(participantCompetitionDto);
     }
     @GetMapping("")//get All Registrations
    public List<ParticipantCompetitionResponseDto> getParticipationInCompetition(){
        return participantCompetitionService.getAllParticipantCompetitions();
     }

     @GetMapping("/{competition_id}/{participant_id}")
    public ParticipantCompetitionResponseDto getRegistrationById(@PathVariable("competition_id") Integer competitionId, @PathVariable("participant_id") Integer participantId){
        return participantCompetitionService.getParticipantCompetitionById(participantId,competitionId);
     }
     @DeleteMapping("/{competition_id}/{participant_id}")
    public void deleteRegistration(@PathVariable("competition_id") Integer competitionId, @PathVariable("participant_id") Integer participantId){
        participantCompetitionService.deleteRegistration(participantId,competitionId);
     }
}
