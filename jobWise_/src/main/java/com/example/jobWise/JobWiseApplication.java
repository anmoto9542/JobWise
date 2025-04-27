package com.example.jobWise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class JobWiseApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobWiseApplication.class, args);
	}

}
