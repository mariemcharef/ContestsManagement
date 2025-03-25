package com.cp.Contests_management.AppUser;

import com.cp.Contests_management.Participant.ParticipantService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppUserService implements IAppUserService {
    private final AppUserRepository appUserRepository;
    private final ParticipantService participantService;
    private final ModelMapper modelMapper;
    @Override
    public AppUser getAppUserById(Long id) {
        return appUserRepository.findById(id).orElseThrow(()->new AppUserNotFoundException("User not found"));
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
    @Transactional
    public AppUser createAppUser(AppUserAddRequest request){
        if (getAppUserByName(request.getName()) != null){
            throw new RuntimeException("User name already exists");
        }
        if (getAppUserByName(request.getEmail()) != null){
            throw new RuntimeException("Email already exists");
        }
        return Optional.of(request)
                .filter(user->!appUserRepository.existsByEmail(request.getEmail()))
                .map(req ->{
                    AppUser appUser = new AppUser();
                    appUser.setEmail(request.getEmail());
                    appUser.setName(request.getName());
                    appUser.setPassword(request.getPassword());
                    appUserRepository.save(appUser);
                    participantService.createParticipant(request.getName(),appUser);
                    return appUser;
                }).orElseThrow(()->new KeyAlreadyExistsException(request.getEmail()+" already exists"));
    }
    @Override
    public AppUser updateAppUser(AppUserUpdateRequest request, Long id) {
        return appUserRepository.findById(id)
                .map(old -> {
                    old.setRating(request.getRating());
                return appUserRepository.save(old);})
                .orElseThrow(()->new AppUserNotFoundException("User Not Found"));
    }

    @Override
    public void deleteAppUser(Long id) {
        appUserRepository.findById(id).ifPresentOrElse(appUserRepository::delete,()->{
            throw new AppUserNotFoundException("User not found");
        } );
    }

    @Override
    public List<AppUserDTO> getConvertedAppUsers(List<AppUser> appUsers) {
        return appUsers.stream().map(this::convertToDto).toList();
    }

    @Override
    public AppUserDTO convertToDto(AppUser appUser) {
        return  modelMapper.map(appUser, AppUserDTO.class);
    }
}
