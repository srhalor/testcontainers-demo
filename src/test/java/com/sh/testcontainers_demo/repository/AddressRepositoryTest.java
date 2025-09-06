package com.sh.testcontainers_demo.repository;

import com.sh.testcontainers_demo.entity.Address;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for the AddressRepository interface.
 * <p>
 * This test class verifies the CRUD operations provided by the AddressRepository
 * using an in-memory database.
 * </p>
 *
 * @author Shailesh Halor
 */
@Slf4j
@DataJpaTest
class AddressRepositoryTest {

    @Autowired
    private AddressRepository addressRepository;

    /*
     * Test saving and retrieving an Address entity.
     */
    @Test
    @DisplayName("Should save and find Address")
    void testSaveAndFindAddress() {
        Address address = Address.builder()
                .street("123 Main St")
                .city("Springfield")
                .state("IL")
                .zipCode("62704")
                .country("USA")
                .build();
        Address saved = addressRepository.save(address);
        Optional<Address> found = addressRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getStreet()).isEqualTo("123 Main St");
    }

    /*
     * Test updating an Address entity.
     */
    @Test
    @DisplayName("Should update Address")
    void testUpdateAddress() {
        Address address = Address.builder()
                .street("Old St")
                .city("Old City")
                .state("OC")
                .zipCode("00000")
                .country("Old Country")
                .build();
        Address saved = addressRepository.save(address);
        saved.setStreet("New St");
        Address updated = addressRepository.save(saved);
        assertThat(updated.getStreet()).isEqualTo("New St");
    }

    /*
     * Test deleting an Address entity.
     */
    @Test
    @DisplayName("Should delete Address")
    void testDeleteAddress() {
        Address address = Address.builder()
                .street("Delete St")
                .city("Delete City")
                .state("DC")
                .zipCode("99999")
                .country("Delete Country")
                .build();
        Address saved = addressRepository.save(address);
        addressRepository.delete(saved);
        Optional<Address> found = addressRepository.findById(saved.getId());
        assertThat(found).isNotPresent();
    }
}
