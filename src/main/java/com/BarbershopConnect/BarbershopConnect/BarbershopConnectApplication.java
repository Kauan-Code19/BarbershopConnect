package com.BarbershopConnect.BarbershopConnect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BarbershopConnectApplication {

	public static void main(String[] args) {
		SpringApplication.run(BarbershopConnectApplication.class, args);
	}

}
