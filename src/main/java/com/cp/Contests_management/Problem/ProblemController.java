package com.cp.Contests_management.Problem;

import com.cp.Contests_management.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}")
public class ProblemController {
   private final ProblemService problemService;

    @GetMapping("/problems/all")
    public List<ProblemDTO> getAllProblems() {
        List<Problem> Problems = problemService.getAllProblems();
        return problemService.getConvertedProblems(Problems);
    }
    @GetMapping("/public/problems/old")
    public List<ProblemDTO> getOldProblems() {
        List<Problem> Problems = problemService.getOldProblems();
        return problemService.getConvertedProblems(Problems);
    }

    @GetMapping("/public/problems/{problem_id}/problem")
    public ProblemDTO getProblemById(@PathVariable Integer problem_id) {
       Problem problem = problemService.getProblemById(problem_id);
       return problemService.convertToDto(problem);

    }

    @PostMapping("/problems/{competition_id}/add")
    public ProblemDTO addProblem(@Valid @RequestBody ProblemAddRequest request, @PathVariable Integer competition_id) {
        Problem problem = problemService.addProblem(request,competition_id);
        return problemService.convertToDto(problem);

    }

    @PutMapping("/problems/{problem_id}/update")
    public ProblemDTO updateProblem(@Valid @RequestBody ProblemUpdateRequest request, @PathVariable Integer problem_id){
        Problem problem = problemService.updateProblem(request,problem_id);
        return problemService.convertToDto(problem);

    }

    @DeleteMapping("/problems/{problem_id}/delete")
    public void deleteProblem(@PathVariable Integer problem_id){
            problemService.deleteProblem(problem_id);
    }

    @GetMapping("/problems/by_name/{problem_name}")
    public List<ProblemDTO> getProblemByName(@PathVariable String problem_name) {

        List<Problem> problems = problemService.getProblemByName(problem_name);
        return problemService.getConvertedProblems(problems);

    }
    //id the competition has finished this bath become public
    @GetMapping ("/problems/competition/{id}")
    public List<ProblemDTO> getProblemByCompetitionId(@PathVariable Integer id){

            List<Problem> problems = problemService.getProblemsByCompetitionId(id);
            return problemService.getConvertedProblems(problems);

    }

}
