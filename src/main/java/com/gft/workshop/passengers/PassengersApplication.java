package com.gft.workshop.passengers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication(scanBasePackages = "com.gft.workshop")
@EnableR2dbcRepositories(basePackages = "com.gft.workshop.passengers")
public class PassengersApplication {

	public static void main(String[] args) {
		SpringApplication.run(PassengersApplication.class, args);
	}

}
