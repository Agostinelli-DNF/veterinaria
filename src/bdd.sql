-- bdd.sql
CREATE DATABASE IF NOT EXISTS veterinaria;
USE veterinaria;

-- Tabla PACIENTES (Atributos sacados de  VistaPacientes)
CREATE TABLE pacientes (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           nombre VARCHAR(100) NOT NULL,
                           especie VARCHAR(50) NOT NULL
);

--  Tabla SERVICIOS (Atributos sacados de tu VistaServicios y VistaFacturas)
CREATE TABLE servicios (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           nombre VARCHAR(100) NOT NULL,
                           tipo VARCHAR(50) NOT NULL,
                           precio INT NOT NULL
);

--  Tabla FACTURAS (Combina el Paciente con la fecha y el total)
CREATE TABLE facturas (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          paciente_id INT NOT NULL,
                          fecha DATE NOT NULL,
                          total INT NOT NULL,
    -- Acá aplicamos la clave foránea (Foreign Key) que relaciona con pacientes
                          FOREIGN KEY (paciente_id) REFERENCES pacientes(id)
);

--Tabla intermedia: FACTURA_SERVICIOS
--  Esta tabla guarda "qué servicios" se llevó "qué factura" (la combinación)
CREATE TABLE factura_servicios (
                                   factura_id INT NOT NULL,
                                   servicio_id INT NOT NULL,
                                   PRIMARY KEY (factura_id, servicio_id),
                                   FOREIGN KEY (factura_id) REFERENCES facturas(id),
                                   FOREIGN KEY (servicio_id) REFERENCES servicios(id)
);