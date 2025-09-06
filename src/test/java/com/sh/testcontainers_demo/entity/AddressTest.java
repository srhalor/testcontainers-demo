package com.sh.testcontainers_demo.entity;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for the Address entity class.
 * <p>
 * This test class verifies the functionality of the Address entity,
 * including its fields and relationships.
 * </p>
 *
 * @author Shailesh Halor
 */
@Slf4j
class AddressTest {

    /*
     * Test that the Address entity fields are set and retrieved correctly.
     */
    @Test
    @DisplayName("Test Address Fields")
    void testAddressFields() {
        log.info("Running testAddressFields");
        Address address = Address.builder()
                .id(1L)
                .street("123 Main St")
                .city("Springfield")
                .state("IL")
                .zipCode("62704")
                .build();
        assertThat(address.getId()).isEqualTo(1L);
        assertThat(address.getStreet()).isEqualTo("123 Main St");
        assertThat(address.getCity()).isEqualTo("Springfield");
        assertThat(address.getState()).isEqualTo("IL");
        assertThat(address.getZipCode()).isEqualTo("62704");
        log.info("Test testAddressFields passed");
    }
}
