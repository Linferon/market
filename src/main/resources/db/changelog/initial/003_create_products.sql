--changeset Ari: 003 create products
create table products
(
    id         long auto_increment primary key not null,
    name       varchar(50)                     not null unique,
    buy_price  decimal(10, 2)                  not null,
    sell_price decimal(10, 2)                  not null
)