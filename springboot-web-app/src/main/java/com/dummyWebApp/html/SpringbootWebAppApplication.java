package com.dummyWebApp.html;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.text.ParseException;

@SpringBootApplication
public class SpringbootWebAppApplication {

	public static void main(String[] args) throws IOException, InterruptedException, ParseException {
		SpringApplication.run(SpringbootWebAppApplication.class, args);
	}
}