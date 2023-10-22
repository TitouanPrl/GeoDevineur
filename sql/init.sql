CREATE TABLE REGION
(
  ID INT PRIMARY KEY,
  Region VARCHAR(55),
  Habitants INT(55),
  Superficie FLOAT(55),
  Position VARCHAR(2),
  Cotier BOOLEAN,
  Voisins INT(2),
  Politique VARCHAR(55)
) 
AS 
SELECT * 
FROM CSVREAD('../csv/regions.csv');

CREATE TABLE DEPARTEMENT
(
  ID INT PRIMARY KEY,
  Nom VARCHAR(55),
  Numero INT(2),
  Habitants INT(55),
  Superficie FLOAT(55),
  Position VARCHAR(2),
  Cotier BOOLEAN,
  Voisins INT(2),
  Politique VARCHAR(55)
) 
AS 
SELECT * 
FROM CSVREAD('../csv/regions.csv');

CREATE TABLE SCORE
(
    ID INT PRIMARY KEY,
    PSEUDO VARCHAR(55),
    TEMPS TIME
)