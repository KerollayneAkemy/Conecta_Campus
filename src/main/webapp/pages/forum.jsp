<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="br.com.conectacampus.model.Forum"%>
<%@ page import="br.com.conectacampus.model.Usuario"%>
<%@ page import="br.com.conectacampus.util.Autorizacao"%>
<%
Usuario usuarioForum = (Usuario) session.getAttribute("usuarioLogado");
List<Forum> topicos = (List<Forum>) request.getAttribute("foruns");
if (topicos == null)
	topicos = (List<Forum>) request.getAttribute("listaForum");
boolean podeGerenciarForum = Autorizacao.podeGerenciarForum(usuarioForum);
boolean adminForum = Autorizacao.ehAdministrador(usuarioForum);
request.setAttribute("paginaAtiva", "forum");
request.setAttribute("tituloPagina", "Fórum - Conecta Campus");
%>
<%@ include file="/WEB-INF/includes/header.jsp"%>

<section class="page-header">
	<div>
		<p class="eyebrow">Comunidade acadêmica</p>
		<h1 class="page-title">
			<i class="bi bi-chat-dots-fill" aria-hidden="true"></i> Fórum
		</h1>
		<p class="page-subtitle">Acompanhe discussões e enquetes
			vinculadas aos tópicos.</p>
	</div>
	<%
	if (podeGerenciarForum) {
	%>
	<a href="${pageContext.request.contextPath}/forum?acao=novo"
		class="btn btn-primary"><i class="bi bi-plus-circle"
		aria-hidden="true"></i> Novo tópico</a>
	<%
	}
	%>
</section>

<div class="row g-3 mb-4">
	<div class="col-md-4">
		<div class="card metric-card h-100">
			<div class="card-body">
				<div>
					<p class="metric-label">Tópicos</p>
					<p class="metric-value"><%=topicos == null ? 0 : topicos.size()%></p>
				</div>
				<span class="metric-icon"><i class="bi bi-chat-square-text"
					aria-hidden="true"></i></span>
			</div>
		</div>
	</div>
	<div class="col-md-8">
		<div class="card h-100">
			<div class="card-body d-flex align-items-center gap-3">
				<span class="metric-icon"><i class="bi bi-ui-checks-grid"
					aria-hidden="true"></i></span>
				<div>
					<strong>Enquetes no contexto</strong>
					<p class="page-subtitle mb-0">As enquetes são criadas dentro de
						um tópico e aparecem na respectiva discussão.</p>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="card">
	<div
		class="card-header d-flex flex-wrap justify-content-between align-items-center gap-3">
		<strong>Tópicos recentes</strong> <input id="pesquisaForum"
			class="form-control search-input" type="search"
			placeholder="Pesquisar tópico..." aria-label="Pesquisar tópico">
	</div>
	<div class="list-group list-group-flush">
		<%
		if (topicos != null && !topicos.isEmpty()) {
			for (Forum topico : topicos) {
		%>
		<article class="list-group-item forum-topic">
			<div class="d-flex justify-content-between align-items-start gap-3">
				<a class="topic-card flex-grow-1"
					href="${pageContext.request.contextPath}/forum?acao=visualizar&id=<%= topico.getIdForum() %>">
					<h2 class="h5 mb-2">
						<i class="bi bi-chat-square-text" aria-hidden="true"></i>
						<%=topico.getTitulo()%></h2>
					<p class="mb-2 text-muted"><%=topico.getMensagem()%></p> <small
					class="text-muted"><i class="bi bi-eye" aria-hidden="true"></i>
						<%=topico.getVisualizacoes()%> visualizações</small>
				</a>
				<%
				if (podeGerenciarForum || adminForum) {
				%><div class="action-row">
					<%
					if (podeGerenciarForum) {
					%><a
						class="btn btn-outline-primary btn-sm"
						href="${pageContext.request.contextPath}/forum?acao=editar&id=<%= topico.getIdForum() %>"
						aria-label="Editar tópico"><i class="bi bi-pencil"
						aria-hidden="true"></i></a>
					<%
					}
					%>
					<%
					if (adminForum) {
					%><a class="btn btn-outline-danger btn-sm"
						href="${pageContext.request.contextPath}/forum?acao=excluir&id=<%= topico.getIdForum() %>"
						aria-label="Excluir tópico" data-confirm="true"
						data-confirm-message="Deseja excluir este tópico?"><i
						class="bi bi-trash" aria-hidden="true"></i></a>
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
			<i class="bi bi-chat-square display-5" aria-hidden="true"></i>
			<h2 class="h5 mt-3">Nenhum tópico criado</h2>
			<p class="page-subtitle">As novas discussões aparecerão aqui.</p>
		</div>
		<%
		}
		%>
	</div>
</div>

<script>
	document.getElementById('pesquisaForum').addEventListener(
			'input',
			function() {
				const termo = this.value.toLowerCase();
				document.querySelectorAll('.forum-topic').forEach(
						function(topico) {
							topico.hidden = !topico.textContent.toLowerCase()
									.includes(termo);
						});
			});
</script>
<%@ include file="/WEB-INF/includes/footer.jsp"%>
