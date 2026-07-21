# Conecta Campus

Sistema web para comunicação acadêmica, desenvolvido com Java, JSP, Servlets, MySQL e Tomcat.

## Melhorias implementadas

### Interface e navegação

- Padronização do cabeçalho, menu lateral e identidade visual em todas as páginas.
- Cabeçalho fixo com menu do usuário, acesso ao perfil e opção de sair.
- Foto de perfil exibida no cabeçalho; quando não houver foto, é mostrada a inicial do usuário.
- Layout responsivo, campos acessíveis e mensagens visuais de sucesso, erro e confirmação.
- Modal de confirmação aplicado às ações de exclusão permitidas.

### Perfis e permissões

- **Administrador:** gerencia usuários, cargos, membros, categorias, comunicados, fórum, pesquisas e visualização de feedbacks.
- **Equipe institucional:** cria e edita comunicados, tópicos do fórum e pesquisas; consulta feedbacks, categorias, membros e perfil. Não possui permissão de exclusão.
- **Aluno:** visualiza comunicados, equipe, fórum, pesquisas e relatório financeiro; pode responder tópicos, votar em enquetes, enviar feedback, marcar pesquisas como respondidas e editar o próprio perfil.
- Menus e botões são exibidos conforme o perfil do usuário.

### Usuários, equipe e cargos

- Cadastro administrativo simplificado: nome, curso, e-mail, senha e perfil.
- Contas da equipe exigem e-mail `@conecta.com.br`; alunos utilizam e-mail `@gmail.com`.
- Usuários podem ser ativados ou inativados, preservando o histórico.
- O editor de usuários não permite alterar senha, cargo ou e-mail institucional.
- Cadastro de membros integrado às contas institucionais: o administrador seleciona a pessoa da equipe e informa telefone e cargo.
- Máscara e validação de telefone no formato `(00) 00000-0000`.

### Perfil

- Atualização de nome, curso, e-mail, foto de perfil e senha.
- Upload de foto com atualização no cabeçalho.

### Comunicados e categorias

- Gestão de categorias por administrador e equipe.
- Filtro por categoria e campo de pesquisa.
- Visual de notícias para melhorar a leitura.
- Inclusão opcional de imagem.
- Página para leitura completa do comunicado.
- Botão **Ler mais** exibido quando houver mais de 90 caracteres ou quebra de linha.

### Fórum e enquetes

- Tópicos podem ser somente respostas, somente enquete ou ambos.
- Enquetes integradas ao Fórum.
- Cada usuário pode votar apenas uma vez por enquete.
- Resultados exibidos no tópico.
- Respostas identificadas ou anônimas.
- Administrador pode excluir tópicos mesmo quando possuem respostas, enquetes, opções e votos vinculados.

### Feedback

- Tipos: sugestão, elogio, reclamação e outros.
- Envio anônimo disponível para alunos.
- Administrador e equipe podem visualizar feedbacks.
- Administrador não envia feedback.

### Pesquisas com Google Forms

- Nova área para questionários acadêmicos e institucionais.
- Administrador e equipe cadastram título, descrição, link do Google Forms e prazo.
- Pesquisas podem ser editadas e ter o status alterado entre aberta e encerrada.
- Alunos acessam o formulário pelo botão **Responder pesquisa**.
- Após responder, o aluno pode marcar a pesquisa como respondida.
- O status **Respondida** é salvo individualmente para cada aluno.
- Migrações `08_pesquisas.sql` e `09_pesquisas_respondidas.sql` criam as tabelas necessárias.

### Relatório financeiro

- Dashboard renomeado para **Relatório financeiro**.
- Leitura de entradas e saídas por planilha CSV.
- Filtro por mês.
- Gráfico comparativo e tabela detalhada.
- Configuração pela variável `GOOGLE_SHEETS_FINANCEIRO_URL`.

### Organização técnica

- Estrutura separada em `controller`, `service`, `dao`, `model`, `filter` e `util`.
- Remoção de arquivos, métodos e logs de depuração sem uso.
- Código reorganizado com responsabilidades separadas.
- Scripts de banco organizados em arquivo principal e migrações.

## Tecnologias

- Java 21
- Jakarta Servlet e JSP
- Apache Tomcat 11
- MySQL
- Bootstrap 5
- Chart.js
- jBCrypt
