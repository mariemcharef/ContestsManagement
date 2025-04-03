package com.cp.Contests_management.ParticipantCompetition;

import com.cp.Contests_management.Competition.Competition;
import com.cp.Contests_management.Competition.CompetitionRepository;
import com.cp.Contests_management.Participant.Participant;
import com.cp.Contests_management.Participant.ParticipantRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ParticipantCompetitionMapper {
    private final ParticipantRepository participantRepository;
    private final CompetitionRepository competitionRepository;

    public ParticipantCompetitionMapper(
            ParticipantRepository participantRepository,
            CompetitionRepository competitionRepository
    ) {
        this.participantRepository = participantRepository;
        this.competitionRepository = competitionRepository;
    }
    public ParticipantCompetition dtoToParticipantCompetition(ParticipantCompetitionDTO dto){
        if(dto==null) return null;
        Participant participant =participantRepository.findByName(dto.getParticipantName());
        if(participant==null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Participant not found");
        }
        Competition competition=competitionRepository.findByName(dto.getCompetitionName());
        if(competition==null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Competition not found");
        }
        ParticipantCompetition participantCompetition = new ParticipantCompetition();
        participantCompetition.setCompetition(competition);
        participantCompetition.setParticipant(participant);

        ParticipantsCompetitionsId id = new ParticipantsCompetitionsId(                participant.getId(),
                competition.getId()
        );
        participantCompetition.setId(id);
        return participantCompetition;
    }
    public ParticipantCompetitionResponseDto participantCompetitionToParticipantCompetitionResponseDto(ParticipantCompetition participantCompetition){
        if(participantCompetition==null) return null;
        ParticipantCompetitionResponseDto participantCompetitionResponseDto = new ParticipantCompetitionResponseDto();
        participantCompetitionResponseDto.setId(participantCompetition.getId());
        participantCompetitionResponseDto.setParticipantName(participantCompetition.getParticipant().getName());
        participantCompetitionResponseDto.setCompetitionName(participantCompetition.getCompetition().getName());
        participantCompetition.setRank(participantCompetition.getRank());
        return participantCompetitionResponseDto;
    }
    /*
        This function will test if the participant and the competition
        exist in the database.

        if both exist it will return <True, Participant, Competition>
        if Participant doesn't exist it will return < False, Null , Competition>
        if Competition doesn't exist it will return < False, Participant, Null>
        if both doesn't exist it will return < False, Null , Null >
     */
      public ParticipantCompetitionTuple existingParticipantCompetition(ParticipantCompetitionDTO dto){
          Participant participant = participantRepository.findByName(dto.getParticipantName());
          Competition competition = competitionRepository.findByName(dto.getCompetitionName());
          boolean exists = participant!=null && competition!=null;
          ParticipantCompetitionTuple participantCompetitionTuple = new ParticipantCompetitionTuple();
          if(participant!=null){
              participantCompetitionTuple.setParticipant(participant);
          }
          else{
              participantCompetitionTuple.setParticipant(null);
          }
          if(competition!=null){
              participantCompetitionTuple.setCompetition(competition);
          }
          else{
              participantCompetitionTuple.setCompetition(null);
          }
          participantCompetitionTuple.setExists(exists);
          return participantCompetitionTuple;
      }


}
