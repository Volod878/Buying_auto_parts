CREATE DATABASE  buying_auto;
USE buying_auto;

CREATE TABLE buying_auto.auto_parts (
                                id int NOT NULL AUTO_INCREMENT,
                                name varchar(50),
                                price double ,
                                in_stock int,
                                delivery_period int,
                                PRIMARY KEY (id)
);

CREATE TABLE buying_auto.customers (
                               id int NOT NULL AUTO_INCREMENT,
                               name varchar(50),
                               PRIMARY KEY (id)
);

CREATE TABLE buying_auto.customer_auto_part (
                                     customer_id int NOT NULL,
                                     auto_part_id int NOT NULL,
                                     PRIMARY KEY (customer_id, auto_part_id),
                                     FOREIGN KEY (customer_id) REFERENCES buying_auto.customers(id),
                                     FOREIGN KEY (auto_part_id) REFERENCES buying_auto.auto_parts(id));