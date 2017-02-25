package com.mastercontrol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan(basePackages = "com.mastercontrol")
public class MultiTenancyApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultiTenancyApplication.class, args);
	}
}
