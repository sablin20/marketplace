CREATE TABLE Product (
 id SERIAL PRIMARY KEY NOT NULL,
 name VARCHAR(100) NOT NULL,
 price BIGINT NOT NULL,
 category VARCHAR(50) NOT NULL,
 brand VARCHAR(50) NOT NULL,
 store_id INTEGER NOT NULL
);

CREATE TABLE Store (
   id SERIAL PRIMARY KEY NOT NULL,
   name VARCHAR(100) NOT NULL
);

CREATE TABLE Client (
    id SERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL
);

CREATE TABLE Purchase_history (
    id SERIAL PRIMARY KEY NOT NULL,
    client_id INTEGER NOT NULL,
    product_id INTEGER NOT NULL,
    amount INTEGER NOT NULL
);

CREATE TABLE Product_amount (
    product_id INTEGER NOT NULL,
    amount INTEGER NOT NULL
);

CREATE TABLE Review (
    id INTEGER NOT NULL PRIMARY KEY,
    client_name VARCHAR(20) NOT NULL,
    store_name VARCHAR(30) NOT NULL,
    message VARCHAR(50) NOT NULL,
    rating INTEGER NOT NULL
);