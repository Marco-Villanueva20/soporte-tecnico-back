CREATE DATABASE IF NOT EXISTS soporte_tecnico;

-- Usar la base de datos recién creada
USE soporte_tecnico;

-- Tabla `usuarios`
CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
	roles ENUM('ADMIN', 'CLIENTE', 'TECNICO') NOT NULL,
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP
);


-- Tabla `estado_ticket`
CREATE TABLE estado_ticket (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) UNIQUE NOT NULL
);

-- Insertar los estados iniciales
INSERT INTO estado_ticket (nombre) VALUES
('Abierto'),
('En Proceso'),
('Resuelto'),
('Cerrado'),
('Pendiente de Cliente');

-- Tabla `ticket`
CREATE TABLE ticket (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    descripcion TEXT NOT NULL,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    
    estado_id BIGINT NOT NULL,
    CONSTRAINT fk_ticket_estado FOREIGN KEY (estado_id)
        REFERENCES estado_ticket(id) ON UPDATE CASCADE ON DELETE RESTRICT,
    
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
ALTER DATABASE soporte_tecnico CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;


