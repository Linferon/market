--changeset Ari: 007 create budgets
create table budgets
(
    id         long auto_increment primary key not null,
    "year"       int                             not null,
    "month"      int                             not null,
    budget     decimal(10, 2)                  not null,
    income     decimal(10, 2)                  not null default 0.0,
    expense    decimal(10, 2)                  not null default 0.0,
    net_result decimal(10, 2) as (income - expense)
)