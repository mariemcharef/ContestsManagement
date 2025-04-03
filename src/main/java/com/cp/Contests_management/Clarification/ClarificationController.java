package com.cp.Contests_management.Clarification;

import com.cp.Contests_management.User.User;
import com.cp.Contests_management.User.UserDTO;
import com.cp.Contests_management.User.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/clarifications")
public class ClarificationController {
    private final ClarificationService clarificationService;
    private final UserService userService;

    @GetMapping("/all")
    public List<ClarificationDTO> getAllClarifications() {
        List<Clarification> clarifications = clarificationService.getAllClarifications();
        return clarificationService.getConvertedClarifications(clarifications);
    }

    @GetMapping("/{clarification_id}")
    public ClarificationDTO getClarificationById(@PathVariable Integer clarification_id) {

        Clarification clarification = clarificationService.getClarificationById(clarification_id);
        return clarificationService.convertToDto(clarification);

    }

    @PostMapping("/{problem_id}/{participant_id}/add")
    public ClarificationDTO addClarification(@Valid @RequestBody ClarificationRequest request,
                                             @PathVariable("problem_id") Integer problem_id,
                                            @PathVariable("participant_id") Integer participant_id ) {

            Clarification clarification =clarificationService.addClarification(request,participant_id,problem_id);
            return clarificationService.convertToDto(clarification);

    }

    @DeleteMapping("/{clarification_id}/delete")
    public void deleteClarification(@PathVariable Integer clarification_id){

            clarificationService.deleteClarification(clarification_id);

    }
    @GetMapping("/by_problem/{problem_id}")
    public List<ClarificationDTO> getClarificationByProblemId(@PathVariable Integer problem_id){

        List<Clarification> clarifications = clarificationService.getClarificationByProblem(problem_id);
        return clarificationService.getConvertedClarifications(clarifications);

    }
    @GetMapping("/{clarification_id}/whoWillReceive")
    public UserDTO whoWillReceive(@PathVariable Integer clarification_id){
        User user=clarificationService.getUserIdForClarificationRecipient(clarification_id);
        return userService.convertToDto(user);
    }

}
