package com.example.web2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.web2.Repository")
@EnableScheduling
public class Web2Application {

	public static void main(String[] args) {
		SpringApplication.run(Web2Application.class, args);
	}

}
