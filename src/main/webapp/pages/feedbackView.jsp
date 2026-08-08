<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="br.com.conectacampus.model.Feedback"%>
<%@ page import="br.com.conectacampus.model.Usuario"%>
<%@ page import="br.com.conectacampus.util.Autorizacao"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<%
List<Feedback> feedbacks = (List<Feedback>) request.getAttribute("listaFeedbacks");
String filtroTipo = (String) request.getAttribute("filtroTipo");
if (filtroTipo == null)
	filtroTipo = "";

Integer totalFeedbacks = (Integer) request.getAttribute("totalFeedbacks");
Integer totalSugestoes = (Integer) request.getAttribute("totalSugestoes");
Integer totalElogios = (Integer) request.getAttribute("totalElogios");
Integer totalReclamacoes = (Integer) request.getAttribute("totalReclamacoes");

DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
Usuario usuarioFeedback = (Usuario) session.getAttribute("usuarioLogado");
boolean podeExcluirFeedback = Autorizacao.ehAdministrador(usuarioFeedback);

request.setAttribute("paginaAtiva", "feedbackview");
request.setAttribute("tituloPagina", "Visualizar Feedbacks - Conecta Campus");
%>
<%@ include file="/WEB-INF/includes/header.jsp"%>

<div class="page-header">
	<div>
		<p class="eyebrow">Administração</p>
		<h1 class="page-title">
			<i class="bi bi-eye-fill" aria-hidden="true"></i> Visualizar
			Feedbacks
		</h1>
		<p class="page-subtitle">Acompanhe sugestões, elogios e
			reclamações enviados pela comunidade.</p>
	</div>
</div>

<div class="row g-3 mb-4">
	<div class="col-md-3">
		<a class="text-decoration-none"
			href="${pageContext.request.contextPath}/feedbackview?acao=listar">
			<div class="card metric-card">
				<div class="card-body">
					<div>
						<p class="metric-label">Total</p>
						<p class="metric-value"><%=totalFeedbacks != null ? totalFeedbacks : 0%></p>
					</div>
					<span class="metric-icon"><i class="bi bi-envelope-fill"
						aria-hidden="true"></i></span>
				</div>
			</div>
		</a>
	</div>
	<div class="col-md-3">
		<a class="text-decoration-none"
			href="${pageContext.request.contextPath}/feedbackview?acao=listar&tipo=SUGESTAO">
			<div class="card metric-card">
				<div class="card-body">
					<div>
						<p class="metric-label">Sugestões</p>
						<p class="metric-value"><%=totalSugestoes != null ? totalSugestoes : 0%></p>
					</div>
					<span class="metric-icon"><i class="bi bi-lightbulb-fill"
						aria-hidden="true"></i></span>
				</div>
			</div>
		</a>
	</div>
	<div class="col-md-3">
		<a class="text-decoration-none"
			href="${pageContext.request.contextPath}/feedbackview?acao=listar&tipo=ELOGIO">
			<div class="card metric-card">
				<div class="card-body">
					<div>
						<p class="metric-label">Elogios</p>
						<p class="metric-value"><%=totalElogios != null ? totalElogios : 0%></p>
					</div>
					<span class="metric-icon"><i
						class="bi bi-hand-thumbs-up-fill" aria-hidden="true"></i></span>
				</div>
			</div>
		</a>
	</div>
	<div class="col-md-3">
		<a class="text-decoration-none"
			href="${pageContext.request.contextPath}/feedbackview?acao=listar&tipo=RECLAMACAO">
			<div class="card metric-card">
				<div class="card-body">
					<div>
						<p class="metric-label">Reclamações</p>
						<p class="metric-value"><%=totalReclamacoes != null ? totalReclamacoes : 0%></p>
					</div>
					<span class="metric-icon"><i
						class="bi bi-exclamation-triangle-fill" aria-hidden="true"></i></span>
				</div>
			</div>
		</a>
	</div>
</div>

<div class="card">
	<div
		class="card-header d-flex flex-wrap justify-content-between align-items-center gap-3">
		<span> Lista de feedbacks <%
		if (!filtroTipo.isEmpty()) {
		%> <span
			class="badge bg-secondary ms-2">Filtro: <%=filtroTipo%></span> <a
			href="${pageContext.request.contextPath}/feedbackview?acao=listar"
			class="ms-2" style="font-size: .8rem;">(limpar filtro)</a> <%
 }
 %>
		</span> <label class="visually-hidden" for="pesquisa">Pesquisar
			feedback</label> <input id="pesquisa" class="form-control search-input"
			type="search" placeholder="Pesquisar..."
			aria-describedby="ajudaPesquisa"> <span id="ajudaPesquisa"
			class="visually-hidden">Digite para filtrar a lista de
			feedbacks exibida na tabela.</span>
	</div>
	<div class="table-responsive">
		<table class="table table-hover align-middle">
			<thead>
				<tr>
					<th>Assunto</th>
					<th>Tipo</th>
					<th>Remetente</th>
					<th>Data</th>
					<th>Ações</th>
				</tr>
			</thead>
			<tbody>
				<%
				if (feedbacks != null && !feedbacks.isEmpty()) {
					for (Feedback f : feedbacks) {
						String badgeClasse;
						switch (f.getTipo()) {
					case "ELOGIO" :
						badgeClasse = "bg-success";
						break;
					case "RECLAMACAO" :
						badgeClasse = "bg-danger";
						break;
					default :
						badgeClasse = "bg-info";
						}
				%>
				<tr class="feedback-row">
					<td><i class="bi bi-envelope me-2" aria-hidden="true"></i><%=f.getAssunto() != null ? f.getAssunto() : "(sem assunto)"%></td>
					<td><span class="badge <%=badgeClasse%>"><%=f.getTipo()%></span></td>
					<td>
						<%
						if (f.isAnonimo()) {
						%> <span class="text-muted"><i
							class="bi bi-incognito me-1" aria-hidden="true"></i>Anônimo</span> <%
 } else if (f.getUsuario() != null) {
 %>
						<%=f.getUsuario().getNome()%> <%
 } else {
 %> <span
						class="text-muted">—</span> <%
 }
 %>
					</td>
					<td><%=f.getDataEnvio() != null ? f.getDataEnvio().format(fmt) : "-"%></td>
					<td>
						<div class="action-row">
							<a class="btn btn-sm btn-outline-primary"
								aria-label="Ver feedback <%=f.getIdFeedback()%>"
								href="${pageContext.request.contextPath}/feedbackview?acao=visualizar&id=<%=f.getIdFeedback()%>"><i
								class="bi bi-eye" aria-hidden="true"></i></a>
							<%
							if (podeExcluirFeedback) {
							%><a
								class="btn btn-sm btn-outline-danger"
								aria-label="Excluir feedback <%=f.getIdFeedback()%>"
								href="${pageContext.request.contextPath}/feedbackview?acao=excluir&id=<%=f.getIdFeedback()%>"
								data-confirm="true"
								data-confirm-message="Deseja realmente excluir este feedback?"><i
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
					<td colspan="5">
						<div class="empty-state">
							<i class="bi bi-envelope-x display-5" aria-hidden="true"></i>
							<h2 class="h5 mt-3">Nenhum feedback encontrado</h2>
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
		document.querySelectorAll(".feedback-row").forEach(function(linha) {
			linha.hidden = !linha.textContent.toLowerCase().includes(termo);
		});
	});
</script>

<%@ include file="/WEB-INF/includes/footer.jsp"%>
