-- Use somente se o banco já existe e foi criado antes do script completo.
USE conectacampus;

ALTER TABLE usuarios
    ADD COLUMN id_cargo INT NULL,
    ADD CONSTRAINT fk_usuario_cargo FOREIGN KEY (id_cargo) REFERENCES cargo(id_cargo);
