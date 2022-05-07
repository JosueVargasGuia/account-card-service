package com.nttdata.accountcard.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication 
@EnableCaching
public class AccountCardServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountCardServiceApplication.class, args);
	}

}
