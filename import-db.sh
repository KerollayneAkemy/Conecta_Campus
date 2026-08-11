#!/usr/bin/env sh
set -eu

cd "$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)"

if [ "$#" -ne 1 ] || [ ! -s "$1" ]; then
    echo "Uso: sh import-db.sh /caminho/backup_conectacampus.sql"
    exit 1
fi

if ! grep -Eiq 'CREATE[[:space:]]+TABLE|INSERT[[:space:]]+INTO' "$1"; then
    echo "O arquivo informado nao parece conter um dump SQL com estrutura ou dados."
    exit 1
fi

if [ ! -f .env ]; then
    echo "Arquivo .env ausente. Copie .env.example para .env e configure as senhas."
    exit 1
fi

arquivo_dump=$1
diretorio_backup=${BACKUP_DIR:-backups}
arquivo_backup="$diretorio_backup/antes-importacao-$(date +%Y%m%d-%H%M%S).sql"

docker compose config --quiet
docker compose up -d db

tentativa=0
while [ "$tentativa" -lt 60 ]; do
    container_id=$(docker compose ps -q db)
    status=$(docker inspect --format '{{.State.Health.Status}}' "$container_id" 2>/dev/null || true)
    if [ "$status" = "healthy" ]; then
        break
    fi
    tentativa=$((tentativa + 1))
    sleep 2
done

if [ "${status:-}" != "healthy" ]; then
    echo "O MySQL nao ficou saudavel. Consulte: docker compose logs db"
    exit 1
fi

mkdir -p "$diretorio_backup"
docker compose exec -T db sh -c \
    'exec mysqldump -u root -p"$MYSQL_ROOT_PASSWORD" --databases "$MYSQL_DATABASE" --single-transaction --routines --triggers' \
    > "$arquivo_backup"

docker compose exec -T db sh -c \
    'exec mysql -u root -p"$MYSQL_ROOT_PASSWORD" "$MYSQL_DATABASE"' \
    < "$arquivo_dump"

docker compose up -d --build app
docker compose ps

echo "Importacao concluida. Backup anterior salvo em: $arquivo_backup"
