package com.zurakin.codingriddles;


import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CodingRiddlesApplication {


	public static void main(String[] args) {
		requireEnvVars("DB_USERNAME", "DB_PASSWORD", "SPRING_DATASOURCE_URL", "SECRET_KEY");
		SpringApplication.run(CodingRiddlesApplication.class, args);
	}

	/**
	 * Ensures all required environment variables are set, loading from .env if needed.
	 * Exits the application with an error if any are missing.
	 */
	private static void requireEnvVars(String... vars) {
		Dotenv dotenv = null;
		boolean missing = false;
		for (String var : vars) {
			String value = System.getenv(var);
			if (value == null) {
				if (dotenv == null) dotenv = Dotenv.load();
				value = dotenv.get(var);
				if (value != null) {
					System.setProperty(var, value);
				} else {
					System.err.println("ERROR: Required environment variable '" + var + "' is not set. Please set it in your environment or .env file.");
					missing = true;
				}
			} else {
				System.setProperty(var, value);
			}
		}
		if (missing) {
			System.exit(1);
		}
	}

}
