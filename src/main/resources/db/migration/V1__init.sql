CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash TEXT NOT NULL,
    total_points INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE matches (
    id BIGSERIAL PRIMARY KEY,
    home_team VARCHAR(100) NOT NULL,
    away_team VARCHAR(100) NOT NULL,
    home_logo TEXT,
    away_logo TEXT,
    league VARCHAR(100),
    kickoff_time TIMESTAMP NOT NULL,
    status VARCHAR(20) DEFAULT 'UPCOMING'
);

CREATE TABLE predictions (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id),
    match_id BIGINT REFERENCES matches(id),
    predicted_home_score INT,
    predicted_away_score INT,
    points_awarded INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT NOW(),
    UNIQUE(user_id, match_id)
);

CREATE TABLE match_results (
    id BIGSERIAL PRIMARY KEY,
    match_id BIGINT UNIQUE REFERENCES matches(id),
    actual_home_score INT,
    actual_away_score INT,
    processed BOOLEAN DEFAULT FALSE
);
