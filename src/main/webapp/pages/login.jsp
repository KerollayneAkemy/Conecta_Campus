<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Entrar - Conecta Campus</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/login.css?v=5">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/passwordToggle.css?v=1">
<script defer
	src="${pageContext.request.contextPath}/js/passwordToggle.js?v=1"></script>
</head>
<body>
	<a class="skip-link" href="#conteudo">Ir para o formulário</a>

	<main class="container-fluid auth-layout">
		<div class="row min-vh-100">
			<section class="col-lg-7 brand-panel"
				aria-label="Apresentação do Conecta Campus">
				<div class="brand-copy">
					<div class="brand-kicker">
						<i class="bi bi-mortarboard-fill" aria-hidden="true"></i> Ambiente
						acadêmico
					</div>
					<h1>Conecta Campus</h1>
					<p>Uma plataforma para aproximar alunos, professores e
						coordenação com comunicados, enquetes, fórum e feedback em um só
						lugar.</p>
				</div>
			</section>

			<section class="col-lg-5 auth-panel" id="conteudo">
				<div class="card-login">
					<h2>Entrar</h2>
					<p class="subtitulo">Acesse sua conta para acompanhar a vida do
						campus.</p>

					<% if(request.getAttribute("erro") != null){ %>
					<div class="alert alert-danger" role="alert">
						<%= request.getAttribute("erro") %>
					</div>
					<% } %>
					<% if(request.getAttribute("sucesso") != null){ %>
					<div class="alert alert-success" role="alert">
						<%= request.getAttribute("sucesso") %>
					</div>
					<% } %>

					<form action="${pageContext.request.contextPath}/login"
						method="post">
						<div class="mb-3">
							<label class="form-label" for="email">E-mail</label> <input
								type="email" class="form-control" id="email" name="email"
								autocomplete="email" placeholder="seunome@email.com" required>
						</div>

						<div class="mb-4">
							<label class="form-label" for="senha">Senha</label> <input
								type="password" class="form-control" id="senha" name="senha"
								autocomplete="current-password" placeholder="Digite sua senha"
								required>
						</div>

						<button type="submit" class="btn btn-primary w-100">
							<i class="bi bi-box-arrow-in-right" aria-hidden="true"></i>
							Entrar
						</button>
					</form>
					<a class="d-block text-center mt-3" href="${pageContext.request.contextPath}/esqueci-senha">Esqueci minha senha</a>

					<section class="signup-cta" aria-labelledby="signup-title">
						<div class="signup-cta-copy">
							<span class="signup-cta-icon" aria-hidden="true">
								<i class="bi bi-person-plus"></i>
							</span>
							<div>
								<h3 id="signup-title">Primeiro acesso?</h3>
								<p>Crie sua conta para participar da comunidade do campus.</p>
							</div>
						</div>
						<a href="${pageContext.request.contextPath}/cadastro"
							class="btn btn-outline-primary w-100 signup-cta-button">
							Criar conta <i class="bi bi-arrow-right" aria-hidden="true"></i>
						</a>
					</section>
				</div>
			</section>
		</div>
	</main>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
	<% if (Boolean.TRUE.equals(request.getAttribute("cadastroConcluido"))) { %>
	<script>
		sessionStorage.removeItem('conectaCampus.cadastro.rascunho');
	</script>
	<% } %>
</body>
</html>
