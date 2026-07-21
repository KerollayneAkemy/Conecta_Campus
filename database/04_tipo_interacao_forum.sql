-- Execute uma vez em bancos criados antes desta atualizaÃ§Ã£o.
ALTER TABLE forum
    ADD COLUMN tipo_interacao ENUM('RESPOSTAS','ENQUETE','AMBOS')
    NOT NULL DEFAULT 'AMBOS' AFTER mensagem;
