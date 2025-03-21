package com.cp.Contests_management.AppUser;


import java.util.List;

public interface IAppUserService {
    AppUser getAppUserById(Long id);
    AppUser getAppUserByName(String name);
    List<AppUser> getAllAppUsers();
    AppUser addAppUser(AppUserAddRequest request);
    AppUser updateAppUser(AppUserUpdateRequest request, Long id);
    void deleteAppUser(Long id);

}
