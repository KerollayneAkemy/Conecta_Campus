-- Execute uma vez em bancos criados antes desta atualização.
ALTER TABLE comunicados ADD COLUMN imagem VARCHAR(255) NULL AFTER mensagem;
