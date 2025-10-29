DROP DATABASE IF EXISTS Airsoft;
create database Airsoft;
use Airsoft;

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS usuarios;
DROP TABLE IF EXISTS campos;
DROP TABLE IF EXISTS eventos;
DROP TABLE IF EXISTS reservas;

SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE usuarios (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nickname varchar(50) NOT NULL UNIQUE,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    Clave VARCHAR(255) NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP, 
    ultima_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE,
    es_admin BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE campos (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    ciudad VARCHAR(100) NOT NULL,
    calle VARCHAR(100) NOT NULL,
    codigo_postal VARCHAR(10) NOT NULL,
    numero_telefono VARCHAR(15) NOT NULL,
    comunidad_autonoma VARCHAR(100) NOT NULL,
    id_admin INT NOT NULL,
    FOREIGN KEY (id_admin) REFERENCES usuarios(id)
);

CREATE TABLE eventos (
    Id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    id_campo INT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    fecha_hora DATETIME NOT NULL,
    numero_plazas INT NOT NULL,
    numero_plazas_restantes INT NOT NULL,
    descripcion TEXT NOT NULL,
    cancelado BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (id_campo) REFERENCES campos(id)
);

CREATE TABLE reservas (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    id_evento INT NOT NULL,
    id_usuario INT NOT NULL,
    numero_personas INT NOT NULL,
    fecha_hora_reserva DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    estado ENUM('confirmada', 'cancelada') NOT NULL DEFAULT 'confirmada',
    FOREIGN KEY (id_evento) REFERENCES eventos(id),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id)
);


-- Insertar datos en usuarios (incluyendo administradores con es_admin = TRUE)
INSERT INTO usuarios (nickname, nombre, apellido, email, Clave, es_admin) VALUES
('juanp', 'Juan', 'Pérez', 'juan.perez@correo.com', 'hashed_password_1', TRUE);
INSERT INTO usuarios (nickname, nombre, apellido, email, Clave, es_admin) VALUES
('mariag', 'María', 'Gómez', 'maria.gomez@correo.com', 'hashed_password_2', FALSE);
INSERT INTO usuarios (nickname, nombre, apellido, email, Clave, es_admin) VALUES
('carlosl', 'Carlos', 'López', 'carlos.lopez@correo.com', 'hashed_password_3', FALSE);
INSERT INTO usuarios (nickname, nombre, apellido, email, Clave, es_admin) VALUES
('anar', 'Ana', 'Rodríguez', 'ana.rodriguez@correo.com', 'hashed_password_4', FALSE);
INSERT INTO usuarios (nickname, nombre, apellido, email, Clave, es_admin) VALUES
('pedrom', 'Pedro', 'Martínez', 'pedro.martinez@correo.com', 'hashed_password_5', FALSE);
INSERT INTO usuarios (nickname, nombre, apellido, email, Clave, es_admin) VALUES
('pablo', 'pablo', 'otalora', 'pablootaloraamor@gmail.com', '$2a$10$5i3f1dj9G8hzGQmurzrdjeGcw4W1Gy3SrCxJpvLGaN90p542ihs3e',TRUE);

-- Insertar datos en campos, asociando un administrador a cada campo
INSERT INTO campos (nombre, ciudad, calle, codigo_postal, numero_telefono, comunidad_autonoma, id_admin) VALUES
('Campo Deportivo A', 'Madrid', 'Calle Falsa 123', '28001', '910123456', 'Madrid', 1);
INSERT INTO campos (nombre, ciudad, calle, codigo_postal, numero_telefono, comunidad_autonoma, id_admin) VALUES
('Campo Deportivo B', 'Barcelona', 'Avenida Primavera 456', '08001', '930654321', 'Cataluña', 2);
INSERT INTO campos (nombre, ciudad, calle, codigo_postal, numero_telefono, comunidad_autonoma, id_admin) VALUES
('Campo Deportivo C', 'Valencia', 'Carrer de l\'Esport 789', '46001', '960987654', 'Comunitat Valenciana', 3);
INSERT INTO campos (nombre, ciudad, calle, codigo_postal, numero_telefono, comunidad_autonoma, id_admin) VALUES
('Campo Deportivo D', 'Sevilla', 'Plaza Mayor 101', '41001', '954123456', 'Andalucía', 4);
INSERT INTO campos (nombre, ciudad, calle, codigo_postal, numero_telefono, comunidad_autonoma, id_admin) VALUES
('Campo Deportivo E', 'Bilbao', 'Calle del Sol 202', '48001', '944765432', 'País Vasco', 5);
INSERT INTO campos (nombre, ciudad, calle, codigo_postal, numero_telefono, comunidad_autonoma, id_admin) VALUES
('Campo Recon Alpha', 'Toledo', 'Calle del Aire 11', '45001', '925111222', 'Castilla-La Mancha', 6);
INSERT INTO campos (nombre, ciudad, calle, codigo_postal, numero_telefono, comunidad_autonoma, id_admin) VALUES
('Campo Recon Bravo', 'Zaragoza', 'Camino del Río 24', '50001', '976333444', 'Aragón', 6);
INSERT INTO campos (nombre, ciudad, calle, codigo_postal, numero_telefono, comunidad_autonoma, id_admin) VALUES
('Campo Recon Charlie', 'Granada', 'Av. Sierra Nevada 88', '18001', '958555666', 'Andalucía', 6);

