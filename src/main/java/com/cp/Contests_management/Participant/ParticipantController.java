package com.cp.Contests_management.Participant;

import com.cp.Contests_management.ApiResponse;

import com.cp.Contests_management.User.User;
import com.cp.Contests_management.User.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/participants")
public class ParticipantController {
    private final ParticipantRepository participantRepository;
    private final ParticipantService participantService;


    @PostMapping("")
    public ParticipantDTO createTeam(@Valid @RequestBody ParticipantAddRequest request,@RequestParam Integer userId){
        Participant participant=participantService.createTeam(request,userId);
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

    @GetMapping("")
    public List<ParticipantDTO> getAllParticipants() {
        List<Participant> participants = participantService.getAllParticipants();
        return participantService.getConvertedParticipants(participants);
    }

    @DeleteMapping("/{id}")
    public void deleteParticipant(@PathVariable Integer id) {
        participantService.deleteParticipantById(id);
    }

    @Transactional
    @PatchMapping("/delete-user/{user_id}/{participant_id}")
    public void deleteUserFromTeam(@PathVariable Integer user_id, @PathVariable Integer participant_id){
        participantService.deleteUserFromTeam(user_id,participant_id);
    }
    @Transactional
    @PatchMapping("addUserToParticipants/{user_id}/{participant_id}")
    public void addUser(@PathVariable Integer participant_id, @PathVariable Integer user_id){
        participantService.addUserToTeam(user_id,participant_id);
    }
    @PatchMapping("/changeTeamName/{participant_id}")
    public ParticipantDTO changeTeamName(@PathVariable Integer participant_id,@Valid @RequestBody ParticipantUpdateRequest request){
        Participant participant = participantService.updateParticipant(request,participant_id);
        return participantService.convertToDto(participant);
    }

}
