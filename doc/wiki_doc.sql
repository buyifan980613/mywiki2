create table doc
(
    id         bigint           not null comment 'id'
        primary key,
    ebook_id   bigint default 0 not null,
    parent     bigint default 0 not null comment '父',
    name       varchar(50)      not null,
    sort       int              null,
    view_count int              null,
    vote_count int              null
)engine = innodb,default charset =utf8mb4;

INSERT INTO wiki.doc (id, ebook_id, parent, name, sort, view_count, vote_count) VALUES (1, 1, 0, '1', 1, 1, 7);
INSERT INTO wiki.doc (id, ebook_id, parent, name, sort, view_count, vote_count) VALUES (4, 4, 1, '1', 1, 1, 1);
INSERT INTO wiki.doc (id, ebook_id, parent, name, sort, view_count, vote_count) VALUES (5, 5, 0, '1', 1, 1, 1);
INSERT INTO wiki.doc (id, ebook_id, parent, name, sort, view_count, vote_count) VALUES (6, 6, 1, '1', 1, 1, 1);
INSERT INTO wiki.doc (id, ebook_id, parent, name, sort, view_count, vote_count) VALUES (166092878316179456, 1, 0, '11', 11, 0, 0);
INSERT INTO wiki.doc (id, ebook_id, parent, name, sort, view_count, vote_count) VALUES (166092947861934080, 1, 1, '1', 3, 0, 3);
INSERT INTO wiki.doc (id, ebook_id, parent, name, sort, view_count, vote_count) VALUES (166454343539757056, 3, 0, '笔记1', 1, 0, 0);