CREATE TABLE proyecto (
	id INT AUTO_INCREMENT PRIMARY KEY,
	nombre VARCHAR(100) NOT NULL
);

CREATE TABLE tarea(
	id INT AUTO_INCREMENT PRIMARY KEY,
	nombre VARCHAR(100) NOT NULL,
	descripcion VARCHAR(250),
	fecha_inicio DATE NOT NULL,
	fecha_fin DATE,
	urgencia ENUM('alta', 'media', 'baja') NOT NULL,
	estado ENUM('pendiente', 'en progreso', 'finalizado') NOT NULL,
	proyecto_id INT NOT NULL,
	FOREIGN KEY (proyecto_id) REFERENCES proyecto(id)
);