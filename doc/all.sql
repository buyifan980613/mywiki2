drop table if exists test;

create  table test(
    id bigint not null ,
    name varchar(50) ,
    password varchar(50) ,
    primary key (id)
)engine = innodb default charset = utf8mb4 comment='测试';



drop table if exists demo;

create  table demo(
                      id bigint not null ,
                      name varchar(50) ,
                      primary key (id)
)engine = innodb default charset = utf8mb4 comment='测试';


create  table ebook (
    id bigint not null comment 'id',
    name varchar(50) comment  '名称',
    category1_id bigint comment '分类1',
    category2_id bigint comment '分类2',
    description varchar(200) comment  '描述',
    cover varchar(200) comment '封面',
    doc_count int comment '文档数',
    view_count int comment '阅读数',
    vote_count int comment '点赞数',
    primary key (id)
)engine =innodb default  charset = utf8mb4 comment ='电子书';
insert into  ebook (id,name,description) values  (1,'Springboot','java入门');
insert into  ebook (id,name,description) values  (2,'Spring','java入门');
insert into  ebook (id,name,description) values  (3,'SpringMVC','java入门');
insert into  ebook (id,name,description) values  (4,'Mybatis','java入门');

create  table category (
                        id bigint not null comment 'id',
                        parent bigint not null default 0 comment '父id',
                        name varchar(50) not null comment '名称',name
                        sort int comment '顺序',
                        primary key (id)
)engine =innodb default  charset = utf8mb4 comment ='分类';


insert into  category(category.id, category.parent, category.name, category.sort) values (100,000,'前端开发',100);
insert into  category(category.id, category.parent, category.name, category.sort) values (101,100,'vue',101);
insert into  category(category.id, category.parent, category.name, category.sort) values (102,100,'vue2',102);


create table doc
(
    id         bigint      not null comment 'id',
    ebook_id   bigint      not null default 0,
    parent     bigint      not null default 0 comment '父',
    name       varchar(50) not null,
    sort       int,
    view_count int,
    vote_count int,
    primary key (id)
)engine=innodb default charset =utf8mb4;

insert into doc values (1,1,0,1,1,1,1);
insert into doc values (2,2,1,1,1,1,1);
insert into doc values (3,3,2,1,1,1,1);
insert into doc values (4,4,1,1,1,1,1);
insert into doc values (5,5,0,1,1,1,1);
insert into doc values (6,6,1,1,1,1,1);


create table  content (
    id bigint not null,
    content mediumtext not null,
    primary key (id)
)engine =innodb default charset  = utf8mb4;

create table  user (
                          id bigint not null,
                          login_name varchar(50) not null,
                          name varchar(50) not null,
                          password char(32) not null,
                          primary key (id),
                          unique key login_name_unique (login_name)
)engine =innodb default charset  = utf8mb4;

create table  ebook_snapshot (
                       id bigint auto_increment not null,
                       ebook_id bigint  not null default 0,
                       date date  not null,
                       view_count int  not null default 0,
                       vote_count int  not null default 0,
                       view_increase int  not null default 0,
                       vote_increase int  not null default 0,

                       primary key (id)
)engine =innodb default charset  = utf8mb4;