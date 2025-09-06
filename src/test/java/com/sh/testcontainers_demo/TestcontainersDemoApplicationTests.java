package com.sh.testcontainers_demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

/*
 * This is a test class to verify that the application context loads successfully with Testcontainers configuration.
 *
 * @author Shailesh Halor
 */
@Slf4j
@Import(TestcontainersConfiguration.class)
@SpringBootTest
class TestcontainersDemoApplicationTests {

    /**
     * Test to ensure the application context loads successfully.
     */
	@Test
	void contextLoads() {
        // Test will fail if the application context cannot start
        log.info("Application context loaded successfully with Testcontainers configuration");
	}

    /**
     * Test to ensure the main method of TestcontainersDemoApplication runs without exceptions.
     */
    @Test
    void testMainMethodRuns() {
        TestcontainersDemoApplication.main(new String[]{"--server.port=0"});
        // If no exception is thrown, the test passes
        log.info("TestcontainersDemoApplication main method executed successfully");
    }

    /**
     * Test to ensure the main method of TestTestcontainersDemoApplication runs without exceptions.
     */
    @Test
    void testTestMainMethodRuns() {
        TestTestcontainersDemoApplication.main(new String[]{"--server.port=0"});
        // If no exception is thrown, the test passes
        log.info("TestTestcontainersDemoApplication main method executed successfully");
    }

}
