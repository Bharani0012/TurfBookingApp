package com.example.TurfBookingApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class TurfBookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(TurfBookingApplication.class, args);
	}

}
