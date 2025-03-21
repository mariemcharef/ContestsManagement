package com.cp.Contests_management.AppUser;

import com.cp.Contests_management.Competition.Competition;
import com.cp.Contests_management.Competition.CompetitionAddRequest;
import com.cp.Contests_management.Competition.CompetitionNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
@RequiredArgsConstructor
public class AppUserService implements IAppUserService {
    private final AppUserRepository appUserRepository;


    @Override
    public AppUser getAppUserById(Long id) {
        return appUserRepository.findById(id).orElseThrow(()->new AppUserNotFoundException("User not found"));
    }

    public AppUser updateExistingAppUser(AppUserUpdateRequest request, Long id) {
        AppUser appUser = appUserRepository.findById(id).orElseThrow(()->new AppUserNotFoundException("User not found"));
        if(request.getPassword()!=null){
            appUser.setPassword(request.getPassword());
        }
        if(request.getEmail()!=null){
            appUser.setEmail(request.getEmail());
        }
        if(request.getRating()!=0){
            appUser.setRating(request.getRating());
        }
        return appUserRepository.save(appUser);
    }

    public AppUser createAppUser(AppUserAddRequest request){
        if (getAppUserByName(request.getName()) != null){
            throw new RuntimeException("User name already exists");
        }
        AppUser appUser = new AppUser();
        appUser.setName(request.getName());
        appUser.setPassword(request.getPassword());
        appUser.setEmail(request.getEmail());
        appUser.setRating(request.getRating());
        return appUserRepository.save(appUser);
    }
    @Override
    public AppUser getAppUserByName(String name) {
        return appUserRepository.findByName(name);
    }

    @Override
    public List<AppUser> getAllAppUsers() {
        return appUserRepository.findAll();
    }

    @Override
    public AppUser addAppUser(AppUserAddRequest request) {
        return appUserRepository.save(createAppUser(request));    }

    @Override
    public AppUser updateAppUser(AppUserUpdateRequest request, Long id) {
        return appUserRepository.findById(id).map(old -> updateExistingAppUser(request,id)).map(appUserRepository :: save).orElseThrow(()->new AppUserNotFoundException("User Not Found"));
    }

    @Override
    public void deleteAppUser(Long id) {
        appUserRepository.findById(id).ifPresentOrElse(appUserRepository::delete,()->{
            throw new AppUserNotFoundException("User not found");
        } );
    }
}
