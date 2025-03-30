package com.cp.Contests_management.Clarification;

import com.cp.Contests_management.Announcement.Announcement;
import com.cp.Contests_management.Announcement.AnnouncementAddRequest;
import com.cp.Contests_management.Announcement.AnnouncementDTO;
import com.cp.Contests_management.Announcement.AnnouncementNotFoundException;
import com.cp.Contests_management.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/clarifications")
public class ClarificationController {
    private final ClarificationService clarificationService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllClarifications() {
        List<Clarification> clarifications = clarificationService.getAllClarifications();
        List<ClarificationDTO> convertedClarifications = clarificationService.getConvertedClarifications(clarifications);
        return ResponseEntity.ok(new ApiResponse("success", convertedClarifications));
    }
    @GetMapping("/{Id}/clarification")
    public ResponseEntity<ApiResponse> getClarificationById(@PathVariable Long Id) {
        try {
            Clarification clarification = clarificationService.getClarificationById(Id);
            var clarificationDto = clarificationService.convertToDto(clarification);
            return ResponseEntity.ok(new ApiResponse("success", clarificationDto));
        } catch (ClarificationNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addClarification(@RequestBody ClarificationAddRequest request){
        try {
            Clarification clarification =clarificationService.addClarification(request);
            ClarificationDTO clarificationDto = clarificationService.convertToDto(clarification);
            return ResponseEntity.ok(new ApiResponse("success", clarificationDto));
        } catch (ClarificationNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));        }
    }
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<ApiResponse> deleteClarification(@PathVariable Long id){
        try {
            clarificationService.deleteClarification(id);
            return ResponseEntity.ok(new ApiResponse("success",null));
        } catch (ClarificationNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
    @GetMapping("/clarification/{id}")
    public ResponseEntity<ApiResponse> getClarificationByProblemId(@PathVariable Long id){
        try {
            List<Clarification> clarifications = clarificationService.getClarificationByProblem(id);
            List<ClarificationDTO> clarificationDTOS = clarificationService.getConvertedClarifications(clarifications);
            return ResponseEntity.ok(new ApiResponse("success", clarificationDTOS));
        } catch (ClarificationNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
}
