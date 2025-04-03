package com.cp.Contests_management.Competition;

import com.cp.Contests_management.Announcement.Announcement;
import com.cp.Contests_management.User.User;
import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
public interface CompetitionRepository extends JpaRepository<Competition, Integer> {

    Competition findByName(String name);

    List<Competition> findAllByName(String name);
    List<Competition> getCompetitionsByUser(User user);

    boolean existsByName(String name);
}
