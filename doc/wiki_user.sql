drop table  if exists user;
create table user
(
    id         bigint      not null
        primary key,
    login_name varchar(50) not null,
    name       varchar(50) not null,
    password   char(32)    not null,
    constraint login_name_unique
        unique (login_name)
)engine = innodb,default charset =utf8mb4;

INSERT INTO wiki.user (id, login_name, name, password) VALUES (1, 'byf', 'byf', '7354a1d413535a6c0dc5c209e198d799');
INSERT INTO wiki.user (id, login_name, name, password) VALUES (166034471647318016, 'byf2', 'byf', '4db71c791e6a5f87027698565abc76c8');