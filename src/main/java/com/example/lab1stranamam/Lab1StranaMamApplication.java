package com.example.lab1stranamam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class Lab1StranaMamApplication {

	public static void main(String[] args) {
		SpringApplication.run(Lab1StranaMamApplication.class, args);
	}

}
