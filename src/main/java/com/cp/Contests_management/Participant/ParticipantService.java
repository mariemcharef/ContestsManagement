package com.cp.Contests_management.Participant;

import com.cp.Contests_management.AppUser.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class ParticipantService implements IParicipantService{

    private final ParticipantRepository participantRepository;
    private final AppUserRepository appUserRepository;

    private final ModelMapper modelMapper;
    @Override
    public Participant getParticipantById(Long id) {
        return participantRepository
                .findById(id).orElseThrow(()->new AppUserNotFoundException("Participant not found"));
    }

    @Override
    public Participant getParticipantByName(String name) {
        return participantRepository.findByName(name);
    }

    @Override
    public List<Participant> getAllParticipants() {
        return participantRepository.findAll();
    }
    @Override
    public Participant createParticipant(String name, AppUser appUser){//single user created automatically when i create user and have the same name
        Participant participant = new Participant();
        participant.setName(name);
        participant.setUserCount(1);
        List<AppUser> appUsers = new ArrayList<>();
        appUsers.add(appUser);
        participant.setAppUsers(appUsers);
        return participantRepository.save(participant);
    }
    @Override
    @Transactional
    public Participant createTeam(ParticipantAddRequest request){
        if (request == null || request.getName() == null ) {
            throw new IllegalArgumentException("Invalid participant request");
        }
        if (request.getAppUsers().isEmpty()) {
            throw new IllegalArgumentException("At least one user must be provided");
        }
        Participant participant = new Participant();
        participant.setName(request.getName());
        List<AppUser> appUsers = new ArrayList<>();
        if (request.getAppUsers() != null && !request.getAppUsers().isEmpty()) {

            for (String name : request.getAppUsers()) {
                AppUser existingUser = appUserRepository.findByName(name);
                appUsers.add(existingUser);
                existingUser.getParticipants().add(participant);
                appUserRepository.save(existingUser);
            }
            participant.setAppUsers(appUsers);
            participant.setUserCount(appUsers.size());
        }
        else{
            throw new IllegalArgumentException("Invalid participant request");
        }
        return participantRepository.save(participant);

    }

    @Override
    public Participant updateParticipant(ParticipantUpdateRequest request, Long id) {
        Participant participant=participantRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Participant not found"));
        if(participant.getUserCount()<2){
            throw new IllegalArgumentException("Invalid participant request");
        }//updates only for teams
        return participantRepository.findById(id)
                .map(old -> {
                    old.setName(request.getName());
                    return participantRepository.save(old);})
                .orElseThrow(()->new AppUserNotFoundException("Participant Not Found"));
    }
    @Override
    @Transactional
    public void deleteAppUserFromTeam(Participant participant,AppUser appUser){
        AppUser existingUser = appUserRepository.findById(appUser.getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + appUser.getId()));
        Participant existingParticipant = participantRepository.findById(participant.getId())
                .orElseThrow(() -> new EntityNotFoundException("Participant not found with id: " + participant.getId())) ;
        existingParticipant.getAppUsers().removeIf(user -> user.getId().equals(existingUser.getId()));
        existingUser.getParticipants().removeIf(p -> p.getId().equals(existingParticipant.getId()));
        existingParticipant.setUserCount(existingParticipant.getUserCount()-1);
        participantRepository.save(existingParticipant);
        appUserRepository.save(existingUser);
    }

    @Override
    @Transactional
    public void addAppUserToTeam(Participant participant, AppUser appUser){
        AppUser existingUser = appUserRepository.findById(appUser.getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + appUser.getId()));
        Participant existingParticipant = participantRepository.findById(participant.getId())
                .orElseThrow(() -> new EntityNotFoundException("Participant not found with id: " + participant.getId())) ;
        existingParticipant.getAppUsers().add(existingUser);
        existingUser.getParticipants().add(participant);
        existingParticipant.setUserCount(existingParticipant.getUserCount()+1);

         participantRepository.save(existingParticipant);
        appUserRepository.save(existingUser);

    }
    @Override
    public void deleteParticipant(Long id) {
        participantRepository.findById(id).ifPresentOrElse(participantRepository::delete,()->{
            throw new ParticipantNotFoundException("Participant not found");
        } );

    }
    @Override
    public ParticipantDTO convertToDto(Participant participant) {
        return modelMapper.map(participant, ParticipantDTO.class);
    }
    @Override
    public List<ParticipantDTO> getConvertedParticipants(List<Participant> participants){
        return participants.stream().map(this::convertToDto).toList();
    }
}
