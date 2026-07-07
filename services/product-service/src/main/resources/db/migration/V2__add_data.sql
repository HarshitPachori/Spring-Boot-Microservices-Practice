insert into category (id, description, name) values
(1, 'Electronic devices and gadgets', 'Electronics'),
(2, 'Books and educational material', 'Books');

insert into product (id, description, name, available_quantity, price, category_id) values
(1, 'Latest model smartphone', 'Smartphone', 100.0, 699.99, 1),
(2, 'Wireless noise-canceling headphones', 'Headphones', 50.0, 199.99, 1),
(3, 'Hardcover science fiction novel', 'Sci-Fi Book', 200.0, 14.99, 2);

select setval('category_seq', 51, false);
select setval('product_seq', 101, false);