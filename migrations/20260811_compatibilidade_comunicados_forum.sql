-- Compatibiliza dumps antigos com os campos usados pela aplicacao atual.
-- A migracao e idempotente e pode ser executada mais de uma vez.

SET @comando_imagem = (
    SELECT IF(
        COUNT(*) = 0,
        'ALTER TABLE comunicados ADD COLUMN imagem VARCHAR(500) NULL AFTER mensagem',
        'SELECT ''Campo comunicados.imagem ja existe'' AS informacao'
    )
    FROM information_schema.columns
    WHERE table_schema = DATABASE()
      AND table_name = 'comunicados'
      AND column_name = 'imagem'
);

PREPARE migracao_imagem FROM @comando_imagem;
EXECUTE migracao_imagem;
DEALLOCATE PREPARE migracao_imagem;

SET @comando_anonimo = (
    SELECT IF(
        COUNT(*) = 0,
        'ALTER TABLE respostas_forum ADD COLUMN anonimo TINYINT(1) NOT NULL DEFAULT 0 AFTER resposta',
        'SELECT ''Campo respostas_forum.anonimo ja existe'' AS informacao'
    )
    FROM information_schema.columns
    WHERE table_schema = DATABASE()
      AND table_name = 'respostas_forum'
      AND column_name = 'anonimo'
);

PREPARE migracao_anonimo FROM @comando_anonimo;
EXECUTE migracao_anonimo;
DEALLOCATE PREPARE migracao_anonimo;
