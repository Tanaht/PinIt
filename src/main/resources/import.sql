#Data fixtures to develop in ease

INSERT INTO authority values('ROLE_ADMIN');
INSERT INTO authority values('ROLE_USER');
INSERT INTO authority values('ROLE_ANONYMOUS');

INSERT INTO user VALUES (1, 'charp.antoine+pinit-user@gmail.com', 'user', '$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.'); #PWD = 123456
INSERT INTO user VALUES (2, 'charp.antoine+pinit-admin@gmail.com', 'admin', '$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.'); #PWD = 123456

INSERT INTO user_authority values(1, 'ROLE_USER');
INSERT INTO user_authority values(2, 'ROLE_ADMIN');