CREATE SCHEMA banking_system_new;
CREATE SCHEMA banking_system_new_test;

USE banking_system_new;
USE banking_system_new_test;

DROP TABLE IF EXISTS user;

CREATE TABLE user(
id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
username VARCHAR(255) NOT NULL UNIQUE,
password VARCHAR(255),
password_date DATE NOT NULL,
role VARCHAR(255) NOT NULL
);

INSERT INTO user (username, password, password_date, role)  VALUES
	('AdminTest', '$2a$10$kSHH79NEXWTMdbQlAFxRUe.CVACVu5rfqOLWuzDWpRE6F7ig7OoBW','2021-09-23','ADMIN'),
	('INewton', '$2a$10$kSHH79NEXWTMdbQlAFxRUe.CVACVu5rfqOLWuzDWpRE6F7ig7OoBW','2021-09-23','ACCOUNTHOLDER'),
    ('MCurie', '$2a$10$kSHH79NEXWTMdbQlAFxRUe.CVACVu5rfqOLWuzDWpRE6F7ig7OoBW','2021-09-23','ACCOUNTHOLDER'),
    ('AEinstein', '$2a$10$kSHH79NEXWTMdbQlAFxRUe.CVACVu5rfqOLWuzDWpRE6F7ig7OoBW','2021-09-23','ACCOUNTHOLDER'),
    ('LPasteur', '$2a$10$kSHH79NEXWTMdbQlAFxRUe.CVACVu5rfqOLWuzDWpRE6F7ig7OoBW','2021-09-23','ACCOUNTHOLDER'),
    ('GGalilei', '$2a$10$kSHH79NEXWTMdbQlAFxRUe.CVACVu5rfqOLWuzDWpRE6F7ig7OoBW','2021-09-23','ACCOUNTHOLDER'),
    ('CDarwin', '$2a$10$kSHH79NEXWTMdbQlAFxRUe.CVACVu5rfqOLWuzDWpRE6F7ig7OoBW','2021-09-23','ACCOUNTHOLDER'),
    ('NCopernico', '$2a$10$kSHH79NEXWTMdbQlAFxRUe.CVACVu5rfqOLWuzDWpRE6F7ig7OoBW','2021-09-23','ACCOUNTHOLDER'),
    ('MFaraday', '$2a$10$kSHH79NEXWTMdbQlAFxRUe.CVACVu5rfqOLWuzDWpRE6F7ig7OoBW','2021-09-23','ACCOUNTHOLDER'),
    ('AFleming', '$2a$10$kSHH79NEXWTMdbQlAFxRUe.CVACVu5rfqOLWuzDWpRE6F7ig7OoBW','2021-09-23','ACCOUNTHOLDER'),
    ('LBeethoven', '$2a$10$kSHH79NEXWTMdbQlAFxRUe.CVACVu5rfqOLWuzDWpRE6F7ig7OoBW','2021-09-23','ACCOUNTHOLDER'),
    ('Amazon', '$2a$10$kSHH79NEXWTMdbQlAFxRUe.CVACVu5rfqOLWuzDWpRE6F7ig7OoBW','2021-09-23','THIRDPARTY'),
    ('Apple', '$2a$10$kSHH79NEXWTMdbQlAFxRUe.CVACVu5rfqOLWuzDWpRE6F7ig7OoBW','2021-09-23','THIRDPARTY'),
    ('BSantander', '$2a$10$kSHH79NEXWTMdbQlAFxRUe.CVACVu5rfqOLWuzDWpRE6F7ig7OoBW','2021-09-23','THIRDPARTY')
;

DROP TABLE IF EXISTS account_holder;

CREATE TABLE account_holder(
id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
user_id BIGINT NOT NULL,
name VARCHAR(255),
nif VARCHAR(10),
date_of_birth DATE,
street VARCHAR(255),
city VARCHAR(255),
country VARCHAR(255),
postal_code INT,
mailing_address VARCHAR(255),
FOREIGN KEY (user_id) REFERENCES user(id)
);

