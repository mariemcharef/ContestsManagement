package com.cp.Contests_management.AppUser;

import com.cp.Contests_management.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
public class AppUserController {
    private final IAppUserService appUserService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllAppUsers() {
        List<AppUser> appUsers = appUserService.getAllAppUsers();
        List<AppUserDTO> convertedAppUsers = appUserService.getConvertedAppUsers(appUsers);
        return ResponseEntity.ok(new ApiResponse("success", convertedAppUsers));
    }

    @GetMapping("/{userId}/user")
    public ResponseEntity<ApiResponse> getAppUserById(@PathVariable Long userId) {
        try {
            AppUser appUser = appUserService.getAppUserById(userId);
            var appUserDTO = appUserService.convertToDto(appUser);
            return ResponseEntity.ok(new ApiResponse("success", appUserDTO));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), e));
        }
    }
    @GetMapping("/by/name")
    public ResponseEntity<ApiResponse> getAppUserByName(@RequestParam String name) {
        try {
            AppUser appUser = appUserService.getAppUserByName(name);
            var appUserDTO = appUserService.convertToDto(appUser);
            if(appUserDTO != null) {
                return ResponseEntity.ok(new ApiResponse("success", appUserDTO));
            }
            else{
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(null,name));
            }
        } catch (AppUserNotFoundException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createUser(@RequestBody AppUserAddRequest request) {
        try {
            AppUser appUser = appUserService.createAppUser(request);
            var appUserDTO = appUserService.convertToDto(appUser);
            return ResponseEntity.ok(new ApiResponse("Create User success", appUserDTO));
        } catch (KeyAlreadyExistsException e) {
           return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), e));
        }
    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody AppUserUpdateRequest request,@PathVariable Long userId) {
        try {
            AppUser appUser = appUserService.updateAppUser( request, userId);
            var appUserDTO = appUserService.convertToDto(appUser);
            return ResponseEntity.ok(new ApiResponse("Update User success", appUserDTO));
        } catch (AppUserNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), e));
        }
    }
    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<ApiResponse> deleteAppUser(@PathVariable Long userId) {
        try {
            appUserService.deleteAppUser(userId);
            return ResponseEntity.ok(new ApiResponse("Delete User success", userId));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), e));
        }
    }


}
