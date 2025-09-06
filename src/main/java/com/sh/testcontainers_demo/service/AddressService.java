package com.sh.testcontainers_demo.service;

import com.sh.testcontainers_demo.dto.AddressRequest;
import com.sh.testcontainers_demo.dto.AddressResponse;
import com.sh.testcontainers_demo.entity.Address;
import com.sh.testcontainers_demo.entity.mapper.AddressMapper;
import com.sh.testcontainers_demo.exception.AddressNotFoundException;
import com.sh.testcontainers_demo.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service class for managing Address entities.
 * <p>
 * This service provides methods to create, retrieve, update, and delete addresses.
 * It uses AddressRepository for database operations and AddressMapper for converting
 * between entity and DTO.
 * </p>
 *
 * @author Shailesh Halor
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    /**
     * Creates a new address.
     *
     * @param request the address request DTO
     * @return the created address response DTO
     */
    @Transactional
    public AddressResponse createAddress(AddressRequest request) {
        log.info("Creating new address: {}", request);
        Address address = addressMapper.toEntity(request);
        Address saved = addressRepository.save(address);
        log.info("Created address with ID: {}", saved.getId());
        return addressMapper.toDto(saved);
    }

    /**
     * Retrieves an address by its ID.
     *
     * @param id the ID of the address
     * @return the address response DTO
     */
    @Transactional(readOnly = true)
    public AddressResponse getAddressById(Long id) {
        log.info("Retrieving address with ID: {}", id);
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException("Address not found with id: " + id));
        log.info("Retrieved address: {}", address);
        return addressMapper.toDto(address);
    }

    /**
     * Updates an existing address.
     *
     * @param id      the ID of the address to update
     * @param request the address request DTO with updated data
     * @return the updated address response DTO
     */
    @Transactional
    public AddressResponse updateAddress(Long id, AddressRequest request) {
        log.info("Updating address with ID: {}", id);
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException("Address not found with id: " + id));
        Address updated = addressMapper.toEntity(request);
        updated.setId(address.getId());
        updated.setCreatedAt(address.getCreatedAt());
        updated.setCreatedBy(address.getCreatedBy());
        updated.setUser(address.getUser());
        Address saved = addressRepository.save(updated);
        log.info("Updated address with ID: {}", saved.getId());
        return addressMapper.toDto(saved);
    }

    /**
     * Deletes an address by its ID.
     *
     * @param id the ID of the address to delete
     */
    @Transactional
    public void deleteAddress(Long id) {
        log.info("Deleting address with ID: {}", id);
        if (!addressRepository.existsById(id)) {
            throw new AddressNotFoundException("Address not found with id: " + id);
        }
        log.info("Deleted address with ID: {}", id);
        addressRepository.deleteById(id);
    }

    /**
     * Retrieves all addresses.
     *
     * @return a list of address response DTOs
     */
    @Transactional(readOnly = true)
    public List<AddressResponse> getAllAddresses() {
        log.info("Retrieving all addresses");
        return addressRepository.findAll().stream()
                .map(addressMapper::toDto)
                .toList();
    }
}
