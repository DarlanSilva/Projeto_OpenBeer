USE OPENBEER;

ALTER TABLE TB_CERVEJA
DROP COLUMN TG_DESTAQUE;

ALTER TABLE TB_CERVEJA
ADD COLUMN TG_DESTAQUE BOOL;

INSERT INTO TS_PERMISSAOACESSO(PERMISSAO, DH_INCLUSAO, TG_INATIVO)
VALUES("ROLE_USER",NOW(),0);

INSERT INTO TS_PERMISSAOACESSO(PERMISSAO, DH_INCLUSAO, TG_INATIVO)
VALUES("ROLE_ADMIN",NOW(),0);

INSERT INTO TS_LOGIN(EMAIL, SENHA, TG_INATIVO, DH_INCLUSAO)
VALUES('darlan.rs@hotmail.com','$2a$10$zQ.u0Ca8pqnWGorUtNLLU.KvgTvrYW77OWpNeru/ArNp22T/rZEnS',0,NOW());

INSERT INTO TS_LOGIN_PERMISSAOACESSO(LOGIN_PK_ID,	PERMISSAO_ACESSO_PK_ID)
VALUES(1,2);

INSERT INTO TB_CLIENTE(CPF, NOME, DT_NASCIMENTO, TG_SEXO, TELEFONE, FK_LOGIN, TG_INATIVO, DH_INCLUSAO)
VALUES('52923880099', 'DARLAN ROCHA', 1996-01-06, 'M','95555-7777',1,0,NOW());

INSERT INTO TB_TIPOCERVEJA (TIPOCERVEJA, TG_INATIVO, DH_INCLUSAO)
VALUES ('ARTESANAL',0,NOW());

INSERT INTO TB_TIPOCERVEJA (TIPOCERVEJA, TG_INATIVO, DH_INCLUSAO)
VALUES ('IMPORTADA',0,NOW());

INSERT INTO TB_TIPOCERVEJA (TIPOCERVEJA, TG_INATIVO, DH_INCLUSAO)
VALUES ('AMARGA',0,NOW());

INSERT INTO TB_TIPOCERVEJA (TIPOCERVEJA, TG_INATIVO, DH_INCLUSAO)
VALUES ('ADOCICADA',0,NOW());

INSERT INTO TB_STATUSPEDIDO (STATUSPEDIDO, DH_INCLUSAO, TG_INATIVO)
VALUES('PEDIDO EFETUADO', NOW(), 0);

INSERT INTO TB_STATUSPEDIDO (STATUSPEDIDO, DH_INCLUSAO, TG_INATIVO)
VALUES('PEDIDO APROVADO', NOW(), 0);

INSERT INTO TB_STATUSPEDIDO (STATUSPEDIDO, DH_INCLUSAO, TG_INATIVO)
VALUES('PEDIDO RECUSADO', NOW(), 0);

INSERT INTO TB_STATUSPEDIDO (STATUSPEDIDO, DH_INCLUSAO, TG_INATIVO)
VALUES('PEDIDO EM TRANSPORTE', NOW(), 0);

INSERT INTO TB_STATUSPEDIDO (STATUSPEDIDO, DH_INCLUSAO, TG_INATIVO)
VALUES('PEDIDO ENTREGUE', NOW(), 0);

INSERT INTO TB_TIPOENTREGA(TIPOENTREGA,TG_INATIVO,DH_INCLUSAO, PRAZOENTREGA, VL_ENTREGA)
VALUES ("SEDEX -  15,00",0,NOW(), 20,15.00);

INSERT INTO TB_TIPOENTREGA(TIPOENTREGA,TG_INATIVO,DH_INCLUSAO, PRAZOENTREGA, VL_ENTREGA)
VALUES ("PAC -  25,00",0,NOW(),10,25.00);

INSERT INTO TB_CUPOM(DS_CUPOM, DH_INCLUSAO, DH_VALIDADE, VL_CUPOM)
VALUES ("CHAMANABREJA", NOW(),'2019-06-07 14:47:12',25.00);

INSERT INTO TB_CUPOM(DS_CUPOM,DH_INCLUSAO, DH_VALIDADE, VL_CUPOM)
VALUES ("BREJA10", NOW(),'2019-07-07 00:00:00',10.00);

