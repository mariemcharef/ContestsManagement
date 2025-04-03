package com.cp.Contests_management.Problem;

import com.cp.Contests_management.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/problems")
public class ProblemController {
   private final ProblemService problemService;

    @GetMapping("/all")
    public List<ProblemDTO> getAllProblems() {
        List<Problem> Problems = problemService.getAllProblems();
        return problemService.getConvertedProblems(Problems);
    }
    @GetMapping("/old")
    public List<ProblemDTO> getOldProblems() {
        List<Problem> Problems = problemService.getOldProblems();
        return problemService.getConvertedProblems(Problems);
    }

    @GetMapping("/{problem_id}/problem")
    public ProblemDTO getProblemById(@PathVariable Integer problem_id) {
       Problem problem = problemService.getProblemById(problem_id);
       return problemService.convertToDto(problem);

    }

    @PostMapping("/{competition_id}/add")
    public ProblemDTO addProblem(@Valid @RequestBody ProblemAddRequest request, @PathVariable Integer competition_id) {
        Problem problem = problemService.addProblem(request,competition_id);
        return problemService.convertToDto(problem);

    }

    @PutMapping("/{problem_id}/update")
    public ProblemDTO updateProblem(@Valid @RequestBody ProblemUpdateRequest request, @PathVariable Integer problem_id){
        Problem problem = problemService.updateProblem(request,problem_id);
        return problemService.convertToDto(problem);

    }

    @DeleteMapping("/{problem_id}/delete")
    public void deleteProblem(@PathVariable Integer problem_id){
            problemService.deleteProblem(problem_id);
    }

    @GetMapping("/by_name/{problem_name}")
    public List<ProblemDTO> getProblemByName(@PathVariable String problem_name) {

        List<Problem> problems = problemService.getProblemByName(problem_name);
        return problemService.getConvertedProblems(problems);

    }

    @GetMapping ("/competition/{id}")
    public List<ProblemDTO> getProblemByCompetitionId(@PathVariable Integer id){

            List<Problem> problems = problemService.getProblemsByCompetitionId(id);
            return problemService.getConvertedProblems(problems);

    }

}
