package com.cp.Contests_management.Announcement;

import com.cp.Contests_management.Competition.Competition;
import com.cp.Contests_management.Problem.Problem;
import com.cp.Contests_management.Problem.ProblemDTO;

import java.util.List;

public interface IAnnouncementService {//in general we use the interface to access these methods from the controller
    Announcement addAnnouncement(AnnouncementAddRequest announcementRequest);
    Announcement getAnnouncementById(Long id);
    Announcement updateAnnouncement(AnnouncementUpdateRequest announcement, Long id);
    void deleteAnnouncement(Long id);
    List<Announcement> getAllAnnouncements();
    List<Announcement> getAnnouncementByCompetition(Long competitionId);

    List<AnnouncementDTO> getConvertedAnnouncements(List<Announcement> announcements);

    AnnouncementDTO convertToDto(Announcement announcement);

}
