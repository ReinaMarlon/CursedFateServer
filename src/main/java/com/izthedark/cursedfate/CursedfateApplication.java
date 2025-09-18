package com.izthedark.cursedfate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CursedfateApplication {

	public static void main(String[] args) {
        System.out.println(">>> ENV PORT = " + System.getenv("PORT"));
        SpringApplication.run(CursedfateApplication.class, args);
	}

}
