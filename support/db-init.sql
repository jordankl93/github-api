CREATE DATABASE IF NOT EXISTS `github`;

USE `github`;

CREATE TABLE IF NOT EXISTS file (
   id INT AUTO_INCREMENT,
   name VARCHAR(50) NOT NULL,
   extension VARCHAR(30),
   number_lines BIGINT,
   size DECIMAL(16,2),
   url_base VARCHAR(200) NOT NULL,
   PRIMARY KEY (id)   
) ENGINE=InnoDB;

CREATE INDEX index_url_base ON file (url_base);

