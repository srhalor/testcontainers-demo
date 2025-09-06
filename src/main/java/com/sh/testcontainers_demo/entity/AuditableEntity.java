package com.sh.testcontainers_demo.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.Instant;

/**
 * Base class for entities that require auditing information.
 * Provides fields for creation and update timestamps.
 * <p>
 * This class should be extended by any entity that requires auditing.
 * </p>
 *
 * @author Shailesh Halor
 */
@Slf4j
@MappedSuperclass
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public abstract class AuditableEntity implements Serializable {

    @Column(name = "created_date", updatable = false, nullable = false)
    @CreatedDate
    private Instant createdAt;

    @Column(name = "updated_date")
    @LastModifiedDate
    private Instant updatedAt;

    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    @LastModifiedBy
    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    /**
     * Default constructor.
     * <p>
     * Protected to prevent instantiation outside of subclasses.
     * </p>
     */
    @PrePersist
    protected void onCreate() {
        log.debug("Setting createdAt and updatedAt timestamps for entity: {}", this.getClass().getSimpleName());
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
        this.createdBy = "System";
        this.lastModifiedBy = "system";
    }

    /**
     * Updates the updatedAt field to the current timestamp.
     * <p>
     * This method is called before the entity is updated in the database.
     * </p>
     */
    @PreUpdate
    protected void onUpdate() {
        log.debug("Updating updatedAt timestamp for entity: {}", this.getClass().getSimpleName());
        this.updatedAt = Instant.now();
        this.lastModifiedBy = "system";
    }
}
