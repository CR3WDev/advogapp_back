CREATE TABLE users (
    id TEXT PRIMARY KEY UNIQUE NOT NULL,
    fullName TEXT NOT NULL UNIQUE,
    email TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    role TEXT NOT NULL,
    lawyer_id TEXT,
    FOREIGN KEY (lawyer_id) REFERENCES lawyer(id)
)