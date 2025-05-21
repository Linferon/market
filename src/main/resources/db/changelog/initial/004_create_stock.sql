--changeset Ari: 004 create stock
create table stock
(
    product_id long not null unique primary key,
    quantity   int  not null default 0,

    constraint fk_stock_product
        foreign key (product_id)
            references products (id)
            on delete restrict
            on update cascade
)