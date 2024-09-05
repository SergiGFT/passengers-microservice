CREATE TABLE passenger (
    passenger_id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(255),
    preferred_payment_method VARCHAR(255),
    registered_at TIMESTAMP WITH TIME ZONE NOT NULL
);