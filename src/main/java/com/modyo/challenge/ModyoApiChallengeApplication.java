package com.modyo.challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ModyoApiChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ModyoApiChallengeApplication.class, args);
	}

}
