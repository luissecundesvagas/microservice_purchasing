package com.gruposv.microservice_stock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MicroservicePurchasingApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicePurchasingApplication.class, args);
	}

}
