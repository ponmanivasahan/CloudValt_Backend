-- V7__create_stars.sql
-- Starred files per user. Unique constraint prevents duplicate stars.

CREATE TABLE stars (
    id         CHAR(36)    NOT NULL,
    user_id    CHAR(36)    NOT NULL,
    file_id    CHAR(36)    NOT NULL,
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),

    CONSTRAINT pk_stars           PRIMARY KEY (id),
    CONSTRAINT uk_stars_user_file UNIQUE      (user_id, file_id),
    CONSTRAINT fk_stars_user      FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_stars_file      FOREIGN KEY (file_id) REFERENCES files (id) ON DELETE CASCADE
);

CREATE INDEX idx_stars_user_id ON stars (user_id);
CREATE INDEX idx_stars_file_id ON stars (file_id);
