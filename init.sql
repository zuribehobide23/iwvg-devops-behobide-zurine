-- Crear tabla de ejemplo para probar endpoints
CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(100) NOT NULL,
                       email VARCHAR(150) NOT NULL UNIQUE
);

-- Insertar datos de prueba
INSERT INTO users (name, email) VALUES ('Zurine', 'zurine@example.com');
INSERT INTO users (name, email) VALUES ('Oier', 'oier@example.com');
INSERT INTO users (name, email) VALUES ('Ane', 'ane@example.com');

-- Verificar datos insertados
SELECT * FROM users;
