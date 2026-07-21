<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List,br.com.conectacampus.model.Categoria"%>
<%@ page
	import="br.com.conectacampus.model.Usuario,br.com.conectacampus.util.Autorizacao"%>
<%
List<Categoria> categorias = (List<Categoria>) request.getAttribute("listaCategorias");
Usuario usuarioCategorias = (Usuario) session.getAttribute("usuarioLogado");
boolean podeExcluirCategoria = Autorizacao.ehAdministrador(usuarioCategorias);
%>
<%@ include file="/WEB-INF/includes/header.jsp"%>
<div class="page-header">
	<div>
		<p class="eyebrow">Administração</p>
		<h1 class="page-title">
			<i class="bi bi-tags-fill"></i> Categorias
		</h1>
		<p class="page-subtitle">Categorias usadas nos comunicados.</p>
	</div>
</div>
<div class="card mb-4">
	<div class="card-body">
		<form action="${pageContext.request.contextPath}/categorias"
			method="post" class="row g-3">
			<div class="col-md-9">
				<label class="form-label" for="nome">Nome da categoria</label><input
					class="form-control" id="nome" name="nome"
					placeholder="Ex.: Acadêmico" required>
			</div>
			<div class="col-md-3 d-flex align-items-end">
				<button class="btn btn-primary w-100">Criar categoria</button>
			</div>
		</form>
	</div>
</div>
<div class="card">
	<div class="card-header">Categorias cadastradas</div>
	<div class="card-body">
		<%
		if (categorias != null)
			for (Categoria c : categorias) {
		%><span class="badge bg-light text-dark border me-2 mb-2 p-2"><%=c.getNome()%>
			<%
			if (podeExcluirCategoria) {
			%> <a class="text-danger ms-1"
			href="${pageContext.request.contextPath}/categorias?acao=excluir&id=<%=c.getIdCategoria()%>"
			data-confirm="true" data-confirm-message="Excluir categoria?"><i
				class="bi bi-x-lg"></i></a> <% } %></span>
		<% } %>
	</div>
</div>
<%@ include file="/WEB-INF/includes/footer.jsp"%>
