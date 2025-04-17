package com.cp.Contests_management.Participant;


import com.cp.Contests_management.User.User;
import com.cp.Contests_management.User.UserDTO;
import com.cp.Contests_management.User.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/participants")
public class ParticipantController {
    private final ParticipantRepository participantRepository;
    private final ParticipantService participantService;
    private final UserService userService;


    @PostMapping("")
    public ParticipantDTO createTeam(@Valid @RequestBody ParticipantAddRequest request,@RequestParam String userName){
        Participant participant=participantService.createTeam(request,userName);
        return participantService.convertToDto(participant);
    }

    @GetMapping("/{id}")
    public ParticipantDTO getParticipantById(@PathVariable Integer id) {
            Participant participant = participantRepository.getParticipantById(id);
            return participantService.convertToDto(participant);
    }

    @GetMapping("/byName/{name}")
    public ParticipantDTO getParticipantByName(@PathVariable String name) {
            Participant participant = participantService.getParticipantByName(name);
            return participantService.convertToDto(participant);
    }
    @GetMapping("/getParticipantsByUser/{name}")
    public List<ParticipantDTO> getParticipantsByUser(@PathVariable String name){
        List<Participant> participants=participantService.getParticipantsByUserName(name);
        return  participantService.getConvertedParticipants(participants);

    }

    @GetMapping("")
    public List<ParticipantDTO> getAllParticipants() {
        List<Participant> participants = participantService.getAllParticipants();
        return participantService.getConvertedParticipants(participants);
    }

    @GetMapping("/membres/{participantId}")
    public List<UserDTO> getMembres(@PathVariable Integer participantId) {
        Participant participant = participantService.getParticipantById(participantId);
        List<User> users = participant.getUsers();
        return userService.getConvertedUsers(users);
    }

    @DeleteMapping("/{id}")
    public void deleteParticipant(@PathVariable Integer id) {
        participantService.deleteParticipantById(id);
    }

    @Transactional
    @PatchMapping("/delete-user/{user_name}/{participant_id}")
    public void deleteUserFromTeam(@PathVariable String user_name, @PathVariable Integer participant_id){
        participantService.deleteUserFromTeam(user_name,participant_id);
    }

    @Transactional
    @PatchMapping("/addUserToParticipants/{user_name}/{participant_id}")
    public void addUser(@PathVariable Integer participant_id, @PathVariable String user_name){
        participantService.addUserToTeam(user_name,participant_id);
    }

    @PatchMapping("/changeTeamName/{participant_id}")
    public ParticipantDTO changeTeamName(@PathVariable Integer participant_id,@Valid @RequestBody ParticipantUpdateRequest request){
        Participant participant = participantService.updateParticipant(request,participant_id);
        return participantService.convertToDto(participant);
    }

}
