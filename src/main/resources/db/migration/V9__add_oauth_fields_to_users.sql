-- V9__add_oauth_fields_to_users.sql
-- Adds provider and provider_id columns to support Google OAuth2 login.

ALTER TABLE users
    ADD COLUMN provider    VARCHAR(20)  NOT NULL DEFAULT 'LOCAL',
    ADD COLUMN provider_id VARCHAR(255);

-- Backfill existing rows
UPDATE users SET provider = 'LOCAL' WHERE provider IS NULL;

CREATE INDEX idx_users_provider_id ON users (provider, provider_id);
