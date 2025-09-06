package com.sh.testcontainers_demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

/**
 * Data Transfer Object for creating or updating a User.
 * <p>
 * This record encapsulates the data required to create or update a user,
 * including name, email, and a list of associated addresses.
 * </p>
 *
 * @param name    the name of the user
 * @param email   the email of the user
 * @param address the list of addresses associated with the user
 * @author Shailesh Halor
 */
public record UserRequest(
        @NotBlank(message = "Name must not be blank")
        String name,
        @NotBlank(message = "Email must not be blank")
        @Email(message = "Email should be valid")
        String email,
        @NotNull(message = "Address list must not be null")
        @Size(min = 1, message = "At least one address is required")
        List<@NotNull AddressRequest> address
) {
}
