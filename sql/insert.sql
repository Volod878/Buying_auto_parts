INSERT INTO buying_auto.auto_parts (name, price, amount, delivery_period)
VALUES
    ('Движок', 100000.00, 120, 40),
    ('АКПП', 50000.00, 200, 25),
    ('Шасси', 20000.00, 240, 15),
    ('Руль', 4999.99, 500, 10),
    ('Лобовое стекло', 14723.12, 330, 5),
    ('Педаль', 720.00, 1400, 10),
    ('Ремень', 1430.00, 800, 5),
    ('Переднее сиденье', 17020.00, 145, 15);


INSERT INTO buying_auto.shop (auto_part_id, in_stock)
VALUES
    (1, 2),
    (2, 5),
    (3, 10),
    (4, 20),
    (5, 15),
    (6, 40),
    (7, 30),
    (8, 5);


INSERT INTO buying_auto.customers (name)
    VALUES ('Администратор'),
           ('Владимир');
