package com.example.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AccountCommandServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountCommandServiceApplication.class, args);
	}

}