-- Insertar datos en eventos (con nombre incluido)
-- Insertar eventos para enero 2025
INSERT INTO eventos (id_campo, nombre, fecha_hora, numero_plazas, numero_plazas_restantes, descripcion) VALUES
(6, 'Operación Invierno', '2025-01-07 10:00:00', 50, 50, 'Partido especial con condiciones invernales simuladas'),
(2, 'Entrenamiento Táctico Inicial', '2025-01-08 16:00:00', 40, 40, 'Entrenamiento básico de tácticas de equipo'),
(3, 'Torneo de Precisión', '2025-01-14 09:00:00', 30, 30, 'Competición individual de puntería'),
(4, 'Asalto Nocturno', '2025-01-15 20:00:00', 45, 45, 'Partido nocturno con equipos de visión limitada'),
(5, 'Defensa de Posición', '2025-01-21 11:00:00', 60, 60, 'Ejercicio de defensa estratégica de posiciones clave'),
(1, 'Misión de Rescate', '2025-01-22 14:00:00', 35, 35, 'Simulación de misión de rescate de rehenes'),
(2, 'Combate Urbano', '2025-01-28 10:00:00', 50, 50, 'Escenarios de combate en entorno urbano'),
(3, 'Francotiradores Elite', '2025-01-29 13:00:00', 25, 25, 'Entrenamiento especializado para tiradores');

-- Insertar eventos para febrero 2025
INSERT INTO eventos (id_campo, nombre, fecha_hora, numero_plazas, numero_plazas_restantes, descripcion) VALUES
(4, 'Operación Tormenta', '2025-02-04 09:00:00', 55, 55, 'Partido con condiciones climáticas adversas simuladas'),
(5, 'Tácticas de Emboscada', '2025-02-05 15:00:00', 40, 40, 'Aprendizaje y práctica de emboscadas'),
(7, 'Torneo por Equipos', '2025-02-11 10:00:00', 60, 60, 'Torneo eliminatorio por equipos'),
(2, 'Supervivencia Extrema', '2025-02-12 08:00:00', 30, 30, 'Pruebas de resistencia y supervivencia'),
(3, 'Operación Silencio', '2025-02-18 19:00:00', 45, 45, 'Partido con restricción de comunicación'),
(4, 'Defensa del Fuerte', '2025-02-19 11:00:00', 50, 50, 'Ejercicio de defensa de posición fortificada'),
(5, 'Patrullas Avanzadas', '2025-02-25 14:00:00', 35, 35, 'Técnicas avanzadas de patrulla'),
(1, 'Tiro en Movimiento', '2025-02-26 10:00:00', 40, 40, 'Entrenamiento de puntería en movimiento');

