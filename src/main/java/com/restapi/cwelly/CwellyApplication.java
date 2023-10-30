package com.restapi.cwelly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CwellyApplication {

	// basePrice 와 maxPrice에 따른 다른 Event 처리할 예정
	public static void main(String[] args) {
		SpringApplication.run(CwellyApplication.class, args);
	}
}
