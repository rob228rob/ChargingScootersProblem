create table users
(
    id       bigserial primary key,
    username varchar(30) not null unique,
    password varchar(80) not null,
    email    varchar(50) unique
);

create table roles
(
    id   serial primary key,
    name varchar(50) not null,
);

CREATE TABLE users_roles
(
    user_id bigint not null,
    role_id int    not null,
    primary key (user_id, role_id),
    foreign key (user_id) references users (id),
    foreign key (role_id) references roles (id)
);

insert into roles (name)
values ('ROLE_USER'),
       ('ROLE_ADMIN');

insert into users(username, password, email)
values ('user', '$2a$16$YPc4kkPrZWCC7/JduxX.zOUiWhQVN6Fs/qQlWdJafQolv/HXKDp3i', 'user@gmail.com'),
       ('admin', '$2a$16$YPc4kkPrZWCC7/JduxX.zOUiWhQVN6Fs/qQlWdJafQolv/HXKDp3i', 'admin@gmail.com');

insert into users_roles (user_id, role_id)
values (1, 1),
       (2, 2);