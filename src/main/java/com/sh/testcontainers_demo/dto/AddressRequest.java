package com.sh.testcontainers_demo.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object for creating or updating an Address.
 * <p>
 * This record encapsulates the data required to create or update an address,
 * including street, city, state, zip code, and country.
 * </p>
 *
 * @param street  the street address
 * @param city    the city
 * @param state   the state
 * @param zipCode the zip code
 * @param country the country
 * @author Shailesh Halor
 */
public record AddressRequest(
        @NotBlank(message = "Street cannot be blank")
        String street,
        @NotBlank(message = "City cannot be blank")
        String city,
        @NotBlank(message = "State cannot be blank")
        String state,
        @NotBlank(message = "Zip code cannot be blank")
        String zipCode,
        @NotBlank(message = "Country cannot be blank")
        String country
) {
}
