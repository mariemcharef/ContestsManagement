package com.cp.Contests_management.Announcement;

import com.cp.Contests_management.Competition.Competition;
import com.cp.Contests_management.Competition.CompetitionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AnnouncementService  {
    private final AnnouncementRepository announcementRepository;
    private final CompetitionRepository competitionRepository;
    private final ModelMapper modelMapper;


    public Announcement addAnnouncement(AnnouncementRequest request ,Integer competitionId) {
        Competition competition = competitionRepository
                .findById(competitionId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"competition not found"));
        Announcement announcement = new Announcement();
        announcement.setContent(request.getContent());
        announcement.setCompetition(competition);
        return announcementRepository.save(announcement);
    }

    public Announcement updateAnnouncement(AnnouncementRequest request, Integer announcement_id) {

        Announcement announcement= announcementRepository.findById(announcement_id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Announcement not found"));
        if(request.getContent()!=null){
            announcement.setContent(request.getContent());
        }
        return announcementRepository.save(announcement);
    }


    public void deleteAnnouncement(Integer id) {
        announcementRepository.findById(id)
                .ifPresentOrElse(announcementRepository::delete,() -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Announcement Not Found");
                });
    }


    public List<Announcement> getAllAnnouncements() {

        return announcementRepository.findAll();
    }


    public List<Announcement> getAnnouncementByCompetition(Integer competitionId) {
        return announcementRepository.findByCompetitionId(competitionId);
    }



    public List<AnnouncementDTO> getConvertedAnnouncements(List<Announcement> announcements) {
        return announcements.stream().map(this::convertToDto).toList();
    }


    public AnnouncementDTO convertToDto(Announcement announcement) {
        return  modelMapper.map(announcement, AnnouncementDTO.class);
    }

    public Announcement getAnnouncementById(Integer id) {
        return announcementRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Announcement not found"));
    }
}
