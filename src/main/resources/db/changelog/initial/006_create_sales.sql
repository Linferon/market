--changeset Ari: 006 create sales
create table sales
(
    id          long auto_increment primary key not null,
    product_id  long                            not null,
    quantity    int                             not null default 1,
    total_price decimal(10, 2)                  not null,
    sale_date   timestamp                       not null default current_timestamp(),

    constraint fk_sales_product_id
        foreign key (product_id)
            references products (id)
            on delete restrict
            on update cascade
)