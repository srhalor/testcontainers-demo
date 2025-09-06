package com.sh.testcontainers_demo.entity;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for the User entity class.
 * <p>
 * This test class verifies the functionality of the User entity,
 * including its fields and relationships.
 * </p>
 *
 * @author Shailesh Halor
 */
@Slf4j
class UserTest {

    /*
     * Test that the User entity fields are set and retrieved correctly.
     */
    @Test
    @DisplayName("Test User Fields")
    void testUserFields() {
        log.info("Running testUserFields");
        User user = User.builder()
                .id(1L)
                .name("John Doe")
                .email("john@example.com")
                .build();
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getName()).isEqualTo("John Doe");
        assertThat(user.getEmail()).isEqualTo("john@example.com");
        log.info("Test testUserFields passed");
    }
}
