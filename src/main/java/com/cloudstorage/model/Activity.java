package com.cloudstorage.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "activities")
@Getter
@Setter
@NoArgsConstructor
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /** e.g. FILE_UPLOAD, FILE_DELETE, FOLDER_CREATE, SHARE_CREATE */
    @Column(nullable = false, length = 64)
    private String action;

    /** e.g. FILE, FOLDER, SHARE */
    @Column(name = "resource_type", nullable = false, length = 32)
    private String resourceType;

    /** UUID of the affected resource. */
    @Column(name = "resource_id", nullable = false)
    private UUID resourceId;

    /** Optional JSON metadata for additional context. */
    @Column(columnDefinition = "TEXT")
    private String metadata;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
