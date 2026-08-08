<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="br.com.conectacampus.model.Comunicado"%>
<%@ page import="br.com.conectacampus.model.Categoria"%>
<%@ page import="br.com.conectacampus.model.Usuario"%>
<%@ page import="br.com.conectacampus.util.Autorizacao"%>
<%
List<Comunicado> comunicados = (List<Comunicado>) request.getAttribute("listaComunicados");
List<Categoria> categorias = (List<Categoria>) request.getAttribute("listaCategorias");
Usuario usuarioVisual = (Usuario) session.getAttribute("usuarioLogado");
boolean podeGerenciarComunicados = Autorizacao.podeGerenciarComunicados(usuarioVisual);
boolean adminComunicados = Autorizacao.ehAdministrador(usuarioVisual);

request.setAttribute("paginaAtiva", "comunicados");
request.setAttribute("tituloPagina", "Comunicados - Conecta Campus");
request.setAttribute("cssPagina", "comunicados.css");
%>
<%@ include file="/WEB-INF/includes/header.jsp"%>

<style>
<%if (!podeGerenciarComunicados) {%>.
	comunicado-row td: nth-last-child(-n +2){ display: none;
}
<%}%>
</style>

<div class="page-header">
	<div>
		<p class="eyebrow">Comunicação oficial</p>
		<h1 class="page-title">
			<i class="bi bi-megaphone-fill" aria-hidden="true"></i> Comunicados
		</h1>
		<p class="page-subtitle">Gerencie avisos, prioridades e
			publicações do campus.</p>
	</div>
	<%
	if (podeGerenciarComunicados) {
	%><a
		href="${pageContext.request.contextPath}/comunicados?acao=novo"
		class="btn btn-primary"> <i class="bi bi-plus-circle"
		aria-hidden="true"></i> Novo comunicado
	</a>
	<%
	}
	%>
</div>

<div class="row g-3 mb-4">
	<div class="col-md-4">
		<div class="card metric-card">
			<div class="card-body">
				<div>
					<p class="metric-label">Total</p>
					<p class="metric-value"><%=comunicados != null ? comunicados.size() : 0%></p>
				</div>
				<span class="metric-icon"><i class="bi bi-megaphone-fill"
					aria-hidden="true"></i></span>
			</div>
		</div>
	</div>
</div>

