package com.cp.Contests_management.Competition;

import com.cp.Contests_management.ApiResponse;
import com.cp.Contests_management.AppUser.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/Competitions")
public class CompetitionController {

        private final ICompetitionService competitionService;
        @GetMapping("/all")
        public ResponseEntity<ApiResponse> getAllCompetitions() {
            List<CompetitionDTO> Competitions = competitionService.getAllCompetitions();
            return ResponseEntity.ok(new ApiResponse("success", Competitions));
        }
        @GetMapping("/{CId}/Competition")
        public ResponseEntity<ApiResponse> getCompetitionById(@PathVariable Long CId) {
            try {
                CompetitionDTO competition = competitionService.getCompetitionById(CId);
                return ResponseEntity.ok(new ApiResponse("success", competition));
            } catch (CompetitionNotFoundException e) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
            }
        }
        @PostMapping("/add")
        public ResponseEntity<ApiResponse> addCompetition(@RequestBody CompetitionAddRequest competition) {
            try {
                Competition theCompetition = competitionService.addCompetition(competition);
                return ResponseEntity.ok(new ApiResponse("Competition added successfully", theCompetition));
            } catch (Exception e) {
                return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
            }

        }
        @PutMapping("/{competitionId}/update")
        public ResponseEntity<ApiResponse> updateCompetition(@RequestBody CompetitionUpdateRequest request,@PathVariable Long competitionId){
            try {
                Competition competition = competitionService.updateCompetition(request,competitionId);
                return ResponseEntity.ok(new ApiResponse("success", competition));
            } catch (CompetitionNotFoundException e) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
            }
        }

        @DeleteMapping("/{CId}/delete")
        public ResponseEntity<ApiResponse> deleteCompetition(@PathVariable Long CId){
            try {
                competitionService.deleteCompetition(CId);
                return ResponseEntity.ok(new ApiResponse("success", null));
            } catch (CompetitionNotFoundException e) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
            }
        }
        @GetMapping("/by/{name}")
        public ResponseEntity<ApiResponse> getCompetitionByName(@PathVariable String name) {
            try {
                List<CompetitionDTO> competitions = competitionService.getCompetitionByName(name);
                if(competitions.isEmpty()){
                    return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(null,name));
                }
                return ResponseEntity.ok(new ApiResponse("success", competitions));
            }catch (Exception e){
                return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
            }

        }
}
