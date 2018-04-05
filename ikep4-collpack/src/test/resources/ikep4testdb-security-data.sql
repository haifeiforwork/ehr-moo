CREATE TABLE USERS(
USER_ID VARCHAR NOT NULL PRIMARY KEY,
USER_NAME VARCHAR NOT NULL,
PASSWORD VARCHAR NOT NULL,
ENABLED INTEGER,
BIRTH_DAY VARCHAR,
AGE INTEGER,
CELL_PHONE VARCHAR,
EMAIL VARCHAR,
REG_DATE DATE);

CREATE TABLE ROLES(
ROLE_ID VARCHAR NOT NULL PRIMARY KEY,
ROLE_NAME VARCHAR,
DESCRIPTION VARCHAR,
CREATE_DATE DATE,
MODIFY_DATE DATE);

CREATE TABLE ROLES_HIERARCHY(
PARENT_ROLE VARCHAR NOT NULL PRIMARY KEY,
CHILD_ROLE VARCHAR NOT NULL);

CREATE TABLE SECURED_RESOURCES(
RESOURCE_ID VARCHAR NOT NULL PRIMARY KEY,
SYSTEM_NAME VARCHAR,
RESOURCE_NAME VARCHAR,
RESOURCE_PATTERN VARCHAR NOT NULL,
DESCRIPTION VARCHAR,
RESOURCE_TYPE VARCHAR,
SORT_ORDER INTEGER,
CREATE_DATE DATE,
MODIFY_DATE DATE);

CREATE TABLE SECURED_RESOURCES_ROLES(
RESOURCE_ID VARCHAR NOT NULL PRIMARY KEY,
ROLE_ID VARCHAR NOT NULL);

CREATE TABLE AUTHORITIES(
USER_ID VARCHAR NOT NULL PRIMARY KEY,
ROLE_ID VARCHAR NOT NULL
);

INSERT INTO USERS VALUES(
'giljae',
'Giljae Joo',
'1q2w3e4r',
1,
'781215',
34,
'010-1111-1111',
'giljae@gmail.com',
'2011-01-31');

INSERT INTO USERS VALUES(
'yangsae',
'Saerohoon Yang',
'1q2w3e4r',
1,
'770102',
35,
'010-1111-1111',
'yangsae@gmail.com',
'2011-01-31');


INSERT INTO USERS VALUES(
'admin',
'admin',
'1q2w3e4r',
1,
'770102',
35,
'010-1111-1111',
'admin@gmail.com',
'2011-01-31');


insert into roles(role_id, role_name, description, create_date) values (
'IS_AUTHENTICATED_ANONYMOUSLY', 
'AANONYMOUS USER', 
'', 
'2011-01-31');

insert into roles(role_id, role_name, description, create_date) values (
'IS_AUTHENTICATED_REMEMBERED', 
'REMEMBERED USER', 
'', 
'2011-01-31');

insert into roles(role_id, role_name, description, create_date) values (
'IS_AUTHENTICATED_FULLY', 
'AUTHENTICATED USER', 
'', 
'2011-01-31');

insert into roles(role_id, role_name, description, create_date) values (
'ROLE_RESTRICTED', 
'RESTRICTED USER', 
'', 
'2011-01-31');

insert into roles(role_id, role_name, description, create_date) values (
'ROLE_USER', 
'USER', 
'', 
'2011-01-31');

insert into roles(role_id, role_name, description, create_date) values (
'ROLE_ADMIN', 
'ADMIN', 
'', 
'2011-01-31');

insert into roles(role_id, role_name, description, create_date) values (
'ROLE_A', 
'A', 
'', 
'2011-01-31');

insert into roles(role_id, role_name, description, create_date) values (
'ROLE_B', 
'B', 
'', 
'2011-01-31');

insert into roles_hierarchy(child_role, parent_role) values (
'ROLE_ADMIN', 
'ROLE_USER');

insert into roles_hierarchy(child_role, parent_role) values (
'ROLE_USER', 
'ROLE_RESTRICTED');

insert into roles_hierarchy(child_role, parent_role) values (
'ROLE_RESTRICTED', 
'IS_AUTHENTICATED_FULLY');

insert into roles_hierarchy(child_role, parent_role) values (
'IS_AUTHENTICATED_FULLY', 
'IS_AUTHENTICATED_REMEMBERED');

insert into roles_hierarchy(child_role, parent_role) values (
'IS_AUTHENTICATED_REMEMBERED', 
'IS_AUTHENTICATED_ANONYMOUSLY');

insert into roles_hierarchy(child_role, parent_role) values (
'ROLE_ADMIN', 
'ROLE_A');

insert into secured_resources (resource_id, system_name, resource_name, resource_pattern, description, resource_type, sort_order, create_date) values (
'web-000001',
'SAMPLE', 
'*.do', 
'\A/.*\.do.*\Z', 
'', 
'url', 
100, 
'2010-01-31');

insert into secured_resources (resource_id, system_name, resource_name, resource_pattern, description, resource_type, sort_order, create_date) values (
'web-000002', 
'SAMPLE', 
'etc_all', 
'\A/.*\Z', 
'', 
'url', 
1000, 
'2010-01-31');

insert into secured_resources_roles(resource_id, role_id) values (
'web-000001', 
'ROLE_USER');

insert into secured_resources_roles(resource_id, role_id) values (
'web-000002', 
'IS_AUTHENTICATED_ANONYMOUSLY');

insert into secured_resources_roles(resource_id, role_id) values (
'web-000003', 
'IS_AUTHENTICATED_ANONYMOUSLY');

insert into secured_resources_roles(resource_id, role_id) values (
'web-000004', 
'ROLE_ADMIN');

INSERT INTO AUTHORITIES(USER_ID, ROLE_ID) VALUES('giljae','ROLE_USER');
INSERT INTO AUTHORITIES(USER_ID, ROLE_ID) VALUES('admin','ROLE_ADMIN');
commit;