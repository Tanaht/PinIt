#Data fixtures to develop in ease

INSERT INTO authority values('ADMIN');
INSERT INTO authority values('USER');
INSERT INTO authority values('ANONYMOUS');

INSERT INTO user VALUES (1, 'charp.antoine+pinit-user@gmail.com', 'user', 'user');
INSERT INTO user VALUES (2, 'charp.antoine+pinit-admin@gmail.com', 'admin', 'admin');

INSERT INTO user_authority values(1, 'USER');
INSERT INTO user_authority values(2, 'ADMIN');