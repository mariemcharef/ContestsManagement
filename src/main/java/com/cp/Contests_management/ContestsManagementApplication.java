package com.cp.Contests_management;

import com.cp.Contests_management.User.User;
import com.cp.Contests_management.User.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
public class ContestsManagementApplication {

	public static void main(String[] args) {

		SpringApplication.run(ContestsManagementApplication.class, args);
	}


}