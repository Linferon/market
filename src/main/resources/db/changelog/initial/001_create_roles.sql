--changeset Ari: 001 create role table

-- roles
create table roles
(
    id   long auto_increment primary key not null,
    name varchar(20)                     not null unique
);

