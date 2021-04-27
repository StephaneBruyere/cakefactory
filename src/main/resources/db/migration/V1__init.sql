CREATE TABLE ITEM ( 
  ID                VARCHAR(3) PRIMARY KEY,
  NAME              VARCHAR(40) NOT NULL,
  PRICE				DECIMAL(8,2) NOT NULL,
  DESCRIPTION       VARCHAR(255),
  IMAGE				VARCHAR(40)
);
CREATE TABLE T_USER ( 
  ID         	VARCHAR(50) PRIMARY KEY,
  PASSWORD   	VARCHAR(255) NOT NULL
);
CREATE TABLE ADDRESS ( 
  ID         	VARCHAR(50) PRIMARY KEY,
  NAME			VARCHAR(50),
  LINE1    	 	VARCHAR(50),
  LINE2    	 	VARCHAR(50),
  POSTCODE 	 	VARCHAR(10),
  CITY 	 		VARCHAR(30)
);
insert into ITEM values ('app','Apple Pie', 8.5, 'a delicious apple pie with fresh apples','applepie.jpg');
insert into ITEM values ('ecc','Eclair au chocolat', 6.5, 'a French chocolate treat','eclair.jpg');
insert into ITEM values ('mac','Macarons', 9.5, 'so delicate !','macarons.jpg');
insert into ITEM values ('str', 'Strudel', 6.0, 'a German apple pie','strudel.jpg');
insert into ITEM values ('cro','Croissant', 1.5,'the famous French pastry','croissant.jpg');
insert into ITEM values ('pro','Profiteroles', 9.0,'Ice cream and melting chocolate : the perfect match','profiteroles.jpg');
INSERT INTO T_USER VALUES ('stb@cnam.fr', '$2a$10$Wp6BYM99hA4B1ufrJBtAzeqgG8qlhbtl94YYn0rcUubpqsaAw/vjm');
INSERT INTO ADDRESS VALUES ('stb@cnam.fr','Stéphane Bruyère','210, R du Fg St Martin','','CP75010', 'PARIS');