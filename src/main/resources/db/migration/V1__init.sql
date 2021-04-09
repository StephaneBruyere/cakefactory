CREATE TABLE ITEM ( 
  ID                VARCHAR(3) NOT NULL,
  NAME              VARCHAR(40),
  PRICE				DECIMAL(8,2),
  DESCRIPTION       VARCHAR(255),
  IMAGE				VARCHAR(40)
);
insert into ITEM values ('app','Apple Pie', 8.5, 'a delicious apple pie with fresh apples','applepie.jpg');
insert into ITEM values ('ecc','Eclair au chocolat', 6.5, 'a French chocolate treat','eclair.jpg');
insert into ITEM values ('mac','Macarons', 9.5, 'so delicate !','macarons.jpg');
insert into ITEM values ('str', 'Strudel', 6.0, 'a German apple pie','strudel.jpg');
insert into ITEM values ('cro','Croissant', 1.5,'the famous French pastry','croissant.jpg');
insert into ITEM values ('pro','Profiteroles', 9.0,'Ice cream and melting chocolate : the perfect match','profiteroles.jpg');