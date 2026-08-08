<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="br.com.conectacampus.model.Pesquisa"%>
<%
Pesquisa pesquisa = (Pesquisa) request.getAttribute("pesquisaEdicao");
request.setAttribute("paginaAtiva", "pesquisas");
request.setAttribute("tituloPagina", "Editar pesquisa - Conecta Campus");
%>
<%@ include file="/WEB-INF/includes/header.jsp"%>
<div class="page-header">
	<div>
		<p class="eyebrow">Pesquisas</p>
		<h1 class="page-title">Editar pesquisa</h1>
		<p class="page-subtitle">Atualize as informações do questionário.</p>
	</div>
</div>
<section class="card">
	<div class="card-body">
		<form action="${pageContext.request.contextPath}/pesquisas"
			method="post">
			<input type="hidden" name="acao" value="atualizar"><input
				type="hidden" name="idPesquisa"
				value="<%=pesquisa.getIdPesquisa()%>">
			<div class="row g-3">
				<div class="col-md-6">
					<label class="form-label">Título</label><input class="form-control"
						name="titulo" value="<%=pesquisa.getTitulo()%>" required>
				</div>
				<div class="col-md-3">
					<label class="form-label">Prazo</label><input class="form-control"
						type="date" name="dataLimite"
						value="<%=pesquisa.getDataLimite() != null ? pesquisa.getDataLimite() : ""%>">
				</div>
				<div class="col-md-3">
					<label class="form-label">Status</label><select class="form-select"
						name="status"><option value="ABERTA"
							<%="ABERTA".equals(pesquisa.getStatus()) ? "selected" : ""%>>Aberta</option>
						<option value="ENCERRADA"
							<%="ENCERRADA".equals(pesquisa.getStatus()) ? "selected" : ""%>>Encerrada</option></select>
				</div>
				<div class="col-12">
					<label class="form-label">Link do formulário</label><input
						class="form-control" type="url" name="linkFormulario"
						value="<%=pesquisa.getLinkFormulario()%>" required>
				</div>
				<div class="col-12">
					<label class="form-label">Descrição</label>
					<textarea class="form-control" name="descricao" rows="4"><%=pesquisa.getDescricao() == null ? "" : pesquisa.getDescricao()%></textarea>
				</div>
			</div>
			<button class="btn btn-primary mt-3">Salvar alterações</button>
			<a class="btn btn-outline-secondary mt-3"
				href="${pageContext.request.contextPath}/pesquisas">Cancelar</a>
		</form>
	</div>
</section>
<%@ include file="/WEB-INF/includes/footer.jsp"%>
