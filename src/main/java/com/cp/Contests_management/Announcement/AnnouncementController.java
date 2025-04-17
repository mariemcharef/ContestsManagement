package com.cp.Contests_management.Announcement;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}")
public class AnnouncementController {
    private final AnnouncementService announcementService;

    @GetMapping("/public/announcements/all")
    public List<AnnouncementDTO> getAllAnnouncements() {
        List<Announcement> announcements = announcementService.getAllAnnouncements();
        return announcementService.getConvertedAnnouncements(announcements);
    }

    @GetMapping("/announcements/{Announcement_id}")
    public AnnouncementDTO getAnnouncementById(@PathVariable Integer Announcement_id) {
        Announcement announcement = announcementService.getAnnouncementById(Announcement_id);
        return announcementService.convertToDto(announcement);

    }

    @PostMapping("/announcements/{competition_id}/add")
    public AnnouncementDTO addCompetition(@RequestBody AnnouncementRequest request,@PathVariable Integer competition_id){
        Announcement announcement = announcementService.addAnnouncement(request,competition_id);
        return announcementService.convertToDto(announcement);
    }

    @PutMapping("/announcements/{announcement_id}/update")
    public AnnouncementDTO updateCompetition(@RequestBody AnnouncementRequest request, @PathVariable Integer announcement_id){
        Announcement announcement = announcementService.updateAnnouncement(request, announcement_id);
        return announcementService.convertToDto(announcement);

    }
    @DeleteMapping("/announcements/{announcement_id}/delete")
    public void deleteCompetition(@PathVariable Integer announcement_id){
       announcementService.deleteAnnouncement(announcement_id);
    }





}
