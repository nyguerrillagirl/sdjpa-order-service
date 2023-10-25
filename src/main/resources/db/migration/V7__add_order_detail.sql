create table order_line
(
    id  bigint not null auto_increment primary key,
    created_date        timestamp,
    last_modified_date  timestamp,
    quantity_ordered    int,
    order_header_id     bigint,
    constraint  order_header_pk FOREIGN KEY (order_header_id) references order_header(id)
);