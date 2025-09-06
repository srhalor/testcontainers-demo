package com.sh.testcontainers_demo.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for the AddressRequest record class.
 * <p>
 * This test class verifies the functionality of AddressRequest,
 * including its fields, equality methods, and validation constraints.
 * </p>
 *
 * @author GitHub Copilot
 */
@Slf4j
class AddressRequestTest {
    private final Validator validator;
    public AddressRequestTest() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    /*
     * Test that the AddressRequest fields are set and retrieved correctly.
     */
    @Test
    @DisplayName("Test AddressRequest Fields")
    void testAddressRequestFields() {
        log.info("Running testAddressRequestFields");
        AddressRequest dto = new AddressRequest(
                "123 Main St",
                "Springfield",
                "IL",
                "62704",
                "USA"
        );
        assertThat(dto.street()).isEqualTo("123 Main St");
        assertThat(dto.city()).isEqualTo("Springfield");
        assertThat(dto.state()).isEqualTo("IL");
        assertThat(dto.zipCode()).isEqualTo("62704");
        assertThat(dto.country()).isEqualTo("USA");
        log.info("Test testAddressRequestFields passed");
    }

    /*
     * Test the equality and hashCode methods of AddressRequest.
     */
    @Test
    @DisplayName("Test AddressRequest Equality and HashCode")
    void testAddressRequestEquality() {
        log.info("Running testAddressRequestEquality");
        AddressRequest dto1 = new AddressRequest("A", "B", "C", "D", "E");
        AddressRequest dto2 = new AddressRequest("A", "B", "C", "D", "E");
        assertThat(dto1).isEqualTo(dto2);
        assertThat(dto1.hashCode()).hasSameHashCodeAs(dto2.hashCode());
        log.info("Test testAddressRequestEquality passed");
    }

    /*
     * Test validation constraints on AddressRequest fields.
     */
    @Test
    @DisplayName("Test AddressRequest Validation - Valid")
    void testAddressRequestValidationValid() {
        log.info("Running testAddressRequestValidationValid");
        AddressRequest dto = new AddressRequest("123 Main St", "Springfield", "IL", "62704", "USA");
        Set<ConstraintViolation<AddressRequest>> violations = validator.validate(dto);
        assertThat(violations).isEmpty();
        log.info("Test testAddressRequestValidationValid passed");
    }

    /*
     * Test validation constraints on AddressRequest fields with blank values.
     */
    @Test
    @DisplayName("Test AddressRequest Validation - Blank Fields")
    void testAddressRequestValidationBlankFields() {
        log.info("Running testAddressRequestValidationBlankFields");
        AddressRequest dto = new AddressRequest("", "", "", "", "");
        Set<ConstraintViolation<AddressRequest>> violations = validator.validate(dto);
        assertThat(violations).hasSize(5);
        for (ConstraintViolation<AddressRequest> violation : violations) {
            log.info("testAddressRequestValidationBlankFields Violation: {}", violation.getMessage());
        }
    }

    /*
     * Test validation constraints on AddressRequest fields with null values.
     */
    @Test
    @DisplayName("Test AddressRequest Validation - Null Fields")
    void testAddressRequestValidationNullFields() {
        log.info("Running testAddressRequestValidationNullFields");
        AddressRequest dto = new AddressRequest(null, null, null, null, null);
        Set<ConstraintViolation<AddressRequest>> violations = validator.validate(dto);
        assertThat(violations).hasSize(5);
        for (ConstraintViolation<AddressRequest> violation : violations) {
            log.info("testAddressRequestValidationNullFields Violation: {}", violation.getMessage());
        }
    }
}
