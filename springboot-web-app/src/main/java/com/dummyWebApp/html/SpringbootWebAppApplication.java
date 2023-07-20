/**
This class, "SpringbootWebAppApplication," is an essential component of a web 
application built using the Spring Boot framework. It resides in the package 
"com.dummyWebApp.html." The class is annotated with "@SpringBootApplication," 
which indicates that it serves as the main entry point for the application and 
includes both configuration and component scanning capabilities. The "main" 
method is the starting point of the application and serves to launch the Spring 
Boot application. It throws three types of exceptions: IOException, 
InterruptedException, and ParseException. Inside the "main" method, it calls "
SpringApplication.run" to initiate the Spring Boot application, providing the 
class itself and any command-line arguments passed as input. This class plays 
a crucial role in enabling the smooth execution of the Spring Boot web application, 
ensuring that it starts up correctly and handles the necessary configuration and scanning processes.
**/

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
