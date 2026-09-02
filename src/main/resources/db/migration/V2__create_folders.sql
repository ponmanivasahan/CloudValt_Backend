-- V2__create_folders.sql
-- Creates folders with self-referencing parent for unlimited nesting.

CREATE TABLE folders (
    id               CHAR(36)     NOT NULL,
    name             VARCHAR(255) NOT NULL,
    owner_id         CHAR(36)     NOT NULL,
    parent_folder_id CHAR(36),
    created_at       DATETIME(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at       DATETIME(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    deleted_at       DATETIME(6),

    CONSTRAINT pk_folders        PRIMARY KEY (id),
    CONSTRAINT fk_folders_owner  FOREIGN KEY (owner_id)         REFERENCES users   (id) ON DELETE CASCADE,
    CONSTRAINT fk_folders_parent FOREIGN KEY (parent_folder_id) REFERENCES folders (id) ON DELETE CASCADE
);

CREATE INDEX idx_folders_owner_id   ON folders (owner_id);
CREATE INDEX idx_folders_parent_id  ON folders (parent_folder_id);
CREATE INDEX idx_folders_deleted_at ON folders (deleted_at);
