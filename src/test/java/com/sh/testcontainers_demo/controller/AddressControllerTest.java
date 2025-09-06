package com.sh.testcontainers_demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sh.testcontainers_demo.dto.AddressRequest;
import com.sh.testcontainers_demo.dto.AddressResponse;
import com.sh.testcontainers_demo.exception.AddressNotFoundException;
import com.sh.testcontainers_demo.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit tests for AddressController.
 * <p>
 * This test class verifies the functionality of AddressController endpoints,
 * including creating, retrieving, updating, and deleting addresses.
 * It uses MockMvc to simulate HTTP requests and Mockito to mock the AddressService.
 * </p>
 *
 * @author Shailesh Halor
 */
@Slf4j
@WebMvcTest(AddressController.class)
class AddressControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    /*
     * Test configuration to provide a mock AddressService bean.
     */
    @TestConfiguration
    static class MockConfig {
        /*
         * Provides a mock AddressService bean for testing.
         */
        @Bean
        public AddressService addressService() {
            return Mockito.mock(AddressService.class);
        }
    }

    @Autowired
    private AddressService addressService;

    /*
     * Test creating a new address.
     */
    @Test
    @DisplayName("Test Create Address")
    void testCreateAddress() throws Exception {
        log.info("Running testCreateAddress");
        AddressRequest request = new AddressRequest("Street", "City", "State", "12345", "Country");
        AddressResponse response = new AddressResponse(1L, "Street", "City", "State", "12345", "Country", Instant.now(), Instant.now(), "creator", "modifier");
        Mockito.when(addressService.createAddress(any(AddressRequest.class))).thenReturn(response);
        mockMvc.perform(post("/addresses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
        log.info("Test testCreateAddress passed");
    }

    /*
     * Test retrieving an address by ID.
     */
    @Test
    @DisplayName("Test Get Address By ID")
    void testGetAddressById() throws Exception {
        log.info("Running testGetAddressById");
        AddressResponse response = new AddressResponse(1L, "Street", "City", "State", "12345", "Country", Instant.now(), Instant.now(), "creator", "modifier");
        Mockito.when(addressService.getAddressById(1L)).thenReturn(response);
        mockMvc.perform(get("/addresses/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
        log.info("Test testGetAddressById passed");
    }

    /*
     * Test updating an existing address.
     */
    @Test
    @DisplayName("Test Update Address")
    void testUpdateAddress() throws Exception {
        log.info("Running testUpdateAddress");
        AddressRequest request = new AddressRequest("New Street", "New City", "New State", "54321", "New Country");
        AddressResponse response = new AddressResponse(1L, "New Street", "New City", "New State", "54321", "New Country", Instant.now(), Instant.now(), "creator", "modifier");
        Mockito.when(addressService.updateAddress(eq(1L), any(AddressRequest.class))).thenReturn(response);
        mockMvc.perform(put("/addresses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.street").value("New Street"));
        log.info("Test testUpdateAddress passed");
    }

    /*
     * Test deleting an address by ID.
     */
    @Test
    @DisplayName("Test Delete Address")
    void testDeleteAddress() throws Exception {
        log.info("Running testDeleteAddress");
        Mockito.doNothing().when(addressService).deleteAddress(1L);
        mockMvc.perform(delete("/addresses/1"))
                .andExpect(status().isNoContent());
        log.info("Test testDeleteAddress passed");
    }

    /*
     * Test retrieving all addresses.
     */
    @Test
    @DisplayName("Test Get All Addresses")
    void testGetAllAddresses() throws Exception {
        log.info("Running testGetAllAddresses");
        AddressResponse response = new AddressResponse(1L, "Street", "City", "State", "12345", "Country", Instant.now(), Instant.now(), "creator", "modifier");
        List<AddressResponse> responses = Collections.singletonList(response);
        Mockito.when(addressService.getAllAddresses()).thenReturn(responses);
        mockMvc.perform(get("/addresses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
        log.info("Test testGetAllAddresses passed");
    }

    /*
     * Test retrieving an address by ID when the address does not exist.
     * Verifies that a 404 Not Found status is returned.
     */
    @Test
    @DisplayName("Test Get Address By ID - Not Found")
    void testGetAddressById_NotFound() throws Exception {
        log.info("Running testGetAddressById_NotFound");
        Mockito.when(addressService.getAddressById(99L)).thenThrow(new AddressNotFoundException("Address not found with id: 99"));
        mockMvc.perform(get("/addresses/99"))
                .andExpect(status().isNotFound());
        log.info("Test testGetAddressById_NotFound passed");
    }
}
