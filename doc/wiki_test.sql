create table test
(
    id       bigint      not null
        primary key,
    name     varchar(50) null,
    password varchar(50) null
)
    comment '测试';

INSERT INTO wiki.test (id, name, password) VALUES (1, 'byf', '123');