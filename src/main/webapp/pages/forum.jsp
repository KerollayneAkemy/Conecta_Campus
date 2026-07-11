<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="br.com.conectacampus.model.Forum"%>
<%@ page import="br.com.conectacampus.model.Usuario"%>
<%
Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
if (usuario == null) {
	response.sendRedirect(request.getContextPath() + "/login");
	return;
}
List<Forum> lista = (List<Forum>) request.getAttribute("foruns");
if (lista == null) {
	lista = (List<Forum>) request.getAttribute("listaForum");
}
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Fórum - Conecta Campus</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<main class="content">
    <div class="page-header">
        <div>
            <p class="eyebrow">Comunidade</p>
            <h1 class="page-title"><i class="bi bi-chat-dots-fill" aria-hidden="true"></i> Fórum</h1>
            <p class="page-subtitle">Compartilhe dúvidas, ideias e discussões acadêmicas.</p>
        </div>
        <a href="${pageContext.request.contextPath}/forum?acao=novo" class="btn btn-primary">
            <i class="bi bi-plus-circle" aria-hidden="true"></i> Novo tópico
        </a>
    </div>

    <div class="card">
        <div class="card-header">Tópicos recentes</div>
        <div class="list-group list-group-flush">
            <%
            if (lista != null && !lista.isEmpty()) {
                for (Forum f : lista) {
            %>
            <div class="list-group-item">
                <div class="d-flex justify-content-between gap-3">
                    <a class="topic-card flex-grow-1" href="${pageContext.request.contextPath}/forum?acao=visualizar&id=<%=f.getIdForum()%>">
                        <h2 class="h5 mb-2"><i class="bi bi-chat-square-text" aria-hidden="true"></i> <%=f.getTitulo()%></h2>
                        <p class="mb-2 text-muted"><%=f.getMensagem()%></p>
                        <small class="text-muted"><i class="bi bi-eye" aria-hidden="true"></i> <%=f.getVisualizacoes()%> visualizações</small>
                    </a>
                    <div class="action-row">
                        <a class="btn btn-outline-primary btn-sm" aria-label="Editar tópico <%=f.getTitulo()%>" href="${pageContext.request.contextPath}/forum?acao=editar&id=<%=f.getIdForum()%>"><i class="bi bi-pencil" aria-hidden="true"></i></a>
                        <a class="btn btn-outline-danger btn-sm" aria-label="Excluir tópico <%=f.getTitulo()%>" href="${pageContext.request.contextPath}/forum?acao=excluir&id=<%=f.getIdForum()%>"><i class="bi bi-trash" aria-hidden="true"></i></a>
                    </div>
                </div>
            </div>
            <%
                }
            } else {
            %>
            <div class="empty-state">
                <i class="bi bi-chat-square display-5" aria-hidden="true"></i>
                <h2 class="h5 mt-3">Nenhum tópico criado</h2>
                <p class="page-subtitle">Seja a primeira pessoa a iniciar uma discussão.</p>
            </div>
            <% } %>
        </div>
    </div>
</main>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
