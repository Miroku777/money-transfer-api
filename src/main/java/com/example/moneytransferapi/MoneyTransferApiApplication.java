package com.example.moneytransferapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class MoneyTransferApiApplication {

	public static void main(String[] args) {
        SpringApplication.run(MoneyTransferApiApplication.class, args);
	}

}
