CREATE TABLE passenger (
    passenger_id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(255),
    preferred_payment_method VARCHAR(255),
    registered_at TIMESTAMP WITH TIME ZONE NOT NULL
);


CREATE TABLE trip (
    trip_id INT PRIMARY KEY,
    route_id VARCHAR(255),
    start_time TIMESTAMP,
    end_time TIMESTAMP,
    start_stop VARCHAR(255),
    end_stop VARCHAR(255),
    fare DOUBLE,
    passenger_id VARCHAR(255),
    CONSTRAINT fk_passenger
         FOREIGN KEY (passenger_id) REFERENCES passenger(passenger_id)
         ON DELETE CASCADE
);