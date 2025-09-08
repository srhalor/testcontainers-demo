package com.sh.testcontainers_demo.entity.mapper;

import com.sh.testcontainers_demo.dto.AddressRequest;
import com.sh.testcontainers_demo.dto.AddressResponse;
import com.sh.testcontainers_demo.entity.Address;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for AddressMapper using MapStruct.
 * <p>
 * Verifies mapping between Address and AddressResponse/AddressRequest, including audit fields.
 * </p>
 *
 * @author Shailesh Halor
 */
@Slf4j
@SpringBootTest
class AddressMapperTest {

    @Autowired
    private AddressMapper mapper;

    /*
     * Test mapping from Address entity to AddressResponse.
     * Verifies all fields including audit fields are correctly mapped.
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
        AddressResponse dto = mapper.toDto(address);
        log.info("Mapped Address to AddressResponse: {}", dto);
        assertThat(dto.id()).isEqualTo(address.getId());
        assertThat(dto.street()).isEqualTo(address.getStreet());
        assertThat(dto.city()).isEqualTo(address.getCity());
        assertThat(dto.state()).isEqualTo(address.getState());
        assertThat(dto.zipCode()).isEqualTo(address.getZipCode());
        assertThat(dto.country()).isEqualTo(address.getCountry());
        assertThat(dto.createdAt()).isEqualTo(address.getCreatedAt());
        assertThat(dto.updatedAt()).isEqualTo(address.getUpdatedAt());
        assertThat(dto.createdBy()).isEqualTo(address.getCreatedBy());
        assertThat(dto.lastModifiedBy()).isEqualTo(address.getLastModifiedBy());
        log.info("Test testToDto passed");
    }

    /*
     * Test mapping from AddressRequest to Address entity.
     * Verifies all fields are correctly mapped and user is ignored.
     */
    @Test
    void testToEntity() {
        log.info("Running testToEntity");
        AddressRequest dto = new AddressRequest(
                "456 Elm St",
                "Metropolis",
                "NY",
                "10001",
                "USA"
        );
        Address address = mapper.toEntity(dto);
        log.info("Mapped AddressResponse to Address: {}", address);
        assertThat(address.getStreet()).isEqualTo(dto.street());
        assertThat(address.getCity()).isEqualTo(dto.city());
        assertThat(address.getState()).isEqualTo(dto.state());
        assertThat(address.getZipCode()).isEqualTo(dto.zipCode());
        assertThat(address.getCountry()).isEqualTo(dto.country());
        assertThat(address.getUser()).isNull();
        // user is ignored
        log.info("Test testToEntity passed");
    }

    /**
     * Test mapping from null Address entity to AddressResponse.
     */
    @Test
    void testToDtoWithNullEntity() {
        log.info("Running testToDtoWithNullEntity");
        AddressResponse dto = mapper.toDto(null);
        assertThat(dto).isNull();
        log.info("Test testToDtoWithNullEntity passed");
    }

    /**
     * Test mapping from null AddressRequest to Address entity.
     */
    @Test
    void testToEntityWithNullRequest() {
        log.info("Running testToEntityWithNullRequest");
        Address address = mapper.toEntity(null);
        assertThat(address).isNull();
        log.info("Test testToEntityWithNullRequest passed");
    }

    /**
     * Test mapping from Address entity with null fields to AddressResponse.
     */
    @Test
    void testToDtoWithNullFields() {
        log.info("Running testToDtoWithNullFields");
        Address address = Address.builder().build();
        AddressResponse dto = mapper.toDto(address);
        assertThat(dto.id()).isNull();
        assertThat(dto.street()).isNull();
        assertThat(dto.city()).isNull();
        assertThat(dto.state()).isNull();
        assertThat(dto.zipCode()).isNull();
        assertThat(dto.country()).isNull();
        assertThat(dto.createdAt()).isNull();
        assertThat(dto.updatedAt()).isNull();
        assertThat(dto.createdBy()).isNull();
        assertThat(dto.lastModifiedBy()).isNull();
        log.info("Test testToDtoWithNullFields passed");
    }

    /**
     * Test mapping from AddressRequest with null fields to Address entity.
     */
    @Test
    void testToEntityWithNullFields() {
        log.info("Running testToEntityWithNullFields");
        AddressRequest dto = new AddressRequest(null, null, null, null, null);
        Address address = mapper.toEntity(dto);
        assertThat(address.getStreet()).isNull();
        assertThat(address.getCity()).isNull();
        assertThat(address.getState()).isNull();
        assertThat(address.getZipCode()).isNull();
        assertThat(address.getCountry()).isNull();
        log.info("Test testToEntityWithNullFields passed");
    }

    /**
     * Test mapping from default AddressRequest to Address entity.
     */
    @Test
    void testToEntityWithDefaultRequest() {
        log.info("Running testToEntityWithDefaultRequest");
        AddressRequest dto = new AddressRequest("", "", "", "", "");
        Address address = mapper.toEntity(dto);
        assertThat(address.getStreet()).isEmpty();
        assertThat(address.getCity()).isEmpty();
        assertThat(address.getState()).isEmpty();
        assertThat(address.getZipCode()).isEmpty();
        assertThat(address.getCountry()).isEmpty();
        log.info("Test testToEntityWithDefaultRequest passed");
    }
}
