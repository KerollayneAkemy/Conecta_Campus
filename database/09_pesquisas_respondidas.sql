USE conectacampus;

CREATE TABLE pesquisa_respostas (
    id_resposta_pesquisa INT AUTO_INCREMENT PRIMARY KEY,
    id_pesquisa INT NOT NULL,
    id_usuario INT NOT NULL,
    data_resposta DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_pesquisa_resposta_pesquisa FOREIGN KEY (id_pesquisa)
        REFERENCES pesquisas(id_pesquisa) ON DELETE CASCADE,
    CONSTRAINT fk_pesquisa_resposta_usuario FOREIGN KEY (id_usuario)
        REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
    CONSTRAINT uk_pesquisa_resposta UNIQUE (id_pesquisa, id_usuario)
);
