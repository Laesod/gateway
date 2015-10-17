create database gateway;

create table users(
		username varchar(100) not null primary key,
		first_name varchar(100) not null,
		last_name varchar(100) not null,
		password varchar(100) not null,
		enabled boolean not null,
		created_by_user varchar(100) not null,
		created_at timestamp not null,
		modified_by_user varchar(100) not null,
		modified_at timestamp not null,
		version bigint);

create table authorities (
		authority_guid varchar(255) not null primary key,
		username varchar(100) not null,
		authority varchar(100) not null,
		created_by_user varchar(100) not null,
		created_at timestamp not null,
		modified_by_user varchar(100) not null,
		modified_at timestamp not null,
		version bigint,
		constraint fk_authorities_users foreign key(username) references users(username));

create unique index ix_auth_username on authorities (username,authority);

--create table projects(
--		project_id long not null primary key,
--		description varchar(250) not null;

--create table groups (
--		id bigint AUTO_INCREMENT primary key,
--		group_name varchar(100) not null);
--
--create table group_authorities (
--		group_id bigint not null,
--		authority varchar(100) not null,
--		constraint fk_group_authorities_group foreign key(group_id) references groups(id));
--
--create table group_members (
--		id bigint AUTO_INCREMENT primary key,
--		username varchar(100) not null,
--		group_id bigint not null,
--		constraint fk_group_members_group foreign key(group_id) references groups(id));

insert into users
        values ('admin@gmail.com', "Tom", "Sawyer", '020aa40d02ed72bc980c05caa7506f7c791ecbd91d1210cc4ab4e830881989f06a9fdaff9a5b5bef',
        	true, "initialPopulation", '2015-01-01 00:00:01', "initialPopulation", '2015-01-01 00:00:01', 0);

insert into users
        values ('user@gmail.com', "Peter", "Pan", '020aa40d02ed72bc980c05caa7506f7c791ecbd91d1210cc4ab4e830881989f06a9fdaff9a5b5bef',
         	true, "initialPopulation", '2015-01-01 00:00:01', "initialPopulation", '2015-01-01 00:00:01', 0);

insert into authorities
        values ('1', 'admin@gmail.com', 'SYSTEM_ADMIN', "initialPopulation", '2015-01-01 00:00:01', "initialPopulation", '2015-01-01 00:00:01', 0);

insert into authorities
        values ('2', 'user@gmail.com', 'SYSTEM_USER', "initialPopulation", '2015-01-01 00:00:01', "initialPopulation", '2015-01-01 00:00:01', 0);