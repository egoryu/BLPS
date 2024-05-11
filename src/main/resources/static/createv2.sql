CREATE TABLE Users (
   id SERIAL PRIMARY KEY,
   username varchar(32),
   email varchar(32) NOT NULL ,
   password varchar(64) NOT NULL,
   role varchar(32) NOT NULL
);

CREATE TABLE Human(
    id SERIAL PRIMARY KEY,
    user_id int UNIQUE REFERENCES Users(id),
    name varchar(32) NOT NULL,
    surname varchar(32) NOT NULL,
    age int NOT NULL,
    sex int NOT NULL DEFAULT 0,
    status varchar(32),
    address varchar(64),
    phone varchar(12),
    description text
);

CREATE TABLE Relation(
    id SERIAL PRIMARY KEY,
    first_person int NOT NULL REFERENCES Human(id),
    second_person int NOT NULL REFERENCES Human(id),
    relation varchar(64) NOT NULL
);

CREATE TABLE Message(
    id SERIAL PRIMARY KEY,
    user_from int NOT NULL REFERENCES Users(id),
    user_to int NOT NULL REFERENCES Users(id),
    date timestamp NOT NULL,
    message_text text NOT NULL,
    type int NOT NULL DEFAULT 0
);

CREATE TABLE Contract(
    id SERIAL PRIMARY KEY,
    master int NOT NULL REFERENCES Users(id),
    contractor int NOT NULL REFERENCES Users(id),
    date timestamp NOT NULL,
    type int NOT NULL DEFAULT 0
);

CREATE TABLE Wallet(
    id SERIAL PRIMARY KEY,
    user_id int NOT NULL UNIQUE REFERENCES Users(id),
    amount int NOT NULL DEFAULT 0
);

CREATE TABLE Item(
    id SERIAL PRIMARY KEY,
    wallet_id int NOT NULL REFERENCES Wallet(id),
    name varchar(32) NOT NULL,
    price int NOT NULL DEFAULT 0,
    description text
);

CREATE TABLE "order"(
    id SERIAL PRIMARY KEY,
    payment_type int NOT NULL DEFAULT 0,
    sum int NOT NULL DEFAULT 0,
    costumer int NOT NULL REFERENCES Users(id),
    seller int NOT NULL REFERENCES Users(id),
    target int NOT NULL REFERENCES Users(id),
    sel_time timestamp,
    status int NOT NULL DEFAULT 0
);

CREATE TABLE Order_Item(
    id SERIAL PRIMARY KEY,
    "order_id" int NOT NULL REFERENCES "order"(id),
    item_id int NOT NULL REFERENCES Item(id)
);

CREATE TABLE History(
    id SERIAL PRIMARY KEY,
    message_id int NOT NULL,
    user_from varchar(32) NOT NULL,
    user_to varchar(32) NOT NULL,
    date timestamp NOT NULL,
    message_text text NOT NULL,
    type int NOT NULL DEFAULT 0,
    log_date timestamp NOT NULL
);