package com.cp.Contests_management.Problem;

import com.cp.Contests_management.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/problems")
public class ProblemController {
   private final ProblemService problemService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProblems() {
        List<Problem> Problems = problemService.getAllProblems();
        List<ProblemDTO> convertedProblems = problemService.getConvertedProblems(Problems);
        return ResponseEntity.ok(new ApiResponse("success", convertedProblems));
    }

    @GetMapping("/{Id}/problem")
    public ResponseEntity<ApiResponse> getProblemById(@PathVariable Long Id) {
        try {
            Problem problem = problemService.getProblemById(Id);
            var problemDto = problemService.convertToDto(problem);
            return ResponseEntity.ok(new ApiResponse("success", problemDto));

        } catch (ProblemNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProblem(@RequestBody ProblemAddRequest problem) {
        try {
            Problem theproblem = problemService.addProblem(problem);
            var problemDto = problemService.convertToDto(theproblem);

            return ResponseEntity.ok(new ApiResponse("Problem added successfully", problemDto));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping("/{Id}/update")
    public ResponseEntity<ApiResponse> updateProblem(@RequestBody ProblemUpdateRequest request, @PathVariable Long Id){
        try {
            Problem problem = problemService.updateProblem(request,Id);
            var problemDto = problemService.convertToDto(problem);

            return ResponseEntity.ok(new ApiResponse("success", problemDto));
        } catch (ProblemNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("/{CId}/delete")
    public ResponseEntity<ApiResponse> deleteProblem(@PathVariable Long CId){
        try {
            problemService.deleteProblem(CId);
            return ResponseEntity.ok(new ApiResponse("success", null));
        } catch (ProblemNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/by/name")
    public ResponseEntity<ApiResponse> getProblemByName(@RequestParam String name) {
        try {
            List<Problem> problems = problemService.getProblemByName(name);
            List<ProblemDTO> convertedProblems = problemService.getConvertedProblems(problems);
            if(convertedProblems.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(null,name));
            }
            return ResponseEntity.ok(new ApiResponse("success", convertedProblems));
        }catch (ProblemNotFoundException e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping ("/competition/{id}")
    public ResponseEntity<ApiResponse> getProblemByCompetitionId(@PathVariable Long id){
        try {
            List<Problem> problems = problemService.getProblemsByCompetitionId(id);
            List<ProblemDTO> convertedProblems = problemService.getConvertedProblems(problems);
            return ResponseEntity.ok(new ApiResponse("success", convertedProblems));
        } catch (ProblemNotFoundException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }


}
