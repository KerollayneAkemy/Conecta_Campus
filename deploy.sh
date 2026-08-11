#!/usr/bin/env sh
set -eu

cd "$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)"

if [ ! -f .env ]; then
    echo "Arquivo .env ausente. Copie .env.example para .env e configure as senhas."
    exit 1
fi

if ! git diff --quiet || ! git diff --cached --quiet; then
    echo "O repositorio possui alteracoes locais. O deploy foi cancelado."
    exit 1
fi

git pull --ff-only
docker compose config --quiet
docker compose up -d --build --remove-orphans
docker compose ps
