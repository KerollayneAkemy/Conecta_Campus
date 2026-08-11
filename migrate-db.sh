#!/usr/bin/env sh
set -eu

cd "$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)"

if [ ! -f .env ]; then
    echo "Arquivo .env ausente. Copie .env.example para .env e configure as senhas."
    exit 1
fi

docker compose config --quiet
docker compose up -d db

tentativa=0
status=
while [ "$tentativa" -lt 60 ]; do
    container_id=$(docker compose ps -q db)
    status=$(docker inspect --format '{{.State.Health.Status}}' "$container_id" 2>/dev/null || true)
    if [ "$status" = "healthy" ]; then
        break
    fi
    tentativa=$((tentativa + 1))
    sleep 2
done

if [ "$status" != "healthy" ]; then
    echo "O MySQL nao ficou saudavel. Consulte: docker compose logs db"
    exit 1
fi

diretorio_backup=${BACKUP_DIR:-backups}
mkdir -p "$diretorio_backup"
arquivo_backup="$diretorio_backup/antes-migracoes-$(date +%Y%m%d-%H%M%S).sql"

docker compose exec -T db sh -c \
    'exec mysqldump -u root -p"$MYSQL_ROOT_PASSWORD" --databases "$MYSQL_DATABASE" --single-transaction --routines --triggers' \
    > "$arquivo_backup"

encontrou_migracao=0
for migracao in migrations/*.sql; do
    if [ ! -f "$migracao" ]; then
        continue
    fi

    encontrou_migracao=1
    echo "Aplicando migracao: $migracao"
    docker compose exec -T db sh -c \
        'exec mysql -u root -p"$MYSQL_ROOT_PASSWORD" "$MYSQL_DATABASE"' \
        < "$migracao"
done

if [ "$encontrou_migracao" -eq 0 ]; then
    echo "Nenhuma migracao encontrada."
fi

echo "Migracoes concluidas. Backup salvo em: $arquivo_backup"
