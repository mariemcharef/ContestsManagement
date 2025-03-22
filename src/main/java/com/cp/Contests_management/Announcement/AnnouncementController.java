package com.cp.Contests_management.Announcement;

import com.cp.Contests_management.ApiResponse;
import com.cp.Contests_management.Competition.Competition;
import com.cp.Contests_management.Competition.CompetitionNotFoundException;
import com.cp.Contests_management.Competition.CompetitionService;
import com.cp.Contests_management.Competition.ICompetitionService;
import com.cp.Contests_management.Problem.Problem;
import com.cp.Contests_management.Problem.ProblemDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;


@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/announcements")
public class AnnouncementController {
    private final AnnouncementService announcementService;
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllAnnouncements() {
        List<Announcement> announcements = announcementService.getAllAnnouncements();
        List<AnnouncementDTO> convertedAnnouncements = announcementService.getConvertedAnnouncements(announcements);
        return ResponseEntity.ok(new ApiResponse("success", convertedAnnouncements));
    }

    @GetMapping("/{Id}/announcement")
    public ResponseEntity<ApiResponse> getAnnouncementById(@PathVariable Long Id) {
        try {
            Announcement announcement = announcementService.getAnnouncementById(Id);
            var announcementDto = announcementService.convertToDto(announcement);
            return ResponseEntity.ok(new ApiResponse("success", announcementDto));
        } catch (AnnouncementNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCompetition(@RequestBody AnnouncementAddRequest request){
        try {
            Announcement announcement =announcementService.addAnnouncement(request);
            AnnouncementDTO announcementDto = announcementService.convertToDto(announcement);
            return ResponseEntity.ok(new ApiResponse("success", announcementDto));
        } catch (AnnouncementNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));        }
    }
    @PutMapping("/{id}/update")
    public ResponseEntity<ApiResponse> updateCompetition(@RequestBody AnnouncementUpdateRequest request, @PathVariable Long id){
        try {
            Announcement announcement = announcementService.updateAnnouncement(request, id);
            AnnouncementDTO announcementDto = announcementService.convertToDto(announcement);
            return ResponseEntity.ok(new ApiResponse("success", announcementDto));
        } catch (AnnouncementNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }

    }
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<ApiResponse> deleteCompetition(@PathVariable Long id){
        try {
            announcementService.deleteAnnouncement(id);
            return ResponseEntity.ok(new ApiResponse("success",null));
        } catch (AnnouncementNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
    @GetMapping("/competition/{id}")
    public ResponseEntity<ApiResponse> getAnnouncementByCompetitionId(@PathVariable Long id){
        try {
            List<Announcement> announcements = announcementService.getAnnouncementByCompetition(id);
            List<AnnouncementDTO> announcementDTOS = announcementService.getConvertedAnnouncements(announcements);
            return ResponseEntity.ok(new ApiResponse("success", announcementDTOS));
        } catch (AnnouncementNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }



}
