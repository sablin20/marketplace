INSERT INTO Product_amount
VALUES (1, 2),
       (2, 4),
       (3, 3),
       (6, 10),
       (7, 23),
       (5, 41),
       (4, 7);

INSERT INTO Purchase_history
VALUES (1, 1, 2, 2),
       (6, 1, 2, 1),
       (2, 5, 5, 5),
       (3, 4, 1, 1),
       (4, 3, 4, 2),
       (5, 2, 3, 1),
       (7, 4, 6, 2),
       (8, 4, 7, 1),
       (9, 2, 6, 1);

INSERT INTO Client
VALUES (1, 'Alex', 'Smith'),
       (2, 'Max', 'Kruse'),
       (3, 'Lion', 'Oppenda'),
       (4, 'Bob', 'Saul'),
       (5, 'Tim', 'Kook');

INSERT INTO Store
VALUES (1, 'DNS'),
       (2, 'OZON'),
       (3, 'AppStore'),
       (4, 'SberMarket'),
       (5, 'Citilink');

INSERT INTO Product
VALUES (1, 'Iphone8', 10000, 'Smartphone', 'Apple', 3),
       (2, 'UHD600tv12', 60000, 'TV', 'LG', 5),
       (3, 'MicroGoVER', 9500, 'Microwave', 'Gorenje', 4),
       (4, 'MacBook14Pro', 134600, 'PC', 'Apple', 1),
       (5, 'Sony8K', 77000, 'TV', 'Sony', 2),
       (6, 'Holodos', 48600, 'Holod', 'LG', 4),
       (7, 'GrillSUPPER1000', 2000, 'GRILL', 'LG', 2);

INSERT INTO Review
VALUES (1, 'Alex', 2, 'отличный товар', 5),
     (2, 'John', 3, 'великолепный товар', 5),
     (3, 'Sem', 4, 'крутой товар', 4),
     (4, 'Don', 3, 'почти лучший товар', 4),
     (5, 'Kro', 3, 'неплохой товар', 3),
     (6, 'Ben', 2, 'не тот товар', 1),
     (7, 'Jack', 1, 'бракованный товар', 2),
     (8, 'Leo', 1, 'ужасный товар', 2),
     (9, 'Poul', 4, 'средний товар', 3),
    (10, 'Feel', 1, 'не о чем товар', 0),
     (11, 'Alex', 2, 'супер товар', 4);