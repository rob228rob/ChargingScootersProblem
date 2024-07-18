CREATE TABLE users
(
    id       BIGSERIAL PRIMARY KEY,
    username VARCHAR(30) NOT NULL UNIQUE,
    password VARCHAR(80) NOT NULL,
    email    VARCHAR(50) UNIQUE
);

CREATE TABLE roles
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE users_roles
(
    user_id BIGINT NOT NULL,
    role_id INT    NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE
);

INSERT INTO roles (name)
VALUES ('ROLE_USER'),
       ('ROLE_ADMIN');

INSERT INTO users (username, password, email)
VALUES ('user', '$2a$16$YPc4kkPrZWCC7/JduxX.zOUiWhQVN6Fs/qQlWdJafQolv/HXKDp3i', 'user@gmail.com'),
       ('admin', '$2a$16$YPc4kkPrZWCC7/JduxX.zOUiWhQVN6Fs/qQlWdJafQolv/HXKDp3i', 'admin@gmail.com');

INSERT INTO users_roles (user_id, role_id)
VALUES ((SELECT id FROM users WHERE username = 'user'),
        (SELECT id FROM roles WHERE name = 'ROLE_USER')),
       ((SELECT id FROM users WHERE username = 'admin'),
        (SELECT id FROM roles WHERE name = 'ROLE_ADMIN'));
