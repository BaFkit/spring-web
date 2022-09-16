create table orders
(
    id          bigserial primary key,
    username    varchar(255) not null,
    total_price int          not null,
    address     varchar,
    phone       varchar(255),
    created_at  timestamp,
    updated_at  timestamp
);

create table order_items
(
    id                bigserial primary key,
    product_id        bigint not null,
    product_title     varchar(255),
    quantity          int    not null,
    order_id          bigint not null references orders (id),
    price_per_product int    not null,
    price             int    not null,
    created_at        timestamp,
    updated_at        timestamp
);

insert into orders (username, total_price, address, phone)
values ('bob', 200, 'address', '12345');

insert into order_items (product_id, order_id, quantity, price_per_product, price)
values (1, 1, 2, 100, 200);
