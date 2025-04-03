package com.cp.Contests_management.Clarification;

import com.cp.Contests_management.Competition.Competition;
import com.cp.Contests_management.Participant.Participant;
import com.cp.Contests_management.Participant.ParticipantRepository;
import com.cp.Contests_management.Problem.Problem;
import com.cp.Contests_management.Problem.ProblemRepository;
import com.cp.Contests_management.Problem.ProblemService;
import com.cp.Contests_management.User.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ClarificationService  {
    private final ClarificationRepository clarificationRepository;
    private final ProblemRepository problemRepository;
    private final ModelMapper modelMapper;
    private final ParticipantRepository participantRepository;
    private final ProblemService problemService;

    public Clarification addClarification(ClarificationRequest request,Integer participantId,Integer problemId) {

        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Problem not found with id: " ));
        Participant participant = participantRepository.findById(participantId)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Participant not found"));

        Clarification clarification=new Clarification();
        clarification.setProblem(problem);
        clarification.setParticipant(participant);
        clarification.setClarification(request.getClarification());
        return clarificationRepository.save(clarification);
    }


    public Clarification getClarificationById(Integer id) {
        return clarificationRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Clarification Not Found"));
    }


    public void deleteClarification(Integer id) {
        clarificationRepository.findById(id)
                .ifPresentOrElse(clarificationRepository::delete,() -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Clarification Not Found");});
    }

    public List<Clarification> getAllClarifications() {

        return clarificationRepository.findAll();
    }

    public List<Clarification> getClarificationByProblem(Integer problemId) {
        problemService.getProblemById(problemId);
        return clarificationRepository.findAll().stream().filter(clarification -> clarification.getProblem().getId().equals(problemId)).toList();
    }
    //send clarification to competition creator
    public User getUserIdForClarificationRecipient(Integer clarificationId) {
        Clarification clarification = getClarificationById(clarificationId);
        Problem problem = clarification.getProblem();
        Competition competition = problem.getCompetition();
        return competition.getUser();

    }



    public List<ClarificationDTO> getConvertedClarifications(List<Clarification> clarifications) {
        return clarifications.stream().map(this::convertToDto).toList();
    }


    public ClarificationDTO convertToDto(Clarification clarification) {
        return  modelMapper.map(clarification, ClarificationDTO.class);
    }

}
