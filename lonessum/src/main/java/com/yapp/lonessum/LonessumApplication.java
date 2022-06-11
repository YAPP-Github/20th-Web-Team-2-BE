package com.yapp.lonessum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class LonessumApplication {

	public static void main(String[] args) {
		SpringApplication.run(LonessumApplication.class, args);
	}

}
