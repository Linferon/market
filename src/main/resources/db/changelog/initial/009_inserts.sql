-- changelog Ari: 009 add static data

-- Роли пользователей
insert into ROLES(NAME)
values ('Директор'),
       ('Бухгалтер'),
       ('Кассир'),
       ('Кладовщик');


-- Пользователи (users)
INSERT INTO users (name, surname, email, password, role_id)
VALUES ('Иван', 'Иванов', 'ivan@company.com', 'qwerty', (SELECT id FROM roles WHERE name = 'Директор')),
       ('Мария', 'Петрова', 'maria@company.com', 'qwerty', (SELECT id FROM roles WHERE name = 'Бухгалтер')),
       ('Алексей', 'Смирнов', 'alex@company.com', 'qwerty', (SELECT id FROM roles WHERE name = 'Кассир')),
       ('Екатерина', 'Козлова', 'kate@company.com', 'qwerty', (SELECT id FROM roles WHERE name = 'Кладовщик'));

-- Продукты (products) - 15 записей
INSERT INTO products (name, buy_price, sell_price)
VALUES ('Молоко 2.5% 1л', 55.00, 75.50),
       ('Хлеб белый', 25.00, 35.00),
       ('Сыр Российский 300г', 180.00, 240.00),
       ('Колбаса вареная 500г', 210.00, 280.00),
       ('Вода минеральная 0.5л', 20.00, 35.00),
       ('Сок яблочный 1л', 70.00, 95.00),
       ('Кока-кола 1.5л', 65.00, 90.00),
       ('Порошок стиральный 3кг', 350.00, 450.00),
       ('Средство для мытья посуды 500мл', 120.00, 160.00),
       ('Шампунь 400мл', 150.00, 200.00);

-- Склад (stock) - заполнение для всех продуктов
INSERT INTO stock (product_id, quantity)
SELECT id, FLOOR(RAND() * 100) + 20
FROM products;


-- Закупки (purchases)
INSERT INTO purchases (product_id, quantity, purchase_date, total_cost)
VALUES ((SELECT id FROM products WHERE name = 'Молоко 2.5% 1л'), 50,
        DATEADD('DAY', -30, CURRENT_TIMESTAMP()), 2750.00),

       ((SELECT id FROM products WHERE name = 'Хлеб белый'), 70,
        DATEADD('DAY', -28, CURRENT_TIMESTAMP()), 1750.00),

       ((SELECT id FROM products WHERE name = 'Сыр Российский 300г'), 30,
        DATEADD('DAY', -25, CURRENT_TIMESTAMP()), 5400.00),

       ((SELECT id FROM products WHERE name = 'Колбаса вареная 500г'), 25,
        DATEADD('DAY', -24, CURRENT_TIMESTAMP()), 5250.00),

       ((SELECT id FROM products WHERE name = 'Вода минеральная 0.5л'), 100,
        DATEADD('DAY', -22, CURRENT_TIMESTAMP()), 2000.00),

       ((SELECT id FROM products WHERE name = 'Сок яблочный 1л'), 45,
        DATEADD('DAY', -20, CURRENT_TIMESTAMP()), 3150.00),

       ((SELECT id FROM products WHERE name = 'Кока-кола 1.5л'), 60,
        DATEADD('DAY', -18, CURRENT_TIMESTAMP()), 3900.00),

       ((SELECT id FROM products WHERE name = 'Порошок стиральный 3кг'), 20,
        DATEADD('DAY', -15, CURRENT_TIMESTAMP()), 7000.00);


-- Продажи (sales) - 20 записей
INSERT INTO sales (product_id, quantity, TOTAL_PRICE, sale_date)
VALUES ((SELECT id FROM products WHERE name = 'Молоко 2.5% 1л'), 3,
        226.50, DATEADD('DAY', -25, CURRENT_TIMESTAMP())),

       ((SELECT id FROM products WHERE name = 'Хлеб белый'), 2,
        70.00, DATEADD('DAY', -25, CURRENT_TIMESTAMP())),

       ((SELECT id FROM products WHERE name = 'Сыр Российский 300г'), 1,
        240.00, DATEADD('DAY', -24, CURRENT_TIMESTAMP())),

       ((SELECT id FROM products WHERE name = 'Колбаса вареная 500г'), 2,
        560.00, DATEADD('DAY', -23, CURRENT_TIMESTAMP())),

       ((SELECT id FROM products WHERE name = 'Вода минеральная 0.5л'), 5,
        175.00, DATEADD('DAY', -22, CURRENT_TIMESTAMP())),

       ((SELECT id FROM products WHERE name = 'Сок яблочный 1л'), 2,
        190.00, DATEADD('DAY', -21, CURRENT_TIMESTAMP())),

       ((SELECT id FROM products WHERE name = 'Кока-кола 1.5л'), 3,
        270.00, DATEADD('DAY', -20, CURRENT_TIMESTAMP())),

       ((SELECT id FROM products WHERE name = 'Порошок стиральный 3кг'), 1,
        450.00, DATEADD('DAY', -19, CURRENT_TIMESTAMP()));


-- Зарплаты (salaries) - 10 записей
INSERT INTO SALARIES (USER_ID, SALARY, period_start, period_end,is_paid)
VALUES ((SELECT id FROM users WHERE email = 'alex@company.com'),
        200.00,
        PARSEDATETIME('01.03.2025', 'dd.MM.yyyy'),
        PARSEDATETIME('31.03.2025', 'dd.MM.yyyy'),
         true),

       ((SELECT id FROM users WHERE email = 'kate@company.com'),
        168.0,
        PARSEDATETIME('01.03.2025', 'dd.MM.yyyy'),
        PARSEDATETIME('31.03.2025', 'dd.MM.yyyy'),
         true);


-- Бюджеты (monthly_budgets) - 10 записей
INSERT INTO BUDGETS ("year", "month", BUDGET, INCOME, EXPENSE)
VALUES (2022, 4, 350000.00, 420000.00, 340000.00),

        (2022, 5, 410000.00, 360000.00, 405000.00),

       (2022, 6,420000.00, 365000.00, 428000.00),

       (2022, 7, 430000.00, 370000.00, 440000.00),

       (2022, 8, 450000.00, 380000.00, 455000.00),

       (2022, 9, 500000.00, 410000.00, 515000.00),

       (2022, 10, 460000.00, 390000.00, 450000.00);
