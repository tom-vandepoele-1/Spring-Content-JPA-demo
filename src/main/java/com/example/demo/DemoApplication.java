package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.content.commons.property.PropertyPath;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

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
			jbauer = store.setContent(jbauer, PropertyPath.from("profilePicture"), resource);

			// save the user
			jbauer = repository.save(jbauer);
		};
	}

	@Configuration
	public static class Config {
		@Value("/org/springframework/content/jpa/schema-drop-h2.sql")
		private Resource dropReopsitoryTables;

		@Value("/org/springframework/content/jpa/schema-h2.sql")
		private Resource dataReopsitorySchema;

		@Bean
		DataSourceInitializer datasourceInitializer(DataSource dataSource) {
			ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();

			databasePopulator.addScript(dropReopsitoryTables);
			databasePopulator.addScript(dataReopsitorySchema);
			databasePopulator.setIgnoreFailedDrops(true);

			DataSourceInitializer initializer = new DataSourceInitializer();
			initializer.setDataSource(dataSource);
			initializer.setDatabasePopulator(databasePopulator);

			return initializer;
		}
	}

}
