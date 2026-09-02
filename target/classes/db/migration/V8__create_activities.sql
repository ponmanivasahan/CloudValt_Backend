-- V8__create_activities.sql
-- Audit log for all user actions.
-- Note: MySQL does not support DESC in index definitions the same way;
-- the index on created_at is ascending by default which is fine for queries.

CREATE TABLE activities (
    id            CHAR(36)    NOT NULL,
    user_id       CHAR(36)    NOT NULL,
    action        VARCHAR(64) NOT NULL,
    resource_type VARCHAR(32) NOT NULL,
    resource_id   CHAR(36)    NOT NULL,
    metadata      TEXT,
    created_at    DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),

    CONSTRAINT pk_activities      PRIMARY KEY (id),
    CONSTRAINT fk_activities_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE INDEX idx_activities_user_id    ON activities (user_id);
CREATE INDEX idx_activities_created_at ON activities (created_at);
CREATE INDEX idx_activities_resource   ON activities (resource_type, resource_id);
