<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="br.com.conectacampus.model.Usuario"%>
<%@ page import="br.com.conectacampus.util.Autorizacao"%>
<%
Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
if (usuario == null) {
	response.sendRedirect(request.getContextPath() + "/login");
	return;
}
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Novo tópico - Conecta Campus</title>
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
    <div class="page-header">
        <div>
            <p class="eyebrow">Fórum</p>
            <h1 class="page-title"><i class="bi bi-chat-square-text" aria-hidden="true"></i> Novo tópico</h1>
            <p class="page-subtitle">Abra uma conversa clara para receber respostas melhores.</p>
        </div>
    </div>

    <div class="card">
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/forum" method="post">
                <input type="hidden" name="acao" value="salvar">
                <input type="hidden" name="idUsuario" value="<%=usuario.getIdUsuario()%>">

                <div class="mb-3">
                    <label class="form-label" for="titulo">Título</label>
                    <input class="form-control" id="titulo" type="text" name="titulo" maxlength="120" placeholder="Ex.: Dúvida sobre calendário de provas" required>
                </div>

                <% if (Autorizacao.podePublicarInstitucional(usuario)) { %>
                <details class="mb-3">
                    <summary class="fw-semibold">Adicionar enquete a este tópico (opcional)</summary>
                    <div class="mt-3">
                        <label class="form-label" for="perguntaEnquete">Pergunta</label>
                        <input class="form-control" id="perguntaEnquete" name="perguntaEnquete" maxlength="200" placeholder="Ex.: Qual data é melhor para o evento?">
                    </div>
                    <div class="mt-3">
                        <label class="form-label" for="descricaoEnquete">Contexto</label>
                        <input class="form-control" id="descricaoEnquete" name="descricaoEnquete" maxlength="300">
                    </div>
                    <div class="mt-3">
                        <label class="form-label" for="opcoesEnquete">Opções (uma por linha)</label>
                        <textarea class="form-control" id="opcoesEnquete" name="opcoesEnquete" rows="4" placeholder="Opção 1&#10;Opção 2"></textarea>
                    </div>
                </details>
                <% } %>

                <div class="mb-3">
                    <label class="form-label" for="mensagem">Mensagem</label>
                    <textarea class="form-control" id="mensagem" name="mensagem" rows="7" placeholder="Explique o contexto da sua dúvida ou ideia..." required></textarea>
                </div>

                <div class="action-row">
                    <button type="submit" class="btn btn-primary"><i class="bi bi-send-fill" aria-hidden="true"></i> Publicar tópico</button>
                    <a href="${pageContext.request.contextPath}/forum" class="btn btn-secondary">Cancelar</a>
                </div>
            </form>
        </div>
    </div>
</main>
</body>
</html>