<div class="card">
	<div class="card-header comunicados-filtros">
		<div>
			<span class="d-block fw-semibold">Lista de comunicados</span><span
				class="small text-muted">Encontre avisos por assunto ou
				palavra-chave.</span>
		</div>
		<div class="campo-filtro">
			<label class="small fw-semibold" for="filtroCategoria">Categoria</label><select
				id="filtroCategoria" class="form-select"
				aria-label="Filtrar por categoria"><option value="">Todas
					as categorias</option>
				<%
				if (categorias != null)
					for (Categoria categoria : categorias) {
				%><option
					value="<%=categoria.getIdCategoria()%>"><%=categoria.getNome()%></option>
				<%
				}
				%></select>
		</div>
		<div class="campo-filtro">
			<label class="small fw-semibold" for="pesquisa">Pesquisar</label><input
				id="pesquisa" class="form-control" type="search"
				placeholder="Título, mensagem ou categoria"
				aria-describedby="ajudaPesquisa">
		</div>
		<span id="ajudaPesquisa" class="visually-hidden">Use a busca e
			o filtro de categoria para encontrar comunicados.</span>
	</div>
	<div class="mural-noticias" aria-live="polite">
		<%
		if (comunicados != null && !comunicados.isEmpty()) {
			for (Comunicado c : comunicados) {
				String classePrioridade = "ALTA".equals(c.getPrioridade())
				? "bg-danger"
				: ("MEDIA".equals(c.getPrioridade()) ? "bg-warning text-dark" : "bg-secondary");
		%>
		<article
			class="noticia-card comunicado-row <%="ALTA".equals(c.getPrioridade()) ? "noticia-destaque" : ""%>"
			data-categoria="<%=c.getIdCategoria()%>"
			data-prioridade="<%=c.getPrioridade()%>">
			<div class="noticia-faixa"></div>
			<%
			if (c.getImagem() != null && !c.getImagem().isBlank()) {
			%><img
				class="noticia-imagem"
				src="${pageContext.request.contextPath}/<%=c.getImagem()%>"
				alt="Imagem do comunicado: <%=c.getTitulo()%>">
			<%
			}
			%>
			<div class="noticia-conteudo">
				<div class="noticia-autor">
					<span class="noticia-avatar" aria-hidden="true">CC</span><span><strong>Conecta
							Campus</strong><small>Comunicado oficial</small></span>
				</div>
				<div class="noticia-meta">
					<span class="badge bg-light text-dark border"><%=c.getNomeCategoria()%></span><span
						class="badge <%=classePrioridade%>">Prioridade: <%=c.getPrioridade()%></span>
				</div>
				<h2 class="noticia-titulo"><%=c.getTitulo()%></h2>
				<p class="noticia-mensagem"><%=c.getMensagem()%></p>
				<%
				if (c.getMensagem() != null
						&& (c.getMensagem().length() > 90 || c.getMensagem().contains("\n") || c.getMensagem().contains("\r"))) {
				%><a
					class="btn btn-sm btn-outline-primary mt-3"
					href="${pageContext.request.contextPath}/comunicados?acao=visualizar&id=<%=c.getIdComunicado()%>">Ler
					mais <i class="bi bi-arrow-right" aria-hidden="true"></i>
				</a>
				<%
				}
				%>
				<%
				if (podeGerenciarComunicados || adminComunicados) {
				%><div
					class="noticia-acoes">
					<%
					if (podeGerenciarComunicados) {
					%><a
						class="btn btn-sm btn-outline-primary"
						href="${pageContext.request.contextPath}/comunicados?acao=editar&id=<%=c.getIdComunicado()%>"><i
						class="bi bi-pencil" aria-hidden="true"></i> Editar</a>
					<%
					}
					%>
					<%
					if (adminComunicados) {
					%><a class="btn btn-sm btn-outline-danger"
						href="${pageContext.request.contextPath}/comunicados?acao=excluir&id=<%=c.getIdComunicado()%>"
						data-confirm="true"
						data-confirm-message="Deseja realmente excluir este comunicado?"><i
						class="bi bi-trash" aria-hidden="true"></i> Excluir</a>
					<%
					}
					%>
				</div>
				<%
				}
				%>
			</div>
		</article>
		<%
		}
		} else {
		%>
		<div class="empty-state">
			<i class="bi bi-inbox display-5" aria-hidden="true"></i>
			<h2 class="h5 mt-3">Nenhum comunicado encontrado</h2>
			<p class="page-subtitle">Ainda não há notícias publicadas.</p>
		</div>
		<%
		}
		%>
	</div>
	<div class="table-responsive tabela-comunicados-legada"
		aria-hidden="true">
		<table class="table table-hover align-middle">
			<thead>
				<tr>
					<th>Título</th>
					<th>Categoria</th>
					<th>Prioridade</th>
					<th>Status</th>
					<%
					if (podeGerenciarComunicados) {
					%><th>Visualizações</th>
					<th>Ações</th>
					<%
					}
					%>
				</tr>
			</thead>
			<tbody>
				<%
				if (comunicados != null && !comunicados.isEmpty()) {
					for (Comunicado c : comunicados) {
						String badgePrioridade;
						switch (c.getPrioridade()) {
					case "ALTA" :
						badgePrioridade = "bg-danger";
						break;
					case "MEDIA" :
						badgePrioridade = "bg-warning text-dark";
						break;
					default :
						badgePrioridade = "bg-secondary";
						}
						String badgeStatus = "ATIVO".equals(c.getStatus()) ? "bg-success" : "bg-secondary";
				%>
				<tr class="comunicado-row" data-categoria="<%=c.getIdCategoria()%>">
					<td><strong class="d-block fs-6"><%=c.getTitulo()%></strong> <details
							class="mt-2">
							<summary class="text-primary">Ler comunicado completo</summary>
							<p class="mb-0 mt-2"><%=c.getMensagem()%></p>
						</details></td>
					<td><span class="badge bg-light text-dark border"><%=c.getNomeCategoria()%></span></td>
					<td><span class="badge <%=badgePrioridade%>">Prioridade:
							<%=c.getPrioridade()%></span></td>
					<td><span class="badge <%=badgeStatus%>"><%=c.getStatus()%></span></td>
					<td><%=c.getVisualizacoes()%></td>
					<td>
						<div class="action-row">
							<%
							if (podeGerenciarComunicados) {
							%><a
								class="btn btn-sm btn-outline-primary"
								aria-label="Editar comunicado <%=c.getTitulo()%>"
								href="${pageContext.request.contextPath}/comunicados?acao=editar&id=<%=c.getIdComunicado()%>"><i
								class="bi bi-pencil" aria-hidden="true"></i></a>
							<%
							}
							%>
							<%
							if (adminComunicados) {
							%><a
								class="btn btn-sm btn-outline-danger"
								aria-label="Excluir comunicado <%=c.getTitulo()%>"
								href="${pageContext.request.contextPath}/comunicados?acao=excluir&id=<%=c.getIdComunicado()%>"
								data-confirm="true"
								data-confirm-message="Deseja realmente excluir o comunicado <%=c.getTitulo()%>?"><i
								class="bi bi-trash" aria-hidden="true"></i></a>
							<%
							}
							%>
						</div>
					</td>
				</tr>
				<%
				}
				} else {
				%>
				<tr>
					<td colspan="4">
						<div class="empty-state">
							<i class="bi bi-inbox display-5" aria-hidden="true"></i>
							<h2 class="h5 mt-3">Nenhum comunicado encontrado</h2>
							<p class="page-subtitle">Publique o primeiro aviso para a
								comunidade.</p>
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
	function filtrarComunicados() {
		var termo = document.getElementById("pesquisa").value.toLowerCase();
		var categoria = document.getElementById("filtroCategoria").value;
		document
				.querySelectorAll(".comunicado-row")
				.forEach(
						function(linha) {
							linha.hidden = !linha.textContent.toLowerCase()
									.includes(termo)
									|| (categoria && linha.dataset.categoria !== categoria);
						});
	}
	document.getElementById("pesquisa").addEventListener("input",
			filtrarComunicados);
	document.getElementById("filtroCategoria").addEventListener("change",
			filtrarComunicados);
</script>

<%@ include file="/WEB-INF/includes/footer.jsp"%>
