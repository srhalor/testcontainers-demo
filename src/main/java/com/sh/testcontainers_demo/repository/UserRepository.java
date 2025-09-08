package com.sh.testcontainers_demo.repository;

import com.sh.testcontainers_demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for User entity.
 * <p>
 * This interface extends JpaRepository to provide CRUD operations
 * and custom query methods for User entities.
 * </p>
 *
 * @author Shailesh Halor
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
