CREATE TABLE people
(
    id         serial PRIMARY KEY,
    ref        VARCHAR(50) UNIQUE NOT NULL,
    first_name VARCHAR(50)        NOT NULL,
    last_name  VARCHAR(50)        NOT NULL,
    birth_date VARCHAR(10)
);

INSERT INTO people (ref, first_name, last_name, birth_date)
VALUES ('13ed6a67-a4c4-4307-85da-2accbcf25aa7','Maarten', 'Vandeperre', '1989-04-17');
INSERT INTO people (ref, first_name, last_name, birth_date)
VALUES ('23ed6a67-a4c4-4307-85da-2accbcf25aa7','Pieter', 'Vandeperre', '1989-04-20');
INSERT INTO people (ref, first_name, last_name, birth_date)
VALUES ('33ed6a67-a4c4-4307-85da-2accbcf25aa7','Bart', 'Joris', '1989-04-15');