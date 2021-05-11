-- 
-- El contenido de este fichero se cargará al arrancar la aplicación, suponiendo que uses
-- 		application-default ó application-externaldb en modo 'create'
--

-- Unos pocos auto-mensajes de prueba
INSERT INTO MESSAGE VALUES(1,NULL,'2020-03-23 10:48:11.074000','probando 1',1,1);
INSERT INTO MESSAGE VALUES(2,NULL,'2020-03-23 10:48:15.149000','probando 2',1,1);
INSERT INTO MESSAGE VALUES(3,NULL,'2020-03-23 10:48:18.005000','probando 3',1,1);
INSERT INTO MESSAGE VALUES(4,NULL,'2020-03-23 10:48:20.971000','probando 4',1,1);
INSERT INTO MESSAGE VALUES(5,NULL,'2020-03-23 10:48:22.926000','probando 5',1,1);


-- Usuarios de prueba (todas las contraseñas son "aa")
INSERT INTO user(id,enabled,username,password,roles,name,first_name) VALUES (
	1, 1, 'admin', 
	'{bcrypt}$2a$10$xLFtBIXGtYvAbRqM95JhcOaG23fHRpDoZIJrsF2cCff9xEHTTdK1u',
	'USER,ADMIN',
	'Admi', 'Nistrador'
);
INSERT INTO user(id,enabled,username,password,roles,name,first_name) VALUES (
	2, 1, 'juan', 
	'{bcrypt}$2a$10$xLFtBIXGtYvAbRqM95JhcOaG23fHRpDoZIJrsF2cCff9xEHTTdK1u',
	'USER',
	'Juan', 'Pérez'
);
INSERT INTO user(id,enabled,username,password,roles,name,first_name) VALUES (
	3, 1, 'bob', 
	'{bcrypt}$2a$10$xLFtBIXGtYvAbRqM95JhcOaG23fHRpDoZIJrsF2cCff9xEHTTdK1u',
	'USER',
	'Bob', 'Smith'
);
INSERT INTO user(id,enabled,username,password,roles,name,first_name) VALUES (
	4, 1, 'alice', 
	'{bcrypt}$2a$10$xLFtBIXGtYvAbRqM95JhcOaG23fHRpDoZIJrsF2cCff9xEHTTdK1u',
	'USER',
	'Alice', 'Peralta'
);
INSERT INTO user(id,enabled,username,password,roles,name,first_name) VALUES (
	5, 1, 'jhon', 
	'{bcrypt}$2a$10$xLFtBIXGtYvAbRqM95JhcOaG23fHRpDoZIJrsF2cCff9xEHTTdK1u',
	'USER',
	'Jhon', 'Doe'
);
Insert into user(id,enabled,username,password,roles,name,first_name) VALUES (
	6,1, 'javi', '{bcrypt}$2a$10$xLFtBIXGtYvAbRqM95JhcOaG23fHRpDoZIJrsF2cCff9xEHTTdK1u', 'USER', 'javi', 'javi'
);


--Stocks en mercados
insert into symbol(name, updated_on, value) values ('TSLA',CURRENT_DATE,641.5);
insert into symbol(name, updated_on, value) values ('AAPL',CURRENT_DATE,128.25);
insert into symbol(name, updated_on, value) values ('AMZN',CURRENT_DATE,3218.61);
insert into symbol(name, updated_on, value) values ('MSFT',CURRENT_DATE,250.43);
insert into symbol(name, updated_on, value) values ('NIO',CURRENT_DATE,35.56);
insert into symbol(name, updated_on, value) values ('NVDA',CURRENT_DATE,576.48);
insert into symbol(name, updated_on, value) values ('MRNA',CURRENT_DATE,158.61);
insert into symbol(name, updated_on, value) values ('NKLA',CURRENT_DATE,11.43);
insert into symbol(name, updated_on, value) values ('FB',CURRENT_DATE,5.22);
insert into symbol(name, updated_on, value) values ('AMD',CURRENT_DATE,76.88);

insert into symbol(name, updated_on, value) values ('TSLA',DATEADD('DAY',-1, CURRENT_DATE),650.5);
insert into symbol(name, updated_on, value) values ('AAPL',DATEADD('DAY',-1, CURRENT_DATE),100.25);
insert into symbol(name, updated_on, value) values ('AMZN',DATEADD('DAY',-1, CURRENT_DATE),3260.61);
insert into symbol(name, updated_on, value) values ('MSFT',DATEADD('DAY',-1, CURRENT_DATE),230.43);
insert into symbol(name, updated_on, value) values ('NIO',DATEADD('DAY',-1, CURRENT_DATE),70.56);
insert into symbol(name, updated_on, value) values ('NVDA',DATEADD('DAY',-1, CURRENT_DATE),590.48);
insert into symbol(name, updated_on, value) values ('MRNA',DATEADD('DAY',-1, CURRENT_DATE),170.61);
insert into symbol(name, updated_on, value) values ('NKLA',DATEADD('DAY',-1, CURRENT_DATE),15.43);
insert into symbol(name, updated_on, value) values ('FB',DATEADD('DAY',-1, CURRENT_DATE),340.22);
insert into symbol(name, updated_on, value) values ('AMD',DATEADD('DAY',-1, CURRENT_DATE),50.88);

