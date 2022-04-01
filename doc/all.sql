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