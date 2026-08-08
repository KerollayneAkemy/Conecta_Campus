<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Recuperar senha - Conecta Campus</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/login.css?v=4">
</head>
<body>
	<main class="container-fluid auth-layout">
		<div class="row min-vh-100">
			<section class="col-lg-7 brand-panel" aria-label="Conecta Campus">
				<div class="brand-copy">
					<div class="brand-kicker">
						<i class="bi bi-shield-lock-fill"></i> Recuperação segura
					</div>
					<h1>Conecta Campus</h1>
					<p>Use o e-mail cadastrado para receber um link seguro de
						redefinição de senha.</p>
				</div>
			</section>
			<section class="col-lg-5 auth-panel">
				<div class="card-login">
					<h2>Esqueci minha senha</h2>
					<p class="subtitulo">Informe seu e-mail. O link de recuperação
						vale por 30 minutos.</p>
					<%
					if (request.getAttribute("erro") != null) {
					%><div
						class="alert alert-danger" role="alert"><%=request.getAttribute("erro")%></div>
					<%
					}
					%>
					<%
					if (request.getAttribute("sucesso") != null) {
					%><div
						class="alert alert-success" role="alert"><%=request.getAttribute("sucesso")%></div>
					<%
					}
					%>
					<form action="${pageContext.request.contextPath}/esqueci-senha"
						method="post">
						<div class="mb-4">
							<label class="form-label" for="email">E-mail cadastrado</label><input
								class="form-control" id="email" name="email" type="email"
								autocomplete="email" required autofocus>
						</div>
						<button class="btn btn-primary w-100">
							<i class="bi bi-send"></i> Enviar link de recuperação
						</button>
					</form>
					<a class="btn btn-outline-primary w-100 mt-3"
						href="${pageContext.request.contextPath}/login"><i
						class="bi bi-arrow-left"></i> Voltar para o login</a>
				</div>
			</section>
		</div>
	</main>
</body>
</html>