-- Insertar eventos para marzo 2025
INSERT INTO eventos (id_campo, nombre, fecha_hora, numero_plazas, numero_plazas_restantes, descripcion) VALUES
(2, 'Primavera Táctica', '2025-03-04 09:00:00', 50, 50, 'Evento especial de inicio de primavera'),
(3, 'Flanqueo Rápido', '2025-03-05 13:00:00', 45, 45, 'Tácticas de movimiento rápido y flanqueo'),
(4, 'Operación Pantano', '2025-03-11 10:00:00', 40, 40, 'Partido en terreno difícil y húmedo'),
(5, 'Comunicaciones de Campo', '2025-03-12 15:00:00', 30, 30, 'Entrenamiento en comunicaciones tácticas'),
(8, 'Torneo Anual', '2025-03-18 09:00:00', 70, 70, 'Gran torneo con premios especiales'),
(2, 'Asalto Nocturno II', '2025-03-19 20:00:00', 50, 50, 'Segunda edición del popular evento nocturno'),
(3, 'Protección VIP', '2025-03-25 11:00:00', 35, 35, 'Simulación de protección de persona importante'),
(4, 'Tiro de Precisión', '2025-03-26 14:00:00', 25, 25, 'Competencia avanzada de precisión');

-- Insertar eventos para abril 2025
INSERT INTO eventos (id_campo, nombre, fecha_hora, numero_plazas, numero_plazas_restantes, descripcion) VALUES
(6, 'Operación Semana Santa', '2025-04-01 10:00:00', 60, 60, 'Evento especial por Semana Santa'),
(1, 'Emboscadas Urbanas', '2025-04-02 16:00:00', 45, 45, 'Tácticas urbanas de emboscada'),
(2, 'Resistencia Extrema', '2025-04-08 08:00:00', 40, 40, 'Prueba de resistencia física y mental'),
(3, 'Tiro en Equipo', '2025-04-09 13:00:00', 50, 50, 'Coordinación de fuego en equipo'),
(4, 'Operación Relámpago', '2025-04-15 10:00:00', 55, 55, 'Partido de ritmo rápido y acciones rápidas'),
(5, 'Defensa Perimetral', '2025-04-16 14:00:00', 45, 45, 'Estrategias de defensa perimetral'),
(1, 'Francotiradores II', '2025-04-22 09:00:00', 30, 30, 'Avanzado para tiradores de precisión'),
(2, 'Combate CQB', '2025-04-23 15:00:00', 40, 40, 'Close Quarters Battle training');

-- Insertar eventos para mayo 2025
INSERT INTO eventos (id_campo, nombre, fecha_hora, numero_plazas, numero_plazas_restantes, descripcion) VALUES
(6, 'Operación Mayo', '2025-05-06 10:00:00', 50, 50, 'Evento especial del mes de mayo'),
(4, 'Tácticas de Asalto', '2025-05-07 14:00:00', 45, 45, 'Técnicas avanzadas de asalto'),
(5, 'Torneo de Primavera', '2025-05-13 09:00:00', 60, 60, 'Gran torneo de primavera'),
(1, 'Supervivencia Nocturna', '2025-05-14 20:00:00', 35, 35, 'Entrenamiento nocturno de supervivencia'),
(2, 'Patrulla Larga', '2025-05-20 08:00:00', 40, 40, 'Ejercicio de patrulla extendida'),
(3, 'Operación Fantasma', '2025-05-21 19:00:00', 50, 50, 'Partido con énfasis en sigilo'),
(4, 'Defensa Móvil', '2025-05-27 11:00:00', 45, 45, 'Estrategias de defensa en movimiento'),
(5, 'Tiro Dinámico', '2025-05-28 15:00:00', 40, 40, 'Entrenamiento de tiro en situaciones dinámicas');

-- Insertar eventos para junio 2025
INSERT INTO eventos (id_campo, nombre, fecha_hora, numero_plazas, numero_plazas_restantes, descripcion) VALUES
(6, 'Operación Verano', '2025-06-03 10:00:00', 55, 55, 'Evento de inicio de verano'),
(2, 'Combate en Calor', '2025-06-04 14:00:00', 45, 45, 'Partido con condiciones de calor extremo'),
(3, 'Torneo de Equipos II', '2025-06-10 09:00:00', 60, 60, 'Segunda edición del torneo por equipos'),
(4, 'Navegación Táctica', '2025-06-11 08:00:00', 35, 35, 'Entrenamiento en navegación y orientación'),
(5, 'Operación Relámpago II', '2025-06-17 10:00:00', 50, 50, 'Segunda edición del popular evento rápido'),
(1, 'Francotiradores III', '2025-06-18 13:00:00', 30, 30, 'Nivel experto para tiradores'),
(2, 'Asalto Anfibio', '2025-06-24 11:00:00', 40, 40, 'Ejercicios con elementos acuáticos'),
(3, 'Defensa Urbana', '2025-06-25 15:00:00', 50, 50, 'Estrategias de defensa en entorno urbano');

