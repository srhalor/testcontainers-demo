package com.sh.testcontainers_demo.dto;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for the UserResponse record class.
 * <p>
 * This test class verifies the functionality of the UserResponse,
 * including its fields and equality methods.
 * </p>
 *
 * @author Shailesh Halor
 */
@Slf4j
class UserResponseTest {

    /*
     * Test that the UserResponse fields are set and retrieved correctly.
     */
    @Test
    @DisplayName("Test UserResponse Fields")
    void testUserResponseFields() {
        log.info("Running testUserResponseFields");
        Instant now = Instant.now();
        AddressResponse address = new AddressResponse(
                2L,
                "456 Elm St",
                "Metropolis",
                "NY",
                "10001",
                "USA",
                now,
                now,
                "creator",
                "modifier"
        );
        UserResponse user = new UserResponse(
                1L,
                "Clark Kent",
                "clark@dailyplanet.com",
                now,
                now,
                "creator",
                "modifier",
                List.of(address)
        );
        assertThat(user.id()).isEqualTo(1L);
        assertThat(user.name()).isEqualTo("Clark Kent");
        assertThat(user.email()).isEqualTo("clark@dailyplanet.com");
        assertThat(user.createdAt()).isEqualTo(now);
        assertThat(user.updatedAt()).isEqualTo(now);
        assertThat(user.createdBy()).isEqualTo("creator");
        assertThat(user.lastModifiedBy()).isEqualTo("modifier");
        assertThat(user.address()).containsExactly(address);
        log.info("Test testUserResponseFields passed");
    }

    /*
     * Test the equality and hashCode methods of UserResponse.
     */
    @Test
    @DisplayName("Test UserResponse Equality and HashCode")
    void testUserResponseEquality() {
        log.info("Running testUserResponseEquality");
        Instant now = Instant.now();
        AddressResponse address = new AddressResponse(2L, "A", "B", "C", "D", "E", now, now, "F", "G");
        UserResponse user1 = new UserResponse(1L, "N", "E", now, now, "C", "M", List.of(address));
        UserResponse user2 = new UserResponse(1L, "N", "E", now, now, "C", "M", List.of(address));
        assertThat(user1).isEqualTo(user2);
        assertThat(user1.hashCode()).hasSameHashCodeAs(user2.hashCode());
        log.info("Test testUserResponseEquality passed");
    }
}
