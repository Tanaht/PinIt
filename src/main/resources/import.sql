#Data fixtures to develop in ease

-- Authority
INSERT INTO authority values('ROLE_ADMIN');
INSERT INTO authority values('ROLE_USER');
INSERT INTO authority values('ROLE_ANONYMOUS');

-- Activity
-- rain max, rain min, snow, temp, wind
INSERT INTO activity VALUES (1,'Randonnée',5,0,50,0,30,10,40,0);
INSERT INTO activity VALUES (2,'Vélo',5,0,5,0,30,10,40,0);