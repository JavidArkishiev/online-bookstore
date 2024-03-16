CREATE TABLE books
(
    id               SERIAL PRIMARY KEY,
    author           VARCHAR(255),
    title            VARCHAR(255),
    price            NUMERIC,
    category_type    VARCHAR(255),
    publisher        VARCHAR(255),
    stock_quantity   INTEGER,
    publication_date DATE,
    serial_number    VARCHAR(255),
    description      TEXT,
    book_cover       VARCHAR(255)
--
);
