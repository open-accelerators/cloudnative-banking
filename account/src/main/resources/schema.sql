DROP TABLE IF EXISTS account;
DROP TABLE IF EXISTS customer_account_xref;

CREATE TABLE account (
  accountId VARCHAR(8) NOT NULL PRIMARY KEY,
  type VARCHAR(24),
  description VARCHAR(30),
  balance NUMERIC(10,2),
  creditLine NUMERIC(10,2),
  beginBalance NUMERIC(10,2),
  beginBalanceTimestamp TIMESTAMP
);

INSERT INTO account VALUES('5005','Money Market','Hi Balance',3300.00,0.00,3500.00,'2003-07-28 23:03:20.000000000');
INSERT INTO account VALUES('5006','Checking','Checking',2458.32,0.00,66.54,'2003-07-21 03:12:00.000000000');
INSERT INTO account VALUES('5007','Credit','Visa',220.03,5000.00,166.08,'2003-07-23 10:13:54.000000000');
INSERT INTO account VALUES('5008','Savings','Super Interest Account',59601.35,0.00,5433.89,'2003-07-15 12:55:33.000000000');
