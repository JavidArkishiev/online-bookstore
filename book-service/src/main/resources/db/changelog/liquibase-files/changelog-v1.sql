CREATE TABLE Authors
(
    id               SERIAL PRIMARY KEY,
    first_name       VARCHAR(255),
    last_name        VARCHAR(255),
    date_of_birthday DATE,
    biography        TEXT


);
