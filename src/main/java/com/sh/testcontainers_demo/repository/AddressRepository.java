package com.sh.testcontainers_demo.repository;

import com.sh.testcontainers_demo.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Address entity.
 * <p>
 * This interface extends JpaRepository to provide CRUD operations
 * and custom query methods for Address entities.
 * </p>
 *
 * @author Shailesh Halor
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}