package com.sh.testcontainers_demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * Main class to run the Spring Boot application.
 *
 * @author Shailesh Halor
 */
@Slf4j
@SpringBootApplication
public class TestcontainersDemoApplication {

    /**
     * Main method to start the Spring Boot application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        log.info("Starting Testcontainers Demo Application...");
        SpringApplication.run(TestcontainersDemoApplication.class, args);
        log.info("Testcontainers Demo Application started successfully.");
    }

}
