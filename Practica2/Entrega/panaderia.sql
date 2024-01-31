DROP SCHEMA IF EXISTS panaderia;

CREATE SCHEMA panaderia;
USE panaderia;

CREATE TABLE Provincia (
	codigo INT UNIQUE NOT NULL,
    nombre VARCHAR(30),
    PRIMARY KEY (codigo)
);

CREATE TABLE Poblacion (
	codigo INT UNIQUE NOT NULL,
    codigo_Provincia INT UNIQUE NOT NULL,
    nombre VARCHAR(30),
	PRIMARY KEY (codigo),
    FOREIGN KEY (codigo_Provincia) REFERENCES Provincia (codigo)
		ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Local (
	codigo INT UNIQUE NOT NULL,
    numero INT,
    calle VARCHAR(30),
    codigo_postal INT,
    tiene_cafeteria BOOL,
    codigo_Poblacion INT UNIQUE NOT NULL,
	PRIMARY KEY (codigo),
    FOREIGN KEY (codigo_Poblacion) REFERENCES Poblacion (codigo)
		ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Categoria (
	codigo INT UNIQUE NOT NULL,
    descripcion VARCHAR(60),
    tipo_producto VARCHAR(30),
    PRIMARY KEY (codigo)
);

CREATE TABLE Modo_cocinado (
	codigo INT UNIQUE NOT NULL,
	descripcion VARCHAR(60),
    PRIMARY KEY (codigo)
);

CREATE TABLE Ingrediente (
	codigo INT UNIQUE NOT NULL,
    nombre VARCHAR(30),
	descripcion VARCHAR(60),
    PRIMARY KEY (codigo)
);

CREATE TABLE Producto (
	codigo INT UNIQUE NOT NULL,
    nombre VARCHAR(30),
	descripcion VARCHAR(60),
    precio_base INT,
    codigo_categoria INT UNIQUE,
    PRIMARY KEY (codigo),
    FOREIGN KEY (codigo_categoria) REFERENCES Categoria (codigo)
		ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE Contener (
	codigo_Modo_cocinado INT UNIQUE NOT NULL,
    codigo_Ingrediente INT UNIQUE NOT NULL,
    codigo_Producto INT UNIQUE NOT NULL,
    PRIMARY KEY (codigo_Modo_cocinado, codigo_Ingrediente, codigo_Producto),
    FOREIGN KEY (codigo_Modo_cocinado) REFERENCES Modo_cocinado (codigo)
		ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (codigo_Ingrediente) REFERENCES Ingrediente (codigo)
		ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (codigo_Producto) REFERENCES Producto (codigo)
		ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Vender (
	codigo_Local INT UNIQUE NOT NULL,
    codigo_Producto INT UNIQUE NOT NULL,
    PRIMARY KEY (codigo_Local, codigo_Producto),
    FOREIGN KEY (codigo_Local) REFERENCES Local (codigo)
		ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (codigo_Producto) REFERENCES Producto (codigo)
		ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE Table Empleado (
	codigo INT UNIQUE NOT NULL,
    telefono_contacto INT,
    nss INT,
    dni VARCHAR(9),
    nombre VARCHAR(30),
    primer_apellido VARCHAR(30),
	segundo_apellido VARCHAR(30),
    PRIMARY KEY (codigo)
);

CREATE TABLE Trabajar (
	fecha_inicio DATE UNIQUE NOT NULL,
    fecha_fin DATE,
    codigo_Local INT UNIQUE NOT NULL,
    codigo_Empleado INT UNIQUE NOT NULL,
    PRIMARY KEY (fecha_inicio, codigo_Local, codigo_Empleado),
    FOREIGN KEY (codigo_Local) REFERENCES Local (codigo)
		ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (codigo_Empleado) REFERENCES Empleado (codigo)
		ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Tarea (
	codigo INT UNIQUE NOT NULL,
    descripcion VARCHAR(60),
    coste DECIMAL,
    prerrequisitos VARCHAR(60),
    PRIMARY KEY (codigo)
);

CREATE TABLE Realizar_turno (
	fecha_inicio DATE UNIQUE NOT NULL,
    hora_inicio TIME,
    horas INT,
    codigo_Local INT UNIQUE NOT NULL,
    codigo_Empleado INT UNIQUE NOT NULL,
    codigo_Tarea INT UNIQUE NOT NULL,
    PRIMARY KEY (fecha_inicio, codigo_Local, codigo_Empleado, codigo_Tarea),
    FOREIGN KEY (codigo_Local) REFERENCES Local (codigo)
		ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (codigo_Empleado) REFERENCES Empleado (codigo)
		ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (codigo_Tarea) REFERENCES Tarea (codigo)
		ON DELETE CASCADE ON UPDATE CASCADE
);
