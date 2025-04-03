package com.cp.Contests_management.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByName(String name);
    boolean existsByEmail(String email);
    boolean findByEmail(String email);

    boolean existsByName(String name);


    List<User> findAllByName(String name);

    List<User> findByNameContaining(String name);
}
