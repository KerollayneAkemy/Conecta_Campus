-- Execute uma vez em bancos criados antes desta atualizaÃ§Ã£o.
ALTER TABLE respostas_forum
    ADD COLUMN anonimo BOOLEAN NOT NULL DEFAULT FALSE AFTER resposta;
