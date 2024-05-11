DROP TABLE Users, Human, Relation, Message, Contract, Wallet, Item, "order", Order_Item, History;

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



INSERT INTO users (username, email, password, role) VALUES
                                                        ('john_doe', 'john.doe@example.com', 'mypassword', 'ADMIN'),
                                                        ('sarah_smith', 'sarah.smith@example.com', 'p@ssw0rd', 'WOMAN'),
                                                        ('alex_brown', 'alex.brown@example.com', 'securepass', 'MAN'),
                                                        ('lucy_wilson', 'lucy.wilson@example.com', 'letmein123', 'WOMAN'),
                                                        ('mike_jones', 'mike.jones@example.com', 'mikespass', 'MAN'),
                                                        ('emily_clark', 'emily.clark@example.com', 'password123', 'TRADER'),
                                                        ('david_miller', 'david.miller@example.com', 'davidpass', 'MAN'),
                                                        ('olivia_taylor', 'olivia.taylor@example.com', 'olivia123', 'TRADER'),
                                                        ('ryan_carter', 'ryan.carter@example.com', 'ryancpass', 'MAN'),
                                                        ('jessica_hall', 'jessica.hall@example.com', 'jessic@pass', 'WOMAN');

INSERT INTO Human (user_id, name, surname, age, sex, status, address, phone, description)
VALUES
    (1, 'Иван', 'Иванов', 25, 0, 'Работает', 'ул. Пушкина, д.10', '1234567890', 'Описание для Ивана'),
    (2, 'Мария', 'Петрова', 30, 1, 'Студент', 'ул. Лермонтова, д.5', '0987654321', 'Описание для Марии'),
    (3, 'Алексей', 'Сидоров', 40, 0, 'Безработный', 'ул. Гоголя, д.15', '7778889990', 'Описание для Алексея'),
    (4, 'Елена', 'Козлова', 35, 1, 'Работает', 'ул. Тургенева, д.20', '5554443330', 'Описание для Елены'),
    (5, 'Дмитрий', 'Никитин', 28, 0, 'Студент', 'ул. Чехова, д.25', '2223334440', 'Описание для Дмитрия'),
    (6, 'Анна', 'Смирнова', 45, 1, 'Работает', 'ул. Достоевского, д.30', '6667778880', 'Описание для Анны'),
    (7, 'Павел', 'Кузнецов', 32, 0, 'Работает', 'ул. Толстого, д.35', '9990001110', 'Описание для Павла'),
    (8, 'Ольга', 'Иванова', 27, 1, 'Студент', 'ул. Чехова, д.40', '3332221110', 'Описание для Ольги'),
    (9, 'Игорь', 'Попов', 33, 0, 'Работает', 'ул. Тургенева, д.45', '1112223330', 'Описание для Игоря'),
    (10, 'Наталья', 'Соколова', 29, 1, 'Работает', 'ул. Гоголя, д.50', '4445556660', 'Описание для Натальи');

INSERT INTO Relation (first_person, second_person, relation)
VALUES
    (1, 2, 'Друзья'),
    (3, 4, 'Коллеги'),
    (5, 6, 'Брат и сестра'),
    (7, 8, 'Соседи'),
    (9, 10, 'Друг'),
    (2, 3, 'Знакомые'),
    (4, 5, 'Родственники'),
    (6, 7, 'Коллеги'),
    (8, 9, 'Друзья'),
    (10, 1, 'Соседи');

INSERT INTO Message (user_from, user_to, date, message_text, type)
VALUES
    (1, 2, '2022-10-01 08:00:00', 'Привет, как дела?', 0),
    (2, 1, '2022-10-01 08:05:00', 'Привет, все хорошо, спасибо!', 0),
    (3, 4, '2022-10-02 10:00:00', 'Привет, давно не виделись, как твои дела?', 0),
    (4, 3, '2022-10-02 10:05:00', 'Привет, да, действительно, давно не общались. У меня все отлично, спасибо!', 0),
    (5, 6, '2022-10-03 12:00:00', 'Привет, как проходит твой день?', 0),
    (6, 5, '2022-10-03 12:05:00', 'Привет, день проходит хорошо, спасибо!', 0),
    (7, 8, '2022-10-04 14:00:00', 'Привет, не могли бы вы помочь мне с этим проектом?', 0),
    (8, 7, '2022-10-04 14:05:00', 'Конечно, я помогу. Давай встретимся и обсудим все детали.', 0),
    (9, 10, '2022-10-05 16:00:00', 'Привет, как прошел твой выходной?', 0),
    (10, 9, '2022-10-05 16:05:00', 'Привет, выходной был отличным, провел его с семьей.', 0);

INSERT INTO Contract (master, contractor, date, type)
VALUES
    (1, 2, '2022-10-01 08:00:00', 0),
    (2, 1, '2022-10-02 08:00:00', 0),
    (3, 4, '2022-10-03 08:00:00', 0),
    (4, 3, '2022-10-04 08:00:00', 0),
    (5, 6, '2022-10-05 08:00:00', 0),
    (6, 5, '2022-10-06 08:00:00', 0),
    (7, 8, '2022-10-07 08:00:00', 0),
    (8, 7, '2022-10-08 08:00:00', 0),
    (9, 10, '2022-10-09 08:00:00', 0),
    (10, 9, '2022-10-10 08:00:00', 0);

INSERT INTO Wallet (user_id, amount)
VALUES
    (1, 1000),
    (2, 500),
    (3, 750),
    (4, 300),
    (5, 1200),
    (6, 800),
    (7, 600),
    (8, 900),
    (9, 1100),
    (10, 400);

INSERT INTO Item (wallet_id, name, price, description)
VALUES
    (1, 'Phone', 500, 'Smartphone model X'),
    (2, 'Laptop', 1000, 'Laptop model Y'),
    (3, 'Watch', 200, 'Smartwatch brand Z'),
    (4, 'Headphones', 100, 'Wireless headphones brand K'),
    (5, 'Camera', 800, 'Professional camera model L'),
    (6, 'Tablet', 300, 'Tablet brand M'),
    (7, 'Speaker', 150, 'Portable speaker brand N'),
    (8, 'Gaming Console', 400, 'Gaming console model O'),
    (9, 'Fitness Tracker', 80, 'Fitness tracker brand P'),
    (10, 'Smart Home Device', 250, 'Smart home device model Q');

INSERT INTO "order" (payment_type, sum, costumer, seller, target, sel_time, status)
VALUES
    (1, 500, 1, 2, 3, '2022-10-01 08:00:00', 1),
    (2, 1000, 2, 1, 4, '2022-10-02 10:00:00', 1),
    (1, 750, 3, 4, 5, '2022-10-03 12:00:00', 1),
    (2, 300, 4, 3, 6, '2022-10-04 14:00:00', 1),
    (1, 1200, 5, 6, 7, '2022-10-05 16:00:00', 1),
    (2, 800, 6, 5, 8, '2022-10-06 18:00:00', 1),
    (1, 600, 7, 8, 9, '2022-10-07 20:00:00', 1),
    (2, 900, 8, 7, 10, '2022-10-08 22:00:00', 1),
    (1, 1100, 9, 10, 1, '2022-10-09 08:00:00', 1),
    (2, 400, 10, 9, 2, '2022-10-10 08:00:00', 1);

INSERT INTO Order_Item ("order_id", "item_id")
VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4),
    (5, 5),
    (6, 6),
    (7, 7),
    (8, 8),
    (9, 9),
    (10, 10);