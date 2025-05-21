--changeset Ari: 008 create salaries
create table salaries
(
    id           long auto_increment primary key not null,
    user_id      long                            not null,
    salary       decimal(10, 2)                  not null,
    period_start date,
    period_end   date,
    is_paid      boolean                         not null default false,

    constraint fk_salaries_user
        foreign key (user_id)
            references users (id)
            on delete restrict
            on update cascade
)