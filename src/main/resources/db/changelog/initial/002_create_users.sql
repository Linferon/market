--changeset Ari: 002 create user table
create table users
(
    id       long auto_increment primary key not null,
    name     varchar(20)                     not null,
    surname  varchar(30),
    email    varchar(100)                    not null unique,
    password varchar(255)                    not null,
    enabled  boolean                         not null default true,
    role_id  long                            not null,

    constraint fk_user_role
        foreign key (role_id)
            references roles (id)
            on delete restrict
            on update cascade
)