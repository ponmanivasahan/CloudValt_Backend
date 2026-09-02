-- V5__create_shares.sql
-- User-to-user file sharing with VIEWER / EDITOR permission.

CREATE TABLE shares (
    id                  CHAR(36)    NOT NULL,
    file_id             CHAR(36)    NOT NULL,
    owner_id            CHAR(36)    NOT NULL,
    shared_with_user_id CHAR(36)    NOT NULL,
    permission          VARCHAR(20) NOT NULL,
    created_at          DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),

    CONSTRAINT pk_shares                  PRIMARY KEY (id),
    CONSTRAINT uk_shares_file_user        UNIQUE      (file_id, shared_with_user_id),
    CONSTRAINT fk_shares_file             FOREIGN KEY (file_id)             REFERENCES files (id) ON DELETE CASCADE,
    CONSTRAINT fk_shares_owner            FOREIGN KEY (owner_id)            REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_shares_shared_with_user FOREIGN KEY (shared_with_user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT chk_shares_permission      CHECK       (permission IN ('VIEWER', 'EDITOR'))
);

CREATE INDEX idx_shares_file_id             ON shares (file_id);
CREATE INDEX idx_shares_owner_id            ON shares (owner_id);
CREATE INDEX idx_shares_shared_with_user_id ON shares (shared_with_user_id);
