package com.cp.Contests_management.Competition;

import com.cp.Contests_management.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}")
public class CompetitionController {

    private final CompetitionService competitionService;

    @PostMapping("/competitions/{creator_id}")
    public CompetitionDTO createCompetition(@PathVariable("creator_id")Integer creator_id,
                                            @Valid @RequestBody CompetitionAddRequest request){
        Competition competition = competitionService.createCompetition(creator_id, request);
        return competitionService.convertToDto(competition);
    }

    @GetMapping("/public/competitions")
        public List<CompetitionDTO> getAllCompetitions() {
            List<Competition> Competitions = competitionService.getAllCompetitions();
            return competitionService.getConvertedCompetitions(Competitions);
    }

    @GetMapping("/public/competitions/{competition_id}")
    public CompetitionDTO getCompetitionById(@PathVariable Integer competition_id) {
        Competition competition = competitionService.getCompetitionById(competition_id);
        return competitionService.convertToDto(competition);

    }

    @GetMapping("/competitions/search/{competition_name}")
    public CompetitionDTO getCompetitionByName(@PathVariable String competition_name ){
         Competition competition=competitionService.getCompetitionByName(competition_name);
         return competitionService.convertToDto(competition);
    }

    //ensure that the person who want to update is the creator
    @PutMapping("/competitions/{competition_id}")
    public CompetitionDTO updateCompetition(@Valid @RequestBody CompetitionUpdateRequest request,
                                            @PathVariable Integer competition_id){

            Competition competition = competitionService.updateCompetition(request,competition_id);
            return competitionService.convertToDto(competition);
    }

    @DeleteMapping("/competitions/{CId}")
    public void deleteCompetition(@PathVariable Integer CId){

        competitionService.deleteCompetition(CId);

    }




}
