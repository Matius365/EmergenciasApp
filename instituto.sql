create database instituto;
use instituto;

CREATE TABLE Alumnos (
num	    	INT not null,
nombre  	VARCHAR(30) not null,
fnac		DATE not null,
media   	DECIMAL(4,2) not null,
curso		VARCHAR(2) not null,
CONSTRAINT pk_alumnos PRIMARY KEY(num));

INSERT INTO Alumnos (num, nombre, fnac, media, curso) VALUES
(1000, 'David Martínez', '1999-07-28', 5.1, '1A'),
(1001, 'Jorge Carlos De la Rosa', '1989-05-11', 4.5, '2A'),
(1002, 'Cristóbal Güiza', '1995-12-01', 3.2, '1B'),
(1003, 'Blanca Quevedo', '2000-03-30', 7.6, '1A'),
(1004, 'Tania Sanchís', '1980-09-27', 8.7, '1B'),
(1005, 'Manuel Viñas', '1991-03-14', 6.5, '1B'),
(1006, 'Julián Muñoz', '2003-11-19', 9.0, '2B'),
(1007, 'Jesús Gil', '1996-05-08', 6.2, '1A'),
(1008, 'Alfonso Arús', '2004-07-26', 7.5, '1B'),
(1009, 'Rita Cárdenas', '2002-06-13', 3.3, '2A');


