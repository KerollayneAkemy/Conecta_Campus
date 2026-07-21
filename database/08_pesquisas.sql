USE conectacampus;

CREATE TABLE pesquisas (
    id_pesquisa INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    descricao TEXT NULL,
    link_formulario VARCHAR(500) NOT NULL,
    data_limite DATE NULL,
    status ENUM('ABERTA', 'ENCERRADA') NOT NULL DEFAULT 'ABERTA',
    data_criacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    id_usuario INT NOT NULL,
    CONSTRAINT fk_pesquisa_usuario FOREIGN KEY (id_usuario)
        REFERENCES usuarios(id_usuario)
);

CREATE INDEX idx_pesquisa_status ON pesquisas(status);
