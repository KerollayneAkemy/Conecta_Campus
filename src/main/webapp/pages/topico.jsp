<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="br.com.conectacampus.model.Forum"%>
<%@ page import="br.com.conectacampus.model.RespostaForum"%>
<%@ page import="br.com.conectacampus.model.Usuario"%>
<%@ page import="br.com.conectacampus.model.Enquete"%>
<%@ page import="br.com.conectacampus.model.OpcaoEnquete"%>
<%@ page import="br.com.conectacampus.util.Autorizacao"%>
<%
Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
if (usuario == null) {
	response.sendRedirect(request.getContextPath() + "/login");
	return;
}
Forum topico = (Forum) request.getAttribute("forum");
String tipoInteracao = topico != null && topico.getTipoInteracao() != null ? topico.getTipoInteracao() : "AMBOS";
boolean permiteEnquete = !"RESPOSTAS".equals(tipoInteracao);
boolean permiteRespostas = !"ENQUETE".equals(tipoInteracao);
List<RespostaForum> respostas = (List<RespostaForum>) request.getAttribute("respostas");
Enquete enquete = (Enquete) request.getAttribute("enquete");
Map<Integer, Integer> votosPorOpcao = (Map<Integer, Integer>) request.getAttribute("votosPorOpcao");
boolean usuarioJaVotou = Boolean.TRUE.equals(request.getAttribute("usuarioJaVotou"));
boolean editando = "editar".equals(request.getParameter("acao"));
boolean administrador = Autorizacao.ehAdministrador(usuario);
%>
<%@ include file="/WEB-INF/includes/header.jsp"%>
<%
if (editando) {
%>
<div class="page-header">
	<div>
		<p class="eyebrow">Fórum</p>
		<h1 class="page-title">
			<i class="bi bi-pencil-square" aria-hidden="true"></i> Editar tópico
		</h1>
		<p class="page-subtitle">Atualize a conversa publicada no fórum.</p>
	</div>
</div>

<div class="card">
	<div class="card-body">
		<form action="${pageContext.request.contextPath}/forum" method="post">
			<input type="hidden" name="acao" value="atualizar"> <input
				type="hidden" name="id"
				value="<%=topico != null ? topico.getIdForum() : 0%>"> <input
				type="hidden" name="idUsuario" value="<%=usuario.getIdUsuario()%>">
			<div class="mb-3">
				<label class="form-label" for="titulo">Título</label> <input
					class="form-control" id="titulo" name="titulo"
					value="<%=topico != null && topico.getTitulo() != null ? topico.getTitulo() : ""%>"
					required>
			</div>
			<div class="mb-3">
				<label class="form-label" for="mensagem">Mensagem</label>
				<textarea class="form-control" id="mensagem" name="mensagem"
					rows="7" required><%=topico != null && topico.getMensagem() != null ? topico.getMensagem() : ""%></textarea>
			</div>
			<div class="action-row">
				<button class="btn btn-primary" type="submit">
					<i class="bi bi-check-circle" aria-hidden="true"></i> Salvar
					alterações
				</button>
				<a href="${pageContext.request.contextPath}/forum"
					class="btn btn-secondary">Cancelar</a>
			</div>
		</form>
	</div>
</div>
<%
} else {
%>
<%
if (administrador && topico != null) {
%>
<div class="d-flex justify-content-end mb-3">
	<a class="btn btn-outline-danger"
		href="${pageContext.request.contextPath}/forum?acao=excluir&id=<%=topico.getIdForum()%>"
		data-confirm="true" data-confirm-message="Deseja excluir este tópico?">
		<i class="bi bi-trash" aria-hidden="true"></i> Excluir tópico
	</a>
</div>
<%
}
%>
<%
if (enquete != null && permiteEnquete) {
%>
<section class="card mb-4" aria-labelledby="titulo-enquete">
	<div class="card-header" id="titulo-enquete">
		<i class="bi bi-bar-chart-steps" aria-hidden="true"></i> Enquete do
		tópico
	</div>
	<div class="card-body">
		<h2 class="h5"><%=enquete.getTitulo()%></h2>
		<%
		if (enquete.getDescricao() != null && !enquete.getDescricao().isBlank()) {
		%><p
			class="text-muted"><%=enquete.getDescricao()%></p>
		<%
		}
		%>
		<%
		if ("registrado".equals(request.getParameter("voto"))) {
		%>
		<div class="alert alert-success" role="status">Voto registrado
			com sucesso.</div>
		<%
		} else if ("ja-realizado".equals(request.getParameter("voto"))) {
		%>
		<div class="alert alert-info" role="status">Você já votou nesta
			enquete. Cada pessoa pode votar apenas uma vez.</div>
		<%
		}
		%>

		<%
		if (!usuarioJaVotou) {
		%>
		<form action="${pageContext.request.contextPath}/votos" method="post">
			<input type="hidden" name="idForum"
				value="<%=topico != null ? topico.getIdForum() : 0%>">
			<%
			for (OpcaoEnquete opcao : enquete.getOpcoes()) {
			%>
			<div class="form-check mb-2">
				<input class="form-check-input" type="radio" name="idOpcao"
					id="opcao-<%=opcao.getIdOpcao()%>" value="<%=opcao.getIdOpcao()%>"
					required> <label class="form-check-label"
					for="opcao-<%=opcao.getIdOpcao()%>"><%=opcao.getDescricao()%></label>
			</div>
			<%
			}
			%>
			<button class="btn btn-primary mt-2" type="submit">Confirmar
				voto</button>
		</form>
		<%
		} else {
		%>
		<p class="text-success mb-3">
			<i class="bi bi-check-circle-fill" aria-hidden="true"></i> Seu voto
			já foi contabilizado.
		</p>
		<%
		}
		%>

		<hr>
		<h3 class="h6 mb-3">
			<i class="bi bi-bar-chart-fill" aria-hidden="true"></i> Resultados
		</h3>
		<%
		int totalVotos = 0;
		if (votosPorOpcao != null)
			for (Integer total : votosPorOpcao.values())
				totalVotos += total;
		%>
		<%
		for (OpcaoEnquete opcao : enquete.getOpcoes()) {
			Integer quantidade = votosPorOpcao != null ? votosPorOpcao.get(opcao.getIdOpcao()) : 0;
			if (quantidade == null)
				quantidade = 0;
			int percentual = totalVotos == 0 ? 0 : (quantidade * 100 / totalVotos);
		%>
		<div class="mb-3">
			<div class="d-flex justify-content-between small mb-1">
				<span><%=opcao.getDescricao()%></span><span><%=quantidade%>
					voto(s) — <%=percentual%>%</span>
			</div>
			<div class="progress" role="progressbar"
				aria-label="<%=opcao.getDescricao()%>"
				aria-valuenow="<%=percentual%>" aria-valuemin="0"
				aria-valuemax="100">
				<div class="progress-bar" style="width: <%=percentual%>%"></div>
			</div>
		</div>
		<%
		}
		%>
		<p class="text-muted small mb-0">
			Total:
			<%=totalVotos%>
			voto(s).
		</p>
	</div>
</section>
<%
}
%>

<%
if (permiteRespostas) {
%>
<div class="card mb-4">
	<div class="card-body">
		<p class="eyebrow">Discussão</p>
		<h1><%=topico != null ? topico.getTitulo() : "Tópico"%></h1>
		<p class="text-muted">
			<i class="bi bi-eye" aria-hidden="true"></i>
			<%=topico != null ? topico.getVisualizacoes() : 0%>
			visualizações
		</p>
		<hr>
		<p class="mb-0"><%=topico != null ? topico.getMensagem() : ""%></p>
	</div>
</div>

<div class="card mb-4">
	<div class="card-header">Respostas</div>
	<div class="card-body">
		<%
		if (respostas != null && !respostas.isEmpty()) {
			for (RespostaForum r : respostas) {
		%>
		<article class="border rounded p-3 mb-3">
			<%
			if (r.isAnonimo()) {
			%>
			<h2 class="h6">
				<i class="bi bi-person-circle" aria-hidden="true"></i> Anônimo
			</h2>
			<%
			} else {
			%>
			<h2 class="h6">
				<i class="bi bi-person-circle" aria-hidden="true"></i>
				<%=r.getUsuario() != null ? r.getUsuario().getNome() : "Usuário"%></h2>
			<%
			}
			%>
			<p class="mb-0"><%=r.getResposta()%></p>
		</article>
		<%
		}
		} else {
		%>
		<div class="empty-state">
			<i class="bi bi-chat-left-text display-6" aria-hidden="true"></i>
			<h2 class="h5 mt-3">Ainda não existem respostas</h2>
			<p class="page-subtitle">Participe da conversa com uma resposta
				útil.</p>
		</div>
		<%
		}
		%>
	</div>
</div>

<div class="card">
	<div class="card-header">Responder</div>
	<div class="card-body">
		<form action="${pageContext.request.contextPath}/respostasForum"
			method="post">
			<input type="hidden" name="idForum"
				value="<%=topico != null ? topico.getIdForum() : 0%>"> <input
				type="hidden" name="idUsuario" value="<%=usuario.getIdUsuario()%>">
			<div class="mb-3">
				<label class="form-label" for="resposta">Sua resposta</label>
				<textarea id="resposta" name="resposta" class="form-control"
					rows="5" placeholder="Escreva sua contribuição..." required></textarea>
			</div>
			<div class="form-check mb-3">
				<input class="form-check-input" type="checkbox" value="true"
					id="anonimo" name="anonimo"> <label
					class="form-check-label" for="anonimo">Enviar resposta
					anonimamente</label>
			</div>
			<button class="btn btn-primary" type="submit">
				<i class="bi bi-send-fill" aria-hidden="true"></i> Publicar resposta
			</button>
		</form>
	</div>
</div>
<%
}
%>

<%
}
%>

<%@ include file="/WEB-INF/includes/footer.jsp"%>
