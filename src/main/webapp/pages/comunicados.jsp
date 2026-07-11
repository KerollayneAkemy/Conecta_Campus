<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="br.com.conectacampus.model.Comunicado"%>
<%@ page import="br.com.conectacampus.model.Usuario"%>
<%
Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
if (usuario == null) {
	response.sendRedirect(request.getContextPath() + "/login");
	return;
}
List<Comunicado> comunicados = (List<Comunicado>) request.getAttribute("listaComunicados");
if (comunicados == null) {
	comunicados = (List<Comunicado>) request.getAttribute("comunicados");
}
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Comunicados - Conecta Campus</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<a class="skip-link" href="#conteudo">Ir para o conteúdo</a>

<nav class="navbar">
    <div class="container-fluid">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/pages/home.jsp"><i class="bi bi-mortarboard-fill" aria-hidden="true"></i> Conecta Campus</a>
        <div class="ms-auto d-flex align-items-center gap-3">
            <span class="navbar-user"><i class="bi bi-person-circle" aria-hidden="true"></i> <%=usuario.getNome()%></span>
            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/logout">Sair</a>
        </div>
    </div>
</nav>

<main class="content" id="conteudo">
    <div class="page-header">
        <div>
            <p class="eyebrow">Comunicação oficial</p>
            <h1 class="page-title"><i class="bi bi-megaphone-fill" aria-hidden="true"></i> Comunicados</h1>
            <p class="page-subtitle">Gerencie avisos, prioridades e publicações do campus.</p>
        </div>
        <a href="${pageContext.request.contextPath}/comunicados?acao=novo" class="btn btn-primary">
            <i class="bi bi-plus-circle" aria-hidden="true"></i> Novo comunicado
        </a>
    </div>

    <div class="card">
        <div class="card-header">Lista de comunicados</div>
        <div class="table-responsive">
            <table class="table table-hover align-middle">
                <thead>
                    <tr>
                        <th>Título</th>
                        <th>Prioridade</th>
                        <th>Status</th>
                        <th>Visualizações</th>
                        <th>Ações</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                    if (comunicados != null && !comunicados.isEmpty()) {
                        for (Comunicado c : comunicados) {
                    %>
                    <tr>
                        <td><strong><%=c.getTitulo()%></strong></td>
                        <td><span class="badge bg-warning text-dark"><%=c.getPrioridade()%></span></td>
                        <td><span class="badge bg-success"><%=c.getStatus()%></span></td>
                        <td><%=c.getVisualizacoes()%></td>
                        <td>
                            <div class="action-row">
                                <a class="btn btn-sm btn-outline-primary" aria-label="Editar comunicado <%=c.getTitulo()%>" href="${pageContext.request.contextPath}/comunicados?acao=editar&id=<%=c.getIdComunicado()%>"><i class="bi bi-pencil" aria-hidden="true"></i></a>
                                <a class="btn btn-sm btn-outline-danger" aria-label="Excluir comunicado <%=c.getTitulo()%>" href="${pageContext.request.contextPath}/comunicados?acao=excluir&id=<%=c.getIdComunicado()%>"><i class="bi bi-trash" aria-hidden="true"></i></a>
                            </div>
                        </td>
                    </tr>
                    <%
                        }
                    } else {
                    %>
                    <tr>
                        <td colspan="5">
                            <div class="empty-state">
                                <i class="bi bi-inbox display-5" aria-hidden="true"></i>
                                <h2 class="h5 mt-3">Nenhum comunicado encontrado</h2>
                                <p class="page-subtitle">Publique o primeiro aviso para a comunidade.</p>
                            </div>
                        </td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
    </div>
</main>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
