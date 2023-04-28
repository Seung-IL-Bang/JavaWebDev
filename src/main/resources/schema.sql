create table todo
(
    tno      int auto_increment primary key,
    title    varchar(100) not null,
    dueDate  date         not null,
    finished  tinyint default 0
);