create schema if not exists `computer-database-dbtest`;

create table company (
    ->     id                        bigint not null auto_increment,
    ->     name                      varchar(255),
    ->     constraint pk_company primary key (id))
    ->   ;
create table computer (
    ->     id                        bigint not null auto_increment,
    ->     name                      varchar(255),
    ->     introduced                date NULL,
    ->     discontinued              date NULL,
    ->     company_id                bigint default NULL,
    ->     constraint pk_computer primary key (id))
    ->   ;

alter table computer add constraint fk_computer_company_1 foreign key (company_id) references company (id) on delete restrict on update restrict;
create index ix_computer_company_1 on computer (company_id);


CREATE USER 'admini'@'localhost' IDENTIFIED BY '1234';
GRANT ALL PRIVILEGES ON `computer-database-dbtest`.* TO 'admini'@'localhost' WITH GRANT OPTION;
FLUSH PRIVILEGES;

insert into company (id,name) values (  1,'Apple Inc.');     --1
insert into company (id,name) values ( 2,'Nokia');           --16
insert into company (id,name) values ( 3,'ASUS');            --37
insert into company (id,name) values ( 4,'Hewlett-Packard'); --27

insert into computer (id,name,introduced,discontinued,company_id) values (  1,'MacBook Pro 15.4 inch',null,null,1);
insert into computer (id,name,introduced,discontinued,company_id) values (  2,'MacBook Pro','2006-01-10',null,1);
insert into computer (id,name,introduced,discontinued,company_id) values ( 3,'Apple III','1980-05-01','1984-04-01',1);
insert into computer (id,name,introduced,discontinued,company_id) values ( 4,'Macintosh','1984-01-24',null,1);
insert into computer (id,name,introduced,discontinued,company_id) values ( 5,'Macintosh','1984-01-24',null,1);
insert into computer (id,name,introduced,discontinued,company_id) values (6,'HP TouchPad','2011-02-09',null,4);
insert into computer (id,name,introduced,discontinued,company_id) values ( 7,'Macintosh','1984-01-24',null,1);
insert into computer (id,name,introduced,discontinued,company_id) values (8,'HP Veer','2011-02-09',null,4);

insert into computer (id,name,introduced,discontinued,company_id) values (9,'Dell Vostro',null,null,null);

