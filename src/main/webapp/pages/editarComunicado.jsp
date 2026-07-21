<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="br.com.conectacampus.model.Categoria"%>
<%@ page import="br.com.conectacampus.model.Comunicado"%>
<%
    Comunicado comunicado = (Comunicado) request.getAttribute("comunicado");
    if (comunicado == null) {
        comunicado = new Comunicado();
    }

    List<Categoria> categorias = (List<Categoria>) request.getAttribute("listaCategorias");

    String prioridadeAtual = comunicado.getPrioridade() != null ? comunicado.getPrioridade() : "MEDIA";
    String statusAtual = comunicado.getStatus() != null ? comunicado.getStatus() : "ATIVO";
    int idCategoriaAtual = comunicado.getIdCategoria();

    request.setAttribute("paginaAtiva", "comunicados");
    request.setAttribute("tituloPagina", "Editar Comunicado - Conecta Campus");
%>
<%@ include file="/WEB-INF/includes/header.jsp"%>

<div class="page-header">
	<div>
		<p class="eyebrow">Edição</p>
		<h1 class="page-title">
			<i class="bi bi-pencil-square" aria-hidden="true"></i> Editar
			comunicado
		</h1>
		<p class="page-subtitle">Atualize o conteúdo e a prioridade do
			aviso.</p>
	</div>
	<a href="${pageContext.request.contextPath}/comunicados"
		class="btn btn-secondary"> <i class="bi bi-arrow-left"
		aria-hidden="true"></i> Voltar
	</a>
</div>

<div class="card">
	<div class="card-body">
		<form action="${pageContext.request.contextPath}/comunicados"
			method="post" enctype="multipart/form-data">
			<input type="hidden" name="acao" value="atualizar"> <input
				type="hidden" name="idComunicado"
				value="<%=comunicado.getIdComunicado()%>">

			<div class="mb-3">
				<label class="form-label" for="titulo">Título</label> <input
					class="form-control" id="titulo" name="titulo" maxlength="200"
					value="<%=comunicado.getTitulo() != null ? comunicado.getTitulo() : ""%>"
					required>
			</div>

			<div class="mb-3">
				<label class="form-label" for="mensagem">Mensagem</label>
				<textarea class="form-control" id="mensagem" rows="6"
					name="mensagem" required><%=comunicado.getMensagem() != null ? comunicado.getMensagem() : ""%></textarea>
			</div>

			<div class="mb-3">
				<label class="form-label" for="imagem">Trocar imagem <span
					class="text-muted fw-normal">(opcional)</span></label>
				<% if (comunicado.getImagem() != null && !comunicado.getImagem().isBlank()) { %><img
					class="img-thumbnail d-block mb-2" style="max-height: 160px"
					src="${pageContext.request.contextPath}/<%=comunicado.getImagem()%>"
					alt="Imagem atual do comunicado">
				<% } %>
				<input class="form-control" id="imagem" name="imagem" type="file"
					accept="image/jpeg,image/png,image/gif,image/webp">
				<div class="form-text">Deixe vazio para manter a imagem atual.</div>
			</div>

			<div class="row g-3">
				<div class="col-md-4">
					<label class="form-label" for="categoria">Categoria</label> <select
						class="form-select" id="categoria" name="idCategoria" required>
						<option value="">Selecione...</option>
						<%
                        if (categorias != null) {
                            for (Categoria cat : categorias) {
                                boolean selecionado = (cat.getIdCategoria() == idCategoriaAtual);
                        %>
						<option value="<%=cat.getIdCategoria()%>"
							<%=selecionado ? "selected" : ""%>><%=cat.getNome()%></option>
						<%
                            }
                        }
                        %>
					</select>
				</div>
				<div class="col-md-4">
					<label class="form-label" for="prioridade">Prioridade</label> <select
						class="form-select" id="prioridade" name="prioridade">
						<option value="BAIXA"
							<%="BAIXA".equals(prioridadeAtual) ? "selected" : ""%>>BAIXA</option>
						<option value="MEDIA"
							<%="MEDIA".equals(prioridadeAtual) ? "selected" : ""%>>MÉDIA</option>
						<option value="ALTA"
							<%="ALTA".equals(prioridadeAtual) ? "selected" : ""%>>ALTA</option>
					</select>
				</div>
				<div class="col-md-4">
					<label class="form-label" for="status">Status</label> <select
						class="form-select" id="status" name="status">
						<option value="ATIVO"
							<%="ATIVO".equals(statusAtual) ? "selected" : ""%>>ATIVO</option>
						<option value="INATIVO"
							<%="INATIVO".equals(statusAtual) ? "selected" : ""%>>INATIVO</option>
					</select>
				</div>
			</div>

			<div class="mt-4 action-row">
				<button type="submit" class="btn btn-primary">
					<i class="bi bi-check-circle" aria-hidden="true"></i> Salvar
					alterações
				</button>
				<a href="${pageContext.request.contextPath}/comunicados"
					class="btn btn-outline-secondary">Cancelar</a>
			</div>
		</form>
	</div>
</div>

<%@ include file="/WEB-INF/includes/footer.jsp"%>
