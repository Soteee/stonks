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
insert into symbol(name, updated_on, value) values ('TSLA',NULL,0.0);
insert into symbol(name, updated_on, value) values ('AAPL',NULL,0.0);
--insert into symbol(name, updated_on, value) values ('AMZN',NULL,0.0);
insert into symbol(name, updated_on, value) values ('MSFT',NULL,0.0);
insert into symbol(name, updated_on, value) values ('NIO',NULL,0.0);
--insert into symbol(name, updated_on, value) values ('NVDIA',NULL,0.0);
--insert into symbol(name, updated_on, value) values ('MRNA',NULL,0.0);
--insert into symbol(name, updated_on, value) values ('NKLA',NULL,0.0);
--insert into symbol(name, updated_on, value) values ('FB',NULL,0.0);
--insert into symbol(name, updated_on, value) values ('AMD',NULL,0.0);


-- Salas de prueba
INSERT INTO room(id,name, is_public, weekly_cash, max_users, start_balance, cash2win, admin_id) VALUES (1,'Andorra', TRUE, 1000, 10, 3000, 100000, 2);
INSERT INTO room(id,name, is_public, weekly_cash, max_users, start_balance, cash2win, admin_id) VALUES (2,'Me encanta pagar impuestos', TRUE, 1000, 10, 3000, 100000, 4);

-- Stocks de cada sala
INSERT INTO ROOM_SYMBOLS(rooms_id, symbols_id) VALUES (1, 1);
INSERT INTO ROOM_SYMBOLS(rooms_id, symbols_id) VALUES (1, 2);
INSERT INTO ROOM_SYMBOLS(rooms_id, symbols_id) VALUES (2, 3);
INSERT INTO ROOM_SYMBOLS(rooms_id, symbols_id) VALUES (2, 4);

-- Memberships de prueba
INSERT INTO membership VALUES (1, 10000, NULL, 1, 1);
INSERT INTO membership VALUES (2, 20000, NULL, 1, 2);
INSERT INTO membership VALUES (3, 5000, NULL, 1, 3);
INSERT INTO membership VALUES (4, 10000, NULL, 2, 4);
INSERT INTO membership VALUES (5, 40000, NULL, 2, 5);
INSERT INTO membership VALUES (6, 8000, NULL, 2, 1);



