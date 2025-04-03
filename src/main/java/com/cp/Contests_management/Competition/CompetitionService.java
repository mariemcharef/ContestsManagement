package com.cp.Contests_management.Competition;


import com.cp.Contests_management.User.User;
import com.cp.Contests_management.User.UserRepository;
import com.cp.Contests_management.User.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompetitionService {

    private final CompetitionRepository competitionRepository;

    private final UserRepository userRepository;
    private final UserService userService;

    private final ModelMapper modelMapper;


    public Competition getCompetitionById(Integer id) {
        return competitionRepository
                .findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Competition not found"));
    }

    public List<Competition> getAllCompetitions() {

        return competitionRepository.findAll();
    }

    public List<Competition> getCompetitionsByName(String name) {

        return competitionRepository.findAllByName(name);
    }

    public Competition getCompetitionByName(String name){
        return competitionRepository.findByName(name);
    }

    public void deleteCompetition(Integer id){
        competitionRepository.deleteById(id);
    }


    public Competition createCompetition(Integer userId, CompetitionAddRequest request){
        if(userId == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User id cannot be null");
        }
        if(getCompetitionByName(request.getName()) != null){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Competition name already exists");
        }
        User user = userService.getUserById(userId);
        Competition competition = new Competition();
        competition.setUser(user);
        competition.setName(request.getName());
        competition.setDuration(request.getDuration());
        competition.setStartTime(request.getStartTime());
        LocalDateTime endTime = request.getStartTime().plusMinutes((long) (request.getDuration() ));
        competition.setEndTime(endTime);
        if (request.getPenalty()>0.0){
            competition.setPenalty(request.getPenalty());
        }

        return competitionRepository.save(competition);
    }


    public Competition updateCompetition(CompetitionUpdateRequest request, Integer id) {
        Competition competition = getCompetitionById(id);

        if(request.getName() != null && !request.getName().equals(competition.getName())) {
            if(getCompetitionByName(request.getName()) != null) {
                throw new IllegalArgumentException("This name is already in use");
            }
            competition.setName(request.getName());
        }

        if( request.getDuration()>30.0){
            competition.setDuration(request.getDuration());
        }
        if(request.getStartTime()!=null){
            competition.setStartTime(request.getStartTime());
        }
        if(request.getPenalty()>0.0){
            competition.setPenalty(request.getPenalty());
        }

        competition.setEndTime();

        return competitionRepository.save(competition);
    }


    public List<CompetitionDTO> getConvertedCompetitions(List<Competition> competitions) {
        return competitions.stream().map(this::convertToDto).toList();
    }


    public CompetitionDTO convertToDto(Competition competition) {
        return  modelMapper.map(competition, CompetitionDTO.class);
    }

}
