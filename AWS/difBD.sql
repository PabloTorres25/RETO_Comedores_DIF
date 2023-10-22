create database DIF;

use DIF;

DROP TABLE IF EXISTS beneficiario;
DROP TABLE IF EXISTS condicion;
DROP TABLE IF EXISTS beneficiarioCondicion;
DROP TABLE IF EXISTS familia;
DROP TABLE IF EXISTS familiaBeneficiario;
DROP TABLE IF EXISTS ingrediente;
DROP TABLE IF EXISTS comedor;
DROP TABLE IF EXISTS asistencia;
DROP TABLE IF EXISTS voluntario;
DROP TABLE IF EXISTS personalComedor;
DROP TABLE IF EXISTS tipoAviso;
DROP TABLE IF EXISTS aviso;
DROP TABLE IF EXISTS administrador;
DROP TABLE IF EXISTS lote;

CREATE TABLE beneficiario (   
  idBeneficiario    INT NOT NULL AUTO_INCREMENT,
  nombre            varchar(50),
  curp              varchar(18),
  fechaNacimiento   DATE DEFAULT NULL,
  sexo              varchar(6) DEFAULT NULL,
  calle             varchar(50) DEFAULT NULL,
  colonia           varchar(50) DEFAULT NULL,
  municipio         varchar(50) DEFAULT NULL,
  PRIMARY KEY (idBeneficiario, curp)); 

CREATE TABLE condicion (
  idCondicion       INT NOT NULL AUTO_INCREMENT,
  nombreCondicion   varchar(30),
  PRIMARY KEY (idCondicion)
);

INSERT INTO condicion (nombreCondicion) VALUES ('Embarazada');
INSERT INTO condicion (nombreCondicion) VALUES ('Discapacidad Física');
INSERT INTO condicion (nombreCondicion) VALUES ('Discapacidad Intelectual');
INSERT INTO condicion (nombreCondicion) VALUES ('Discapacidad Visual');
INSERT INTO condicion (nombreCondicion) VALUES ('Discapacidad Auditiva');
INSERT INTO condicion (nombreCondicion) VALUES ('Inmigrante');
INSERT INTO condicion (nombreCondicion) VALUES ('Analfabeta');
INSERT INTO condicion (nombreCondicion) VALUES ('Tercera Edad');
INSERT INTO condicion (nombreCondicion) VALUES ('Otro');


CREATE TABLE beneficiarioCondicion (
  idBeneficiario    INT NOT NULL,
  idCondicion       INT NOT NULL,
  PRIMARY KEY (idBeneficiario, idCondicion),
  CONSTRAINT `fk_condicion_beneficiario` FOREIGN KEY (`idBeneficiario`) REFERENCES `beneficiario` (`idBeneficiario`) ON DELETE CASCADE,
  CONSTRAINT `fk_condicion_cond` FOREIGN KEY (`idCondicion`) REFERENCES `condicion` (`idCondicion`) ON DELETE CASCADE
);

CREATE TABLE familia (   
  idFamilia         INT NOT NULL AUTO_INCREMENT,
  nombre            varchar(75) DEFAULT NULL UNIQUE,
  PRIMARY KEY (idFamilia)
);

CREATE TABLE familiaBeneficiario (   
  idFamilia         INT,
  idBeneficiario    INT,
  PRIMARY KEY (idFamilia, idBeneficiario),
  CONSTRAINT `fk_familia_beneficiario` FOREIGN KEY (`idBeneficiario`) REFERENCES `beneficiario` (`idBeneficiario`) ON DELETE CASCADE,
  CONSTRAINT `fk_familia` FOREIGN KEY (`idFamilia`) REFERENCES `familia` (`idFamilia`)
);

CREATE TABLE comedor (  
  idComedor               INT NOT NULL AUTO_INCREMENT,
  nombre                  varchar(150) DEFAULT NULL,
  contrasena              varchar(20) DEFAULT NULL,
  calle                   varchar(100) DEFAULT NULL,
  colonia                 varchar(50) DEFAULT NULL,
  municipio               varchar(75) DEFAULT NULL,
  telefono                varchar(15) DEFAULT NULL,
  nombreRP                varchar(50) DEFAULT NULL,
  telefonoRP              varchar(15) DEFAULT NULL,
  PRIMARY KEY (idComedor, nombre)
);
  
CREATE TABLE ingrediente(
idIngrediente     INT NOT NULL AUTO_INCREMENT,
nombre            varchar(30) DEFAULT NULL,
PRIMARY KEY (idIngrediente));

INSERT INTO ingrediente (nombre) VALUES ('Jitomate');
INSERT INTO ingrediente (nombre) VALUES ('Cebolla');
INSERT INTO ingrediente (nombre) VALUES ('Chile');
INSERT INTO ingrediente (nombre) VALUES ('Ajo');
INSERT INTO ingrediente (nombre) VALUES ('Maíz');
INSERT INTO ingrediente (nombre) VALUES ('Frijol');
INSERT INTO ingrediente (nombre) VALUES ('Aguacate');
INSERT INTO ingrediente (nombre) VALUES ('Calabaza');
INSERT INTO ingrediente (nombre) VALUES ('Chocolate');
INSERT INTO ingrediente (nombre) VALUES ('Nopal');
INSERT INTO ingrediente (nombre) VALUES ('Pollo');
INSERT INTO ingrediente (nombre) VALUES ('Lechuga');
INSERT INTO ingrediente (nombre) VALUES ('Carne');
INSERT INTO ingrediente (nombre) VALUES ('Leche');
INSERT INTO ingrediente (nombre) VALUES ('Queso');
INSERT INTO ingrediente (nombre) VALUES ('Tortilla');
INSERT INTO ingrediente (nombre) VALUES ('Papa');
INSERT INTO ingrediente (nombre) VALUES ('Plátano');
  
