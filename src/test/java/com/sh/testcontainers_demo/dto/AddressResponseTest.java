package com.sh.testcontainers_demo.dto;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for the AddressResponse record class.
 * <p>
 * This test class verifies the functionality of the AddressResponse,
 * including its fields and equality methods.
 * </p>
 *
 * @author Shailesh Halor
 */
@Slf4j
class AddressResponseTest {

    /*
     * Test that the AddressResponse fields are set and retrieved correctly.
     */
    @Test
    @DisplayName("Test AddressResponse Fields")
    void testAddressResponseFields() {
        log.info("Running testAddressResponseFields");
        Instant now = Instant.now();
        AddressResponse dto = new AddressResponse(
                1L,
                "123 Main St",
                "Springfield",
                "IL",
                "62704",
                "USA",
                now,
                now,
                "creator",
                "modifier"
        );
        assertThat(dto.id()).isEqualTo(1L);
        assertThat(dto.street()).isEqualTo("123 Main St");
        assertThat(dto.city()).isEqualTo("Springfield");
        assertThat(dto.state()).isEqualTo("IL");
        assertThat(dto.zipCode()).isEqualTo("62704");
        assertThat(dto.country()).isEqualTo("USA");
        assertThat(dto.createdAt()).isEqualTo(now);
        assertThat(dto.updatedAt()).isEqualTo(now);
        assertThat(dto.createdBy()).isEqualTo("creator");
        assertThat(dto.lastModifiedBy()).isEqualTo("modifier");
        log.info("Test testAddressResponseFields passed");
    }

    /*
     * Test the equality and hashCode methods of AddressResponse.
     */
    @Test
    @DisplayName("Test AddressResponse Equality and HashCode")
    void testAddressResponseEquality() {
        log.info("Running testAddressResponseEquality");
        Instant now = Instant.now();
        AddressResponse dto1 = new AddressResponse(1L, "A", "B", "C", "D", "E", now, now, "F", "G");
        AddressResponse dto2 = new AddressResponse(1L, "A", "B", "C", "D", "E", now, now, "F", "G");
        assertThat(dto1).isEqualTo(dto2);
        assertThat(dto1.hashCode()).hasSameHashCodeAs(dto2.hashCode());
        log.info("Test testAddressResponseEquality passed");
    }
}
