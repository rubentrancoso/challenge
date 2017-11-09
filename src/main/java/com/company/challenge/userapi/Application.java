package com.company.challenge.userapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.company.challenge.repositories.UserRepository;

@EnableAutoConfiguration
@EnableJpaRepositories (basePackageClasses = {UserRepository.class})
@SpringBootApplication
@EntityScan (basePackages = {"com.company.challenge.entities"}) 
public class Application {
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