-- Insertar eventos para julio 2025
INSERT INTO eventos (id_campo, nombre, fecha_hora, numero_plazas, numero_plazas_restantes, descripcion) VALUES
(4, 'Operación Julio Caliente', '2025-07-01 09:00:00', 60, 60, 'Evento especial de verano'),
(5, 'Tácticas Desérticas', '2025-07-02 16:00:00', 45, 45, 'Adaptación a condiciones desérticas'),
(6, 'Torneo de Verano', '2025-07-08 10:00:00', 70, 70, 'Gran torneo estival'),
(2, 'Nocturno Extremo', '2025-07-09 21:00:00', 40, 40, 'Partido nocturno con desafíos extras'),
(3, 'Operación Tormenta de Arena', '2025-07-15 10:00:00', 50, 50, 'Simulación de condiciones de tormenta'),
(4, 'Movimiento Táctico', '2025-07-16 14:00:00', 45, 45, 'Técnicas avanzadas de movimiento'),
(5, 'Rescate en Zona Hostil', '2025-07-22 11:00:00', 35, 35, 'Simulación de rescate en zona peligrosa'),
(1, 'Tiro de Precisión III', '2025-07-23 13:00:00', 30, 30, 'Masterclass para francotiradores');

-- Insertar eventos para agosto 2025
INSERT INTO eventos (id_campo, nombre, fecha_hora, numero_plazas, numero_plazas_restantes, descripcion) VALUES
(6, 'Operación Agosto', '2025-08-05 09:00:00', 55, 55, 'Evento especial de agosto'),
(3, 'Combate en Altura', '2025-08-06 14:00:00', 40, 40, 'Tácticas para terreno elevado'),
(4, 'Torneo de Vacaciones', '2025-08-12 10:00:00', 60, 60, 'Torneo especial de verano'),
(5, 'Supervivencia en Montaña', '2025-08-13 08:00:00', 35, 35, 'Técnicas de supervivencia en montaña'),
(1, 'Operación Relámpago III', '2025-08-19 10:00:00', 50, 50, 'Tercera edición del evento rápido'),
(2, 'Defensa de Edificio', '2025-08-20 15:00:00', 45, 45, 'Estrategias para defender estructuras'),
(3, 'Patrulla Nocturna', '2025-08-26 20:00:00', 40, 40, 'Ejercicio de patrulla nocturna'),
(4, 'Tiro en Equipo II', '2025-08-27 13:00:00', 50, 50, 'Coordinación avanzada de fuego');

-- Insertar eventos para septiembre 2025
INSERT INTO eventos (id_campo, nombre, fecha_hora, numero_plazas, numero_plazas_restantes, descripcion) VALUES
(6, 'Operación Otoño', '2025-09-02 10:00:00', 60, 60, 'Evento de inicio de otoño'),
(1, 'Tácticas de Bosque', '2025-09-03 14:00:00', 45, 45, 'Estrategias para combate en bosque'),
(2, 'Torneo de Otoño', '2025-09-09 09:00:00', 65, 65, 'Gran torneo otoñal'),
(3, 'Emboscadas en Naturaleza', '2025-09-10 11:00:00', 40, 40, 'Técnicas de emboscada en exterior'),
(4, 'Operación Niebla', '2025-09-16 10:00:00', 50, 50, 'Partido con visibilidad reducida'),
(5, 'Movimiento Sigiloso', '2025-09-17 15:00:00', 35, 35, 'Técnicas avanzadas de sigilo'),
(1, 'Francotiradores IV', '2025-09-23 09:00:00', 30, 30, 'Elite training para tiradores'),
(2, 'Defensa Perimetral II', '2025-09-24 14:00:00', 45, 45, 'Estrategias avanzadas de defensa');

