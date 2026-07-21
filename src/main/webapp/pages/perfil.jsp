<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="br.com.conectacampus.model.Usuario"%>
<%!private String esc(String texto) {
		if (texto == null)
			return "";
		return texto.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
	}%>
<%
Usuario usuarioPerfil = (Usuario) request.getAttribute("usuario");
if (usuarioPerfil == null) {
	response.sendRedirect(request.getContextPath() + "/login");
	return;
}
String erro = (String) session.getAttribute("perfilErro");
String sucesso = (String) session.getAttribute("perfilSucesso");
session.removeAttribute("perfilErro");
session.removeAttribute("perfilSucesso");
request.setAttribute("paginaAtiva", "perfil");
request.setAttribute("tituloPagina", "Meu perfil - Conecta Campus");
%>
<%@ include file="/WEB-INF/includes/header.jsp"%>
<div class="page-header">
	<div>
		<p class="eyebrow">Minha conta</p>
		<h1 class="page-title">
			<i class="bi bi-person-gear"></i> Meu perfil
		</h1>
		<p class="page-subtitle">Atualize seus dados, foto e senha. Seu
			nível de acesso não pode ser alterado aqui.</p>
	</div>
</div>
<%
if (erro != null) {
%><div class="alert alert-danger"><%=esc(erro)%></div>
<%
}
%>
<%
if (sucesso != null) {
%><div class="alert alert-success"><%=esc(sucesso)%></div>
<%
}
%>
<div class="row g-4">
	<div class="col-lg-8">
		<div class="card">
			<div class="card-body">
				<form action="${pageContext.request.contextPath}/perfil"
					method="post" enctype="multipart/form-data">
					<input type="hidden" name="acao" value="dados">
					<div class="d-flex align-items-center gap-3 mb-4">
						<%
						if (usuarioPerfil.getFotoPerfil() != null && !usuarioPerfil.getFotoPerfil().isBlank()) {
						%><img
							class="profile-avatar"
							src="${pageContext.request.contextPath}/<%= esc(usuarioPerfil.getFotoPerfil()) %>"
							alt="Foto de perfil">
						<%
						} else {
						%><div class="profile-avatar profile-avatar-placeholder">
							<i class="bi bi-person"></i>
						</div>
						<%
						}
						%>
						<div>
							<label class="form-label" for="foto">Foto de perfil</label><input
								class="form-control" id="foto" name="foto" type="file"
								accept="image/jpeg,image/png,image/gif,image/webp">
							<div class="form-text">JPG, PNG, GIF ou WEBP; máximo de 2
								MB.</div>
						</div>
					</div>
					<div class="row g-3">
						<div class="col-md-6">
							<label class="form-label" for="nome">Nome</label><input
								class="form-control" id="nome" name="nome"
								value="<%=esc(usuarioPerfil.getNome())%>" required>
						</div>
						<div class="col-md-6">
							<label class="form-label" for="email">E-mail</label><input
								class="form-control" id="email" name="email" type="email"
								value="<%=esc(usuarioPerfil.getEmail())%>" required>
						</div>
						<div class="col-md-6">
							<label class="form-label" for="curso">Curso</label><input
								class="form-control" id="curso" name="curso"
								value="<%=esc(usuarioPerfil.getCurso())%>">
						</div>
						<div class="col-md-6">
							<label class="form-label">Perfil de acesso</label><input
								class="form-control"
								value="<%=usuarioPerfil.getPerfil() != null ? esc(usuarioPerfil.getPerfil().getNome()) : ""%>"
								disabled>
						</div>
					</div>
					<button class="btn btn-primary mt-4" type="submit">
						<i class="bi bi-check-circle"></i> Salvar perfil
					</button>
				</form>
			</div>
		</div>
	</div>
	<div class="col-lg-4">
		<div class="card">
			<div class="card-body">
				<h2 class="h5 mb-3">Alterar senha</h2>
				<form action="${pageContext.request.contextPath}/perfil"
					method="post">
					<input type="hidden" name="acao" value="senha">
					<div class="mb-3">
						<label class="form-label" for="senhaAtual">Senha atual</label><input
							class="form-control" id="senhaAtual" name="senhaAtual"
							type="password" required>
					</div>
					<div class="mb-3">
						<label class="form-label" for="novaSenha">Nova senha</label><input
							class="form-control" id="novaSenha" name="novaSenha"
							type="password" minlength="6" required>
					</div>
					<div class="mb-3">
						<label class="form-label" for="confirmacaoSenha">Confirmar
							nova senha</label><input class="form-control" id="confirmacaoSenha"
							name="confirmacaoSenha" type="password" minlength="6" required>
					</div>
					<button class="btn btn-outline-primary w-100" type="submit">Alterar
						senha</button>
				</form>
			</div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/includes/footer.jsp"%>
