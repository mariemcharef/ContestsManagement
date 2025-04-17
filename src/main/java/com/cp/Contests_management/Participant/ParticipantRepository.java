package com.cp.Contests_management.Participant;

import com.cp.Contests_management.User.User;
import com.cp.Contests_management.User.UserDTO;
import org.aspectj.apache.bcel.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface ParticipantRepository extends JpaRepository<Participant, Integer> {
    Participant findByName(String name);

    Participant getParticipantById(Integer id);



}
