package com.cp.Contests_management.Problem;

import com.cp.Contests_management.Competition.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProblemService implements IProblemService {

    private final ProblemRepository problemRepository;
    private final CompetitionRepository competitionRepository;
    private final ModelMapper modelMapper;
    @Override
    public Problem getProblemById(Long id) {
        return problemRepository.findById(id).orElseThrow(() -> new ProblemNotFoundException("Problem Not Found"));
    }

    @Override
    public List<Problem> getProblemByName(String name) {
        return problemRepository.findByName(name);
    }

    @Override
    public List<Problem> getAllProblems() {

        return problemRepository.findAll();
    }

    public Problem createProblem(ProblemAddRequest request){
        Competition competition = competitionRepository.findById(request.getCompetitionId())
                .orElseThrow(() -> new CompetitionNotFoundException("Competition not found with id: " + request.getCompetitionId()));
        Problem problem = new Problem();
        problem.setName(request.getName());
        problem.setLabel(request.getLabel());
        problem.setContent(request.getContent());
        problem.setTimeLimit(request.getTimeLimit());
        problem.setMemoryLimit(request.getMemoryLimit());
        problem.setTopics(request.getTopics());
        problem.setCompetition(competition);
        return problem;
    }
    @Override
    public Problem addProblem(ProblemAddRequest request) {
        return problemRepository.save(createProblem(request));
    }
    public Problem updateExistingProblem(ProblemUpdateRequest request, Long id) {
        Problem problem = problemRepository.findById(id).orElseThrow(()->new ProblemNotFoundException("Problem not found"));
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
        return problem;
    }
    @Override
    public Problem updateProblem(ProblemUpdateRequest request, Long id) {
        return problemRepository.findById(id)
                .map(old -> updateExistingProblem(request,id))
                .map(problemRepository :: save).orElseThrow(()->new ProblemNotFoundException("Problem Not Found"));

    }

    @Override
    public void deleteProblem(Long id) {
        problemRepository.deleteById(id);
    }

    @Override
    public List<ProblemDTO> getConvertedProblems(List<Problem> problems) {
        return problems.stream().map(this::convertToDto).toList();
    }

    @Override
    public ProblemDTO convertToDto(Problem problem) {

        return  modelMapper.map(problem, ProblemDTO.class);
    }
    @Override
    public List<Problem> getProblemsByCompetitionId(Long competitionId) {
        return problemRepository.findByCompetitionId(competitionId);
    }
}
