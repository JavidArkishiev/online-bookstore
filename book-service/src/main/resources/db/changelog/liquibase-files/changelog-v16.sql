
alter table cart_items
    add constraint fk_cart_id
        foreign key (cart_id) references cart (id);
alter table cart_items
    add constraint fk_book_id
        foreign key (book_id) references books (id);