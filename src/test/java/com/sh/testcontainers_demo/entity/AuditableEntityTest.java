package com.sh.testcontainers_demo.entity;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for the AuditableEntity class.
 * <p>
 * This test class verifies the functionality of the auditing fields
 * and methods in the AuditableEntity base class.
 * </p>
 *
 * @author Shailesh Halor
 */
@Slf4j
class AuditableEntityTest {

    /*
     * A simple concrete subclass of AuditableEntity for testing purposes.
     */
    static class TestEntity extends AuditableEntity {
        // No additional fields
    }

    /*
     * Test that onCreate sets the createdAt, updatedAt, createdBy, and lastModifiedBy fields correctly.
     */
    @Test
    @DisplayName("Test onCreate Sets Audit Fields")
    void testOnCreateSetsAuditFields() {
        log.info("Running testOnCreateSetsAuditFields");
        TestEntity entity = new TestEntity();
        entity.onCreate();
        assertThat(entity.getCreatedAt()).isNotNull();
        assertThat(entity.getUpdatedAt()).isNotNull();
        assertThat(entity.getCreatedBy()).isEqualTo("System");
        assertThat(entity.getLastModifiedBy()).isEqualTo("system");
        assertThat(entity.getCreatedAt()).isEqualTo(entity.getUpdatedAt());
        log.info("Test testOnCreateSetsAuditFields passed");
    }

    /*
     * Test that onUpdate sets the updatedAt and lastModifiedBy fields correctly.
     */
    @Test
    @DisplayName("Test onUpdate Sets Updated Fields")
    void testOnUpdateSetsUpdatedFields() {
        log.info("Running testOnUpdateSetsUpdatedFields");
        TestEntity entity = new TestEntity();
        entity.onCreate();
        Instant createdAt = entity.getCreatedAt();
        entity.onUpdate();
        assertThat(entity.getUpdatedAt()).isAfterOrEqualTo(createdAt);
        assertThat(entity.getLastModifiedBy()).isEqualTo("system");
        log.info("Test testOnUpdateSetsUpdatedFields passed");
    }
}
