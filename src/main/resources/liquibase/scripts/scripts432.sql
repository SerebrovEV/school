-- liquibase formatted sql

--changeset eserebrov:1
CREATE INDEX students_name_index ON student (name);

--changeset eserebrov:2
CREATE INDEX faculty_nc_index ON faculty (name, color);

