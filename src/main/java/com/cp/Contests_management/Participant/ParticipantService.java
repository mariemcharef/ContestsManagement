package com.cp.Contests_management.Participant;

import com.cp.Contests_management.User.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParticipantService {

    private final ParticipantRepository participantRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    public Participant createParticipant(String name, User User){//single user created automatically when i create user and have the same name

        Participant participant = new Participant();
        participant.setName(name);
        List<User> Users = new ArrayList<>();
        Users.add(User);

        participant.setUsers(Users);


        return participantRepository.save(participant);
    }

    public Participant getParticipantById(Integer id) {
        return participantRepository
                .findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Participant not found"));
    }

    public Participant getParticipantByName(String name) {

        return participantRepository.findByName(name);
    }

    public List<Participant> getAllParticipants() {

        return participantRepository.findAll();
    }
    public List<Participant> getParticipantsByUserName(String userName) {
        User user=userRepository.findByName(userName);
        return user.getParticipants();
    }

    @Transactional

    public Participant createTeam(ParticipantAddRequest request, String TeamCreator){

        if(participantRepository.findByName(request.getName())!=null){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Team name already exists");
        }
        Participant participant = new Participant();
        participant.setName(request.getName());

        List<User> users = new ArrayList<>();
        User createUser =userRepository.findByName(TeamCreator);
        if(createUser==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        users.add(createUser);
        createUser.getParticipants().add(participant);
        userRepository.save(createUser);
        // the user who create the team will be added to list

        participant.setUsers(users);
        return participantRepository.save(participant);

    }


    public Participant updateParticipant(ParticipantUpdateRequest request, Integer id) {
        //updates only for team name
        if(request.getName()!=null){
            return participantRepository.findById(id)
                    .map(old -> {
                        old.setName(request.getName());
                        return participantRepository.save(old);
                    }).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        }
        return participantRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Participant not found"));

    }


    public void deleteParticipantById(Integer id) {
       Participant participant = getParticipantById(id);
       if(participant.getUsers().size()==1){
           User user = participant.getUsers().get(0);
           if(user.getName().equals(participant.getName())){
               throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Cannot delete default participant team");
           }
       }
        participantRepository.delete(participant);
    }

    @Transactional

    public void addUserToTeam( String username,Integer participantId){
        User existingUser = userRepository.findByName(username);
        if(existingUser==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        Participant existingParticipant = participantRepository.findById(participantId)
                .orElseThrow(() -> new EntityNotFoundException("Team not found with id: " + participantId)) ;
        if(existingParticipant.getUsers().contains(existingUser)){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists in this team");
        }
        if(existingParticipant.getUsers().size()==3){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"team size too large");
        }
        String participantName = existingParticipant.getName();
        if(userRepository.existsByName(participantName)){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cannot add user to default participant of another user");
        }
        existingParticipant.getUsers().add(existingUser);
        existingUser.getParticipants().add(existingParticipant);
        participantRepository.save(existingParticipant);
        userRepository.save(existingUser);

    }
    @Transactional
    public void deleteUserFromTeam(String userName,Integer participantId){
        User existingUser = userRepository.findByName(userName);
        if(existingUser==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Membre not found");
        }
        Integer userId = existingUser.getId();
        Participant existingParticipant = getParticipantById(participantId);
        if(!existingParticipant.getUsers().contains(existingUser)){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User does not exist in this team");
        }
        if(existingParticipant.getUsers().size()<2){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Cannot delete default user");
        }

        existingParticipant.getUsers().removeIf(user -> user.getId().equals(userId));
        existingUser.getParticipants().removeIf(p -> p.getId().equals(participantId));
        participantRepository.save(existingParticipant);
        userRepository.save(existingUser);
    }


    public ParticipantDTO convertToDto(Participant participant) {
        return modelMapper.map(participant, ParticipantDTO.class);
    }

    public List<ParticipantDTO> getConvertedParticipants(List<Participant> participants){
        return participants.stream().map(this::convertToDto).toList();
    }
}
