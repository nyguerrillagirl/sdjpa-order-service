-- creates the new CUSTOMER table
create table customer (
                          id bigint not null auto_increment primary key,
                          customer_name varchar(50),
                          address varchar(30),
                          city varchar(30),
                          state varchar(30),
                          zip_code varchar(30),
                          phone varchar(20),
                          email varchar(255),
                          created_date timestamp,
                          last_modified_date timestamp
);

insert into customer (customer_name, address, city, state, zip_code, phone, email, created_date, last_modified_date)
    values ('Lorraine Figueroa', '6 Fairway Dr.', 'Plymouth Meeting','PA', '19462', '555-555-5555',
            'lorraine.figueroa@brainycode.com', now(), now());

-- add customer_id to order header table
alter table order_header
    drop column customer,
    add column customer_id bigint;

-- set all orders to be from the same 1 customer (me!)
update order_header
    set customer_id = (select id from customer where customer_name = 'Lorraine Figueroa');

-- add constraint that customer_id is a foreign_key that needs to be filled
alter table order_header
    add constraint customer_id_fk FOREIGN KEY (customer_id) references customer(id);
