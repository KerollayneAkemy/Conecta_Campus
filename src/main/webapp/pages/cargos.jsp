<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="br.com.conectacampus.model.Cargo"%>
<%
List<Cargo> cargos = (List<Cargo>) request.getAttribute("listaCargos");

request.setAttribute("paginaAtiva", "cargos");
request.setAttribute("tituloPagina", "Cargos - Conecta Campus");
%>
<%@ include file="/WEB-INF/includes/header.jsp"%>

<div class="page-header">
	<div>
		<p class="eyebrow">Administração</p>
		<h1 class="page-title">
			<i class="bi bi-briefcase-fill" aria-hidden="true"></i> Cargos
		</h1>
		<p class="page-subtitle">Gerencie os cargos cadastrados na
			plataforma.</p>
	</div>
	<a href="${pageContext.request.contextPath}/pages/cadastroCargo.jsp"
		class="btn btn-primary"> <i class="bi bi-plus-lg"
		aria-hidden="true"></i> Novo cargo
	</a>
</div>

<div class="row g-3 mb-4">
	<div class="col-md-4">
		<div class="card metric-card">
			<div class="card-body">
				<div>
					<p class="metric-label">Total</p>
					<p class="metric-value"><%=cargos != null ? cargos.size() : 0%></p>
				</div>
				<span class="metric-icon"><i class="bi bi-briefcase-fill"
					aria-hidden="true"></i></span>
			</div>
		</div>
	</div>
</div>

<div class="card">
	<div
		class="card-header d-flex flex-wrap justify-content-between align-items-center gap-3">
		<span>Lista de cargos</span> <label class="visually-hidden"
			for="pesquisa">Pesquisar cargo</label> <input id="pesquisa"
			class="form-control search-input" type="search"
			placeholder="Pesquisar..." aria-describedby="ajudaPesquisa">
		<span id="ajudaPesquisa" class="visually-hidden">Digite para
			filtrar a lista de cargos exibida na tabela.</span>
	</div>
	<div class="table-responsive">
		<table class="table table-hover align-middle">
			<thead>
				<tr>
					<th>Nome</th>
					<th>Descrição</th>
					<th>Ações</th>
				</tr>
			</thead>
			<tbody>
				<%
				if (cargos != null && !cargos.isEmpty()) {
					for (Cargo c : cargos) {
				%>
				<tr class="cargo-row">
					<td><i class="bi bi-briefcase-fill me-2" aria-hidden="true"></i><%=c.getNome()%></td>
					<td><%=c.getDescricao() != null ? c.getDescricao() : "-"%></td>
					<td>
						<div class="action-row">
							<a class="btn btn-sm btn-outline-primary"
								aria-label="Editar cargo <%=c.getNome()%>"
								href="${pageContext.request.contextPath}/cargos?acao=editar&id=<%=c.getIdCargo()%>"><i
								class="bi bi-pencil" aria-hidden="true"></i></a> <a
								class="btn btn-sm btn-outline-danger"
								aria-label="Excluir cargo <%=c.getNome()%>"
								href="${pageContext.request.contextPath}/cargos?acao=excluir&id=<%=c.getIdCargo()%>"
								data-confirm="true"
								data-confirm-message="Deseja realmente excluir o cargo <%=c.getNome()%>?"><i
								class="bi bi-trash" aria-hidden="true"></i></a>
						</div>
					</td>
				</tr>
				<%
				}
				} else {
				%>
				<tr>
					<td colspan="3">
						<div class="empty-state">
							<i class="bi bi-briefcase display-5" aria-hidden="true"></i>
							<h2 class="h5 mt-3">Nenhum cargo encontrado</h2>
						</div>
					</td>
				</tr>
				<%
				}
				%>
			</tbody>
		</table>
	</div>
</div>

<script>
	document.getElementById("pesquisa").addEventListener("input", function() {
		var termo = this.value.toLowerCase();
		document.querySelectorAll(".cargo-row").forEach(function(linha) {
			linha.hidden = !linha.textContent.toLowerCase().includes(termo);
		});
	});
</script>

<%@ include file="/WEB-INF/includes/footer.jsp"%>
