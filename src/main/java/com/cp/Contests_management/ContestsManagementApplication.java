package com.cp.Contests_management;

import com.cp.Contests_management.AppUser.AppUser;
import com.cp.Contests_management.AppUser.AppUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ContestsManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContestsManagementApplication.class, args);
	}
	@Bean
	public CommandLineRunner commandLineRunner(//default user before doing the authentification
			AppUserRepository repository

	){
		return args -> {

			AppUser appUser0 = new AppUser();
			appUser0.setName("arem");
			appUser0.setEmail("arem2@gmail.com");
			appUser0.setPassword("123");
			appUser0.setRating(0);
			repository.save(appUser0);
		};
	}
	
}