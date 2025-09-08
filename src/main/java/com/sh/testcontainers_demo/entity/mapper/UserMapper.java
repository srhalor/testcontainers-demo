package com.sh.testcontainers_demo.entity.mapper;

import com.sh.testcontainers_demo.dto.UserRequest;
import com.sh.testcontainers_demo.dto.UserResponse;
import com.sh.testcontainers_demo.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

/**
 * Mapper interface for User entity and UserResponse, UserRequest.
 * <p>
 * This interface uses MapStruct to automatically generate
 * the implementation for mapping between User and UserResponse.
 * </p>
 *
 * @author Shailesh Halor
 */
@Mapper(componentModel = SPRING, uses = AddressMapper.class)
public interface UserMapper {

    /**
     * Converts a User entity to a UserResponse.
     *
     * @param user the User entity
     * @return the corresponding UserResponse
     */
    UserResponse toDto(User user);


    /**
     * Converts a UserRequest to a User entity.
     *
     * @param userRequest the UserRequest
     * @return the corresponding User entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    User toEntity(UserRequest userRequest);
}
