package com.cp.Contests_management.ParticipantCompetition;

import com.cp.Contests_management.Competition.CompetitionRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParticipantCompetitionService {
    private final ParticipantCompetitionRepository participantCompetitionRepository;
    private final CompetitionRepository competitionRepository;
    private final ParticipantCompetitionMapper participantCompetitionMapper;
    /*
        This method will allow to a user to register  within
        a competition.

        If the user is the creator of the competition
        he will not be able to compete (participate) .
     */
    public ParticipantCompetitionResponseDto createParticipationInCompetition(ParticipantCompetitionDTO participantCompetitionDTO){
        ParticipantCompetitionTuple participantCompetitionTuple =  participantCompetitionMapper.existingParticipantCompetition(participantCompetitionDTO);
        if(!participantCompetitionTuple.isExists()){
            if(participantCompetitionTuple.getCompetition() == null && participantCompetitionTuple.getParticipant()==null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Participant and competition are not founds");

            }else if(participantCompetitionTuple.getCompetition()==null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Competition not found");
            }else if(participantCompetitionTuple.getParticipant()==null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Participant not found");
            }
        }
        /*
            Check if the participant is the creator of the competition
         */
        if(participantCompetitionTuple.getCompetition().getUser().getName().equals(participantCompetitionDTO.getParticipantName())){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"You are the creator of this competition," +
                    "You are not allowed to participate in this competition");
        }
        /*
            check if the participant already registered in the competition
        */
        ParticipantsCompetitionsId id = new ParticipantsCompetitionsId(
                participantCompetitionTuple.getParticipant().getId(),
                participantCompetitionTuple.getCompetition().getId()
        );
        if(participantCompetitionRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Participant already registred with this competition");

        }
        if(LocalDateTime.now().isAfter(participantCompetitionTuple.getCompetition().getStartTime())){
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Competition might be running or already finished");
        }
        /*
            If not registered, persist it then return the response
        */
        ParticipantCompetition participantCompetitionToSave=participantCompetitionRepository.save(participantCompetitionMapper.dtoToParticipantCompetition(participantCompetitionDTO));
        return participantCompetitionMapper.participantCompetitionToParticipantCompetitionResponseDto(participantCompetitionToSave);

    }
    public ParticipantCompetitionResponseDto getParticipantCompetitions(
            ParticipantCompetitionDTO participantCompetitionDTO
    ){
        ParticipantCompetitionTuple participantCompetitionTuple=participantCompetitionMapper.existingParticipantCompetition(participantCompetitionDTO);
        if(participantCompetitionTuple.isExists()){
            ParticipantsCompetitionsId id = new ParticipantsCompetitionsId(
                participantCompetitionTuple.getParticipant().getId(),
                participantCompetitionTuple.getCompetition().getId()
            );
            ParticipantCompetition participantCompetition=participantCompetitionRepository.findById(id).orElse(null);
            return participantCompetitionMapper.participantCompetitionToParticipantCompetitionResponseDto(participantCompetition);
        }
        if(participantCompetitionTuple.getCompetition()==null && participantCompetitionTuple.getParticipant()==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Competition and participant are not found");
        }else if(participantCompetitionTuple.getCompetition()==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Competition not found");
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Competition not found");
        }
    }

    public List<ParticipantCompetitionResponseDto> getAllParticipantCompetitions() {
        List<ParticipantCompetition> participantCompetitions=participantCompetitionRepository.findAll();
        return participantCompetitions.stream().map(participantCompetitionMapper::participantCompetitionToParticipantCompetitionResponseDto).collect(Collectors.toList());
    }
    public ParticipantCompetitionResponseDto getParticipantCompetitionById(
            Integer participantId,Integer competitionId
    ){
        ParticipantsCompetitionsId id = new ParticipantsCompetitionsId(participantId, competitionId);
       // participantCompetitionRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Regestration with this id not found"));
        return participantCompetitionMapper.participantCompetitionToParticipantCompetitionResponseDto(participantCompetitionRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Registration Not Found")));
    }


    public void deleteRegistration(Integer participantId, Integer competitionId) {
        ParticipantsCompetitionsId id = new ParticipantsCompetitionsId(participantId, competitionId);
        ParticipantCompetition participantCompetition = participantCompetitionRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Registration Not Found"));
        participantCompetitionRepository.delete(participantCompetition);
    }
}
