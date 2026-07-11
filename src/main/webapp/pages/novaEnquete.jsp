<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="br.com.conectacampus.model.Usuario"%>
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
<title>Nova enquete - Conecta Campus</title>
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
            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/enquetes">Voltar</a>
        </div>
    </div>
</nav>

<main class="content">
    <div class="page-header">
        <div>
            <p class="eyebrow">Votação</p>
            <h1 class="page-title"><i class="bi bi-plus-circle" aria-hidden="true"></i> Nova enquete</h1>
            <p class="page-subtitle">Crie uma pergunta objetiva com opções claras para votação.</p>
        </div>
    </div>

    <div class="card">
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/enquetes" method="post">
                <input type="hidden" name="acao" value="salvar">

                <div class="mb-3">
                    <label class="form-label" for="titulo">Título da enquete</label>
                    <input class="form-control" id="titulo" name="titulo" maxlength="120" placeholder="Ex.: Qual oficina você quer no próximo evento?" required>
                </div>

                <div class="mb-3">
                    <label class="form-label" for="descricao">Descrição</label>
                    <textarea class="form-control" id="descricao" name="descricao" rows="4" placeholder="Explique rapidamente o objetivo da votação."></textarea>
                </div>

                <div class="mb-3">
                    <label class="form-label" for="opcoes">Opções</label>
                    <textarea class="form-control" id="opcoes" name="opcoes" rows="6" placeholder="Digite uma opção por linha&#10;Oficina de IA&#10;Minicurso de Java&#10;Palestra de carreira" required></textarea>
                    <div class="form-text">Digite uma opção por linha. Recomenda-se pelo menos duas opções.</div>
                </div>

                <div class="action-row">
                    <button class="btn btn-primary" type="submit"><i class="bi bi-check-circle" aria-hidden="true"></i> Publicar enquete</button>
                    <a class="btn btn-secondary" href="${pageContext.request.contextPath}/enquetes">Cancelar</a>
                </div>
            </form>
        </div>
    </div>
</main>
</body>
</html>
