package com.cp.Contests_management.User;

import com.cp.Contests_management.ApiResponse;
import com.cp.Contests_management.Competition.Competition;
import com.cp.Contests_management.Competition.CompetitionDTO;
import com.cp.Contests_management.Competition.CompetitionService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {
    private final UserService userService;
    private final CompetitionService competitionService;
    private final UserRepository userRepository;


    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return userService.getConvertedUsers(users);
    }

    @GetMapping("/{userId}")
    public UserDTO getUserById(@PathVariable Integer userId) {
            User user = userService.getUserById(userId);
            return userService.convertToDto(user);
    }

    @GetMapping("/search/{name}")
    public List<UserDTO> searchUsersByName(@PathVariable String name){
        List<User> users = userService.getUsersByName(name);
        return userService.getConvertedUsers(users);
    }


    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable Integer userId) {
        userService.deleteUserById(userId);

    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public void deleteUserByName(@RequestHeader("Authorization") String authHeader){

        userService.deleteUserByToken(authHeader);
    }

    @GetMapping("/by-name/{name}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUserByName(@PathVariable String name) {
            User user = userService.getUserByName(name);
            return userService.convertToDto(user);
    }

    @GetMapping("/competitions/{userid}")
    @ResponseStatus(HttpStatus.OK)
    public List<CompetitionDTO> getAllUserCompetitions(@PathVariable Integer userid) {
        List<Competition> competitions = userService.getAllUserCompetitions(userid);
        return competitionService.getConvertedCompetitions(competitions);
    }

    @PutMapping("/{userId}/update")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO updateUser(@RequestBody @Valid UserUpdateRequest request, @PathVariable Integer userId) {

        User user = userService.updateUser( request, userId);
        return userService.convertToDto(user);
    }

    @PostMapping("/changePassword")
    @ResponseStatus(HttpStatus.OK)
    public void updateUserPassword(
            @Valid @RequestBody PasswordChangeRequest request,
            @RequestHeader("Authorization") String authHeader) {

        userService.updatePassword(authHeader, request);

    }
    @PatchMapping("/{userId}/updateRating")
    @ResponseStatus(HttpStatus.OK)
    public void updateUserRating(@RequestBody @Valid UserUpdateRequest request, @PathVariable Integer userId) {

        userService.updateRating(userId, request.getRating());

    }
    @GetMapping("/username")
    public UserDTO getUsername(@RequestParam  String email) {
        User user = userService.getUserNameByEmail(email);

        return userService.convertToDto(user);
    }


}
