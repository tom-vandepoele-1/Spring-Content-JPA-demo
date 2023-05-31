package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.content.commons.property.PropertyPath;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Value("classpath:test.json")
	private Resource resource;

	@Bean
	public CommandLineRunner demo(UserRepository repository, PictureStore store) {
		return args -> {
			// create a new user
			User jbauer = new User("jbauer");

			// store profile picture
			store.setContent(jbauer, PropertyPath.from("profilePicture"), resource);

			// save the user
			repository.save(jbauer);
		};
	}

}
