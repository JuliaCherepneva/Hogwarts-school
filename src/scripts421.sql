ALTER TABLE student
ADD CONSTRAINT age_constraint CHECK (age < 10),
ALTER COLUMN name SET NOT NULL,
ADD CONSTRAINT name_unique UNIQUE (name);

ALTER TABLE faculty ADD CONSTRAINT name_unique UNIQUE (name),
ADD CONSTRAINT color_unique UNIQUE (color);

ALTER TABLE student ADD CONSTRAINT age_constraint INTEGER DEFAULT 20;
