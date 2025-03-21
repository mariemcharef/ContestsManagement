package com.cp.Contests_management.Announcement;

import com.cp.Contests_management.Competition.Competition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnnouncementRepository extends JpaRepository<Announcement,Long> {
    List<Announcement> findByCompetition(Competition competition);
    Long countByCompetition(Competition competition);
}
