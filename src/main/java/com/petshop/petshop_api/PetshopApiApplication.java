package com.petshop.petshop_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;


@SpringBootApplication
public class PetshopApiApplication {

	public static void main(String[] args) {

		Dotenv dotenv = Dotenv.load();

		System.setProperty("EMAIL_USERNAME", dotenv.get("EMAIL_USERNAME"));
		System.setProperty("EMAIL_PASSWORD", dotenv.get("EMAIL_PASSWORD"));

		SpringApplication.run(PetshopApiApplication.class, args);
	}

}

