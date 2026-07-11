<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="br.com.conectacampus.model.Comunicado"%>
<%@ page import="br.com.conectacampus.model.Usuario"%>
<%
Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
if (usuario == null) {
	response.sendRedirect(request.getContextPath() + "/login");
	return;
}
Comunicado comunicado = (Comunicado) request.getAttribute("comunicado");
if (comunicado == null) {
	comunicado = new Comunicado();
}
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Editar comunicado - Conecta Campus</title>
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
            <p class="eyebrow">Edição</p>
            <h1 class="page-title"><i class="bi bi-pencil-square" aria-hidden="true"></i> Editar comunicado</h1>
            <p class="page-subtitle">Atualize o conteúdo e a prioridade do aviso.</p>
        </div>
    </div>

    <div class="card">
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/comunicados" method="post">
                <input type="hidden" name="acao" value="atualizar">
                <input type="hidden" name="idComunicado" value="<%=comunicado.getIdComunicado()%>">
                <input type="hidden" name="idCategoria" value="1">
                <input type="hidden" name="idUsuario" value="<%=usuario.getIdUsuario()%>">

                <div class="mb-3">
                    <label class="form-label" for="titulo">Título</label>
                    <input class="form-control" id="titulo" name="titulo" value="<%=comunicado.getTitulo() != null ? comunicado.getTitulo() : ""%>" required>
                </div>

                <div class="mb-3">
                    <label class="form-label" for="mensagem">Mensagem</label>
                    <textarea class="form-control" id="mensagem" rows="6" name="mensagem" required><%=comunicado.getMensagem() != null ? comunicado.getMensagem() : ""%></textarea>
                </div>

                <div class="row g-3">
                    <div class="col-md-6">
                        <label class="form-label" for="prioridade">Prioridade</label>
                        <select class="form-select" id="prioridade" name="prioridade">
                            <option>BAIXA</option>
                            <option>MÉDIA</option>
                            <option>ALTA</option>
                        </select>
                    </div>
                    <div class="col-md-6">
                        <label class="form-label" for="status">Status</label>
                        <select class="form-select" id="status" name="status">
                            <option>ATIVO</option>
                            <option>INATIVO</option>
                        </select>
                    </div>
                </div>

                <div class="mt-4 action-row">
                    <button type="submit" class="btn btn-primary"><i class="bi bi-check-circle" aria-hidden="true"></i> Salvar alterações</button>
                    <a href="${pageContext.request.contextPath}/comunicados" class="btn btn-secondary">Cancelar</a>
                </div>
            </form>
        </div>
    </div>
</main>
</body>
</html>
