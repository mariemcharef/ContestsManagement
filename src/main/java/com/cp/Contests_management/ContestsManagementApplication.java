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

}