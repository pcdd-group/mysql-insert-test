create database if not exists mysql_insert_test;
use mysql_insert_test;
create table person
(
    id   bigint primary key auto_increment,
    name varchar(10) not null,
    age  int         not null
);