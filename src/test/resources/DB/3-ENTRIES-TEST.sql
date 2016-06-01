use `computer-database-db-test`;

SET foreign_key_checks = 0;
truncate table computer;
truncate table company;
SET foreign_key_checks = 1;

insert into company (id,name) values (  1,'Apple Inc');
insert into company (id,name) values (  2,'Microsoft');

insert into computer (id,name,introduced,discontinued,company_id) values (  1,'MacBook Pro 15.4 inch',null,null,1);
insert into computer (id,name,introduced,discontinued,company_id) values (  2,'CM-2a',null,null,2);
insert into computer (id,name,introduced,discontinued,company_id) values (  3,'CM-200',null,null,2);
insert into computer (id,name,introduced,discontinued,company_id) values (  4,'CM-5e',null,null,2);
insert into computer (id,name,introduced,discontinued,company_id) values (  5,'CM-5','1991-01-01',null,2);
insert into computer (id,name,introduced,discontinued,company_id) values (  6,'MacBook Pro','2006-01-10',null,1);
