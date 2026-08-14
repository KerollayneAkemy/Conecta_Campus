<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String tokenRecuperacao = request.getParameter("token");
tokenRecuperacao = tokenRecuperacao == null ? ""
		: tokenRecuperacao.replace("&", "&amp;").replace("\"", "&quot;").replace("<", "&lt;").replace(">", "&gt;");
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Nova senha - Conecta Campus</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/login.css?v=4">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/passwordToggle.css?v=1">
<script defer
	src="${pageContext.request.contextPath}/js/passwordToggle.js?v=1"></script>
</head>
<body>
	<main class="container-fluid auth-layout">
		<div class="row min-vh-100">
			<section class="col-lg-7 brand-panel" aria-label="Conecta Campus">
				<div class="brand-copy">
					<div class="brand-kicker">
						<i class="bi bi-key-fill"></i> Nova senha
					</div>
					<h1>Conecta Campus</h1>
					<p>Escolha uma senha nova para voltar a acessar sua conta.</p>
				</div>
			</section>
			<section class="col-lg-5 auth-panel">
				<div class="card-login">
					<h2>Redefinir senha</h2>
					<p class="subtitulo">Crie uma senha com ao menos 6 caracteres.</p>

					<%
					if (request.getAttribute("erro") != null) {
					%><div
						class="alert alert-danger" role="alert"><%=request.getAttribute("erro")%></div>
					<%
					}
					%>
					<form action="${pageContext.request.contextPath}/redefinir-senha"
						method="post">
						<input type="hidden" name="token" value="<%=tokenRecuperacao%>">
						<div class="mb-3">
							<label class="form-label" for="senha">Nova senha</label><input
								class="form-control" id="senha" name="senha" type="password"
								minlength="6" autocomplete="new-password" required autofocus>
						</div>
						<div class="mb-4">
							<label class="form-label" for="confirmacao">Confirmar
								nova senha</label><input class="form-control" id="confirmacao"
								name="confirmacao" type="password" minlength="6"
								autocomplete="new-password" required>
						</div>
						<button class="btn btn-primary w-100">
							<i class="bi bi-check-circle"></i> Salvar nova senha
						</button>
					</form>
				</div>
			</section>
		</div>
	</main>
</body>
</html>
