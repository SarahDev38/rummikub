package com.sarahdev.rummikub.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "com.sarahdev.rummikub")
@Data
public class CustomProperties {

	private String apiUrl;
	private String player_name_duplicated;

}