-- Insertar eventos para octubre 2025
INSERT INTO eventos (id_campo, nombre, fecha_hora, numero_plazas, numero_plazas_restantes, descripcion) VALUES
(6, 'Operación Halloween', '2025-10-07 18:00:00', 60, 60, 'Evento especial temático de Halloween'),
(4, 'Nocturno Terror', '2025-10-08 20:00:00', 50, 50, 'Partido nocturno con elementos de terror'),
(5, 'Torneo de Otoño II', '2025-10-14 10:00:00', 70, 70, 'Segundo torneo otoñal'),
(1, 'Tácticas en Oscuridad', '2025-10-15 19:00:00', 40, 40, 'Combate en condiciones de poca luz'),
(2, 'Operación Fantasma II', '2025-10-21 10:00:00', 55, 55, 'Segunda edición del evento de sigilo'),
(3, 'Defensa de Posición II', '2025-10-22 14:00:00', 45, 45, 'Estrategias avanzadas de defensa'),
(4, 'Tiro Nocturno', '2025-10-28 18:00:00', 35, 35, 'Entrenamiento de tiro nocturno'),
(5, 'Asalto Sorpresa', '2025-10-29 11:00:00', 50, 50, 'Ejercicios de ataque por sorpresa');

-- Insertar eventos para noviembre 2025
INSERT INTO eventos (id_campo, nombre, fecha_hora, numero_plazas, numero_plazas_restantes, descripcion) VALUES
(6, 'Operación Noviembre', '2025-11-04 10:00:00', 60, 60, 'Evento especial de noviembre'),
(2, 'Combate en Lluvia', '2025-11-05 14:00:00', 45, 45, 'Partido con condiciones de lluvia simulada'),
(3, 'Torneo de Invierno', '2025-11-11 09:00:00', 65, 65, 'Preparación para la temporada invernal'),
(4, 'Supervivencia en Frío', '2025-11-12 08:00:00', 40, 40, 'Técnicas para condiciones frías'),
(5, 'Operación Ventisca', '2025-11-18 10:00:00', 50, 50, 'Partido con nieve y viento simulados'),
(1, 'Tácticas Invernales', '2025-11-19 14:00:00', 45, 45, 'Estrategias para combate invernal'),
(2, 'Nocturno Invierno', '2025-11-25 18:00:00', 40, 40, 'Partido nocturno invernal'),
(3, 'Defensa de Refugio', '2025-11-26 11:00:00', 50, 50, 'Protección de posición en condiciones extremas');

-- Insertar eventos para diciembre 2025
INSERT INTO eventos (id_campo, nombre, fecha_hora, numero_plazas, numero_plazas_restantes, descripcion) VALUES
(6, 'Operación Navidad', '2025-12-02 10:00:00', 60, 60, 'Evento especial navideño'),
(5, 'Torneo de Fin de Año', '2025-12-03 09:00:00', 70, 70, 'Último gran torneo del año'),
(1, 'Combate Invernal', '2025-12-09 10:00:00', 55, 55, 'Partido con condiciones invernales extremas'),
(2, 'Defensa de Base', '2025-12-10 14:00:00', 45, 45, 'Protección de instalación estratégica'),
(3, 'Operación Año Nuevo', '2025-12-16 10:00:00', 60, 60, 'Evento especial de fin de año'),
(4, 'Tácticas de Retroceso', '2025-12-17 15:00:00', 40, 40, 'Estrategias de retirada ordenada'),
(5, 'Francotiradores V', '2025-12-30 09:00:00', 30, 30, 'Sesión final del año para tiradores'),
(1, 'Cierre de Temporada', '2025-12-31 12:00:00', 80, 80, 'Evento festivo de cierre de año');

-- Insertar datos en reservas
INSERT INTO reservas (id_evento, id_usuario, numero_personas, estado) VALUES
(1, 5, 4, 'confirmada');

INSERT INTO reservas (id_evento, id_usuario, numero_personas, estado) VALUES
(2, 4, 2, 'confirmada');

INSERT INTO reservas (id_evento, id_usuario, numero_personas, estado) VALUES
(3, 3, 5, 'confirmada');

INSERT INTO reservas (id_evento, id_usuario, numero_personas, estado) VALUES
(4, 2, 3, 'cancelada');

INSERT INTO reservas (id_evento, id_usuario, numero_personas, estado) VALUES
(5, 1, 6,'confirmada');

-- Consultas para verificar la inserción
SELECT * FROM usuarios;
SELECT * FROM campos;
SELECT * FROM eventos;
SELECT * FROM reservas;