insert into symbol(name, updated_on, value) values ('TSLA',DATEADD('DAY',-2, CURRENT_DATE),611.5);
insert into symbol(name, updated_on, value) values ('AAPL',DATEADD('DAY',-2, CURRENT_DATE),168.25);
insert into symbol(name, updated_on, value) values ('AMZN',DATEADD('DAY',-2, CURRENT_DATE),3288.61);
insert into symbol(name, updated_on, value) values ('MSFT',DATEADD('DAY',-2, CURRENT_DATE),1000.43);
insert into symbol(name, updated_on, value) values ('NIO',DATEADD('DAY',-2, CURRENT_DATE),40.6);
insert into symbol(name, updated_on, value) values ('NVDA',DATEADD('DAY',-2, CURRENT_DATE),570.8);
insert into symbol(name, updated_on, value) values ('MRNA',DATEADD('DAY',-2, CURRENT_DATE),108.1);
insert into symbol(name, updated_on, value) values ('NKLA',DATEADD('DAY',-2, CURRENT_DATE),40.43);
insert into symbol(name, updated_on, value) values ('FB',DATEADD('DAY',-2, CURRENT_DATE),320.22);
insert into symbol(name, updated_on, value) values ('AMD',DATEADD('DAY',-2, CURRENT_DATE),80.88);

insert into symbol(name, updated_on, value) values ('TSLA',DATEADD('DAY',-3, CURRENT_DATE),645.5);
insert into symbol(name, updated_on, value) values ('AAPL',DATEADD('DAY',-3, CURRENT_DATE),124.25);
insert into symbol(name, updated_on, value) values ('AMZN',DATEADD('DAY',-3, CURRENT_DATE),1000.61);
insert into symbol(name, updated_on, value) values ('MSFT',DATEADD('DAY',-3, CURRENT_DATE),10.43);
insert into symbol(name, updated_on, value) values ('NIO',DATEADD('DAY',-3, CURRENT_DATE),20.56);
insert into symbol(name, updated_on, value) values ('NVDA',DATEADD('DAY',-3, CURRENT_DATE),491.48);
insert into symbol(name, updated_on, value) values ('MRNA',DATEADD('DAY',-3, CURRENT_DATE),200.61);
insert into symbol(name, updated_on, value) values ('NKLA',DATEADD('DAY',-3, CURRENT_DATE),20.43);
insert into symbol(name, updated_on, value) values ('FB',DATEADD('DAY',-3, CURRENT_DATE),290.22);
insert into symbol(name, updated_on, value) values ('AMD',DATEADD('DAY',-3, CURRENT_DATE),50.88);

insert into symbol(name, updated_on, value) values ('TSLA',DATEADD('DAY',-4, CURRENT_DATE),640.5);
insert into symbol(name, updated_on, value) values ('AAPL',DATEADD('DAY',-4, CURRENT_DATE),160.25);
insert into symbol(name, updated_on, value) values ('AMZN',DATEADD('DAY',-4, CURRENT_DATE),2980.61);
insert into symbol(name, updated_on, value) values ('MSFT',DATEADD('DAY',-4, CURRENT_DATE),269.43);
insert into symbol(name, updated_on, value) values ('NIO',DATEADD('DAY',-4, CURRENT_DATE),40.56);
insert into symbol(name, updated_on, value) values ('NVDA',DATEADD('DAY',-4, CURRENT_DATE),510.48);
insert into symbol(name, updated_on, value) values ('MRNA',DATEADD('DAY',-4, CURRENT_DATE),180.61);
insert into symbol(name, updated_on, value) values ('NKLA',DATEADD('DAY',-4, CURRENT_DATE),15.43);
insert into symbol(name, updated_on, value) values ('FB',DATEADD('DAY',-4, CURRENT_DATE),301.22);
insert into symbol(name, updated_on, value) values ('AMD',DATEADD('DAY',-4, CURRENT_DATE),80.88);


-- Salas de prueba
INSERT INTO room(id,name, is_public, weekly_cash, max_users, start_balance, cash2win, admin_id) VALUES (1,'Andorra', TRUE, 1000, 10, 3000, 100000, 2);
INSERT INTO room(id,name, is_public, weekly_cash, max_users, start_balance, cash2win, admin_id) VALUES (2,'Me encanta pagar impuestos', TRUE, 1000, 10, 3000, 100000, 4);

-- Stocks de cada sala
INSERT INTO SYMBOL_ROOMS(rooms_id, symbol_id) VALUES (1, 1);
INSERT INTO SYMBOL_ROOMS(rooms_id, symbol_id) VALUES (1, 2);
INSERT INTO SYMBOL_ROOMS(rooms_id, symbol_id) VALUES (1, 3);
INSERT INTO SYMBOL_ROOMS(rooms_id, symbol_id) VALUES (1, 4);
INSERT INTO SYMBOL_ROOMS(rooms_id, symbol_id) VALUES (1, 5);
INSERT INTO SYMBOL_ROOMS(rooms_id, symbol_id) VALUES (2, 6);
INSERT INTO SYMBOL_ROOMS(rooms_id, symbol_id) VALUES (2, 7);
INSERT INTO SYMBOL_ROOMS(rooms_id, symbol_id) VALUES (2, 8);
INSERT INTO SYMBOL_ROOMS(rooms_id, symbol_id) VALUES (2, 9);
INSERT INTO SYMBOL_ROOMS(rooms_id, symbol_id) VALUES (2, 10);

-- Memberships de prueba
INSERT INTO membership VALUES (1, 10000, NULL, 1, 1);
INSERT INTO membership VALUES (2, 20000, NULL, 1, 2);
INSERT INTO membership VALUES (3, 5000, NULL, 1, 3);
INSERT INTO membership VALUES (4, 10000, NULL, 2, 4);
INSERT INTO membership VALUES (5, 40000, NULL, 2, 5);
INSERT INTO membership VALUES (6, 8000, NULL, 2, 1);



