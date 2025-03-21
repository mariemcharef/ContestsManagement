package com.cp.Contests_management.AppUser;

import com.cp.Contests_management.Competition.Competition;
import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    AppUser findByName(String name);

}
