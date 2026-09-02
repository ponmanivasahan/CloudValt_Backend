-- V3__create_files.sql
-- Stores file metadata only. Actual binaries live in S3 (storage_key).

CREATE TABLE files (
    id            CHAR(36)     NOT NULL,
    name          VARCHAR(255) NOT NULL,
    original_name VARCHAR(255) NOT NULL,
    mime_type     VARCHAR(127) NOT NULL,
    size          BIGINT       NOT NULL,
    storage_key   VARCHAR(512) NOT NULL,
    owner_id      CHAR(36)     NOT NULL,
    folder_id     CHAR(36),
    created_at    DATETIME(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at    DATETIME(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    deleted_at    DATETIME(6),

    CONSTRAINT pk_files             PRIMARY KEY (id),
    CONSTRAINT uk_files_storage_key UNIQUE      (storage_key),
    CONSTRAINT fk_files_owner       FOREIGN KEY (owner_id)  REFERENCES users   (id) ON DELETE CASCADE,
    CONSTRAINT fk_files_folder      FOREIGN KEY (folder_id) REFERENCES folders (id) ON DELETE SET NULL,
    CONSTRAINT chk_files_size       CHECK       (size >= 0)
);

CREATE INDEX idx_files_owner_id   ON files (owner_id);
CREATE INDEX idx_files_folder_id  ON files (folder_id);
CREATE INDEX idx_files_deleted_at ON files (deleted_at);
CREATE INDEX idx_files_name       ON files (name);
