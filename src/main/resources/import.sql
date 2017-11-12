#Data fixtures to develop in ease


INSERT INTO authority values('ROLE_ADMIN');
INSERT INTO authority values('ROLE_USER');
INSERT INTO authority values('ROLE_ANONYMOUS');

-- user
INSERT INTO user VALUES (1, 'loic.beaulieu@etudiant.univ-rennes1.fr', 'user', '$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.'); #PWD = 123456
INSERT INTO user VALUES (2, 'loic.beaulieu@etudiant.univ-rennes1.fr', 'admin', '$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.'); #PWD = 123456

-- Activity
-- rain max, rain min, snow, temp, wind
INSERT INTO activity VALUES (1,'Randonnée',5,0,50,0,30,10,40,0);
INSERT INTO activity VALUES (2,'Vélo',5,0,5,0,30,10,40,0);

-- inscription
insert into inscription_activity VALUES (1,48.11,-1.638,1,1);
insert into inscription_activity VALUES (2,46.8896024,-0.9303045,1,1);


INSERT INTO user_authority values(1, 'ROLE_USER');
INSERT INTO user_authority values(2, 'ROLE_ADMIN');