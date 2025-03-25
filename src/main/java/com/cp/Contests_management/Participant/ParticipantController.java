package com.cp.Contests_management.Participant;

import com.cp.Contests_management.ApiResponse;
import com.cp.Contests_management.AppUser.AppUser;
import com.cp.Contests_management.AppUser.AppUserDTO;
import com.cp.Contests_management.AppUser.AppUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/participants")
public class ParticipantController {
    private final ParticipantRepository participantRepository;
    private final IParicipantService participantService;

    private final AppUserService appUserService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllParticipants() {
        List<Participant> participants = participantService.getAllParticipants();
        List<ParticipantDTO> convertedToDto = participantService.getConvertedParticipants(participants);
        return ResponseEntity.ok(new ApiResponse("success", convertedToDto));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getParticipantById(@PathVariable Long id) {
        try {
            Participant participant = participantRepository.getParticipantById(id);
            var participantDTO = participantService.convertToDto(participant);
            return ResponseEntity.ok(new ApiResponse("success", participantDTO));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), e));
        }
    }
    @GetMapping("/by/name")
    public ResponseEntity<ApiResponse> getParticipantByName(@RequestParam String name) {
        try {
            Participant participant = participantService.getParticipantByName(name);
            var participantDTO = participantService.convertToDto(participant);
            return ResponseEntity.ok(new ApiResponse("success", participantDTO));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), e));
        }
    }
    @PutMapping("/{id}/update")
    public ResponseEntity<ApiResponse> updateParticipant(@PathVariable Long id, @RequestBody ParticipantUpdateRequest request) {
        try {
            Participant participant= participantService.updateParticipant(request,id);
            var participantDTO = participantService.convertToDto(participant);
            return ResponseEntity.ok(new ApiResponse("success", participantDTO));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), e));
        }
    }
    @PostMapping("/team")
    public ResponseEntity<ApiResponse> createTeam(@RequestBody ParticipantAddRequest request){
        try {
            Participant participant=participantService.createTeam(request);
            var participantDTO = participantService.convertToDto(participant);
            return ResponseEntity.ok(new ApiResponse("success", participantDTO));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), e));
        }
    }
    @PutMapping("{Pid}/addUser/{Uid}")
    public ResponseEntity<ApiResponse> addUser(@PathVariable Long Pid, @PathVariable Long Uid){
        try {
            Participant participant = participantService.getParticipantById(Pid);
            AppUser appUser=appUserService.getAppUserById((Uid));
            participantService.addAppUserToTeam(participant,appUser);
            var participantDTO = participantService.convertToDto(participant);
            return ResponseEntity.ok(new ApiResponse("success", participantDTO));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), e));
        }
    }
    @DeleteMapping("{Pid}/deleteUser/{Uid}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long Pid, @PathVariable Long Uid){
        try {
            Participant participant = participantService.getParticipantById(Pid);
            AppUser appUser=appUserService.getAppUserById((Uid));
            participantService.deleteAppUserFromTeam(participant,appUser);
            var participantDTO = participantService.convertToDto(participant);
            return ResponseEntity.ok(new ApiResponse("success", participantDTO));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), e));
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParticipant(@PathVariable Long id) {
        participantService.deleteParticipant(id);
        return ResponseEntity.noContent().build();
    }

}