INSERT INTO account_holder (user_id, name, nif, date_of_birth, street, city, country, postal_code, mailing_address)  VALUES
	(2,'Isaac Newton', '00000002Z','1975-09-23','Gravity Street', 'London', 'United Kingdom', 20505,'isaacnewton@gmail.com'),
    (3,'Marie Curie', '00000003Z','1985-10-15','Radius Street', 'Varsovia', 'Poland', 30949,'mariecurie@gmail.com'),
    (4,'Albert Einstein', '00000004Z','1966-03-12','Realativity Street', 'Ulm','Germany', 16496,'alberteinstein@gmail.com'),
    (5,'Louis Pasteur', '00000005Z','2000-11-23','Vaccine Street', 'Dole','France', 91616,'louispasteur@gmail.com'),
    (6,'Galileo Galilei', '00000006Z','1982-09-23','Astronomy Street', 'Pisa','Italy', 41518,'galileogalilei@gmail.com'),
    (7,'Charles Darwin', '00000007Z','1985-05-21','Evolution Street', 'Shrewsbury','United Kingdom', 64813,'charlesdarwin@gmail.com'),
    (8,'Nicolás Copérnico', '00000008Z','2005-06-23','Heliocentrism Street', 'Torun','Poland', 70693,'nicolascopernico@gmail.com'),
    (9,'Michael Faraday', '00000009Z','1956-09-07','Electromagnetism Street', 'Newington','United Kingdom', 16516,'michaelfaraday@gmail.com'),
    (10,'Alexander Fleming', '000000010Z','1977-09-23','Medicine Street', 'Darve','United Kingdom', 20564,'alexanderfleming@gmail.com'),
    (11,'Ludwig van Beethoven', '000000011Z','1999-02-23','Music Street', 'Bonn','Germany', 22654,'ludwingvanbethoven@gmail.com')
;

DROP TABLE IF EXISTS admin;

CREATE TABLE admin(
id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
user_id BIGINT NOT NULL,
name VARCHAR(255),
FOREIGN KEY (user_id) REFERENCES user(id)
);

INSERT INTO admin (user_id, name)  VALUES
	(1,'admin')
;

DROP TABLE IF EXISTS third_party;

CREATE TABLE third_party(
id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
user_id BIGINT NOT NULL,
name VARCHAR(255),
hashed_key VARCHAR(25),
FOREIGN KEY (user_id) REFERENCES user(id)
);

INSERT INTO third_party (user_id, name, hashed_key)  VALUES
	(12,'Amazon', 'amazon_123'),
    (13,'Apple', 'apple_123'),
    (14,'Banco Santander', 'bsantander_123')
;

DROP TABLE IF EXISTS accounts;

CREATE TABLE accounts(
id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
entity_number VARCHAR(5),
branch_number VARCHAR(5),
account_number VARCHAR(25),
primary_owner_id BIGINT,
secondary_owner_id BIGINT,
balance DECIMAL,
currency VARCHAR(5),
penalty_fee DECIMAL,
account_type VARCHAR(255),
FOREIGN KEY (primary_owner_id) REFERENCES account_holder (user_id),
FOREIGN KEY (secondary_owner_id) REFERENCES account_holder (user_id)
);

INSERT INTO accounts (entity_number, branch_number, account_number, primary_owner_id, secondary_owner_id, balance, currency, penalty_fee, account_type)  VALUES
('0049','2036','004920361',2,3,10500,'USD',40,'CHECKING'),
('0049','3535','004935352',3,2,2798,'USD',40,'CHECKING'),
('0049','4643','004946433',4,NULL,78,'USD',40,'CHECKING'),
('0049','2562','004925624',5,NULL,1500,'USD',40,'STUDENT_CHECKING'),
('0049','3625','004936255',6,7,15200,'USD',40,'CHECKING'),
('0049','5416','004954166',7,6,1200,'USD',40,'CHECKING'),
('0049','1635','004916357',8,NULL,12000,'USD',40,'STUDENT_CHECKING'),
('0049','6161','004961618',9,NULL,2500,'USD',40,'CHECKING'),
('0049','6161','004961619',10,NULL,3500,'USD',40,'CHECKING'),
('0049','6161','0049616110',11,NULL,3580,'USD',40,'STUDENT_CHECKING'),
('0049','2036','0049203611',2,NULL,20000,'USD',40,'SAVING'),
('0049','1616','0049161612',3,NULL,9704,'USD',40,'SAVING'),
('0049','6166','0049616613',5,NULL,5000,'USD',40,'SAVING'),
('0049','4616','0049461614',10,NULL,7500,'USD',40,'SAVING'),
('0049','2036','0049203615',2,NULL,2500,'USD',40,'CREDIT_CARD'),
('0049','6196','0049619616',6,NULL,1500,'USD',40,'CREDIT_CARD')
;


DROP TABLE IF EXISTS checking_account;

CREATE TABLE checking_account(
id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
account_id BIGINT,
secret_key VARCHAR(255),
minimum_balance DECIMAL,
monthly_maintenance_fee DECIMAL,
creation_date DATE,
status VARCHAR(255),
last_monthly_fee_date DATE,
FOREIGN KEY (account_id) REFERENCES accounts (id)
);

INSERT INTO checking_account (account_id, secret_key, minimum_balance, monthly_maintenance_fee, creation_date, status, last_monthly_fee_date)  VALUES
(1,'abc123',250,12,'2020-09-23','ACTIVE','2021-09-23'),
(2,'abc123',250,12,'2019-09-23','ACTIVE','2021-07-23'),
(3,'abc123',250,12,'2020-09-23','ACTIVE','2021-09-23'),
(5,'abc123',250,12,'2021-09-23','ACTIVE','2021-09-23'),
(6,'abc123',250,12,'2018-09-23','ACTIVE','2021-09-23'),
(8,'abc123',250,12,'2021-09-23','ACTIVE','2021-09-23'),
(9,'abc123',250,12,'2021-09-23','ACTIVE','2021-06-23')
;


