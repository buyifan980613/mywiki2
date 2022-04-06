drop table  if exists ebook;
create table ebook
(
    id           bigint       not null comment 'id'
        primary key,
    name         varchar(50)  null comment '名称',
    category1_id bigint       null comment '分类1',
    category2_id bigint       null comment '分类2',
    description  varchar(200) null comment '描述',
    cover        varchar(200) null comment '封面',
    doc_count    int          null comment '文档数',
    view_count   int          null comment '阅读数',
    vote_count   int          null comment '点赞数'
)engine = innodb,default charset =utf8mb4
    comment '电子书';

INSERT INTO wiki.ebook (id, name, category1_id, category2_id, description, cover, doc_count, view_count, vote_count) VALUES (1, 'Springboot', 100, 102, 'java入门', '/image/cover1.png', 3, 1, 10);
INSERT INTO wiki.ebook (id, name, category1_id, category2_id, description, cover, doc_count, view_count, vote_count) VALUES (3, 'SpringMVC', 100, 102, 'java入门', null, 1, 0, 0);
INSERT INTO wiki.ebook (id, name, category1_id, category2_id, description, cover, doc_count, view_count, vote_count) VALUES (4, 'Mybatis', 100, 101, 'java入门', null, 1, 1, 1);
INSERT INTO wiki.ebook (id, name, category1_id, category2_id, description, cover, doc_count, view_count, vote_count) VALUES (165082352903655424, '123', 100, 102, null, '123', 4, 54, 3);