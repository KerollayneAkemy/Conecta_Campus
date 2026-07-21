-- Conecta Campus: script único para instalar o banco do zero.
-- ATENÇÃO: a primeira instrução apaga o banco conectacampus existente.
DROP DATABASE IF EXISTS conectacampus;
CREATE DATABASE conectacampus CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE conectacampus;

CREATE TABLE perfis (
    id_perfil INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    curso VARCHAR(100) NULL,
    setor_institucional ENUM('REITORIA','GREMIO') NULL,
    email_institucional VARCHAR(150) NULL UNIQUE,
    foto_perfil VARCHAR(255) NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    ultimo_acesso DATETIME NULL,
    data_cadastro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    id_perfil INT NOT NULL,
    CONSTRAINT fk_usuario_perfil FOREIGN KEY (id_perfil)
        REFERENCES perfis(id_perfil)
);

CREATE TABLE categorias (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL UNIQUE,
    descricao TEXT NULL
);

CREATE TABLE comunicados (
    id_comunicado INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    mensagem TEXT NOT NULL,
    imagem VARCHAR(255) NULL,
    prioridade ENUM('BAIXA','MEDIA','ALTA') NOT NULL DEFAULT 'MEDIA',
    status ENUM('ATIVO','INATIVO') NOT NULL DEFAULT 'ATIVO',
    visualizacoes INT NOT NULL DEFAULT 0,
    data_publicacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao DATETIME NULL,
    id_usuario INT NOT NULL,
    id_categoria INT NOT NULL,
    CONSTRAINT fk_comunicado_usuario FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario),
    CONSTRAINT fk_comunicado_categoria FOREIGN KEY (id_categoria) REFERENCES categorias(id_categoria)
);

CREATE TABLE comentarios (
    id_comentario INT AUTO_INCREMENT PRIMARY KEY,
    mensagem TEXT NOT NULL,
    data_comentario DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    id_usuario INT NOT NULL,
    id_comunicado INT NOT NULL,
    CONSTRAINT fk_comentario_usuario FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario),
    CONSTRAINT fk_comentario_comunicado FOREIGN KEY (id_comunicado)
        REFERENCES comunicados(id_comunicado) ON DELETE CASCADE
);

CREATE TABLE forum (
    id_forum INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    categoria VARCHAR(80) NULL,
    mensagem TEXT NOT NULL,
    tipo_interacao ENUM('RESPOSTAS','ENQUETE','AMBOS') NOT NULL DEFAULT 'AMBOS',
    visualizacoes INT NOT NULL DEFAULT 0,
    data_postagem DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    id_usuario INT NOT NULL,
    CONSTRAINT fk_forum_usuario FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
);

CREATE TABLE respostas_forum (
    id_resposta INT AUTO_INCREMENT PRIMARY KEY,
    resposta TEXT NOT NULL,
    anonimo BOOLEAN NOT NULL DEFAULT FALSE,
    data_resposta DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    id_usuario INT NOT NULL,
    id_forum INT NOT NULL,
    CONSTRAINT fk_resposta_usuario FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario),
    CONSTRAINT fk_resposta_forum FOREIGN KEY (id_forum)
        REFERENCES forum(id_forum) ON DELETE CASCADE
);

CREATE TABLE enquetes (
    id_enquete INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    descricao TEXT NULL,
    data_inicio DATE NOT NULL,
    data_fim DATE NULL,
    status ENUM('ABERTA','ENCERRADA') NOT NULL DEFAULT 'ABERTA',
    id_usuario INT NOT NULL,
    id_forum INT NULL,
    CONSTRAINT fk_enquete_usuario FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario),
    CONSTRAINT fk_enquete_forum FOREIGN KEY (id_forum) REFERENCES forum(id_forum) ON DELETE CASCADE
);

CREATE TABLE opcoes_enquete (
    id_opcao INT AUTO_INCREMENT PRIMARY KEY,
    descricao VARCHAR(150) NOT NULL,
    id_enquete INT NOT NULL,
    CONSTRAINT fk_opcao_enquete FOREIGN KEY (id_enquete)
        REFERENCES enquetes(id_enquete) ON DELETE CASCADE
);

CREATE TABLE votos (
    id_voto INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    id_opcao INT NOT NULL,
    id_enquete INT NOT NULL,
    data_voto DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_voto_usuario FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario),
    CONSTRAINT fk_voto_opcao FOREIGN KEY (id_opcao) REFERENCES opcoes_enquete(id_opcao),
    CONSTRAINT fk_voto_enquete FOREIGN KEY (id_enquete)
        REFERENCES enquetes(id_enquete) ON DELETE CASCADE,
    CONSTRAINT uk_voto_por_enquete UNIQUE (id_usuario, id_enquete)
);

CREATE TABLE feedbacks (
    id_feedback INT AUTO_INCREMENT PRIMARY KEY,
    assunto VARCHAR(100) NULL,
    tipo ENUM('SUGESTAO','RECLAMACAO','ELOGIO','OUTROS') NOT NULL,
    mensagem TEXT NOT NULL,
    anonimo BOOLEAN NOT NULL DEFAULT FALSE,
    data_envio DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    id_usuario INT NULL,
    CONSTRAINT fk_feedback_usuario FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
);

-- Tabelas da vitrine de membros da equipe.
CREATE TABLE cargo (
    id_cargo INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(150) NOT NULL UNIQUE,
    descricao TEXT NULL
);

CREATE TABLE membro (
    id_membro INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    telefone VARCHAR(20) NOT NULL,
    id_cargo INT NULL,
    CONSTRAINT fk_membro_cargo FOREIGN KEY (id_cargo) REFERENCES cargo(id_cargo)
);

-- O cargo associa a conta de uma pessoa da equipe à função cadastrada pelo admin.
ALTER TABLE usuarios
    ADD COLUMN id_cargo INT NULL,
    ADD CONSTRAINT fk_usuario_cargo FOREIGN KEY (id_cargo) REFERENCES cargo(id_cargo);

CREATE INDEX idx_usuario_perfil ON usuarios(id_perfil);
CREATE INDEX idx_comunicado_categoria ON comunicados(id_categoria);
CREATE INDEX idx_comunicado_usuario ON comunicados(id_usuario);
CREATE INDEX idx_forum_usuario ON forum(id_usuario);
CREATE INDEX idx_feedback_tipo ON feedbacks(tipo);
CREATE INDEX idx_votos_opcao ON votos(id_opcao);
CREATE INDEX idx_enquete_forum ON enquetes(id_forum);
CREATE INDEX idx_voto_enquete ON votos(id_enquete);

INSERT INTO perfis (nome) VALUES
    ('ALUNO'),
    ('EQUIPE_INSTITUCIONAL'),
    ('ADMINISTRADOR');

-- Crie a primeira conta pela tela de cadastro e depois promova-a:
-- UPDATE usuarios u JOIN perfis p ON p.nome = 'ADMINISTRADOR'
-- SET u.id_perfil = p.id_perfil
-- WHERE u.email = 'seu-email@gmail.com';
