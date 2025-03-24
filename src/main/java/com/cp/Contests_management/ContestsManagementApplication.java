package com.cp.Contests_management;

import com.cp.Contests_management.AppUser.AppUser;
import com.cp.Contests_management.AppUser.AppUserRepository;
import com.cp.Contests_management.Competition.Competition;
import com.cp.Contests_management.Competition.CompetitionRepository;
import com.cp.Contests_management.Participant.Participant;
import com.cp.Contests_management.Participant.ParticipantRepository;
import com.cp.Contests_management.Problem.Problem;
import com.cp.Contests_management.Problem.ProblemRepository;
import com.cp.Contests_management.TestCase.TestCase;
import com.cp.Contests_management.TestCase.TestCaseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication
public class ContestsManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContestsManagementApplication.class, args);
	}


	/*@Bean
	public CommandLineRunner commandLineRunner(

			AppUserRepository appUserRepository
	){
		return args -> {


			AppUser appUser0 = new AppUser();
			appUser0.setName("firstParticipant");
			appUser0.setEmail("firstParticipant@gmail.com");
			appUser0.setPassword("firstParticipant");

			appUserRepository.save(appUser0);
		};
	}

	@Bean
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
	}
	@Bean
	public CommandLineRunner commandLineRunner(
			ParticipantRepository repository,
			AppUserRepository appUserRepository
	){
		return args -> {

			AppUser appUser=appUserRepository.findById(1L).orElseThrow(()->new RuntimeException("User not found"));
			Participant participant0 = new Participant();
			participant0.setName("firstParticipant");
			participant0.setAppUsers(new ArrayList<>());
			participant0.getAppUsers().add(appUser);
			repository.save(participant0);
		};
	}*/

	
}