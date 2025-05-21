--changeset Ari: 005 create purchases
create table purchases
(
    id            long auto_increment primary key not null,
    product_id    long                            not null,
    quantity      int                             not null,
    total_cost    decimal(10, 2)                  not null,
    purchase_date timestamp                       not null default current_timestamp,

    constraint fk_pur_product
        foreign key (product_id)
            references products (id)
            on delete restrict
            on update cascade
)