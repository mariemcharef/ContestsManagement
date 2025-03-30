package com.cp.Contests_management.Clarification;

import com.cp.Contests_management.Clarification.*;
import com.cp.Contests_management.Participant.Participant;
import com.cp.Contests_management.Participant.ParticipantNotFoundException;
import com.cp.Contests_management.Participant.ParticipantRepository;
import com.cp.Contests_management.Problem.Problem;
import com.cp.Contests_management.Problem.ProblemNotFoundException;
import com.cp.Contests_management.Problem.ProblemRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ClarificationService implements IClarificationService {
    private final ClarificationRepository clarificationRepository;
    private final ProblemRepository problemRepository;
    private final ModelMapper modelMapper;
    private final ParticipantRepository participantRepository;

    private Clarification createClarification(ClarificationAddRequest request) {

        Problem problem = problemRepository.findById(request.getProblemId())
                .orElseThrow(() -> new ProblemNotFoundException("Problem not found with id: " + request.getProblemId()));
        Participant participant = participantRepository.findById(request.getParticipantId())
                .orElseThrow(() -> new ParticipantNotFoundException("Participant not found with id: " + request.getParticipantId()));
        Clarification clarification=new Clarification();
        clarification.setProblem(problem);
        clarification.setParticipant(participant);
        return clarification;
    }
    @Override
    public Clarification addClarification(ClarificationAddRequest request) {
        return clarificationRepository.save(createClarification(request));
    }

    @Override
    public Clarification getClarificationById(Long id) {
        return clarificationRepository.findById(id).orElseThrow(()->new ClarificationNotFoundException("Clarification Not Found"));
    }

    @Override
    public void deleteClarification(Long id) {
        clarificationRepository.findById(id)
                .ifPresentOrElse(clarificationRepository::delete,() -> {throw new ClarificationNotFoundException("Announcement Not Found");});
    }

    @Override
    public List<Clarification> getAllClarifications() {
        return clarificationRepository.findAll();
    }

    @Override
    public List<Clarification> getClarificationByProblem(Long competitionId) {
        return clarificationRepository.findByProblemId(competitionId);
    }


    @Override
    public List<ClarificationDTO> getConvertedClarifications(List<Clarification> clarifications) {
        return clarifications.stream().map(this::convertToDto).toList();
    }

    @Override
    public ClarificationDTO convertToDto(Clarification clarification) {
        return  modelMapper.map(clarification, ClarificationDTO.class);
    }

}
