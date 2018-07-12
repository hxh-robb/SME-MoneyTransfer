package com.sme.mts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jersey.JerseyAutoConfiguration;

@SpringBootApplication
// @EnableAutoConfiguration(exclude = {JerseyAutoConfiguration.class})
public class MoneyTransferService {
	public static void main(String[] args) {
		SpringApplication.run(MoneyTransferService.class, args);
	}
}
