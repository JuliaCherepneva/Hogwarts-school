-- liquibase formatted sql

-- changeset julia:1
CREATE INDEX student_name_index ON student (name);
CREATE INDEX faculty_name_index ON faculty (name, color);
