DROP DATABASE buying_auto;

CREATE DATABASE buying_auto;
USE buying_auto;

CREATE TABLE buying_auto.auto_parts (
                                        id int NOT NULL AUTO_INCREMENT,
                                        name varchar(50) NOT NULL,
                                        price double NOT NULL,
                                        amount int NOT NULL,
                                        delivery_period int NOT NULL,
                                        PRIMARY KEY (id)
);

CREATE TABLE buying_auto.shop (
                                  id int NOT NULL AUTO_INCREMENT,
                                  PRIMARY KEY (id),
                                  in_stock int NOT NULL,
                                  auto_part_id int NOT NULL,
                                  FOREIGN KEY (auto_part_id) REFERENCES buying_auto.auto_parts(id)
);

CREATE TABLE buying_auto.customers (
                                         id int NOT NULL AUTO_INCREMENT,
                                         PRIMARY KEY (id),
                                         name VARCHAR(50) NOT NULL
  );

CREATE TABLE buying_auto.orders (
                                    id int NOT NULL AUTO_INCREMENT,
                                    PRIMARY KEY (id),
                                    total_cost DOUBLE NOT NULL,
                                    customer_id int NOT NULL,
                                    FOREIGN KEY (customer_id) REFERENCES buying_auto.customers(id)
);

CREATE TABLE buying_auto.shopping_cart (
                                    id int NOT NULL AUTO_INCREMENT,
                                    PRIMARY KEY (id),
                                    name varchar(50) NOT NULL,
                                    price double NOT NULL,
                                    amount int NOT NULL,
                                    cost double NOT NULL,
                                    order_id int NOT NULL,
                                    FOREIGN KEY (order_id) REFERENCES buying_auto.orders(id)
);

