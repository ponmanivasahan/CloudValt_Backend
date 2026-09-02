-- V6__create_link_shares.sql
-- Public share links with optional BCrypt-hashed password and expiry.

CREATE TABLE link_shares (
    id            CHAR(36)     NOT NULL,
    file_id       CHAR(36)     NOT NULL,
    token         VARCHAR(128) NOT NULL,
    password_hash VARCHAR(255),
    expires_at    DATETIME(6),
    enabled       TINYINT(1)   NOT NULL DEFAULT 1,
    created_at    DATETIME(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6),

    CONSTRAINT pk_link_shares       PRIMARY KEY (id),
    CONSTRAINT uk_link_shares_token UNIQUE      (token),
    CONSTRAINT fk_link_shares_file  FOREIGN KEY (file_id) REFERENCES files (id) ON DELETE CASCADE
);

CREATE INDEX idx_link_shares_file_id ON link_shares (file_id);
CREATE INDEX idx_link_shares_token   ON link_shares (token);
