CREATE TABLE person (id SERIAL, name TEXT, age INTEGER CHECK (age > 0), driver BOOLEAN NOT NULL);

CREATE TABLE person_car (person_id SERIAL, car_id SERIAL);

CREATE TABLE car (id SERIAL, brand TEXT NOT NULL, model TEXT NOT NULL, price NUMERIC (16,2) CHECK (price > 0));