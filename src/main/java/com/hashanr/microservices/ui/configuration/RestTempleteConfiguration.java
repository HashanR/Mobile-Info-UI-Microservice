package com.hashanr.microservices.ui.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTempleteConfiguration {

	@Bean
	RestTemplate getRestTemplete() {
		return new RestTemplate();
	}
	
}
