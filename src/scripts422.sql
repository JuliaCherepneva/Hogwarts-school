CREATE TABLE person (id INTEGER PRIMARY KEY, name TEXT, age INTEGER CHECK (age > 0), driver BOOLEAN NOT NULL);

CREATE TABLE person_car (person_id INTEGER REFERENCES person (id), car_id INTEGER REFERENCES car (id));

CREATE TABLE car (id INTEGER PRIMARY KEY, brand TEXT NOT NULL, model TEXT NOT NULL, price NUMERIC (16,2) CHECK (price > 0));