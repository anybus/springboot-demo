drop table if exists city;

create table city (id int primary key auto_increment, name varchar(50), state varchar(5), country varchar(50));

insert into city (name, state, country) values ('San Francisco', 'CA', 'US');


insert into city (name, state, country) values ('WuHan', 'WH', 'China');