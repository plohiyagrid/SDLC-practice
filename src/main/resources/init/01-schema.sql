-- Drop tables in correct dependency order (most dependent first)
DROP TABLE IF EXISTS BadmintonEvent;
DROP TABLE IF EXISTS CricketEvent;
DROP TABLE IF EXISTS GameTeams;
DROP TABLE IF EXISTS EventRegistration;
DROP TABLE IF EXISTS TeamPlayers;
DROP TABLE IF EXISTS IndividualRegistration;
DROP TABLE IF EXISTS TeamRegistration;
DROP TABLE IF EXISTS Game;
DROP TABLE IF EXISTS Event;
DROP TABLE IF EXISTS Registration;
DROP TABLE IF EXISTS EventStatus;
DROP TABLE IF EXISTS VenueBookedDates;
DROP TABLE IF EXISTS Venue;

-- Create enum type for event status
CREATE TYPE event_status AS ENUM (
    'REGISTRATION_OPEN',
    'REGISTRATION_CLOSED',
    'EVENT_ONGOING',
    'PLANNING',
    'EVENT_ENDED'
);

-- Venue table
CREATE TABLE Venue (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    location VARCHAR(255) NOT NULL,
    capacity INT NOT NULL,
    CONSTRAINT venue_capacity_positive CHECK (capacity > 0)
);

-- Store booked dates for venues
CREATE TABLE VenueBookedDates (
    venue_id INT,
    booked_date DATE NOT NULL,
    PRIMARY KEY (venue_id, booked_date),
    FOREIGN KEY (venue_id) REFERENCES Venue(id) ON DELETE CASCADE
);

-- Event base table
CREATE TABLE Event (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    status event_status NOT NULL,
    max_registrations INT,
    event_date DATE,
    venue_id INT,
    FOREIGN KEY (venue_id) REFERENCES Venue(id) ON DELETE SET NULL
);

-- Registration base table
CREATE TABLE Registration (
    id SERIAL PRIMARY KEY,
    contact_name VARCHAR(255) NOT NULL,
    contact_email VARCHAR(255) NOT NULL,
    contact_phone VARCHAR(50)
);

-- Game table (fixture)
CREATE TABLE Game (
    id SERIAL PRIMARY KEY,
    game_date TIMESTAMP,
    venue_id INT,
    winner_id INT NULL,
    event_id INT NOT NULL,
    FOREIGN KEY (venue_id) REFERENCES Venue(id) ON DELETE SET NULL,
    FOREIGN KEY (event_id) REFERENCES Event(id) ON DELETE CASCADE
);

-- TeamRegistration table
CREATE TABLE TeamRegistration (
    registration_id INT PRIMARY KEY,
    team_name VARCHAR(255) NOT NULL,
    team_size INT NOT NULL,
    FOREIGN KEY (registration_id) REFERENCES Registration(id) ON DELETE CASCADE,
    CONSTRAINT tr_team_size_positive CHECK (team_size > 0)
);

-- IndividualRegistration table
CREATE TABLE IndividualRegistration (
    registration_id INT PRIMARY KEY,
    player_name VARCHAR(255) NOT NULL,
    age INT,
    FOREIGN KEY (registration_id) REFERENCES Registration(id) ON DELETE CASCADE
);

-- TeamPlayers table (for list of player names)
CREATE TABLE TeamPlayers (
    registration_id INT,
    player_name VARCHAR(255) NOT NULL,
    player_index INT NOT NULL,
    PRIMARY KEY (registration_id, player_index),
    FOREIGN KEY (registration_id) REFERENCES TeamRegistration(registration_id) ON DELETE CASCADE
);

-- Event-Registration join table
CREATE TABLE EventRegistration (
    event_id INT,
    registration_id INT,
    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (event_id, registration_id),
    FOREIGN KEY (event_id) REFERENCES Event(id) ON DELETE CASCADE,
    FOREIGN KEY (registration_id) REFERENCES Registration(id) ON DELETE CASCADE
);

-- Game team assignments
CREATE TABLE GameTeams (
    game_id INT,
    team_registration_id INT,
    team_position INT NOT NULL, -- 1 for team1, 2 for team2
    PRIMARY KEY (game_id, team_registration_id),
    FOREIGN KEY (game_id) REFERENCES Game(id) ON DELETE CASCADE,
    FOREIGN KEY (team_registration_id) REFERENCES TeamRegistration(registration_id) ON DELETE CASCADE,
    CONSTRAINT valid_team_position CHECK (team_position IN (1, 2))
);

-- CricketEvent table - specialized event type
CREATE TABLE CricketEvent (
    event_id INT PRIMARY KEY,
    overs INT NOT NULL,
    team_size INT NOT NULL,
    FOREIGN KEY (event_id) REFERENCES Event(id) ON DELETE CASCADE,
    CONSTRAINT cricket_overs_positive CHECK (overs > 0),
    CONSTRAINT cricket_team_size_positive CHECK (team_size > 0)
);

-- BadmintonEvent table - specialized event type
CREATE TABLE BadmintonEvent (
    event_id INT PRIMARY KEY,
    is_doubles BOOLEAN NOT NULL,
    FOREIGN KEY (event_id) REFERENCES Event(id) ON DELETE CASCADE
);
