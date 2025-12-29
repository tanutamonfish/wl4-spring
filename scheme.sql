CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS point_checks (
    id BIGSERIAL PRIMARY KEY,
    r DOUBLE PRECISION,
    x DOUBLE PRECISION,
    y DOUBLE PRECISION,
    hit BOOLEAN,
    checked_at TIMESTAMP,
    execution_time_ms BIGINT,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_point_checks_user_id ON point_checks(user_id);
CREATE INDEX IF NOT EXISTS idx_point_checks_checked_at ON point_checks(checked_at DESC);