package com.petshop.petshop_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;


@SpringBootApplication
public class PetshopApiApplication {

	public static void main(String[] args) {

		Dotenv dotenv = Dotenv.configure()
				.ignoreIfMissing()
				.load();

		String emailUsername = dotenv.get("EMAIL_USERNAME");
		if (emailUsername != null) {
			System.setProperty("EMAIL_USERNAME", emailUsername);
		}

		String emailPassword = dotenv.get("EMAIL_PASSWORD");
		if (emailPassword != null) {
			System.setProperty("EMAIL_PASSWORD", emailPassword);
		}

		String dbUrl = dotenv.get("DB_URL");
		if (dbUrl != null) {
			System.setProperty("DB_URL", dbUrl);
		}

		String dbUsername = dotenv.get("DB_USERNAME");
		if (dbUsername != null) {
			System.setProperty("DB_USERNAME", dbUsername);
		}

		String dbPassword = dotenv.get("DB_PASSWORD");
		if (dbPassword != null) {
			System.setProperty("DB_PASSWORD", dbPassword);
		}

		String jwtPrivateKey = dotenv.get("JWT_PRIVATE_KEY");

		if (jwtPrivateKey != null) {
			jwtPrivateKey = jwtPrivateKey.replace("\\n", "\n");
			System.setProperty("JWT_PRIVATE_KEY", jwtPrivateKey);
		}

		SpringApplication.run(PetshopApiApplication.class, args);
	}

}

