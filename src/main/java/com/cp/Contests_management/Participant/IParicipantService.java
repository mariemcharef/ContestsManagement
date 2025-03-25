package com.cp.Contests_management.Participant;


import com.cp.Contests_management.AppUser.AppUser;
import jakarta.transaction.Transactional;

import java.util.List;

public interface IParicipantService {
    Participant getParticipantById(Long id);
    Participant getParticipantByName(String name);
    List<Participant> getAllParticipants();

    Participant createParticipant(String name, AppUser appUser);//if it is a single user we create it automatically when we create appuser to permit ech user to submit

    Participant createTeam(ParticipantAddRequest request);

    Participant updateParticipant(ParticipantUpdateRequest request, Long id);

    void deleteAppUserFromTeam(Participant participant,AppUser appUser);

    @Transactional
    void addAppUserToTeam(Participant participant, AppUser appUser);

    void deleteParticipant(Long id);


    ParticipantDTO convertToDto(Participant participant);

    List<ParticipantDTO> getConvertedParticipants(List<Participant> participants);
}
