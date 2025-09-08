package com.sh.testcontainers_demo.dto;

import java.time.Instant;
import java.util.List;

/**
 * Data Transfer Object for User entity.
 * <p>
 * This record encapsulates the data related to a user,
 * including id, name, email, auditing fields, and a list of associated addresses.
 * </p>
 *
 * @param id      the unique identifier of the user
 * @param name    the name of the user
 * @param email   the email of the user
 * @param createdAt the creation timestamp
 * @param updatedAt the last update timestamp
 * @param createdBy the creator
 * @param lastModifiedBy the last modifier
 * @param address the list of addresses associated with the user
 *
 * @author Shailesh Halor
 */
public record UserResponse(
    Long id,
    String name,
    String email,
    Instant createdAt,
    Instant updatedAt,
    String createdBy,
    String lastModifiedBy,
    List<AddressResponse> address
) {}
