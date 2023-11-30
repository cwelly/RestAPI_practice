package com.restapi.cwelly;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CwellyApplication {
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	// basePrice 와 maxPrice에 따른 다른 Event 처리할 예정
	public static void main(String[] args) {
		SpringApplication.run(CwellyApplication.class, args);
	}

}
