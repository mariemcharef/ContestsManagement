package com.cp.Contests_management;

import com.cp.Contests_management.AppUser.AppUser;
import com.cp.Contests_management.AppUser.AppUserRepository;
import com.cp.Contests_management.Competition.Competition;
import com.cp.Contests_management.Competition.CompetitionRepository;
import com.cp.Contests_management.Problem.Problem;
import com.cp.Contests_management.Problem.ProblemRepository;
import com.cp.Contests_management.TestCase.TestCase;
import com.cp.Contests_management.TestCase.TestCaseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ContestsManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContestsManagementApplication.class, args);
	}

	/*@Bean
	public CommandLineRunner commandLineRunner(
			ProblemRepository repository,
			CompetitionRepository competitionRepository
	){
		return args -> {
			Competition competition = competitionRepository.findById(1L)
					.orElseThrow(() -> new RuntimeException("Competition with ID 1 not found"));
			Problem problem0 = new Problem();
			problem0.setName("firstProblem");
			problem0.setLabel("A");
			problem0.setContent("print 123");
			problem0.setCompetition(competition);
			repository.save(problem0);
		};
	}*/
	
}