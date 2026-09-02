-- V4__create_file_versions.sql
-- Prepares the architecture for file versioning.

CREATE TABLE file_versions (
    id             CHAR(36)     NOT NULL,
    file_id        CHAR(36)     NOT NULL,
    version_number INT          NOT NULL,
    storage_key    VARCHAR(512) NOT NULL,
    size           BIGINT       NOT NULL,
    created_at     DATETIME(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6),

    CONSTRAINT pk_file_versions              PRIMARY KEY (id),
    CONSTRAINT uk_file_versions_storage_key  UNIQUE      (storage_key),
    CONSTRAINT uk_file_versions_file_version UNIQUE      (file_id, version_number),
    CONSTRAINT fk_file_versions_file         FOREIGN KEY (file_id) REFERENCES files (id) ON DELETE CASCADE,
    CONSTRAINT chk_file_versions_version_num CHECK       (version_number > 0),
    CONSTRAINT chk_file_versions_size        CHECK       (size >= 0)
);

CREATE INDEX idx_file_versions_file_id ON file_versions (file_id);
