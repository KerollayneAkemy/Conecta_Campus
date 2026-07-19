<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="br.com.conectacampus.model.Forum"%>
<%@ page import="br.com.conectacampus.model.RespostaForum"%>
<%@ page import="br.com.conectacampus.model.Usuario"%>
<%@ page import="br.com.conectacampus.model.Enquete"%>
<%@ page import="br.com.conectacampus.model.OpcaoEnquete"%>
<%
Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
if (usuario == null) {
	response.sendRedirect(request.getContextPath() + "/login");
	return;
}
Forum topico = (Forum) request.getAttribute("forum");
List<RespostaForum> respostas = (List<RespostaForum>) request.getAttribute("respostas");
Enquete enquete = (Enquete) request.getAttribute("enquete");
boolean editando = "editar".equals(request.getParameter("acao"));
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Tópico - Conecta Campus</title>
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
            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/forum">Voltar</a>
        </div>
    </div>
</nav>
<main class="content">
    <% if (editando) { %>
    <div class="page-header">
        <div>
            <p class="eyebrow">Fórum</p>
            <h1 class="page-title"><i class="bi bi-pencil-square" aria-hidden="true"></i> Editar tópico</h1>
            <p class="page-subtitle">Atualize a conversa publicada no fórum.</p>
        </div>
    </div>

    <div class="card">
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/forum" method="post">
                <input type="hidden" name="acao" value="atualizar">
                <input type="hidden" name="id" value="<%=topico != null ? topico.getIdForum() : 0%>">
                <input type="hidden" name="idUsuario" value="<%=usuario.getIdUsuario()%>">
                <div class="mb-3">
                    <label class="form-label" for="titulo">Título</label>
                    <input class="form-control" id="titulo" name="titulo" value="<%=topico != null && topico.getTitulo() != null ? topico.getTitulo() : ""%>" required>
                </div>
                <div class="mb-3">
                    <label class="form-label" for="mensagem">Mensagem</label>
                    <textarea class="form-control" id="mensagem" name="mensagem" rows="7" required><%=topico != null && topico.getMensagem() != null ? topico.getMensagem() : ""%></textarea>
                </div>
                <div class="action-row">
                    <button class="btn btn-primary" type="submit"><i class="bi bi-check-circle" aria-hidden="true"></i> Salvar alterações</button>
                    <a href="${pageContext.request.contextPath}/forum" class="btn btn-secondary">Cancelar</a>
                </div>
            </form>
        </div>
    </div>
    <% } else { %>
    <% if (enquete != null) { %>
    <section class="card mb-4" aria-labelledby="titulo-enquete">
        <div class="card-header" id="titulo-enquete"><i class="bi bi-bar-chart-steps" aria-hidden="true"></i> Enquete do tópico</div>
        <div class="card-body">
            <h2 class="h5"><%=enquete.getTitulo()%></h2>
            <% if (enquete.getDescricao() != null && !enquete.getDescricao().isBlank()) { %><p class="text-muted"><%=enquete.getDescricao()%></p><% } %>
            <form action="${pageContext.request.contextPath}/votos" method="post">
                <% for (OpcaoEnquete opcao : enquete.getOpcoes()) { %>
                <div class="form-check mb-2">
                    <input class="form-check-input" type="radio" name="idOpcao" id="opcao-<%=opcao.getIdOpcao()%>" value="<%=opcao.getIdOpcao()%>" required>
                    <label class="form-check-label" for="opcao-<%=opcao.getIdOpcao()%>"><%=opcao.getDescricao()%></label>
                </div>
                <% } %>
                <button class="btn btn-primary mt-2" type="submit">Confirmar voto</button>
            </form>
        </div>
    </section>
    <% } %>

    <div class="card mb-4">
        <div class="card-body">
            <p class="eyebrow">Discussão</p>
            <h1><%=topico != null ? topico.getTitulo() : "Tópico"%></h1>
            <p class="text-muted"><i class="bi bi-eye" aria-hidden="true"></i> <%=topico != null ? topico.getVisualizacoes() : 0%> visualizações</p>
            <hr>
            <p class="mb-0"><%=topico != null ? topico.getMensagem() : ""%></p>
        </div>
    </div>

    <div class="card mb-4">
        <div class="card-header">Respostas</div>
        <div class="card-body">
            <%
            if (respostas != null && !respostas.isEmpty()) {
                for (RespostaForum r : respostas) {
            %>
            <article class="border rounded p-3 mb-3">
                <h2 class="h6"><i class="bi bi-person-circle" aria-hidden="true"></i> <%=r.getUsuario() != null ? r.getUsuario().getNome() : "Usuário"%></h2>
                <p class="mb-0"><%=r.getResposta()%></p>
            </article>
            <%
                }
            } else {
            %>
            <div class="empty-state">
                <i class="bi bi-chat-left-text display-6" aria-hidden="true"></i>
                <h2 class="h5 mt-3">Ainda não existem respostas</h2>
                <p class="page-subtitle">Participe da conversa com uma resposta útil.</p>
            </div>
            <% } %>
        </div>
    </div>

    <div class="card">
        <div class="card-header">Responder</div>
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/respostasForum" method="post">
                <input type="hidden" name="idForum" value="<%=topico != null ? topico.getIdForum() : 0%>">
                <input type="hidden" name="idUsuario" value="<%=usuario.getIdUsuario()%>">
                <div class="mb-3">
                    <label class="form-label" for="resposta">Sua resposta</label>
                    <textarea id="resposta" name="resposta" class="form-control" rows="5" placeholder="Escreva sua contribuição..." required></textarea>
                </div>
                <button class="btn btn-primary" type="submit"><i class="bi bi-send-fill" aria-hidden="true"></i> Publicar resposta</button>
            </form>
        </div>
    </div>
    <% } %>
</main>
</body>
</html>
