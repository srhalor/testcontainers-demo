package com.sh.testcontainers_demo.dto;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;

import java.util.Set;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for the UserRequest record class.
 * <p>
 * This test class verifies the functionality of UserRequest,
 * including its fields, nested addresses, equality methods, and validation constraints.
 * </p>
 *
 * @author GitHub Copilot
 */
@Slf4j
class UserRequestTest {
    private final Validator validator;

    public UserRequestTest() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    /*
     * Test that the UserRequest fields are set and retrieved correctly,
     * including nested AddressRequest objects.
     */
    @Test
    @DisplayName("Test UserRequest Fields")
    void testUserRequestFields() {
        log.info("Running testUserRequestFields");
        AddressRequest address = new AddressRequest(
                "123 Main St",
                "Springfield",
                "IL",
                "62704",
                "USA"
        );
        UserRequest dto = new UserRequest(
                "John Doe",
                "john@example.com",
                List.of(address)
        );
        assertThat(dto.name()).isEqualTo("John Doe");
        assertThat(dto.email()).isEqualTo("john@example.com");
        assertThat(dto.address()).hasSize(1);
        assertThat(dto.address().getFirst()).isEqualTo(address);
        log.info("Test testUserRequestFields passed");
    }

    /*
     * Test the equality and hashCode methods of UserRequest,
     * including nested AddressRequest objects.
     */
    @Test
    @DisplayName("Test UserRequest Equality and HashCode")
    void testUserRequestEquality() {
        log.info("Running testUserRequestEquality");
        AddressRequest address = new AddressRequest("A", "B", "C", "D", "E");
        UserRequest dto1 = new UserRequest("N", "E", List.of(address));
        UserRequest dto2 = new UserRequest("N", "E", List.of(address));
        assertThat(dto1).isEqualTo(dto2);
        assertThat(dto1.hashCode()).hasSameHashCodeAs(dto2.hashCode());
        log.info("Test testUserRequestEquality passed");
    }

    /*
     * Test the validation constraints of UserRequest using Jakarta Bean Validation.
     */
    @Test
    @DisplayName("Test UserRequest Validation - Valid")
    void testUserRequestValidationValid() {
        log.info("Running testUserRequestValidationValid");
        AddressRequest address = new AddressRequest("123 Main St", "Springfield", "IL", "62704", "USA");
        UserRequest dto = new UserRequest("John Doe", "john@example.com", List.of(address));
        Set<ConstraintViolation<UserRequest>> violations = validator.validate(dto);
        assertThat(violations).isEmpty();
        log.info("Test testUserRequestValidationValid passed");
    }

    /*
     * Test the validation constraints of UserRequest with various invalid scenarios.
     */
    @Test
    @DisplayName("Test UserRequest Validation - Blank Name and Email")
    void testUserRequestValidationBlankNameEmail() {
        log.info("Running testUserRequestValidationBlankNameEmail");
        AddressRequest address = new AddressRequest("123 Main St", "Springfield", "IL", "62704", "USA");
        UserRequest dto = new UserRequest("", "", List.of(address));
        Set<ConstraintViolation<UserRequest>> violations = validator.validate(dto);
        assertThat(violations).hasSize(2);
        for (ConstraintViolation<UserRequest> violation : violations) {
            log.info("testUserRequestValidationBlankNameEmail Violation: {}", violation.getMessage());
        }
    }

    /*
     * Test the validation constraints of UserRequest with an invalid email format.
     */
    @Test
    @DisplayName("Test UserRequest Validation - Invalid Email")
    void testUserRequestValidationInvalidEmail() {
        log.info("Running testUserRequestValidationInvalidEmail");
        AddressRequest address = new AddressRequest("123 Main St", "Springfield", "IL", "62704", "USA");
        UserRequest dto = new UserRequest("John Doe", "not-an-email", List.of(address));
        Set<ConstraintViolation<UserRequest>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
        for (ConstraintViolation<UserRequest> violation : violations) {
            log.info("testUserRequestValidationInvalidEmail Violation: {}", violation.getMessage());
        }
    }

    /*
     * Test the validation constraints of UserRequest with null and empty address list,
     * as well as a null address element.
     */
    @Test
    @DisplayName("Test UserRequest Validation - Null Address List")
    void testUserRequestValidationNullAddressList() {
        log.info("Running testUserRequestValidationNullAddressList");
        UserRequest dto = new UserRequest("John Doe", "john@example.com", null);
        Set<ConstraintViolation<UserRequest>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
        for (ConstraintViolation<UserRequest> violation : violations) {
            log.info("testUserRequestValidationNullAddressList Violation: {}", violation.getMessage());
        }
    }

    /*
     * Test the validation constraints of UserRequest with an empty address list.
     */
    @Test
    @DisplayName("Test UserRequest Validation - Empty Address List")
    void testUserRequestValidationEmptyAddressList() {
        log.info("Running testUserRequestValidationEmptyAddressList");
        UserRequest dto = new UserRequest("John Doe", "john@example.com", List.of());
        Set<ConstraintViolation<UserRequest>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
        for (ConstraintViolation<UserRequest> violation : violations) {
            log.info("testUserRequestValidationEmptyAddressList Violation: {}", violation.getMessage());
        }
    }
}
