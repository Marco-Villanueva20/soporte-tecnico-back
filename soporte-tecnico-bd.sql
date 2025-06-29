CREATE DATABASE IF NOT EXISTS soportetecnico;

-- Usar la base de datos recién creada
USE soportetecnico;

-- Tabla `usuarios`
CREATE TABLE usuarios(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
	rol ENUM('ADMIN', 'CLIENTE', 'TECNICO') NOT NULL,
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP
);



-- Tabla `ticket`
CREATE TABLE ticket (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    descripcion TEXT NOT NULL,
    estado ENUM('ABIERTO','EN PROCESOR','RESUELTO','CERRADO') NOT NULL,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    
    cliente_id BIGINT NOT NULL,
    CONSTRAINT fk_ticket_cliente FOREIGN KEY (cliente_id)
        REFERENCES usuarios(id) ON UPDATE CASCADE ON DELETE RESTRICT,
    
    trabajador_id BIGINT,
    CONSTRAINT fk_ticket_trabajador FOREIGN KEY (trabajador_id)
        REFERENCES usuarios(id) ON UPDATE CASCADE ON DELETE SET NULL
);

-- Tabla `chat`
CREATE TABLE chat (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    mensaje TEXT NOT NULL,
    fecha_mensaje DATETIME DEFAULT CURRENT_TIMESTAMP,
    ticket_id BIGINT NOT NULL,
    CONSTRAINT fk_chat_ticket
        FOREIGN KEY (ticket_id)
        REFERENCES ticket(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    usuario_id BIGINT NOT NULL,
    CONSTRAINT fk_chat_usuario
        FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

-- Tabla `preg_frecuentes`
CREATE TABLE preg_frecuentes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) UNIQUE NOT NULL,
    usuario_id BIGINT NOT NULL,
    CONSTRAINT fk_preg_frecuentes_usuario
        FOREIGN KEY (usuario_id)
        REFERENCES usuarios(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

-- Tabla `det_preg_frecuentes`
CREATE TABLE det_preg_frecuentes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    descripcion TEXT NOT NULL,
    preg_frec_id BIGINT NOT NULL,
    CONSTRAINT fk_det_preg_frecuentes_preg_frec
        FOREIGN KEY (preg_frec_id)
        REFERENCES preg_frecuentes(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

-- Ajuste de codificación de la base de datos
ALTER DATABASE soportetecnico CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;


