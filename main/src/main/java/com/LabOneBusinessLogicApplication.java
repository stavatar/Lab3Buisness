package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
@ComponentScan({"com.*","api.*"})
public class LabOneBusinessLogicApplication {

	public static void main(String[] args) {
		SpringApplication.run(LabOneBusinessLogicApplication.class, args);
	}

}
