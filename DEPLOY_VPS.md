# Deploy na VPS com Git e Docker

Esta configuracao cria dois containers:

- `app`: Java 21, Tomcat 11 e o arquivo WAR da aplicacao.
- `db`: MySQL 8.4, acessivel pelo `app` e pelo DBeaver via tunel SSH, sem porta publica.

O Git permanece instalado na VPS. Ele baixa as novas versoes do codigo e o Docker Compose recria apenas o que mudou.

## 1. Instalar Git, Docker Engine e Compose

Os comandos abaixo consideram Ubuntu. Para outra distribuicao, use a pagina oficial do Docker correspondente.

```bash
sudo apt update
sudo apt install -y git ca-certificates curl
sudo install -m 0755 -d /etc/apt/keyrings
sudo curl -fsSL https://download.docker.com/linux/ubuntu/gpg -o /etc/apt/keyrings/docker.asc
sudo chmod a+r /etc/apt/keyrings/docker.asc

source /etc/os-release
printf '%s\n' \
  'Types: deb' \
  'URIs: https://download.docker.com/linux/ubuntu' \
  "Suites: ${UBUNTU_CODENAME:-$VERSION_CODENAME}" \
  'Components: stable' \
  "Architectures: $(dpkg --print-architecture)" \
  'Signed-By: /etc/apt/keyrings/docker.asc' \
  | sudo tee /etc/apt/sources.list.d/docker.sources > /dev/null

sudo apt update
sudo apt install -y docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
sudo systemctl enable --now docker
sudo usermod -aG docker "$USER"
```

Saia da sessao SSH, entre novamente e valide:

```bash
docker version
docker compose version
```

Documentacao oficial: https://docs.docker.com/engine/install/ubuntu/

## 2. Clonar o projeto

```bash
sudo install -d -o "$USER" -g "$USER" /opt/conecta-campus
git clone --branch projeto-final --single-branch \
  https://github.com/KerollayneAkemy/Conecta_Campus.git \
  /opt/conecta-campus
cd /opt/conecta-campus
```

Se o repositorio se tornar privado, use uma chave SSH de deploy do GitHub em vez de salvar token na VPS.

## 3. Configurar as variaveis

```bash
cp .env.example .env
nano .env
```

Troque obrigatoriamente `MYSQL_PASSWORD` e `MYSQL_ROOT_PASSWORD`. Use em `MYSQL_DATABASE` o mesmo nome exibido na conexao do DBeaver e ajuste `APP_BASE_URL` para o dominio real.

Com Nginx, mantenha `APP_BIND_ADDRESS=127.0.0.1`. Sem proxy reverso, use temporariamente `APP_BIND_ADDRESS=0.0.0.0` e libere a porta definida em `APP_PORT` no firewall.

## 4. Exportar do DBeaver e importar no container

O DBeaver e o cliente de administracao. Para levar todas as tabelas e dados atuais, clique com o botao direito no banco `conectacampus`, abra **Ferramentas** e use **Dump database/Backup**. Exporte estrutura e dados para um unico arquivo, por exemplo `backup_conectacampus.sql`.

Guarde o dump fora do repositorio Git. Crie uma pasta de backup na VPS e envie o arquivo por SFTP ou `scp`:

```bash
sudo install -d -o "$USER" -g "$USER" /opt/conecta-backups
```

Execute o importador. Ele sobe primeiro o MySQL, espera o banco ficar saudavel, faz um backup preventivo, importa seu dump e so entao constroi e inicia a aplicacao:

```bash
cd /opt/conecta-campus
sh import-db.sh /opt/conecta-backups/backup_conectacampus.sql
```

Esse passo deve terminar sem erros antes de iniciar a aplicacao.

### Continuar usando o DBeaver

Crie uma nova conexao para o banco da VPS:

- Na aba principal: host `127.0.0.1`, porta `3307`, banco `conectacampus` e o usuario definido em `MYSQL_USER`.
- Na aba SSH: habilite o tunel e informe IP, usuario e chave SSH da VPS.

A porta `3307` esta vinculada somente ao `127.0.0.1` da VPS. Portanto, nao abra a porta `3306` nem a `3307` no firewall publico.

## 5. Primeiro deploy da aplicacao

```bash
sh deploy.sh
docker compose ps
docker compose logs -f app
```

O banco e os uploads usam volumes persistentes. Reiniciar ou recriar os containers nao apaga esses dados.

### Levar imagens que ja foram enviadas

Fotos e imagens nao ficam no MySQL; o banco guarda somente seus caminhos. Envie a pasta `uploads` do Tomcat local para `/opt/conecta-backups/uploads` na VPS e copie-a para o volume da aplicacao:

```bash
docker compose cp /opt/conecta-backups/uploads/. app:/data/uploads/
docker compose restart app
```

### Proxy reverso Nginx

Se o Nginx ja esta instalado na VPS, aponte o dominio para a porta local da aplicacao. O limite de `4m` permite os uploads aceitos pelo sistema:

```nginx
location / {
    client_max_body_size 4m;
    proxy_pass http://127.0.0.1:8080;
    proxy_set_header Host $host;
    proxy_set_header X-Real-IP $remote_addr;
    proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    proxy_set_header X-Forwarded-Proto $scheme;
}
```

Depois valide com `sudo nginx -t` e recarregue com `sudo systemctl reload nginx`.

## 6. Atualizacoes usando Git + Docker

Depois de enviar uma nova versao para a branch `projeto-final`, execute na VPS:

```bash
cd /opt/conecta-campus
sh deploy.sh
```

O script executa `git pull --ff-only`, valida o Compose, recompila a aplicacao e atualiza os containers sem remover os volumes.

## 7. Criar o primeiro administrador

Crie primeiro uma conta comum pela tela de cadastro. Depois promova o e-mail escolhido:

```bash
docker compose exec db mysql -u root -p conectacampus
```

No console do MySQL:

```sql
UPDATE usuarios u
JOIN perfis p ON p.nome = 'ADMINISTRADOR'
SET u.id_perfil = p.id_perfil
WHERE u.email = 'seu-email@gmail.com';
```

## Comandos uteis

```bash
docker compose ps
docker compose logs --tail=200 app
docker compose logs --tail=200 db
docker compose restart app
docker compose stop
docker compose up -d
```

Evite `docker compose down -v`: a opcao `-v` remove os volumes e apaga os dados persistidos.
