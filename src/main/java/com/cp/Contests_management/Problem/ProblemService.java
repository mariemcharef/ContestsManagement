package com.cp.Contests_management.Problem;

import com.cp.Contests_management.Competition.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProblemService  {

    private final ProblemRepository problemRepository;
    private final CompetitionRepository competitionRepository;
    private final ModelMapper modelMapper;

    public Problem getProblemById(Integer id) {
        return problemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Problem not found"));
    }

    public List<Problem> getProblemByName(String name) {

        return problemRepository.findByName(name);
    }


    public List<Problem> getAllProblems() {

        return problemRepository.findAll();
    }
    //get old problems

    public List<Problem> getOldProblems() {
        return problemRepository.findAll().stream()
                .filter(problem -> problem.getCompetition() != null &&
                        problem.getCompetition().getEndTime().isBefore(LocalDateTime.now()))
                .collect(Collectors.toList());
    }

    public Problem addProblem(ProblemAddRequest request,Integer competitionId){
        Competition competition = competitionRepository.findById(competitionId)
                .orElseThrow(() -> new RuntimeException("Competition not found with id: " + competitionId));
        Problem problem = new Problem();
        problem.setName(request.getName());
        problem.setLabel(request.getLabel());
        problem.setContent(request.getContent());
        problem.setTimeLimit(request.getTimeLimit());
        problem.setMemoryLimit(request.getMemoryLimit());
        problem.setTopics(request.getTopics());
        problem.setCompetition(competition);
        return problemRepository.save(problem);
    }


    public Problem updateProblem(ProblemUpdateRequest request, Integer id) {
        Problem problem = problemRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Problem not found"));
        if(request.getName()!=null){
            problem.setName(request.getName());
        }
        if(request.getLabel()!=null){
            problem.setLabel(request.getLabel());
        }
        if(request.getContent()!=null){
            problem.setContent(request.getContent());
        }
        if(request.getMemoryLimit()!=0){
            problem.setMemoryLimit(request.getMemoryLimit());
        }
        if(request.getTopics()!=null){
            problem.setTopics(request.getTopics());
        }
        if(request.getTimeLimit()!=0){
            problem.setTimeLimit(request.getTimeLimit());
        }
        return problemRepository.save(problem);
    }

    public void deleteProblem(Integer id) {

        problemRepository.deleteById(id);
    }
    public List<Problem> getProblemsByCompetitionId(Integer competitionId) {
        return problemRepository.findAll().stream().filter(problem -> problem.getCompetition().getId().equals(competitionId)).toList();
    }

    public List<ProblemDTO> getConvertedProblems(List<Problem> problems) {
        return problems.stream().map(this::convertToDto).toList();
    }

    public ProblemDTO convertToDto(Problem problem) {

        return  modelMapper.map(problem, ProblemDTO.class);
    }



}
