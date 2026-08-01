<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page
	import="java.util.List,java.util.Set,java.util.Map,br.com.conectacampus.model.Pesquisa,br.com.conectacampus.model.Usuario,br.com.conectacampus.util.Autorizacao"%>
<%
List<Pesquisa> pesquisas = (List<Pesquisa>) request.getAttribute("listaPesquisas");
Set<Integer> respondidas = (Set<Integer>) request.getAttribute("pesquisasRespondidas");
Map<Integer, Integer> totalRespondidas = (Map<Integer, Integer>) request.getAttribute("totalRespondidas");
Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
boolean podeCriar = Autorizacao.podePublicarInstitucional(usuario);
boolean aluno = Autorizacao.ehAluno(usuario);
request.setAttribute("paginaAtiva", "pesquisas");
request.setAttribute("tituloPagina", "Pesquisas - Conecta Campus");
%>
<%@ include file="/WEB-INF/includes/header.jsp"%>

<div class="page-header">
	<div>
		<p class="eyebrow">Participação acadêmica</p>
		<h1 class="page-title">
			<i class="bi bi-clipboard2-check-fill"></i> Pesquisas
		</h1>
		<p class="page-subtitle">Responda questionários importantes para a
			comunidade.</p>
	</div>
</div>

<%
if (podeCriar) {
%>
<section class="card mb-4">
	<div class="card-header">
		<i class="bi bi-plus-circle"></i> Nova pesquisa
	</div>
	<div class="card-body">
		<form action="${pageContext.request.contextPath}/pesquisas"
			method="post">
			<div class="row g-3">
				<div class="col-md-6">
					<label class="form-label" for="titulo">Título</label><input
						class="form-control" id="titulo" name="titulo" maxlength="200"
						required>
				</div>
				<div class="col-md-3">
					<label class="form-label" for="dataLimite">Prazo</label><input
						class="form-control" type="date" id="dataLimite" name="dataLimite">
				</div>
				<div class="col-md-3">
					<label class="form-label" for="linkFormulario">Link do
						formulário</label><input class="form-control" type="url"
						id="linkFormulario" name="linkFormulario" required>
				</div>
				<div class="col-12">
					<label class="form-label" for="descricao">Descrição</label>
					<textarea class="form-control" id="descricao" name="descricao"
						rows="3"></textarea>
				</div>
			</div>
			<button class="btn btn-primary mt-3">Publicar pesquisa</button>
		</form>
	</div>
</section>
<%
}
%>

<section class="row g-3">
	<%
	if (pesquisas != null && !pesquisas.isEmpty()) {
		for (Pesquisa pesquisa : pesquisas) {
			boolean aberta = "ABERTA".equals(pesquisa.getStatus());
			boolean respondida = respondidas != null && respondidas.contains(pesquisa.getIdPesquisa());
	%>
	<div class="col-md-6 col-xl-4">
		<article class="card h-100">
			<div class="card-body d-flex flex-column">
				<div class="d-flex justify-content-between">
					<span
						class="badge <%=respondida ? "text-bg-primary" : aberta ? "text-bg-success" : "text-bg-secondary"%>"><%=respondida ? "RESPONDIDA" : pesquisa.getStatus()%></span>
					<%
					if (pesquisa.getDataLimite() != null) {
					%><small
						class="text-muted">Até <%=pesquisa.getDataLimite()%></small>
					<%
					}
					%>
				</div>
				<h2 class="h5 mt-3"><%=pesquisa.getTitulo()%></h2>
				<p class="text-muted flex-grow-1"><%=pesquisa.getDescricao() == null || pesquisa.getDescricao().isBlank() ? "Participe desta pesquisa."
		: pesquisa.getDescricao()%></p>
				<small class="text-muted mb-3">Publicada por <%=pesquisa.getUsuario().getNome()%></small>
				<% if (podeCriar) { %>
				<span class="text-muted mb-2"><i class="bi bi-check2-circle"></i> <%=totalRespondidas != null ? totalRespondidas.getOrDefault(pesquisa.getIdPesquisa(), 0) : 0%> pessoa(s) concluíram</span>
				<% } %>
				<%
				if (podeCriar) {
				%><a class="btn btn-outline-primary mb-2"
					href="${pageContext.request.contextPath}/pesquisas?acao=editar&id=<%=pesquisa.getIdPesquisa()%>"><i
					class="bi bi-pencil"></i> Editar pesquisa</a>
				<%
				}
				%>
				<%
				if (aberta && !respondida) {
				%><a class="btn btn-primary mb-2"
					href="<%=pesquisa.getLinkFormulario()%>" target="_blank"
					rel="noopener noreferrer">Responder pesquisa</a>
				<%
				if (aluno) {
				%><form
					action="${pageContext.request.contextPath}/pesquisas" method="post">
					<input type="hidden" name="acao" value="marcarRespondida"><input
						type="hidden" name="idPesquisa"
						value="<%=pesquisa.getIdPesquisa()%>">
					<button class="btn btn-outline-success w-100">
						<i class="bi bi-check2-circle"></i> Marcar como respondida
					</button>
				</form>
				<%
				}
				%>
				<%
				} else if (respondida) {
				%><span class="btn btn-success disabled"><i
					class="bi bi-check2-circle"></i> Pesquisa respondida</span>
				<% } %>
			</div>
		</article>
	</div>
	<% } } else { %><div class="col-12">
		<div class="empty-state">
			<h2 class="h5">Nenhuma pesquisa disponível</h2>
		</div>
	</div>
	<% } %>
</section>
<%@ include file="/WEB-INF/includes/footer.jsp"%>
