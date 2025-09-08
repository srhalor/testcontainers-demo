package com.sh.testcontainers_demo.service;

import com.sh.testcontainers_demo.dto.AddressRequest;
import com.sh.testcontainers_demo.dto.AddressResponse;
import com.sh.testcontainers_demo.entity.Address;
import com.sh.testcontainers_demo.entity.mapper.AddressMapper;
import com.sh.testcontainers_demo.exception.AddressNotFoundException;
import com.sh.testcontainers_demo.repository.AddressRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for AddressService.
 * <p>
 * This test class verifies the functionality of AddressService methods,
 * including creating, retrieving, updating, and deleting addresses.
 * It uses Mockito to mock dependencies and JUnit 5 for assertions.
 * </p>
 *
 * @author Shailesh Halor
 */
@Slf4j
class AddressServiceTest {

    @Mock
    private AddressRepository addressRepository;
    @Mock
    private AddressMapper addressMapper;
    @InjectMocks
    private AddressService addressService;

    private Address address;
    private AddressRequest addressRequest;
    private AddressResponse addressResponse;
    private AutoCloseable mocks;

    /*
     * Set up test data and mocks before each test.
     */
    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
        addressRequest = new AddressRequest("Street", "City", "State", "12345", "Country");
        address = new Address();
        address.setId(1L);
        address.setStreet("Street");
        address.setCity("City");
        address.setState("State");
        address.setZipCode("12345");
        address.setCountry("Country");
        addressResponse = new AddressResponse(1L, "Street", "City", "State", "12345", "Country", Instant.now(), Instant.now(), "creator", "modifier");
    }

    /*
     * Clean up mocks after each test.
     */
    @AfterEach
    void tearDown() throws Exception {
        if (mocks != null) {
            mocks.close();
        }
    }

    /*
     * Test creating a new address.
     * Verifies that the address is saved and the response is correct.
     */
    @Test
    @DisplayName("Test Create Address")
    void testCreateAddress() {
        log.info("Running testCreateAddress");
        when(addressMapper.toEntity(any(AddressRequest.class))).thenReturn(address);
        when(addressRepository.save(any(Address.class))).thenReturn(address);
        when(addressMapper.toDto(any(Address.class))).thenReturn(addressResponse);
        AddressResponse result = addressService.createAddress(addressRequest);
        assertNotNull(result);
        assertEquals(addressResponse.id(), result.id());
        verify(addressRepository).save(address);
        log.info("Test testCreateAddress passed");
    }

    /*
     * Test retrieving an address by ID when the address exists.
     * Verifies that the correct address is returned.
     */
    @Test
    @DisplayName("Test Get Address By ID - Found")
    void testGetAddressById_Found() {
        log.info("Running testGetAddressById_Found");
        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));
        when(addressMapper.toDto(address)).thenReturn(addressResponse);
        AddressResponse result = addressService.getAddressById(1L);
        assertNotNull(result);
        assertEquals(1L, result.id());
        log.info("Test testGetAddressById_Found passed");
    }

    /*
     * Test retrieving an address by ID when the address does not exist.
     * Verifies that a RuntimeException is thrown.
     */
    @Test
    @DisplayName("Test Get Address By ID - Not Found")
    void testGetAddressById_NotFound() {
        log.info("Running testGetAddressById_NotFound");
        when(addressRepository.findById(1L)).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class, () -> addressService.getAddressById(1L));
        assertTrue(ex.getMessage().contains("Address not found"));
        log.info("Test testGetAddressById_NotFound passed");
    }

    /*
     * Test updating an existing address when the address exists.
     * Verifies that the address is updated and the response is correct.
     */
    @Test
    @DisplayName("Test Update Address - Found")
    void testUpdateAddress_Found() {
        log.info("Running testUpdateAddress_Found");
        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));
        Address updated = new Address();
        updated.setId(1L);
        updated.setStreet("New Street");
        updated.setCity("New City");
        updated.setState("New State");
        updated.setZipCode("54321");
        updated.setCountry("New Country");
        when(addressMapper.toEntity(addressRequest)).thenReturn(updated);
        when(addressRepository.save(any(Address.class))).thenReturn(updated);
        AddressResponse updatedResponse = new AddressResponse(1L, "New Street", "New City", "New State", "54321", "New Country", Instant.now(), Instant.now(), "creator", "modifier");
        when(addressMapper.toDto(updated)).thenReturn(updatedResponse);
        AddressResponse result = addressService.updateAddress(1L, addressRequest);
        assertNotNull(result);
        assertEquals("New Street", result.street());
        log.info("Test testUpdateAddress_Found passed");
    }

    /*
     * Test updating an address when the address does not exist.
     * Verifies that a RuntimeException is thrown.
     */
    @Test
    @DisplayName("Test Update Address - Not Found")
    void testUpdateAddress_NotFound() {
        log.info("Running testUpdateAddress_NotFound");
        when(addressRepository.findById(1L)).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class, () -> addressService.updateAddress(1L, addressRequest));
        assertTrue(ex.getMessage().contains("Address not found"));
        log.info("Test testUpdateAddress_NotFound passed");
    }

    /*
     * Test deleting an address when the address exists.
     * Verifies that the delete operation is performed.
     */
    @Test
    @DisplayName("Test Delete Address - Found")
    void testDeleteAddress_Found() {
        log.info("Running testDeleteAddress_Found");
        when(addressRepository.existsById(1L)).thenReturn(true);
        addressService.deleteAddress(1L);
        verify(addressRepository).deleteById(1L);
        log.info("Test testDeleteAddress_Found passed");
    }

    /*
     * Test deleting an address when the address does not exist.
     * Verifies that a RuntimeException is thrown.
     */
    @Test
    @DisplayName("Test Delete Address - Not Found")
    void testDeleteAddress_NotFound() {
        log.info("Running testDeleteAddress_NotFound");
        when(addressRepository.existsById(1L)).thenReturn(false);
        RuntimeException ex = assertThrows(RuntimeException.class, () -> addressService.deleteAddress(1L));
        assertTrue(ex.getMessage().contains("Address not found"));
        log.info("Test testDeleteAddress_NotFound passed");
    }

    /*
     * Test retrieving all addresses.
     * Verifies that the correct list of addresses is returned.
     */
    @Test
    @DisplayName("Test Get All Addresses")
    void testGetAllAddresses() {
        log.info("Running testGetAllAddresses");
        when(addressRepository.findAll()).thenReturn(Collections.singletonList(address));
        when(addressMapper.toDto(address)).thenReturn(addressResponse);
        List<AddressResponse> result = addressService.getAllAddresses();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(addressResponse.id(), result.getFirst().id());
        log.info("Test testGetAllAddresses passed");
    }

    /*
     * Test retrieving all addresses when there are no addresses.
     * Verifies that an empty list is returned.
     */
    @Test
    @DisplayName("Test Get All Addresses - Empty List")
    void testGetAllAddresses_Empty() {
        log.info("Running testGetAllAddresses_Empty");
        when(addressRepository.findAll()).thenReturn(Collections.emptyList());
        List<AddressResponse> result = addressService.getAllAddresses();
        assertNotNull(result);
        assertTrue(result.isEmpty());
        log.info("Test testGetAllAddresses_Empty passed");
    }

    /*
     * Test creating an address with minimal valid input.
     * Verifies that the address is created successfully.
     */
    @Test
    @DisplayName("Test Create Address - Minimal Valid Input")
    void testCreateAddress_MinimalValidInput() {
        log.info("Running testCreateAddress_MinimalValidInput");
        AddressRequest minimalRequest = new AddressRequest("A", "B", "C", "D", "E");
        Address minimalAddress = new Address();
        minimalAddress.setStreet("A");
        minimalAddress.setCity("B");
        minimalAddress.setState("C");
        minimalAddress.setZipCode("D");
        minimalAddress.setCountry("E");
        AddressResponse minimalResponse = new AddressResponse(2L, "A", "B", "C", "D", "E", Instant.now(), Instant.now(), "creator", "modifier");
        when(addressMapper.toEntity(any(AddressRequest.class))).thenReturn(minimalAddress);
        when(addressRepository.save(any(Address.class))).thenReturn(minimalAddress);
        when(addressMapper.toDto(any(Address.class))).thenReturn(minimalResponse);
        AddressResponse result = addressService.createAddress(minimalRequest);
        assertNotNull(result);
        assertEquals(minimalResponse.id(), result.id());
        assertEquals("A", result.street());
        log.info("Test testCreateAddress_MinimalValidInput passed");
    }

    /*
     * Test updating an address with partial changes.
     * Verifies that only the changed fields are updated.
     */
    @Test
    @DisplayName("Test Update Address - Partial Change")
    void testUpdateAddress_PartialChange() {
        log.info("Running testUpdateAddress_PartialChange");
        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));
        Address partialUpdate = new Address();
        partialUpdate.setId(1L);
        partialUpdate.setStreet("Street"); // unchanged
        partialUpdate.setCity("New City"); // changed
        partialUpdate.setState("State"); // unchanged
        partialUpdate.setZipCode("12345"); // unchanged
        partialUpdate.setCountry("Country"); // unchanged
        AddressRequest partialRequest = new AddressRequest("Street", "New City", "State", "12345", "Country");
        AddressResponse partialResponse = new AddressResponse(1L, "Street", "New City", "State", "12345", "Country", Instant.now(), Instant.now(), "creator", "modifier");
        when(addressMapper.toEntity(partialRequest)).thenReturn(partialUpdate);
        when(addressRepository.save(any(Address.class))).thenReturn(partialUpdate);
        when(addressMapper.toDto(partialUpdate)).thenReturn(partialResponse);
        AddressResponse result = addressService.updateAddress(1L, partialRequest);
        assertNotNull(result);
        assertEquals("New City", result.city());
        log.info("Test testUpdateAddress_PartialChange passed");
    }

    /*
     * Test creating an address with a null request.
     * Verifies that a NullPointerException is thrown.
     */
    @Test
    @DisplayName("Test Create Address - Null Request")
    void testCreateAddress_NullRequest() {
        log.info("Running testCreateAddress_NullRequest");
        assertThrows(NullPointerException.class, () -> addressService.createAddress(null));
        log.info("Test testCreateAddress_NullRequest passed");
    }

    /*
     * Test updating an address with a null request.
     * Verifies that a NullPointerException is thrown.
     */
    @Test
    @DisplayName("Test Update Address - Null Request")
    void testUpdateAddress_NullRequest() {
        log.info("Running testUpdateAddress_NullRequest");
        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));
        assertThrows(NullPointerException.class, () -> addressService.updateAddress(1L, null));
        log.info("Test testUpdateAddress_NullRequest passed");
    }

    /*
     * Test deleting an address with a null ID.
     */
    @Test
    @DisplayName("Test Delete Address - Null Id")
    void testDeleteAddress_NullId() {
        log.info("Running testDeleteAddress_NullId");
        assertThrows(AddressNotFoundException.class, () -> addressService.deleteAddress(null));
        log.info("Test testDeleteAddress_NullId passed");
    }

    /*
     * Test deleting an address with a negative ID.
     */
    @Test
    @DisplayName("Test Delete Address - Negative Id")
    void testDeleteAddress_NegativeId() {
        log.info("Running testDeleteAddress_NegativeId");
        when(addressRepository.existsById(-1L)).thenReturn(false);
        RuntimeException ex = assertThrows(RuntimeException.class, () -> addressService.deleteAddress(-1L));
        assertTrue(ex.getMessage().contains("Address not found"));
        log.info("Test testDeleteAddress_NegativeId passed");
    }

    /*
     * Test retrieving all addresses when the repository returns null.
     * Verifies that a NullPointerException is thrown.
     */
    @Test
    @DisplayName("Test Get All Addresses - Repository Returns Null")
    void testGetAllAddresses_RepositoryReturnsNull() {
        log.info("Running testGetAllAddresses_RepositoryReturnsNull");
        when(addressRepository.findAll()).thenReturn(null);
        assertThrows(NullPointerException.class, () -> addressService.getAllAddresses());
        log.info("Test testGetAllAddresses_RepositoryReturnsNull passed");
    }
}
