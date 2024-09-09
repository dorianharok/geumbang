INSERT INTO products (type, price)
VALUES ('GOLD_999', 11000.00)
    ON DUPLICATE KEY UPDATE price = VALUES(price);

INSERT INTO products (type, price)
VALUES ('GOLD_9999', 13000.00)
    ON DUPLICATE KEY UPDATE price = VALUES(price);