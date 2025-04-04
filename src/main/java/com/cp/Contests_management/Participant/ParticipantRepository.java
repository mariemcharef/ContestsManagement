package com.cp.Contests_management.Participant;

import org.aspectj.apache.bcel.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, Integer> {
    Participant findByName(String name);

    Participant getParticipantById(Integer id);
}
