package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class SpringBootFileuploadApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootFileuploadApplication.class, args);
	}
}
