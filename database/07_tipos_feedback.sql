-- Execute uma vez em bancos criados antes desta atualização.
ALTER TABLE feedbacks
    MODIFY COLUMN tipo ENUM('SUGESTAO','RECLAMACAO','ELOGIO','OUTROS') NOT NULL;
