package com.cp.Contests_management.User;

import com.cp.Contests_management.Competition.Competition;
import com.cp.Contests_management.Participant.Participant;
import com.cp.Contests_management.Participant.ParticipantRepository;
import com.cp.Contests_management.Participant.ParticipantService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService  {
    private final UserRepository userRepository;
    private final ParticipantService participantService;
    private final ModelMapper modelMapper;
    private final ParticipantRepository participantRepository;

    @Transactional

    public User createUser(UserAddRequest request) {
        if (userRepository.existsByName(request.getName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User name already exists");
        }
        return Optional.of(request)
                .filter(user -> !userRepository.existsByEmail(request.getEmail()))
                .map(req -> {
                    User user = new User();
                    user.setEmail(request.getEmail());
                    user.setName(request.getName());
                    user.setPassword(request.getPassword());
                    userRepository.save(user);
                    participantService.createParticipant(request.getName(), user);
                    return user;
                }).orElseThrow(() -> new KeyAlreadyExistsException(request.getEmail() + " already exists"));
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    public User getUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }


    public User getUserByName(String name) {
        List<User> users = userRepository.findAllByName(name);
        if(users.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        if(users.size() > 1) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Multiple users found with the same name");
        }
        return users.get(0);
    }
    @Transactional

    public void deleteUserById(Integer id) {
        User user = getUserById(id);
        List<Participant> participantsOfDeletedUser=user.getParticipants();
        for(Participant participant : participantsOfDeletedUser) {
            participant.getUsers().remove(user);
            if(participant.getUsers().isEmpty()) {
                participantRepository.delete(participant);
            }
        }
        userRepository.delete(user);
    }
    @Transactional

    public void deleteUserByName(String name){
        User user = getUserByName(name);
        List<Participant> participantsOfDeletedUser=user.getParticipants();
        for(Participant participant : participantsOfDeletedUser) {
            participant.getUsers().remove(user);
            if(participant.getUsers().isEmpty()) {
                participantRepository.delete(participant);
            }
        }
        userRepository.delete(user);
    }


    public List<Participant> getAllUserParticipants(Integer id){
        User user = getUserById(id);
        return user.getParticipants();
    }

    public List<Competition> getAllUserCompetitions(Integer id){
        User user = getUserById(id);
        return user.getCompetitions();
    }


    public User updateUser(UserUpdateRequest request, Integer id) {
        return userRepository.findById(id)
                .map(old -> {
                    old.setRating(request.getRating());
                    old.setPassword(request.getPassword());
                    return userRepository.save(old);})
                .orElseThrow( ()->new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public User updateRating(Integer id, int rating){
        return userRepository.findById(id)
                .map(old -> {
                    old.setRating(rating);
                    return userRepository.save(old);})
                .orElseThrow( ()->new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }


    public User updatePassword(Integer id, String password){
        if(password.length() < 6 ){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password must be at least 6 characters long.");
        }
        return userRepository.findById(id)
                .map(old -> {
                    old.setPassword(password);
                    return userRepository.save(old);})
                .orElseThrow( ()->new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }


    public List<User> getUsersByName(String name) {
        List<User> users = userRepository.findByNameContaining(name);
        if(users.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user found with");

        }
        return users;
    }


    public List<UserDTO> getConvertedUsers(List<User> users) {
        return users.stream().map(this::convertToDto).toList();
    }


    public UserDTO convertToDto(User user) {
        return  modelMapper.map(user, UserDTO.class);
    }


}