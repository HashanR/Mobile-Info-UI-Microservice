package com.hashanr.microservices.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MobileInfoUiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MobileInfoUiApplication.class, args);
	}

}
