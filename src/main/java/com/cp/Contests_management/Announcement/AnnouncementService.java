package com.cp.Contests_management.Announcement;

import com.cp.Contests_management.Competition.Competition;
import com.cp.Contests_management.Competition.CompetitionNotFoundException;
import com.cp.Contests_management.Competition.CompetitionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AnnouncementService implements IAnnouncementService {
    private final AnnouncementRepository announcementRepository;
    private final CompetitionRepository competitionRepository;
    private final ModelMapper modelMapper;
    private Announcement createAnnouncement(AnnouncementAddRequest request) {

        Competition competition = competitionRepository.findById(request.getCompetitionId())
                .orElseThrow(() -> new CompetitionNotFoundException("Competition not found with id: " + request.getCompetitionId()));
        Announcement announcement = new Announcement();
        announcement.setContent(request.getContent());
        announcement.setCompetition(competition);
        return announcement;
    }
    @Override
    public Announcement addAnnouncement(AnnouncementAddRequest request) {
        return announcementRepository.save(createAnnouncement(request));
    }
    public Announcement updateExistingAnnouncement(AnnouncementUpdateRequest request, Long id) {

        Announcement announcement= announcementRepository.findById(id).orElseThrow(()->new AnnouncementNotFoundException("Announcement not found"));
        if(request.getContent()!=null){
            announcement.setContent(request.getContent());
        }
        return announcement;
    }
    @Override
    public Announcement getAnnouncementById(Long id) {
        return announcementRepository.findById(id).orElseThrow(()->new AnnouncementNotFoundException("Announcement Not Found"));
    }

    @Override
    public Announcement updateAnnouncement(AnnouncementUpdateRequest request, Long id) {
        return announcementRepository.findById(id).map(old -> updateExistingAnnouncement(request,id)).map(announcementRepository :: save).orElseThrow(()->new AnnouncementNotFoundException("Announcement Not Found"));
    }

    @Override
    public void deleteAnnouncement(Long id) {
        announcementRepository.findById(id)
                .ifPresentOrElse(announcementRepository::delete,() -> {throw new AnnouncementNotFoundException("Announcement Not Found");});
    }

    @Override
    public List<Announcement> getAllAnnouncements() {
        return announcementRepository.findAll();
    }

    @Override
    public List<Announcement> getAnnouncementByCompetition(Long competitionId) {
        return announcementRepository.findByCompetitionId(competitionId);
    }


    @Override
    public List<AnnouncementDTO> getConvertedAnnouncements(List<Announcement> announcements) {
        return announcements.stream().map(this::convertToDto).toList();
    }

    @Override
    public AnnouncementDTO convertToDto(Announcement announcement) {
        return  modelMapper.map(announcement, AnnouncementDTO.class);
    }

}
