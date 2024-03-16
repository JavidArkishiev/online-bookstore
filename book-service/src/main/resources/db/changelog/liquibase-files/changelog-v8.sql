create table address
(
    id           serial primary key,
    country varchar(255),
    street_name  varchar(255),
    house_number varchar(255),
    post_code    varchar(255)
)