INSERT INTO TB_CERVEJA (`brevedescricao`,`codigo`,`descricao`,`tg_destaque`,`dh_alteracao`,`dh_inclusao`,`imagemcerveja`,`tg_inativo`,`marca`,`ml`,`cerveja`,`quantidade`,`teor`,`vl_total`,`fk_tipocerveja`) VALUES ('Produzida a partir de maltes e lúpulos selecionado','12354','Produzida a partir de maltes e lúpulos selecionados, Polar Export tem cor clara, processo de baixa fermentação, com aroma, sabor e amargor suaves. É uma herança do Rio Grande do Sul, onde foi lançado em 1929, na cidade de Estrela. Em 1972 o controle da Cervejaria Polar foi adquirido pela Cia. Paulista Antarctica.\r\nINGREDIENTES: Água, malte, milho e lúpulo.\r\nALERGÊNICOS: Contém cevada e glúten','1',NULL,'2019-06-07 14:47:12','tupiniquin-beer.png',0,'OpenBeer','10000','Polar',100,0,25.00,2);
INSERT INTO TB_CERVEJA (`brevedescricao`,`codigo`,`descricao`,`tg_destaque`,`dh_alteracao`,`dh_inclusao`,`imagemcerveja`,`tg_inativo`,`marca`,`ml`,`cerveja`,`quantidade`,`teor`,`vl_total`,`fk_tipocerveja`) VALUES ('Tradicional cerveja alemã de Munique.','66589','Tradicional cerveja alemã de Munique, a cerveja Löwenbräu é uma típica Munich Helles: de cor clara, sabor maltado e levemente amargo, além de muito refrescante. Essas características fazem da Löwenbräu uma ótima opção de cerveja para curtir o happy hour com os amigos, acompanhada por petiscos clássicos, como amendoins ou bolinhos de bacalhau.\r\nINGREDIENTES: Água, malte de cevada, lúpulo, extrato de lúpulo.\r\nALERGÊNICOS: Contém cevada e glúten.','0',NULL,'2019-06-07 14:48:54','beer3.png',0,'OpenBeer','1000000','Löwenbräu',100,6,30.00,1);
INSERT INTO TB_CERVEJA (`brevedescricao`,`codigo`,`descricao`,`tg_destaque`,`dh_alteracao`,`dh_inclusao`,`imagemcerveja`,`tg_inativo`,`marca`,`ml`,`cerveja`,`quantidade`,`teor`,`vl_total`,`fk_tipocerveja`) VALUES ('A Beck’s 275ml é uma tradicional.','89542','A Beck’s 275ml é uma tradicional cerveja alemã puro malte, com mais de 100 anos. Seu estilo é Premium American Lager. Ela é leve, de cor dourada, sabor suave e refrescante.\r\n\r\nINGREDIENTES: Água, malte e lúpulo.\r\nALERGÊNICOS: Contém malte.','1',NULL,'2019-06-07 14:50:06','beer-artesanal.png',0,'OpenBeer','275','Beck\'s',100,5,35.00,3);
INSERT INTO TB_CERVEJA (`brevedescricao`,`codigo`,`descricao`,`tg_destaque`,`dh_alteracao`,`dh_inclusao`,`imagemcerveja`,`tg_inativo`,`marca`,`ml`,`cerveja`,`quantidade`,`teor`,`vl_total`,`fk_tipocerveja`) VALUES ('Criada pelo alemão Henrique Thielen.','15516','A Cerveja Adriática 600ml foi criada pelo alemão Henrique Thielen, um visionário cervejeiro do início do século XX. Ela teve seu nome em homenagem à cervejaria que traduz toda uma era de tradição passada de pai para filho. Hoje, conhecida como a irmã mais velha da Original, ela é uma pedida certa para a mesa de bar. Reconhecidamente uma cerveja puro malte de alta qualidade, fácil de beber e com aromas especiais que dão um toque equilibrado.\r\nINGREDIENTES: Água, malte e lúpulo.\r\nALERGÊNICOS: Contém cevada e glúten.','1',NULL,'2019-06-07 14:51:39','adriatica_600ml.png',0,'OpenBeer','600','Adriática',100,5,40.00,1);
INSERT INTO TB_CERVEJA (`brevedescricao`,`codigo`,`descricao`,`tg_destaque`,`dh_alteracao`,`dh_inclusao`,`imagemcerveja`,`tg_inativo`,`marca`,`ml`,`cerveja`,`quantidade`,`teor`,`vl_total`,`fk_tipocerveja`) VALUES (' Cerveja de amargor mais acentuado','665582','A Bohemia 838 Pale Ale é uma cerveja de amargor mais acentuado. Com aromas intensos de lúpulo, passando por notas cítricas e frutadas até uma sutil nota de malte caramelo, a 838 Pale Ale foi eleita a melhor da categoria no Festival Brasileiro da Cerveja de 2016.\r\n\r\nINGREDIENTES: Água, malte e lúpulo.\r\nALERGÊNICOS: Contém cevada e glúten.','1',NULL,'2019-06-07 14:53:19','bohemia_838pale-ale_300ml-1.png',0,'OpenBeer','300','838 Pale Ale',100,5,15.00,3);
INSERT INTO TB_CERVEJA (`brevedescricao`,`codigo`,`descricao`,`tg_destaque`,`dh_alteracao`,`dh_inclusao`,`imagemcerveja`,`tg_inativo`,`marca`,`ml`,`cerveja`,`quantidade`,`teor`,`vl_total`,`fk_tipocerveja`) VALUES ('Combina com ocasiões especiais','556247','Essa Premium American Lager combina com ocasiões especiais. Uma cerveja feita com três tipos especiais de malte, equilibrada entre o amargor e o adocicado, nascida para comemorar a reabertura da Cervejaria Bohemia, em 2012.\r\n\r\nINGREDIENTES: Água, malte e lúpulo.\r\nALERGÊNICOS: Contém cevada e glúten.','1',NULL,'2019-06-07 14:55:15','bohemia-imperial_550ml.png',0,'OpenBeer','1000','Imperial',100,5,50.00,1);
INSERT INTO TB_CERVEJA (`brevedescricao`,`codigo`,`descricao`,`tg_destaque`,`dh_alteracao`,`dh_inclusao`,`imagemcerveja`,`tg_inativo`,`marca`,`ml`,`cerveja`,`quantidade`,`teor`,`vl_total`,`fk_tipocerveja`) VALUES ('A Bohemia Escura conta com maltes raros.','55624','A Bohemia Escura conta com maltes raros bem torrados que são importados de Munique, na Alemanha. O resultado é uma bebida de cor única, encorpada e extremamente cremosa. Com sabor suave, a Bohemia Escura tem aroma com notas de toffe e espuma de consistência aveludada.\r\n\r\nINGREDIENTES: Água, malte, milho, lúpulo e corante caramelo III INS 150c.\r\nALERGÊNICOS: Contém cevada e glúten.','0',NULL,'2019-06-07 14:56:31','bohemia-escura_350ml.png',0,'OpenBeer','350','Bohemia Escura',0,5,10.00,2);
INSERT INTO TB_CERVEJA (`brevedescricao`,`codigo`,`descricao`,`tg_destaque`,`dh_alteracao`,`dh_inclusao`,`imagemcerveja`,`tg_inativo`,`marca`,`ml`,`cerveja`,`quantidade`,`teor`,`vl_total`,`fk_tipocerveja`) VALUES ('Resultado da união da tradicional receita belga.','88662','A Wäls Witte é resultado da união da tradicional receita belga das cervejas de trigo com especiarias diversas, que criam um sabor suave com fundo amargo. Seu aroma é picante, lembrando pimenta jamaicana e sua refrescância é ideal para os dias mais quentes. É uma cerveja medalha de ouro no Bière Beer e prata no Festival Brasileiro da Cerveja.\r\n\r\nINGREDIENTES: Água, malte, trigo, aveia, lúpulo, leveduras.\r\nALERGÊNICOS: Contém cevada, trigo e glúten.','0',NULL,'2019-06-07 14:58:46','wals_belgian-witte_600ml.png',0,'OpenBeer','600','Wäls Belgian Witte',0,5,26.00,2);

INSERT INTO TS_LOGIN(EMAIL, SENHA, TG_INATIVO, DH_INCLUSAO)
VALUES('cliente@sandbox.pagseguro.com.br','$2a$10$zQ.u0Ca8pqnWGorUtNLLU.KvgTvrYW77OWpNeru/ArNp22T/rZEnS',0,NOW());

INSERT INTO TB_CLIENTE(CPF, NOME, DT_NASCIMENTO, TG_SEXO, TELEFONE, FK_LOGIN, TG_INATIVO, DH_INCLUSAO)
VALUES('39912182081', 'Cliente Teste', 1996-01-06, 'M','1195555-7777',2,0,NOW());

INSERT INTO TB_ENDERECO(BAIRRO, CEP, CIDADE, COMPLEMENTO, ESTADO, LOGRADOURO, NUMERO, FK_CLIENTE, DH_INCLUSAO, TG_INATIVO)
VALUES("Jardim Internet", "99999999", "Cidade Exemplo", "99o andar", "SP", "Av. Senac", "9999", 2, NOW(), 0);

INSERT INTO TS_LOGIN_PERMISSAOACESSO(LOGIN_PK_ID, PERMISSAO_ACESSO_PK_ID)
VALUES(2, 1);

