drop table  if exists ebook_snapshot;
create table ebook_snapshot
(
    id            bigint auto_increment
        primary key,
    ebook_id      bigint default 0 not null,
    date          date             not null,
    view_count    int    default 0 not null,
    vote_count    int    default 0 not null,
    view_increase int    default 0 not null,
    vote_increase int    default 0 not null
)engine = innodb,default charset =utf8mb4;

INSERT INTO wiki.ebook_snapshot (id, ebook_id, date, view_count, vote_count, view_increase, vote_increase) VALUES (1, 1, '2022-04-05', 1, 10, 1, 1);
INSERT INTO wiki.ebook_snapshot (id, ebook_id, date, view_count, vote_count, view_increase, vote_increase) VALUES (2, 3, '2022-04-05', 0, 0, 0, 0);
INSERT INTO wiki.ebook_snapshot (id, ebook_id, date, view_count, vote_count, view_increase, vote_increase) VALUES (3, 4, '2022-04-05', 1, 1, 1, 1);
INSERT INTO wiki.ebook_snapshot (id, ebook_id, date, view_count, vote_count, view_increase, vote_increase) VALUES (4, 165082352903655424, '2022-04-05', 54, 3, 54, 3);
INSERT INTO wiki.ebook_snapshot (id, ebook_id, date, view_count, vote_count, view_increase, vote_increase) VALUES (5, 1, '2022-04-04', 0, 9, 1, 10);