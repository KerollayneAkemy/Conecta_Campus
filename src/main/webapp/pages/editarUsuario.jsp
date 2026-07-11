<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="br.com.conectacampus.model.Usuario"%>
<%
Usuario usuario = (Usuario) request.getAttribute("usuario");
if (usuario == null) {
	usuario = new Usuario();
}
Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
if (usuarioLogado == null) {
	response.sendRedirect(request.getContextPath() + "/login");
	return;
}
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Editar usuário - Conecta Campus</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav class="navbar">
    <div class="container-fluid">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/pages/home.jsp"><i class="bi bi-mortarboard-fill" aria-hidden="true"></i> Conecta Campus</a>
        <div class="top-actions ms-auto">
            <span class="navbar-user"><i class="bi bi-person-circle" aria-hidden="true"></i> <%=usuarioLogado.getNome()%></span>
            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/usuarios">Voltar</a>
        </div>
    </div>
</nav>
<main class="content">
    <div class="page-header">
        <div>
            <p class="eyebrow">Administração</p>
            <h1 class="page-title"><i class="bi bi-person-gear" aria-hidden="true"></i> Editar usuário</h1>
            <p class="page-subtitle">Atualize os dados principais da conta.</p>
        </div>
    </div>

    <div class="card">
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/usuarios" method="post">
                <input type="hidden" name="acao" value="atualizar">
                <input type="hidden" name="id" value="<%=usuario.getIdUsuario()%>">

                <div class="row g-3">
                    <div class="col-md-6">
                        <label class="form-label" for="nome">Nome</label>
                        <input class="form-control" id="nome" name="nome" value="<%=usuario.getNome() != null ? usuario.getNome() : ""%>" required>
                    </div>
                    <div class="col-md-6">
                        <label class="form-label" for="email">E-mail</label>
                        <input class="form-control" id="email" type="email" name="email" value="<%=usuario.getEmail() != null ? usuario.getEmail() : ""%>" required>
                    </div>
                    <div class="col-md-6">
                        <label class="form-label" for="curso">Curso</label>
                        <input class="form-control" id="curso" name="curso" value="<%=usuario.getCurso() != null ? usuario.getCurso() : ""%>" required>
                    </div>
                    <div class="col-md-3">
                        <label class="form-label" for="idPerfil">Perfil</label>
                        <select class="form-select" id="idPerfil" name="idPerfil">
                            <option value="1">Aluno</option>
                            <option value="2">Professor</option>
                            <option value="3">Coordenação</option>
                        </select>
                    </div>
                    <div class="col-md-3">
                        <label class="form-label" for="ativo">Status</label>
                        <select class="form-select" id="ativo" name="ativo">
                            <option value="true">Ativo</option>
                            <option value="false">Inativo</option>
                        </select>
                    </div>
                    <div class="col-12">
                        <label class="form-label" for="senha">Senha</label>
                        <input class="form-control" id="senha" type="password" name="senha" placeholder="Informe uma nova senha para salvar" required>
                    </div>
                </div>

                <div class="mt-4 action-row">
                    <button class="btn btn-primary" type="submit"><i class="bi bi-check-circle" aria-hidden="true"></i> Salvar alterações</button>
                    <a href="${pageContext.request.contextPath}/usuarios" class="btn btn-secondary">Cancelar</a>
                </div>
            </form>
        </div>
    </div>
</main>
</body>
</html>
