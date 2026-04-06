CREATE DATABASE Agenda;
USE Agenda;

CREATE TABLE Persona (
id INT NOT NULL,
nombre VARCHAR(10),
apellido VARCHAR(10),
telefono VARCHAR(9),
CONSTRAINT pk_pers PRIMARY KEY(id));

INSERT INTO Persona VALUES 
(1, 'Juan', 'Gómez', '988696969'),
(2, 'María', 'López', '988292929'),
(4, 'Manuel', 'López', '988494949'),
(5, 'María', 'García', '988595959');

SELECT * FROM Persona;
