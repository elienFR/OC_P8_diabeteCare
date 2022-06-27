-- Create database
create database if not exists diabetecare;

use diabetecare;

-- Dropping all tables first
drop table if exists users;

CREATE TABLE diabetecare.patients (
	id int auto_increment NOT NULL,
	lastname varchar(100) NOT NULL,
	firstname varchar(100) NOT NULL,
	gender varchar(4) NOT NULL,
	dob DATE NOT NULL,
	address varchar(255) NOT NULL,
	phone varchar(100) NOT NULL,
	CONSTRAINT patients_pk PRIMARY KEY (id),
	CONSTRAINT patients_un UNIQUE KEY (lastname,firstname,dob)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci;
