CREATE TABLE lawyer (
    id TEXT PRIMARY KEY UNIQUE NOT NULL,
    CPF TEXT UNIQUE NOT NULL,
    OAB TEXT NOT NULL,
    description TEXT,
    rating DECIMAL DEFAULT 0,
    reviewsNumber INTEGER DEFAULT 0,
    specialization INTEGER NOT NULL
    user_id TEXT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
)