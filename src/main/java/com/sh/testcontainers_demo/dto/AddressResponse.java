package com.sh.testcontainers_demo.dto;

import java.time.Instant;

/**
 * Data Transfer Object for Address entity.
 * <p>
 * This record encapsulates the data related to an address,
 * including id, street, city, state, zip code, country, and auditing fields.
 * </p>
 *
 * @param id          the unique identifier of the address
 * @param street      the street of the address
 * @param city        the city of the address
 * @param state       the state of the address
 * @param zipCode     the zip code of the address
 * @param country     the country of the address
 * @param createdAt   the creation timestamp
 * @param updatedAt   the last update timestamp
 * @param createdBy   the creator
 * @param lastModifiedBy the last modifier
 *
 * @author Shailesh Halor
 */
public record AddressResponse(
    Long id,
    String street,
    String city,
    String state,
    String zipCode,
    String country,
    Instant createdAt,
    Instant updatedAt,
    String createdBy,
    String lastModifiedBy
) {}