DROP TABLE IF EXISTS student_checking_account;

CREATE TABLE student_checking_account(
id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
account_id BIGINT,
secret_key VARCHAR(255),
creation_date DATE,
status VARCHAR(255)
);

INSERT INTO student_checking_account (account_id, secret_key, creation_date, status)  VALUES
(4,'abc123','2020-09-23','ACTIVE'),
(7,'abc123','2019-09-23','ACTIVE'),
(10,'abc123','2020-09-23','ACTIVE')
;

DROP TABLE IF EXISTS saving_account;

CREATE TABLE saving_account(
id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
account_id BIGINT,
secret_key VARCHAR(255),
minimum_balance DECIMAL,
creation_date DATE,
status VARCHAR(255),
interest_rate FLOAT,
last_interest_rate_date DATE
);

INSERT INTO saving_account (account_id, secret_key, minimum_balance, creation_date, status, interest_rate, last_interest_rate_date)  VALUES
(11,'abc123',1000,'2020-09-23','ACTIVE',0.0025,'2021-09-23'),
(12,'abc123',500,'2019-08-23','ACTIVE',0.0025,'2020-09-22'),
(13,'abc123',500,'2020-07-23','ACTIVE',0.0025,'2021-01-23'),
(14,'abc123',100,'2021-09-23','ACTIVE',0.0025,'2021-09-23')
;

DROP TABLE IF EXISTS credit_card_account;

CREATE TABLE credit_card_account(
id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
account_id BIGINT,
credit_limit DECIMAL,
interest_rate FLOAT,
last_interest_rate_date DATE
);

INSERT INTO credit_card_account (account_id, credit_limit, interest_rate, last_interest_rate_date)  VALUES
(15,3000,0.2,'2021-09-23'),
(16,2000,0.2,'2020-12-15')
;

DROP TABLE IF EXISTS transactions;

CREATE TABLE transactions(
id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
origin_account_id BIGINT,
destination_account_id BIGINT,
paymaster_id BIGINT,
receiver_id BIGINT,
amount DECIMAL,
currency VARCHAR(5),
time_stamp TIMESTAMP,
FOREIGN KEY (origin_account_id) REFERENCES accounts (id),
FOREIGN KEY (destination_account_id) REFERENCES accounts (id),
FOREIGN KEY (paymaster_id) REFERENCES user (id),
FOREIGN KEY (receiver_id) REFERENCES user (id)
);

INSERT INTO transactions (origin_account_id, destination_account_id, paymaster_id, receiver_id, amount, currency, time_stamp)  VALUES
(3,2,4,3,100,'USD','2021-09-18 14:57:16'),
(3,2,4,3,100,'USD','2021-09-18 14:57:16'),
(3,2,4,3,100,'USD','2021-09-18 14:57:16'),
(3,2,4,3,100,'USD','2021-09-18 14:57:16'),
(3,2,4,3,100,'USD','2021-09-18 14:57:16'),
(2,12,3,3,100,'USD','2021-08-23 14:57:16'),
(2,12,3,3,100,'USD','2021-07-20 14:57:16'),
(2,12,3,3,100,'USD','2021-09-23 14:57:16'),
(2,12,3,3,100,'USD','2021-09-23 14:57:16'),
(3,2,4,3,100,'USD','2021-09-23 14:57:16'),
(3,2,4,3,100,'USD','2021-01-23 14:57:16'),
(3,2,4,3,100,'USD','2020-09-23 14:57:16'),
(3,2,4,3,100,'USD','2020-09-23 14:57:16'),
(3,2,4,3,100,'USD','2021-09-01 14:57:16'),
(3,2,4,3,100,'USD','2021-09-02 14:57:16'),
(3,2,4,3,100,'USD','2020-09-23 14:57:16'),
(3,2,4,3,100,'USD','2021-09-15 14:57:16'),
(3,2,4,3,100,'USD','2021-09-23 14:57:16'),
(3,2,4,3,100,'USD','2021-09-14 14:57:16'),
(3,2,4,3,100,'USD','2020-09-23 14:57:16'),
(3,2,4,3,100,'USD','2021-09-23 14:57:16'),
(3,2,4,3,100,'USD','2021-09-23 14:57:16')
;

SELECT * FROM user;
SELECT * FROM accounts;
SELECT * FROM checking_account;
SELECT * FROM student_checking_account;
SELECT * FROM saving_account;
SELECT * FROM credit_card_account;
SELECT * FROM transactions;


