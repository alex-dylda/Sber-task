CREATE SCHEMA IF NOT EXISTS db2;

DROP TABLE IF EXISTS db2.table2;

CREATE TABLE db2.table2 (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  int1 INT NOT NULL,
  float2 FLOAT NOT NULL,
  string3 VARCHAR(250) DEFAULT NULL,
  date4 DATE DEFAULT NULL
);