-- ====================================================
-- Initialization database script
-- executed in RDS MYSQL after created the database
-- ====================================================

-- Create database (if not exists)
CREATE DATABASE IF NOT EXISTS person_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE person_db;
-- Create person table
-- (Note: JPA with ddl-auto=update also create the table, but is recommended you've the script)
CREATE TABLE IF NOT EXISTS persona (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero_identificacion VARCHAR(20) NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    INDEX idx_numero_identificacion (numero_identificacion)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO persona (numero_identificacion, nombre, email) VALUES
  ('12345678', 'John doe', 'john.doe@email.com'),
  ('87654321', 'Angeline smith', 'angeline.smith@email.com')
    ON DUPLICATE KEY UPDATE nombre = VALUES(nombre);