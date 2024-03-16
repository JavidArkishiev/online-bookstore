CREATE TABLE orders
(
    id            SERIAL PRIMARY KEY,
    total_price   NUMERIC,
    quantity      NUMERIC,
    order_status  VARCHAR(255),
    order_history timestamp

);