CREATE TABLE lote (
  idComedor               INT, 
  idIngrediente           INT,
  caducidad               DATE NOT NULL,
  cantidad                INT DEFAULT NULL,
  tipoCantidad            varchar(4) DEFAULT NULL,
  estado                  varchar (10) DEFAULT 'Activo',
  PRIMARY KEY (idComedor, idIngrediente, caducidad),
  CONSTRAINT `fk_inv_comedor` FOREIGN KEY (`idComedor`) REFERENCES `comedor` (`idComedor`) ON DELETE CASCADE,
  CONSTRAINT `fk_ingrediente` FOREIGN KEY (`idIngrediente`) REFERENCES `ingrediente` (`idIngrediente`)
);

CREATE TABLE asistencia (   
  idAsistencia            INT NOT NULL AUTO_INCREMENT,
  idComedor               INT,
  idBeneficiario          INT,
  fecha                   DATETIME DEFAULT NULL,
  comidaPagada            varchar(1) DEFAULT NULL,
  presente                varchar(1) DEFAULT NULL, 
  PRIMARY KEY (idAsistencia, idComedor, idBeneficiario),
  CONSTRAINT `fk_comedor` FOREIGN KEY (`idComedor`) REFERENCES `comedor` (`idComedor`) ON DELETE CASCADE,
  CONSTRAINT `fk_beneficiario` FOREIGN KEY (`idBeneficiario`) REFERENCES `beneficiario` (`idBeneficiario`));

CREATE TABLE voluntario (  
  idVoluntario            INT NOT NULL AUTO_INCREMENT,
  nombre                  varchar(50) DEFAULT NULL, 
  fechaNacimiento         DATE DEFAULT NULL, 
  telefono                varchar(15) DEFAULT NULL,
  PRIMARY KEY (idVoluntario),
  UNIQUE KEY `unique_nombre` (`nombre`));

CREATE TABLE personalComedor (  
  idComedor               INT NOT NULL,
  idVoluntario            INT NOT NULL,
  rol                     varchar(20) DEFAULT NULL,
  PRIMARY KEY (idComedor, idVoluntario),
  CONSTRAINT `fk_comedorPersonal` FOREIGN KEY (`idComedor`) REFERENCES `comedor` (`idComedor`) ON DELETE CASCADE,
  CONSTRAINT `fk_voluntario` FOREIGN KEY (`idVoluntario`) REFERENCES `voluntario` (`idVoluntario`));


CREATE TABLE tipoAviso (  
  idTipoAviso     INT NOT NULL AUTO_INCREMENT,
  nombreTipoAviso       varchar(30) DEFAULT NULL,
  PRIMARY KEY (idTipoAviso));
  
INSERT INTO tipoAviso (nombreTipoAviso) VALUES ('Falta de servicios');
INSERT INTO tipoAviso (nombreTipoAviso) VALUES ('Comedor cerrado hoy');
INSERT INTO tipoAviso (nombreTipoAviso) VALUES ('Falta de alimentos');
INSERT INTO tipoAviso (nombreTipoAviso) VALUES ('Fallas de equipo de trabajo');
INSERT INTO tipoAviso (nombreTipoAviso) VALUES ('Falta de personal');
INSERT INTO tipoAviso (nombreTipoAviso) VALUES ('Otro');

CREATE TABLE aviso (   
  idAviso       INT NOT NULL AUTO_INCREMENT,
  idComedor     INT,
  idTipoAviso   INT,
  fecha         DATETIME DEFAULT NULL,
  descripcion   varchar(225),
  estado        varchar(15) DEFAULT 'Pendiente',
  PRIMARY KEY (idAviso),
  CONSTRAINT `fk_comedorAviso` FOREIGN KEY (`idComedor`) REFERENCES `comedor` (`idComedor`) ON DELETE CASCADE ,
  CONSTRAINT `fk_tipoAviso` FOREIGN KEY (`idTipoAviso`) REFERENCES `tipoAviso` (`idTipoAviso`));
  
  
CREATE TABLE administrador (
  idAdministrador     INT NOT NULL AUTO_INCREMENT,
  usuario             varchar(50),
  contrasena          varchar(20),
  nombre              varchar(50),
  rol                 varchar(15),
  PRIMARY KEY (idAdministrador),
  UNIQUE KEY `unique_usuario` (`usuario`));
  
  
  
DELIMITER //

CREATE PROCEDURE BorrarVoluntarios(IN idVol INT, IN idCom INT)
BEGIN
    DECLARE cuentaVol INT;
    
    -- Get the count from table2
    SELECT COUNT(*) INTO cuentaVol FROM personalComedor where idVoluntario = idVol;
    
    IF cuentaVol > 1 THEN
        -- Delete from table1 based on count in table2
        DELETE FROM personalComedor WHERE idComedor = idCom and idVoluntario = idVol;
    ELSEIF cuentaVol = 1 THEN
        -- Delete from both tables if count in table2 is 1
        DELETE FROM personalComedor WHERE idComedor = idCom and idVoluntario =idVol;
        DELETE FROM voluntario WHERE idVoluntario = idVol;
    END IF;
END //

DELIMITER ;

