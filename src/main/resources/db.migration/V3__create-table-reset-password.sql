CREATE TABLE reset_password (
            id TEXT PRIMARY KEY UNIQUE NOT NULL,
            token VARCHAR(255) NOT NULL,
            user_id INT NOT NULL,
            expiration_time TIMESTAMP NOT NULL
)