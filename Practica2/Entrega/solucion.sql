DROP SCHEMA IF EXISTS panaderia;

CREATE SCHEMA panaderia;
USE panaderia;

CREATE TABLE provincia (
codigo INT AUTO_INCREMENT,
nombre VARCHAR (45) NOT NULL,
PRIMARY KEY(codigo)
);

CREATE TABLE poblacion (
codigo INT AUTO_INCREMENT,
nombre VARCHAR (45) NOT NULL,
codigo_provincia INT UNIQUE NOT NULL,
PRIMARY KEY(codigo),
FOREIGN KEY (codigo_provincia) REFERENCES provincia (codigo)
		ON DELETE CASCADE ON UPDATE CASCADE 
);

CREATE TABLE local (
codigo INT AUTO_INCREMENT,
numero INT NOT NULL, 
calle VARCHAR (255), 
codigo_postal INT NOT NULL,
tiene_cafeteria BOOLEAN,
codigo_poblacion INT UNIQUE NOT NULL,
PRIMARY KEY (codigo),
FOREIGN KEY (codigo_poblacion) REFERENCES poblacion (codigo)
		ON DELETE CASCADE ON UPDATE CASCADE 
);

CREATE TABLE categoria (
codigo INT AUTO_INCREMENT,
descripcion VARCHAR (255),
tipo_producto VARCHAR (45),
PRIMARY KEY (codigo)
);

CREATE TABLE producto (
codigo INT AUTO_INCREMENT,
nombre VARCHAR (45) NOT NULL,
descripcion VARCHAR (255),
precio_base FLOAT, 
codigo_categoria INT UNIQUE NOT NULL,
PRIMARY KEY (codigo),
FOREIGN KEY (codigo_categoria) REFERENCES categoria (codigo)
		ON DELETE CASCADE ON UPDATE CASCADE 
);

CREATE TABLE ingrediente (
codigo INT AUTO_INCREMENT,
nombre VARCHAR (45) NOT NULL,
descripcion VARCHAR (255),
PRIMARY KEY (codigo)
);

CREATE TABLE modo_cocinado (
codigo INT AUTO_INCREMENT,
descripcion VARCHAR (255),
PRIMARY KEY (codigo)
);

CREATE TABLE empleado (
codigo INT AUTO_INCREMENT,
dni VARCHAR (15) UNIQUE NOT NULL, 
nss CHAR (15) UNIQUE NOT NULL,
nombre VARCHAR (45) NOT NULL,
primer_apellido VARCHAR (45),
segundo_apellido VARCHAR (45),
telefono_contacto VARCHAR (15),
codigo_empleado INT NOT NULL,
PRIMARY KEY (codigo),
FOREIGN KEY (codigo_empleado) REFERENCES empleado (codigo)
	ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE tarea (
codigo INT AUTO_INCREMENT,
descripcion VARCHAR (255),
coste FLOAT, 
prerrequisitos VARCHAR (255),
PRIMARY KEY (codigo)
);

CREATE TABLE producto_ingrediente_modo_cocinado (
codigo_producto INT UNIQUE NOT NULL,
codigo_ingrediente INT UNIQUE NOT NULL,
codigo_modo_cocinado INT UNIQUE NOT NULL,
cantidad INT NOT NULL,
PRIMARY KEY (codigo_producto,codigo_ingrediente,codigo_modo_cocinado),
    FOREIGN KEY (codigo_producto) REFERENCES producto (codigo)
		ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (codigo_ingrediente) REFERENCES ingrediente (codigo)
		ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (codigo_modo_cocinado) REFERENCES modo_cocinado (codigo)
		ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE local_empleado_tarea (
codigo_local INT UNIQUE NOT NULL,
codigo_empleado INT UNIQUE NOT NULL,
codigo_tarea INT UNIQUE NOT NULL,
fecha_inicio DATE,
horas TIME,
hora_inicio TIME,
PRIMARY KEY (codigo_local,codigo_empleado,codigo_tarea,fecha_inicio),
    FOREIGN KEY (codigo_local) REFERENCES local (codigo)
		ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (codigo_empleado) REFERENCES empleado (codigo)
		ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (codigo_tarea) REFERENCES tarea (codigo)
		ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE local_empleado (
codigo_local INT UNIQUE NOT NULL,
codigo_empleado INT UNIQUE NOT NULL,
fecha_inicio DATE,
fecha_fin DATE,
PRIMARY KEY (codigo_local,codigo_empleado,fecha_inicio),
	FOREIGN KEY (codigo_local) REFERENCES local (codigo)
		ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (codigo_empleado) REFERENCES empleado (codigo)
		ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE local_producto (
codigo_local INT UNIQUE NOT NULL,
codigo_producto INT UNIQUE NOT NULL,
PRIMARY KEY (codigo_local,codigo_producto),
	FOREIGN KEY (codigo_local) REFERENCES local (codigo)
		ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (codigo_producto) REFERENCES producto (codigo)
		ON DELETE CASCADE ON UPDATE CASCADE
);