package com.cp.Contests_management.Announcement;

import com.cp.Contests_management.Competition.Competition;

import java.util.List;

public interface IAnnouncementService {
    Announcement addAnnouncement(AnnouncementAddRequest announcementRequest);
    Announcement getAnnouncementById(Long id);
    Announcement updateAnnouncement(AnnouncementUpdateRequest announcement, Long id);
    void deleteAnnouncement(Long id);
    List<Announcement> getAllAnnouncements();
    List<Announcement> getAnnouncementByCompetition(Competition competition);
    Long countAllAnnouncementsByCompetition(Competition competition);
}
