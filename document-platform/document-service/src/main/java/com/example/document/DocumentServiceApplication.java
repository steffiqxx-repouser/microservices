package com.example.document;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = "com.example")

@EntityScan(basePackages = {
        "com.example.document.domain",
        "com.example.outbox.domain"
})
@EnableJpaRepositories(basePackages = {
        "com.example.document.repository",
        "com.example.outbox.infrastructure"
})
@EnableScheduling

public class DocumentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DocumentServiceApplication.class, args);
	}

}
