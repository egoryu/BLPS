CREATE TABLE Users (
    id SERIAL PRIMARY KEY,
    username varchar(32),
    email varchar(32) NOT NULL ,
    password varchar(64) NOT NULL
);

CREATE TABLE Rubric (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES Users(id),
    name varchar(32) NOT NULL
);

CREATE TABLE Record (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES Users(id),
    header varchar(32) NOT NULL,
    body text,
    create_time timestamp NOT NULL,
    rating int DEFAULT 0,
    type int DEFAULT 0
);

CREATE TABLE Rubric_Record (
    id SERIAL PRIMARY KEY,
    rubric_id INT NOT NULL REFERENCES Rubric(id),
    record_id INT NOT NULL REFERENCES Record(id)
);

CREATE TABLE Poll (
    id SERIAL PRIMARY KEY,
    record_id INT NOT NULL UNIQUE REFERENCES Record(id),
    question varchar(256) NOT NULL,
    type int DEFAULT 0
);

CREATE TABLE Answers (
    id SERIAL PRIMARY KEY,
    poll_id INT NOT NULL REFERENCES Poll(id),
    answer_text varchar(128) NOT NULL,
    index INT NOT NULL
);

