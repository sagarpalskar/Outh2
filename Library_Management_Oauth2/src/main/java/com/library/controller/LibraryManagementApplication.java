package com.library.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages= {"com.model"})
@SpringBootApplication(scanBasePackages={"com.model","com.dao","com.security","com.library.controller"})
@EnableJpaRepositories({"com.dao"})
public class LibraryManagementApplication {

	public static void main(String[] args) 
	{
		SpringApplication.run(LibraryManagementApplication.class, args);
	}

}
