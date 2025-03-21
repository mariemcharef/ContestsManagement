package com.cp.Contests_management.Announcement;

import com.cp.Contests_management.Competition.Competition;
import com.cp.Contests_management.Competition.CompetitionNotFoundException;
import com.cp.Contests_management.Competition.CompetitionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AnnouncementService implements IAnnouncementService {
    private final AnnouncementRepository announcementRepository;
    private final CompetitionRepository competitionRepository;
    @Override
    public Announcement addAnnouncement(AnnouncementAddRequest announcementRequest) {
        //check if the competition is found in the db
        //if yes , set it as the new product competition
        //if no , return error
        Competition competition = Optional.ofNullable(competitionRepository.findByName(announcementRequest.getCompetition().getName()))
                .orElseThrow(() -> new CompetitionNotFoundException("Competition Not Found"));
        announcementRequest.setCompetition(competition);

        return announcementRepository.save(createAnnouncement(announcementRequest,competition));
    }

    private Announcement createAnnouncement(AnnouncementAddRequest request, Competition competition) {
        return new Announcement(
                request.getContent(),
                competition
        );
    }
    private Announcement updateExistingAnnouncement(Announcement announcement,AnnouncementUpdateRequest request){
        announcement.setContent(request.getContent());
        return announcement;
    }
    @Override
    public Announcement getAnnouncementById(Long id) {
        return announcementRepository.findById(id).orElseThrow(()->new AnnouncementNotFoundException("Announcement Not Found"));
    }

    @Override
    public Announcement updateAnnouncement(AnnouncementUpdateRequest request, Long id) {
        return announcementRepository.findById(id).map(announcement -> updateExistingAnnouncement(announcement,request)).map(announcementRepository :: save).orElseThrow(()->new AnnouncementNotFoundException("Announcement Not Found"));
    }

    @Override
    public void deleteAnnouncement(Long id) {
        announcementRepository.findById(id).ifPresentOrElse(announcementRepository::delete,() -> {throw new AnnouncementNotFoundException("Announcement Not Found");});
    }

    @Override
    public List<Announcement> getAllAnnouncements() {
        return announcementRepository.findAll();
    }

    @Override
    public List<Announcement> getAnnouncementByCompetition(Competition competition) {
        return announcementRepository.findByCompetition(competition);
    }



    @Override
    public Long countAllAnnouncementsByCompetition(Competition competition) {
        return announcementRepository.countByCompetition(competition);
    }
}
