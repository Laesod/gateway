---
-- #%L
-- Gateway
-- %%
-- Copyright (C) 2015 Powered by Sergey
-- %%
-- Licensed under the Apache License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
-- 
--      http://www.apache.org/licenses/LICENSE-2.0
-- 
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
-- #L%
---
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
		version bigint,
		avatar_s3_object_key varchar(100));

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
insert into users values ('admin@gmail.com', '2015-01-01 00:00:01', 'initialPopulation', '2015-01-01 00:00:01', 'initialPopulation', 0, '',  '', true, 'Tom', 'Sawyer', '020aa40d02ed72bc980c05caa7506f7c791ecbd91d1210cc4ab4e830881989f06a9fdaff9a5b5bef', '');
--insert into users
--        values ('admin@gmail.com', "Tom", "Sawyer", '020aa40d02ed72bc980c05caa7506f7c791ecbd91d1210cc4ab4e830881989f06a9fdaff9a5b5bef',
--        	true, "initialPopulation", '2015-01-01 00:00:01', "initialPopulation", '2015-01-01 00:00:01', 0, '', '', '');

--insert into users
--        values ('user@gmail.com', "Peter", "Pan", '020aa40d02ed72bc980c05caa7506f7c791ecbd91d1210cc4ab4e830881989f06a9fdaff9a5b5bef',
--           	true, "initialPopulation", '2015-01-01 00:00:01', "initialPopulation", '2015-01-01 00:00:01', 0, '', '', '');

insert into authorities
        values ('1', '2015-01-01 00:00:01', 'initialPopulation', '2015-01-01 00:00:01', 'initialPopulation', 0, 'SYSTEM_ADMIN', 'admin@gmail.com');

--insert into authorities
--        values ('2', 'user@gmail.com', 'SYSTEM_USER', "initialPopulation", '2015-01-01 00:00:01', "initialPopulation", '2015-01-01 00:00:01', 0);

--initial entry types creation...
insert into translation_maps
        values ('1');
insert into translations
        values ('1', '2015-01-01 00:00:01', "initialPopulation", '2015-01-01 00:00:01', "initialPopulation", 0, "Deficiency", "name", "English", "EntryType", "1");
insert into translations
        values ('2', '2015-01-01 00:00:01', "initialPopulation", '2015-01-01 00:00:01', "initialPopulation", 0, "Deficiency (fr)", "name", "French", "EntryType", "1");
insert into entry_types
        values ('1', '1');

--initial deficiency statuses creation...
insert into translation_maps
        values ('2');
insert into translations
        values ('3', '2015-01-01 00:00:01', "initialPopulation", '2015-01-01 00:00:01', "initialPopulation", 0, "Open", "name", "English", "EntryStatus", "2");
insert into translations
        values ('4', '2015-01-01 00:00:01', "initialPopulation", '2015-01-01 00:00:01', "initialPopulation", 0, "Open (fr)", "name", "French", "EntryStatus", "2");
insert into entry_statuses
        values ('1', '#ff0000', '', 'Deficiency', 1, '2');

insert into translation_maps
        values ('3');
insert into translations
        values ('5', '2015-01-01 00:00:01', "initialPopulation", '2015-01-01 00:00:01', "initialPopulation", 0, "In Progress", "name", "English", "EntryStatus", "3");
insert into translations
        values ('6', '2015-01-01 00:00:01', "initialPopulation", '2015-01-01 00:00:01', "initialPopulation", 0, "In Progress (fr)", "name", "French", "EntryStatus", "3");
insert into entry_statuses
        values ('2', '#ffff33', '', 'Deficiency', 2, '3');

insert into translation_maps
        values ('4');
insert into translations
        values ('7', '2015-01-01 00:00:01', "initialPopulation", '2015-01-01 00:00:01', "initialPopulation", 0, "Done", "name", "English", "EntryStatus", "4");
insert into translations
        values ('8', '2015-01-01 00:00:01', "initialPopulation", '2015-01-01 00:00:01', "initialPopulation", 0, "Done (fr)", "name", "French", "EntryStatus", "4");
insert into entry_statuses
        values ('3', '#33ff33', '', 'Deficiency', 3, '4');