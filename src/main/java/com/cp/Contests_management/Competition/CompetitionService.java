package com.cp.Contests_management.Competition;

import com.cp.Contests_management.Announcement.AnnouncementNotFoundException;
import com.cp.Contests_management.AppUser.AppUser;
import com.cp.Contests_management.AppUser.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompetitionService implements ICompetitionService{
    private final CompetitionRepository competitionRepository;
    private final AppUserRepository appUserRepository;
    @Override
    public CompetitionDTO getCompetitionById(Long id) {
        Competition competition= competitionRepository.findById(id).orElseThrow(()->new CompetitionNotFoundException("Competition not found"));
        return CompetitionMapper.toDTO(competition);
    }
    public Competition createCompetition(CompetitionAddRequest request){
       // if (getCompetitionByName(request.getName()) != null){
         //   throw new RuntimeException("Competition name already exists");
       // }
        AppUser appUser = appUserRepository.findById(request.getAppUserId())
                .orElseThrow(()->new RuntimeException("User not found"));
        if (request.getStartTime() == null) {
            throw new RuntimeException("startTime cannot be null");
        }
        if (request.getDuration() <= 0) {
            throw new RuntimeException("duration must be greater than 0");
        }
        Competition competition = new Competition();
        competition.setName(request.getName());
        competition.setAppUser(appUser);
        competition.setDuration(request.getDuration());
        competition.setStartTime(request.getStartTime());
        LocalDateTime endTime = request.getStartTime().plusMinutes((long) (request.getDuration() * 60));
        competition.setEndTime(endTime);
        competition.setPenalty(request.getPenalty());
        return competitionRepository.save(competition);
    }

    public Competition updateExistingCompetition(CompetitionUpdateRequest request,Long id) {
        Competition competition = competitionRepository.findById(id).orElseThrow(()->new CompetitionNotFoundException("Competition not found"));
        if(request.getDuration()!=0){
            competition.setDuration(request.getDuration());
        }
        if(request.getStartTime()!=null){
            competition.setStartTime(request.getStartTime());
        }
        LocalDateTime endTime = request.getStartTime().plusMinutes((long) (request.getDuration() * 60));
        competition.setEndTime(endTime);
        if(request.getPenalty()!=0){
            competition.setPenalty(request.getPenalty());
        }
        return competition;
    }
    @Override
    public Competition updateCompetition(CompetitionUpdateRequest request, Long id) {
        return competitionRepository.findById(id).map(old -> updateExistingCompetition(request,id)).map(competitionRepository :: save).orElseThrow(()->new CompetitionNotFoundException("Competition Not Found"));

    }
    @Override
    public List<CompetitionDTO> getCompetitionByName(String name) {
        return competitionRepository.findAllByName(name).stream().map(CompetitionMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<CompetitionDTO> getAllCompetitions() {
        return competitionRepository.findAll().stream().map(CompetitionMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public Competition addCompetition(CompetitionAddRequest request) {
        return competitionRepository.save(createCompetition(request));
    }



    @Override
    public void deleteCompetition(Long id) {
        competitionRepository.findById(id).ifPresentOrElse(competitionRepository::delete,()->{
            throw new CompetitionNotFoundException("Competition not found");
        } );
    }
}
