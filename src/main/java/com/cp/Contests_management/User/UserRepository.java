package com.cp.Contests_management.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);


    boolean existsByName(@NotEmpty(message="Name cannot be empty!") String userName);

    boolean existsByEmail(@Email(message="Please enter a valid email!") @NotEmpty(message="Email cannot be empty!") String email);

    List<User> findAllByName(String name);

    List<User> findByNameContaining(String name);

    User findByName(String userName);
}
