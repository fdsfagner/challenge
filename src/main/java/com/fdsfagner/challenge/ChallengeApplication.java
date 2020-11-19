package com.fdsfagner.challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.fdsfagner.challenge"})
@ComponentScan({"com.fdsfagner.challenge"})
@EnableJpaRepositories({"com.fdsfagner.challenge.framework.adapters.out.h2.repository"})
@EntityScan({"com.fdsfagner.challenge.framework.adapters.out.h2.entity"})
public class ChallengeApplication {

	public static void main(String[] args) {

		SpringApplication.run(ChallengeApplication.class, args);
	}

}
