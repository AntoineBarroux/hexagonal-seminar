
CREATE TABLE IF NOT EXISTS seminar (
    id UUID PRIMARY KEY,
    arrival_country_code VARCHAR(5) NOT NULL,
    arrival_iata_code VARCHAR(5) NOT NULL,
    departure_country_code VARCHAR(5) NOT NULL,
    departure_iata_code VARCHAR(5) NOT NULL,
    start_date DATE NOT NULL,
    attendees INT NOT NULL,
    carbon INT NOT NULL
);
