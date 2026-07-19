<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="br.com.conectacampus.model.Usuario"%>
<%@ page import="java.util.List"%>
<%@ page import="br.com.conectacampus.model.MovimentoFinanceiro"%>
<%
Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
if (usuario == null) {
	response.sendRedirect(request.getContextPath() + "/login");
	return;
}
Integer totalUsuarios = (Integer) request.getAttribute("totalUsuarios");
Integer totalComunicados = (Integer) request.getAttribute("totalComunicados");
Integer totalTopicos = (Integer) request.getAttribute("totalTopicos");
Integer totalEnquetes = (Integer) request.getAttribute("totalEnquetes");
Integer totalFeedbacks = (Integer) request.getAttribute("totalFeedbacks");
int usuarios = totalUsuarios != null ? totalUsuarios : 0;
int comunicados = totalComunicados != null ? totalComunicados : 0;
int topicos = totalTopicos != null ? totalTopicos : 0;
int enquetes = totalEnquetes != null ? totalEnquetes : 0;
int feedbacks = totalFeedbacks != null ? totalFeedbacks : 0;
List<MovimentoFinanceiro> movimentos = (List<MovimentoFinanceiro>) request.getAttribute("movimentosFinanceiros");
String competenciaFinanceira = (String) request.getAttribute("competenciaFinanceira");
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Dashboard - Conecta Campus</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

	<a class="skip-link" href="#conteudo">Ir para o conteúdo</a>

	<nav class="navbar">
		<div class="container-fluid">
			<a class="navbar-brand"
				href="${pageContext.request.contextPath}/pages/home.jsp"><i
				class="bi bi-mortarboard-fill"></i> Conecta Campus</a>
			<div class="ms-auto d-flex align-items-center gap-3">
				<span class="navbar-user"><i class="bi bi-person-circle"></i>
					<%=usuario.getNome()%></span> <a class="btn btn-secondary"
					href="${pageContext.request.contextPath}/logout">Sair</a>
			</div>
		</div>
	</nav>

	<div class="app-shell">
		<aside class="sidebar">
			<ul>
				<li><a href="${pageContext.request.contextPath}/pages/home.jsp">Home</a></li>
				<li><a href="${pageContext.request.contextPath}/usuarios">Usuários</a></li>
				<li><a href="${pageContext.request.contextPath}/comunicados">Comunicados</a></li>
				<li><a href="${pageContext.request.contextPath}/forum">Fórum</a></li>
				<li><a href="${pageContext.request.contextPath}/enquetes">Enquetes</a></li>
				<li><a href="${pageContext.request.contextPath}/feedback">Feedback</a></li>
				<li><a class="active"
					href="${pageContext.request.contextPath}/dashboard">Dashboard</a></li>
			</ul>
		</aside>

		<main class="content" id="conteudo">

			<div class="page-header">
				<h1>Dashboard</h1>
				<p>Resumo da plataforma.</p>
			</div>

			

			<section class="card mt-4">
				<div
					class="card-header d-flex justify-content-between align-items-center">
					<span><i class="bi bi-cash-stack"></i> Transparência
						financeira</span>
					<form method="get"
						action="${pageContext.request.contextPath}/dashboard"
						class="d-flex gap-2">
						<input class="form-control" type="month" name="competencia"
							value="<%=competenciaFinanceira != null ? competenciaFinanceira : ""%>">
						<button class="btn btn-outline-primary">Filtrar</button>
					</form>
				</div>

				<div class="card-body">
					<%
					if (movimentos != null && !movimentos.isEmpty()) {
					%>
					<div class="table-responsive">
						<table class="table">
							<thead>
								<tr>
									<th>Data</th>
									<th>Tipo</th>
									<th>Categoria</th>
									<th>Descrição</th>
									<th class="text-end">Valor</th>
								</tr>
							</thead>
							<tbody>
								<%
								for (MovimentoFinanceiro movimento : movimentos) {
								%>
								<tr>
									<td><%=movimento.getData()%></td>
									<td><%=movimento.getTipo()%></td>
									<td><%=movimento.getCategoria()%></td>
									<td><%=movimento.getDescricao()%></td>
									<td class="text-end">R$ <%=movimento.getValor()%></td>
								</tr>
								<% } %>
							</tbody>
						</table>
					</div>
					<% } else { %>
					<p class="text-muted">Nenhum lançamento financeiro encontrado
						para este período.</p>
					<% } %>
				</div>
			</section>

		</main>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
