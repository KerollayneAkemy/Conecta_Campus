<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="br.com.conectacampus.model.Enquete"%>
<%@ page import="br.com.conectacampus.model.OpcaoEnquete"%>
<%@ page import="br.com.conectacampus.model.Usuario"%>
<%
Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
if (usuario == null) {
	response.sendRedirect(request.getContextPath() + "/login");
	return;
}
List<Enquete> enquetes = (List<Enquete>) request.getAttribute("listaEnquetes");
if (enquetes == null) {
	enquetes = (List<Enquete>) request.getAttribute("enquetes");
}
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Enquetes - Conecta Campus</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav class="navbar">
    <div class="container-fluid">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/pages/home.jsp"><i class="bi bi-mortarboard-fill" aria-hidden="true"></i> Conecta Campus</a>
        <div class="top-actions ms-auto">
            <span class="navbar-user"><i class="bi bi-person-circle" aria-hidden="true"></i> <%=usuario.getNome()%></span>
            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/pages/home.jsp">Voltar</a>
        </div>
    </div>
</nav>

<main class="content">
    <div class="page-header">
        <div>
            <p class="eyebrow">Votação</p>
            <h1 class="page-title"><i class="bi bi-ui-checks-grid" aria-hidden="true"></i> Enquetes</h1>
            <p class="page-subtitle">Vote e ajude a comunidade a tomar decisões melhores.</p>
        </div>
        <a class="btn btn-primary" href="${pageContext.request.contextPath}/enquetes?acao=novo">
            <i class="bi bi-plus-circle" aria-hidden="true"></i> Nova enquete
        </a>
    </div>

    <div class="row g-4">
        <%
        if (enquetes != null && !enquetes.isEmpty()) {
            for (Enquete e : enquetes) {
        %>
        <div class="col-lg-6">
            <article class="card poll-card h-100">
                <div class="card-body">
                    <div class="d-flex align-items-start gap-3 mb-3">
                        <span class="metric-icon"><i class="bi bi-bar-chart-steps" aria-hidden="true"></i></span>
                        <div>
                            <h2 class="h4 mb-1"><%=e.getTitulo()%></h2>
                            <p class="page-subtitle"><%=e.getDescricao() != null ? e.getDescricao() : ""%></p>
                        </div>
                    </div>

                    <form action="${pageContext.request.contextPath}/votos" method="post">
                        <input type="hidden" name="idUsuario" value="<%=usuario.getIdUsuario()%>">
                        <%
                        if (e.getOpcoes() != null && !e.getOpcoes().isEmpty()) {
                            for (OpcaoEnquete op : e.getOpcoes()) {
                                String opcaoId = "opcao-" + e.getIdEnquete() + "-" + op.getIdOpcao();
                        %>
                        <label class="poll-option mb-2" for="<%=opcaoId%>">
                            <input class="form-check-input" type="radio" id="<%=opcaoId%>" name="idOpcao" value="<%=op.getIdOpcao()%>" required>
                            <span><%=op.getDescricao()%></span>
                        </label>
                        <%
                            }
                        } else {
                        %>
                        <div class="alert alert-warning">Esta enquete ainda não possui opções cadastradas.</div>
                        <% } %>

                        <button class="btn btn-primary mt-3" type="submit">
                            <i class="bi bi-check-circle-fill" aria-hidden="true"></i> Confirmar voto
                        </button>
                    </form>
                </div>
            </article>
        </div>
        <%
            }
        } else {
        %>
        <div class="col-12">
            <div class="card">
                <div class="empty-state">
                    <i class="bi bi-clipboard-x display-5" aria-hidden="true"></i>
                    <h2 class="h5 mt-3">Nenhuma enquete disponível</h2>
                    <p class="page-subtitle">Quando uma votação for publicada, ela aparecerá aqui.</p>
                    <a class="btn btn-primary mt-3" href="${pageContext.request.contextPath}/enquetes?acao=novo">Criar primeira enquete</a>
                </div>
            </div>
        </div>
        <% } %>
    </div>
</main>
</body>
</html>
