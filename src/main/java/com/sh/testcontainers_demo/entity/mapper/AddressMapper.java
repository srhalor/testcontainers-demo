package com.sh.testcontainers_demo.entity.mapper;

import com.sh.testcontainers_demo.dto.AddressRequest;
import com.sh.testcontainers_demo.dto.AddressResponse;
import com.sh.testcontainers_demo.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

/**
 * Mapper interface for Address entity and AddressResponse, AddressRequest.
 * <p>
 * This interface uses MapStruct to automatically generate
 * the implementation for mapping between Address and AddressResponse.
 * </p>
 *
 * @author Shailesh Halor
 */
@Mapper(componentModel = SPRING)
public interface AddressMapper {

    /**
     * Converts an Address entity to an AddressResponse.
     *
     * @param address the Address entity
     * @return the corresponding AddressResponse
     */
    AddressResponse toDto(Address address);

    /**
     * Converts an AddressRequest to an Address entity.
     *
     * @param addressRequest the AddressRequest
     * @return the corresponding Address entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "user", ignore = true)
    Address toEntity(AddressRequest addressRequest);
}
