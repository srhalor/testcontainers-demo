package com.sh.testcontainers_demo.controller;

import com.sh.testcontainers_demo.dto.AddressRequest;
import com.sh.testcontainers_demo.dto.AddressResponse;
import com.sh.testcontainers_demo.service.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/addresses")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    /**
     * Creates a new address.
     *
     * @param request the address request DTO
     * @return ResponseEntity with the created address and HTTP status 201
     */
    @PostMapping
    public ResponseEntity<AddressResponse> createAddress(@RequestBody AddressRequest request) {
        log.info("Received request to create address: {}", request);
        AddressResponse response = addressService.createAddress(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Retrieves an address by its ID.
     *
     * @param id the ID of the address
     * @return ResponseEntity with the address and HTTP status 200
     */
    @GetMapping("/{id}")
    public ResponseEntity<AddressResponse> getAddressById(@PathVariable Long id) {
        log.info("Received request to get address with ID: {}", id);
        AddressResponse response = addressService.getAddressById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates an existing address.
     *
     * @param id      the ID of the address to update
     * @param request the address request DTO with updated data
     * @return ResponseEntity with the updated address and HTTP status 200
     */
    @PutMapping("/{id}")
    public ResponseEntity<AddressResponse> updateAddress(@PathVariable Long id, @RequestBody AddressRequest request) {
        log.info("Received request to update address with ID: {}", id);
        AddressResponse response = addressService.updateAddress(id, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Deletes an address by its ID.
     *
     * @param id the ID of the address to delete
     * @return ResponseEntity with HTTP status 204 (No Content)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        log.info("Received request to delete address with ID: {}", id);
        addressService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves all addresses.
     *
     * @return ResponseEntity with the list of addresses and HTTP status 200
     */
    @GetMapping
    public ResponseEntity<List<AddressResponse>> getAllAddresses() {
        log.info("Received request to get all addresses");
        List<AddressResponse> responses = addressService.getAllAddresses();
        return ResponseEntity.ok(responses);
    }
}

