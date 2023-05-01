create table todo
(
    tno      int auto_increment primary key,
    title    varchar(100) not null,
    dueDate  date         not null,
    finished  tinyint default 0
);

create table member (
    mid varchar(50) primary key,
    mpw varchar(50) not null,
    name varchar(100) not null
);

alter table member
    add column uuid varchar(50);
