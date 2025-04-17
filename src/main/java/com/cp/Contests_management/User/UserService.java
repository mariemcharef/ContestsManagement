package com.cp.Contests_management.User;

import com.cp.Contests_management.Competition.Competition;
import com.cp.Contests_management.Participant.Participant;
import com.cp.Contests_management.Participant.ParticipantRepository;
import com.cp.Contests_management.Participant.ParticipantService;
import com.cp.Contests_management.Security.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;

import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

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
    public User getUserNameByEmail(String email) {
       User user= userRepository.findByEmail(email)
               .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
       return user;
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
        user.getCompetitions().forEach(competition -> competition.setUser(null));
        userRepository.delete(user);
    }
    @Transactional
//******************************************
    public void deleteUserByToken(String jwtToken){
        try {
            String email=jwtService.getEmailFromToken(jwtToken);
            System.out.println(email);
            User user = userRepository.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
            List<Participant> participantsOfDeletedUser=user.getParticipants();
            for(Participant participant : participantsOfDeletedUser) {
                participant.getUsers().remove(user);
                if(participant.getUsers().isEmpty()) {
                    participantRepository.delete(participant);
                }
            }
            user.getCompetitions().forEach(competition -> competition.setUser(null));
            userRepository.delete(user);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid authentification");
        }
    }


    public List<Participant> getAllUserParticipants(Integer id){
        User user = getUserById(id);
        return user.getParticipants();
    }

    public List<Competition> getAllUserCompetitions(Integer id){
        User user = getUserById(id);
        return user.getCompetitions();
    }
    public Integer getRating(String name){
        User user=getUserByName(name);
        return user.getRating();
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

    @Transactional
    public void updatePassword(String authJwt, PasswordChangeRequest request){
        try{
            String email=jwtService.getEmailFromToken(authJwt);
            User user=userRepository.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
            if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Password is incorrect");
            }
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            userRepository.save(user);
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to change password!");
        }

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