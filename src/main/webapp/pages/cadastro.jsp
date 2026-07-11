<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Criar conta - Conecta Campus</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
</head>
<body>
<a class="skip-link" href="#conteudo">Ir para o formulário</a>

<main class="container-fluid auth-layout">
    <div class="row min-vh-100">
        <section class="col-lg-7 brand-panel" aria-label="Apresentação do Conecta Campus">
            <div class="brand-copy">
                <div class="brand-kicker"><i class="bi bi-stars" aria-hidden="true"></i> Comece agora</div>
                <h1>Conecta Campus</h1>
                <p>Cadastre-se para participar de comunicados, fóruns, enquetes e feedbacks da sua comunidade acadêmica.</p>
            </div>
        </section>

        <section class="col-lg-5 auth-panel" id="conteudo">
            <div class="card-login">
                <h2>Criar conta</h2>
                <p class="subtitulo">Preencha seus dados para acessar a plataforma.</p>

                <% if(request.getAttribute("erro") != null){ %>
                    <div class="alert alert-danger" role="alert">
                        <%= request.getAttribute("erro") %>
                    </div>
                <% } %>

                <form action="${pageContext.request.contextPath}/cadastro" method="post">
                    <div class="mb-3">
                        <label class="form-label" for="nome">Nome completo</label>
                        <input type="text" class="form-control" id="nome" name="nome" autocomplete="name" required>
                    </div>

                    <div class="mb-3">
                        <label class="form-label" for="curso">Curso</label>
                        <input type="text" class="form-control" id="curso" name="curso" placeholder="Ex.: Sistemas de Informação" required>
                    </div>

                    <div class="mb-3">
                        <label class="form-label" for="email">E-mail</label>
                        <input type="email" class="form-control" id="email" name="email" autocomplete="email" required>
                    </div>

                    <div class="mb-4">
                        <label class="form-label" for="senha">Senha</label>
                        <input type="password" class="form-control" id="senha" name="senha" autocomplete="new-password" minlength="6" required>
                    </div>

                    <input type="hidden" name="idPerfil" value="1">

                    <button type="submit" class="btn btn-primary w-100">
                        <i class="bi bi-check-circle" aria-hidden="true"></i> Cadastrar
                    </button>
                </form>

                <hr class="my-4">

                <p class="text-center mb-3">Já possui conta?</p>
                <a href="${pageContext.request.contextPath}/login" class="btn btn-outline-primary w-100">
                    <i class="bi bi-box-arrow-in-right" aria-hidden="true"></i> Fazer login
                </a>
            </div>
        </section>
    </div>
</main>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
