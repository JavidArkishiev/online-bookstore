ALTER TABLE books
    ADD COLUMN authors_id BIGINT;

ALTER TABLE books
    ADD CONSTRAINT fk_author_id
        FOREIGN KEY (authors_id) REFERENCES authors (id);

ALTER TABLE books
    ADD COLUMN publisher_id BIGINT;

ALTER TABLE books
    ADD CONSTRAINT fk_publisher_id
        FOREIGN KEY (publisher_id) REFERENCES publisher (id);

ALTER TABLE books
    ADD COLUMN carts_id BIGINT;

ALTER TABLE books
    ADD CONSTRAINT fk_cart_id
        FOREIGN KEY (carts_id) REFERENCES cart (id);

ALTER TABLE cart
    ADD COLUMN user_id BIGINT;

ALTER TABLE cart
    ADD CONSTRAINT fk_user_id
        FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE users
    ADD COLUMN cart_id BIGINT;

ALTER TABLE users
    ADD CONSTRAINT fk_cart_id
        FOREIGN KEY (cart_id) REFERENCES cart (id);

alter table orders
    add column users_id BIGINT;

alter table orders
    add column books_id bigint;

alter table orders
    add constraint fk_user_id
        foreign key (users_id) references users (id);

alter table orders
    add constraint fk_books_id
        foreign key (books_id) references books (id);

alter table review
    add column user_id bigint;

alter table review
    add column books_id bigint;

alter table review
    add constraint fk_users_id
        foreign key (user_id) references users (id);

alter table review
    add constraint fk_books_id
        foreign key (books_id) references books (id);
alter table users
    add column address_id bigint;
alter table address
    add column users_id bigint;

alter table users
    add constraint fk_address_id
        foreign key (address_id) references address (id);

alter table address
    add constraint fk_users_id
        foreign key (users_id) references users (id);

alter table order_items
    add column books_id bigint;
alter table order_items
    add column orders_id bigint;

alter table order_items
    add constraint fk_books_id
        foreign key (books_id) references books (id);
alter table order_items
    add constraint fk_orders_id
        foreign key (orders_id) references orders (id);





