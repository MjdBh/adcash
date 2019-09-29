INSERT INTO user (id,first_name,last_name,login,password_hash) VALUES  (1,'admin','admin','admin','$2a$10$/1VFtlrxzP.oifb2EJQxzOrwLxFZh9MYNHOUql7NiVqjYI90aYd86');
INSERT INTO authority(name) VALUES  ('ROLE_ADMIN');
INSERT INTO user_authority(user_id,authority_name) VALUES    (1,'ROLE_ADMIN');
INSERT INTO product (id,name,identifier) VALUES     (1,'car','CAR'),    (2,'bus','BUS'),    (3,'bike','BIK');
INSERT INTO category(id,name,identifier) VALUES    (1,'transport','TRNS'),    (2,'home','HOME'),    (3,'food','FOOD');
INSERT INTO product_category(product_id,category_id) VALUES     (1,1),    (2,1),    (3,1),(2,3),(1,3);