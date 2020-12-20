INSERT INTO category (name) VALUES ('Legetoej');
INSERT INTO category (name) VALUES ('Slik');
INSERT INTO category (name) VALUES ('Grønt');
INSERT INTO category (name) VALUES ('Frost');

INSERT INTO product (name, price, description)
VALUES ('Fisk',20.0,'Hey');
INSERT INTO product (name, price, description)
VALUES ('legetoejbil',30.0,'koerer stærkt');

INSERT INTO product_category (category_id, product_id) values (1, 1);
INSERT INTO product_category (category_id, product_id) values (4, 1);
INSERT INTO product_category (category_id, product_id) values (3, 2);