DELETE FROM T_ITEM;
DELETE FROM T_PRODUCT;
DELETE FROM T_CATEGORY;


-- New cat
INSERT INTO T_CATEGORY (id, name, description) VALUES (6, 'Phones', 'All kind of phones' );


--New products
INSERT INTO T_PRODUCT (id, name, description, category_fk) VALUES (1, 'Tablettes Android', 'Tablettes Android', 6);
INSERT INTO T_PRODUCT (id, name, description, category_fk) VALUES (2, 'Smartphones Android', 'Smartphones Android', 6);
INSERT INTO T_PRODUCT (id, name, description, category_fk) VALUES (3, 'Smartphones Windows', 'Smartphones Windows', 6);
INSERT INTO T_PRODUCT (id, name, description, category_fk) VALUES (4, 'IPhone', 'All IPhones', 6);



--New items
INSERT INTO T_ITEM (id, name, unitCost, imagePath, product_fk) VALUES (1, 'Motorola XOOM with Wi-Fi', '80.00', 'img/phones/motorola-xoom-with-wi-fi.0.jpg', 1);
INSERT INTO T_ITEM (id, name, unitCost, imagePath, product_fk) VALUES (2, 'MOTOROLA XOOM', '80.00', 'img/phones/motorola-xoom.0.jpg', 1);
INSERT INTO T_ITEM (id, name, unitCost, imagePath, product_fk) VALUES (3, 'MOTOROLA ATRIX 4G', '80.00', 'img/phones/motorola-atrix-4g.0.jpg', 2);
INSERT INTO T_ITEM (id, name, unitCost, imagePath, product_fk) VALUES (4, 'Dell Streak 7', '80.00', 'img/phones/dell-streak-7.0.jpg', 1);
INSERT INTO T_ITEM (id, name, unitCost, imagePath, product_fk) VALUES (5, 'Samsung Gem', '80.00', 'img/phones/samsung-gem.0.jpg', 2);
INSERT INTO T_ITEM (id, name, unitCost, imagePath, product_fk) VALUES (6, 'Dell Venue', '416.00', 'img/phones/dell-venue.0.jpg', 1);
INSERT INTO T_ITEM (id, name, unitCost, imagePath, product_fk) VALUES (7, 'Nexus S', '400.00', 'img/phones/nexus-s.0.jpg', 1);
INSERT INTO T_ITEM (id, name, unitCost, imagePath, product_fk) VALUES (8, 'LG Axis', '80.00', 'img/phones/lg-axis.0.jpg', 2);
INSERT INTO T_ITEM (id, name, unitCost, imagePath, product_fk) VALUES (9, 'Samsung Galaxy Tab', '287.00', 'img/phones/samsung-galaxy-tab.0.jpg', 1);
INSERT INTO T_ITEM (id, name, unitCost, imagePath, product_fk) VALUES (10, 'Samsung Showcase a Galaxy S phone', '45.00', 'img/phones/samsung-showcase-a-galaxy-s-phone.0.jpg', 2);
INSERT INTO T_ITEM (id, name, unitCost, imagePath, product_fk) VALUES (11, 'DROID 2 Global by Motorola', '150.00', 'img/phones/droid-2-global-by-motorola.0.jpg', 2);
INSERT INTO T_ITEM (id, name, unitCost, imagePath, product_fk) VALUES (12, 'DROID Pro by Motorola', '250.00', 'img/phones/droid-pro-by-motorola.0.jpg', 2);
INSERT INTO T_ITEM (id, name, unitCost, imagePath, product_fk) VALUES (13, 'MOTOROLA BRAVO with MOTOBLUR', '49.00', 'img/phones/motorola-bravo-with-motoblur.0.jpg', 2);
INSERT INTO T_ITEM (id, name, unitCost, imagePath, product_fk) VALUES (14, 'Motorola DEFY with MOTOBLUR', '119.00', 'img/phones/motorola-defy-with-motoblur.0.jpg', 2);
INSERT INTO T_ITEM (id, name, unitCost, imagePath, product_fk) VALUES (15, 'T-Mobile myTouch 4G', '77.00', 'img/phones/t-mobile-mytouch-4g.0.jpg', 2);
INSERT INTO T_ITEM (id, name, unitCost, imagePath, product_fk) VALUES (16, 'Samsung Mesmerize a Galaxy S phone', '189.00', 'img/phones/samsung-mesmerize-a-galaxy-s-phone.0.jpg', 2);
INSERT INTO T_ITEM (id, name, unitCost, imagePath, product_fk) VALUES (17, 'SANYO ZIO', '220.00', 'img/phones/sanyo-zio.0.jpg', 2);
INSERT INTO T_ITEM (id, name, unitCost, imagePath, product_fk) VALUES (18, 'T-Mobile G2', '80.00', 'img/phones/t-mobile-g2.0.jpg', 2);
INSERT INTO T_ITEM (id, name, unitCost, imagePath, product_fk) VALUES (19, 'Motorola CHARM with MOTOBLUR', '55.00', 'img/phones/motorola-charm-with-motoblur.0.jpg', 2);





