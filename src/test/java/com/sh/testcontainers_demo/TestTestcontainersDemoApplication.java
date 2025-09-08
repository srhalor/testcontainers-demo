package com.sh.testcontainers_demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.testcontainers.utility.TestcontainersConfiguration;

/*
 * This is a test application class to run the main application with testcontainers configuration.
 * It uses Spring Boot's ability to start an application context with a specific configuration class.
 *
 * @author Shailesh Halor
 */
@Slf4j
public class TestTestcontainersDemoApplication {

    /**
     * The main method to run the application with Testcontainers configuration.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        log.info("Starting TestTestcontainersDemoApplication with Testcontainers configuration");
        SpringApplication.from(TestcontainersDemoApplication::main).with(TestcontainersConfiguration.class).run(args);
        log.info("TestTestcontainersDemoApplication started with Testcontainers configuration");
    }

}
