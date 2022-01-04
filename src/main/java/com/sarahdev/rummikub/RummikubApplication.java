package com.sarahdev.rummikub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.sarahdev.rummikub.repository.CardRepository;
import com.sarahdev.rummikub.repository.PlayerRepository;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = { PlayerRepository.class, CardRepository.class })
@ComponentScan({ "com.sarahdev.rummikub" })
@EntityScan("com.sarahdev.rummikub")
@Configuration
public class RummikubApplication {

	public static void main(String[] args) {
		SpringApplication.run(RummikubApplication.class, args);
	}

}
