ALTER TABLE student
ADD CONSTRAINT age_constraint CHECK (age >=16),
ADD CONSTRAINT name_unique UNIQUE (name);

ALTER TABLE student ALTER COLUMN name NOT NULL,

ALTER TABLE faculty
ADD CONSTRAINT name_color_unique UNIQUE (name, color);

ALTER TABLE student ALTER COLUMN age SET DEFAULT 20;
