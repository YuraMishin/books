CREATE TABLE users
(
    id    BIGINT NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

INSERT INTO users(username, password)
VALUES('q', 'q');