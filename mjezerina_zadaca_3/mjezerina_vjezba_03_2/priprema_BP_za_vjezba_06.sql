# Za ostale grupe treba promijeniti g1 u gi (i=2,4)

CREATE DATABASE nwtis_g1;
CREATE USER nwtis_g1; 
CREATE USER nwtis_admin; 
SET PASSWORD FOR 'nwtis_g1'@'%' = PASSWORD('g1_nwtis');
SET PASSWORD FOR 'nwtis_admin'@'%' = PASSWORD('nwtis_foi');
GRANT SELECT, INSERT, UPDATE, DELETE ON nwtis_g1.* TO 'nwtis_g1'@'%' IDENTIFIED BY 'g1_nwtis';
GRANT ALL ON nwtis_g1.* TO 'nwtis_admin'@'%' IDENTIFIED BY 'nwtis_foi';
