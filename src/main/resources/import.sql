INSERT INTO authority values('ADMIN');
INSERT INTO authority values('USER');
INSERT INTO authority values('ANONYMOUS');

INSERT INTO user VALUES (1, 'user', 'user');
INSERT INTO user VALUES (2, 'admin', 'admin');

INSERT INTO user_authority values(1, 'USER');
INSERT INTO user_authority values(2, 'ADMIN');