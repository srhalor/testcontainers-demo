package com.sh.testcontainers_demo.entity.mapper;

import com.sh.testcontainers_demo.dto.AddressRequest;
import com.sh.testcontainers_demo.dto.AddressResponse;
import com.sh.testcontainers_demo.dto.UserRequest;
import com.sh.testcontainers_demo.dto.UserResponse;
import com.sh.testcontainers_demo.entity.Address;
import com.sh.testcontainers_demo.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for UserMapper using MapStruct.
 * <p>
 * Verifies mapping between User and UserResponse/UserRequest, including nested addresses and audit fields.
 * </p>
 *
 * @author Shailesh Halor
 */
@Slf4j
@SpringBootTest
class UserMapperTest {

    @Autowired
    private UserMapper mapper;

    /*
     * Test mapping from User entity to UserResponse.
     * Verifies all fields including nested addresses and audit fields are correctly mapped.
     */
    @Test
    void testToDto() {
        log.info("Running testToDto");
        Instant now = Instant.now();
        Address address = Address.builder()
                .id(1L)
                .street("123 Main St")
                .city("Springfield")
                .state("IL")
                .zipCode("62704")
                .country("USA")
                .createdAt(now)
                .updatedAt(now)
                .createdBy("creator")
                .lastModifiedBy("modifier")
                .build();
        User user = User.builder()
                .id(10L)
                .name("Bruce Wayne")
                .email("bruce@wayne.com")
                .createdAt(now)
                .updatedAt(now)
                .createdBy("creator")
                .lastModifiedBy("modifier")
                .address(List.of(address))
                .build();
        UserResponse dto = mapper.toDto(user);
        log.info("Mapped User to UserResponse: {}", dto);
        assertThat(dto.id()).isEqualTo(user.getId());
        assertThat(dto.name()).isEqualTo(user.getName());
        assertThat(dto.email()).isEqualTo(user.getEmail());
        assertThat(dto.createdAt()).isEqualTo(user.getCreatedAt());
        assertThat(dto.updatedAt()).isEqualTo(user.getUpdatedAt());
        assertThat(dto.createdBy()).isEqualTo(user.getCreatedBy());
        assertThat(dto.lastModifiedBy()).isEqualTo(user.getLastModifiedBy());
        assertThat(dto.address()).hasSize(1);
        AddressResponse addressResponse = dto.address().getFirst();
        assertThat(addressResponse.street()).isEqualTo(address.getStreet());
        log.info("Test testToDto passed");
    }

    /**
     * Test mapping from UserRequest to User entity.
     * Verifies all fields including nested addresses are correctly mapped.
     */
    @Test
    void testToEntity() {
        log.info("Running testToEntity");
        AddressRequest addressRequest = new AddressRequest(
                "456 Elm St",
                "Metropolis",
                "NY",
                "10001",
                "USA"
        );
        UserRequest dto = new UserRequest(
                "Clark Kent",
                "clark@dailyplanet.com",
                List.of(addressRequest)
        );
        User user = mapper.toEntity(dto);
        log.info("Mapped UserRequest to User: {}", user);
        assertThat(user.getName()).isEqualTo(dto.name());
        assertThat(user.getEmail()).isEqualTo(dto.email());
        assertThat(user.getAddress()).hasSize(1);
        Address address = user.getAddress().getFirst();
        assertThat(address.getStreet()).isEqualTo(addressRequest.street());
        log.info("Test testToEntity passed");
    }

    /**
     * Test mapping from null User entity to UserResponse.
     */
    @Test
    void testToDtoWithNullEntity() {
        log.info("Running testToDtoWithNullEntity");
        UserResponse dto = mapper.toDto(null);
        assertThat(dto).isNull();
        log.info("Test testToDtoWithNullEntity passed");
    }

    /**
     * Test mapping from null UserRequest to User entity.
     */
    @Test
    void testToEntityWithNullRequest() {
        log.info("Running testToEntityWithNullRequest");
        User user = mapper.toEntity(null);
        assertThat(user).isNull();
        log.info("Test testToEntityWithNullRequest passed");
    }

    /**
     * Test mapping from User entity with null fields to UserResponse.
     */
    @Test
    void testToDtoWithNullFields() {
        log.info("Running testToDtoWithNullFields");
        User user = User.builder().build();
        UserResponse dto = mapper.toDto(user);
        assertThat(dto.id()).isNull();
        assertThat(dto.name()).isNull();
        assertThat(dto.email()).isNull();
        assertThat(dto.createdAt()).isNull();
        assertThat(dto.updatedAt()).isNull();
        assertThat(dto.createdBy()).isNull();
        assertThat(dto.lastModifiedBy()).isNull();
        assertThat(dto.address()).isNull();
        log.info("Test testToDtoWithNullFields passed");
    }

    /**
     * Test mapping from UserRequest with null fields to User entity.
     */
    @Test
    void testToEntityWithNullFields() {
        log.info("Running testToEntityWithNullFields");
        UserRequest dto = new UserRequest(null, null, null);
        User user = mapper.toEntity(dto);
        assertThat(user.getName()).isNull();
        assertThat(user.getEmail()).isNull();
        assertThat(user.getAddress()).isNull();
        log.info("Test testToEntityWithNullFields passed");
    }

    /**
     * Test mapping from default UserRequest to User entity.
     */
    @Test
    void testToEntityWithDefaultRequest() {
        log.info("Running testToEntityWithDefaultRequest");
        UserRequest dto = new UserRequest("", "", List.of());
        User user = mapper.toEntity(dto);
        assertThat(user.getName()).isEmpty();
        assertThat(user.getEmail()).isEmpty();
        assertThat(user.getAddress()).isEmpty();
        log.info("Test testToEntityWithDefaultRequest passed");
    }

    /**
     * Test mapping from User entity with empty address list to UserResponse.
     */
    @Test
    void testToDtoWithEmptyAddressList() {
        log.info("Running testToDtoWithEmptyAddressList");
        User user = User.builder().address(List.of()).build();
        UserResponse dto = mapper.toDto(user);
        assertThat(dto.address()).isEmpty();
        log.info("Test testToDtoWithEmptyAddressList passed");
    }

    /**
     * Test mapping from UserRequest with empty address list to User entity.
     */
    @Test
    void testToEntityWithEmptyAddressList() {
        log.info("Running testToEntityWithEmptyAddressList");
        UserRequest dto = new UserRequest("Clark Kent", "clark@dailyplanet.com", List.of());
        User user = mapper.toEntity(dto);
        assertThat(user.getAddress()).isEmpty();
        log.info("Test testToEntityWithEmptyAddressList passed");
    }
}
