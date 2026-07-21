-- Execute depois do script inicial do banco.
USE conectacampus;

INSERT INTO perfis (nome) VALUES
    ('ALUNO'),
    ('EQUIPE_INSTITUCIONAL'),
    ('ADMINISTRADOR')
ON DUPLICATE KEY UPDATE nome = VALUES(nome);

ALTER TABLE usuarios
    ADD COLUMN setor_institucional ENUM('REITORIA', 'GREMIO') NULL AFTER curso,
    ADD COLUMN email_institucional VARCHAR(150) NULL AFTER setor_institucional,
    ADD COLUMN foto_perfil VARCHAR(255) NULL AFTER email_institucional,
    ADD CONSTRAINT uk_usuario_email_institucional UNIQUE (email_institucional);

ALTER TABLE enquetes
    ADD COLUMN id_forum INT NULL AFTER id_usuario,
    ADD CONSTRAINT fk_enquete_forum FOREIGN KEY (id_forum)
        REFERENCES forum(id_forum) ON DELETE CASCADE;

ALTER TABLE votos
    ADD COLUMN id_enquete INT NULL AFTER id_opcao;

UPDATE votos v
INNER JOIN opcoes_enquete o ON o.id_opcao = v.id_opcao
SET v.id_enquete = o.id_enquete;

ALTER TABLE votos
    DROP INDEX uk_voto,
    MODIFY COLUMN id_enquete INT NOT NULL,
    ADD CONSTRAINT fk_voto_enquete FOREIGN KEY (id_enquete)
        REFERENCES enquetes(id_enquete) ON DELETE CASCADE,
    ADD CONSTRAINT uk_voto_por_enquete UNIQUE (id_usuario, id_enquete);

CREATE INDEX idx_enquete_forum ON enquetes(id_forum);
CREATE INDEX idx_voto_enquete ON votos(id_enquete);

-- Depois de criar a primeira conta, promova-a manualmente uma única vez:
-- UPDATE usuarios u JOIN perfis p ON p.nome = 'ADMINISTRADOR'
-- SET u.id_perfil = p.id_perfil WHERE u.email = 'seu-email@instituicao.edu.br';

-- A planilha financeira será consultada pelo aplicativo, portanto não armazene
-- chave de API ou credenciais no banco. Configure-as no ambiente do servidor.
