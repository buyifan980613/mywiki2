create table category
(
    id     bigint           not null comment 'id'
        primary key,
    parent bigint default 0 not null comment '父id',
    name   varchar(50)      not null comment '名称',
    sort   int              null comment '顺序'
)engine = innodb,default charset =utf8mb4
    comment '分类';

INSERT INTO wiki.category (id, parent, name, sort) VALUES (100, 0, '前端开发', 100);
INSERT INTO wiki.category (id, parent, name, sort) VALUES (101, 100, 'vue', 101);
INSERT INTO wiki.category (id, parent, name, sort) VALUES (102, 100, 'vue2', 102);
INSERT INTO wiki.category (id, parent, name, sort) VALUES (103, 102, '孙子', 103);
INSERT INTO wiki.category (id, parent, name, sort) VALUES (165721387233841152, 100, 'vue3', 2);
INSERT INTO wiki.category (id, parent, name, sort) VALUES (165723450420367360, 100, '1', 2);