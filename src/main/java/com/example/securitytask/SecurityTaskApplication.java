package com.example.securitytask;

import com.example.securitytask.model.UserEntity;
import com.example.securitytask.repository.UserEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

@SpringBootApplication
@Slf4j
@ComponentScan(basePackages = "com.example.securitytask")
public class SecurityTaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityTaskApplication.class, args);
	}

	@Bean
	public CommandLineRunner printUserEntities(UserEntityRepository repository) {
		return args -> {
			List<UserEntity> users = repository.findAll();
			log.info("UserEntities: ");
			log.info(users.toString());
			log.info("");
		};
	}

}
