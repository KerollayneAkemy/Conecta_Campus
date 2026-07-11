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
<title>Novo comunicado - Conecta Campus</title>
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
            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/comunicados">Voltar</a>
        </div>
    </div>
</nav>
<main class="content">
    <div class="page-header">
        <div>
            <p class="eyebrow">Publicação</p>
            <h1 class="page-title"><i class="bi bi-megaphone-fill" aria-hidden="true"></i> Novo comunicado</h1>
            <p class="page-subtitle">Crie um aviso claro para a comunidade acadêmica.</p>
        </div>
    </div>

    <div class="card">
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/comunicados" method="post">
                <input type="hidden" name="acao" value="salvar">
                <input type="hidden" name="idUsuario" value="<%=usuario.getIdUsuario()%>">

                <div class="mb-3">
                    <label class="form-label" for="titulo">Título</label>
                    <input type="text" id="titulo" name="titulo" class="form-control" maxlength="120" required>
                </div>

                <div class="mb-3">
                    <label class="form-label" for="mensagem">Mensagem</label>
                    <textarea id="mensagem" name="mensagem" rows="6" class="form-control" required></textarea>
                </div>

                <div class="row g-3">
                    <div class="col-md-6">
                        <label class="form-label" for="categoria">Categoria</label>
                        <select id="categoria" name="idCategoria" class="form-select">
                            <option value="1">Acadêmico</option>
                            <option value="2">Eventos</option>
                            <option value="3">Avisos</option>
                        </select>
                    </div>
                    <div class="col-md-6">
                        <label class="form-label" for="prioridade">Prioridade</label>
                        <select id="prioridade" name="prioridade" class="form-select">
                            <option>BAIXA</option>
                            <option>MÉDIA</option>
                            <option>ALTA</option>
                        </select>
                    </div>
                </div>

                <div class="mt-4 action-row">
                    <button type="submit" class="btn btn-primary"><i class="bi bi-check-circle" aria-hidden="true"></i> Salvar</button>
                    <a href="${pageContext.request.contextPath}/comunicados" class="btn btn-secondary">Cancelar</a>
                </div>
            </form>
        </div>
    </div>
</main>
</body>
</html>
