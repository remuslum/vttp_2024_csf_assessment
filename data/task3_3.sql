-- TODO Task 3
-- drop database if exists
DROP DATABASE IF EXISTS products;

-- create database
CREATE DATABASE products;

-- select database
USE products;

-- create orders table
CREATE TABLE orders(
    orderId VARCHAR(32) NOT NULL,
    date DATE NOT NULL,
    name VARCHAR(256) NOT NULL,
    address VARCHAR(256) NOT NULL,
    priority BOOLEAN NOT NULL,
    comments TEXT NOT NULL,

    CONSTRAINT pk_orderId PRIMARY KEY(orderId)
);

-- create cart table
CREATE TABLE lineItem(
    id INT AUTO_INCREMENT,
    productId VARCHAR(256) NOT NULL,
    name VARCHAR(256) NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    orderId VARCHAR(32) NOT NULL,

    CONSTRAINT pk_id PRIMARY KEY(id),
    CONSTRAINT fk_order_id FOREIGN KEY(orderId) REFERENCES orders(orderId)
);

-- grant privileges to fred
SELECT "Granting privileges to fred..." AS msg;
GRANT ALL PRIVILEGES ON movies.* TO 'fred'@'%';
FLUSH PRIVILEGES;