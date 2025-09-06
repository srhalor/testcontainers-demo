package com.sh.testcontainers_demo.repository;

import com.sh.testcontainers_demo.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for the UserRepository interface.
 * <p>
 * This test class verifies the CRUD operations provided by the UserRepository
 * using an in-memory database.
 * </p>
 *
 * @author Shailesh Halor
 */
@Slf4j
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    /*
     * Test saving and retrieving a User entity.
     */
    @Test
    @DisplayName("Should save and find User")
    void testSaveAndFindUser() {
        log.info("Running testSaveAndFindUser");
        User user = User.builder()
                .name("John Doe")
                .email("john@example.com")
                .build();
        User saved = userRepository.save(user);
        Optional<User> found = userRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo("john@example.com");
        log.info("Test testSaveAndFindUser passed");
    }

    /*
     * Test updating a User entity.
     */
    @Test
    @DisplayName("Should update User")
    void testUpdateUser() {
        log.info("Running testUpdateUser");
        User user = User.builder()
                .name("Old Name")
                .email("old@example.com")
                .build();
        User saved = userRepository.save(user);
        saved.setName("New Name");
        User updated = userRepository.save(saved);
        assertThat(updated.getName()).isEqualTo("New Name");
        log.info("Test testUpdateUser passed");
    }

    /*
     * Test deleting a User entity.
     */
    @Test
    @DisplayName("Should delete User")
    void testDeleteUser() {
        log.info("Running testDeleteUser");
        User user = User.builder()
                .name("Delete Me")
                .email("delete@example.com")
                .build();
        User saved = userRepository.save(user);
        userRepository.delete(saved);
        Optional<User> found = userRepository.findById(saved.getId());
        assertThat(found).isNotPresent();
        log.info("Test testDeleteUser passed");
    }
}
