<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="br.com.conectacampus.model.Usuario"%>
<%@ page import="br.com.conectacampus.util.Autorizacao"%>
<%
Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
if (usuario == null) {
	response.sendRedirect(request.getContextPath() + "/login");
	return;
}
%>
<%
request.setAttribute("paginaAtiva", "forum");
%>
<%@ include file="/WEB-INF/includes/header.jsp"%>
<div class="page-header">
	<div>
		<p class="eyebrow">Fórum</p>
		<h1 class="page-title">
			<i class="bi bi-chat-square-text" aria-hidden="true"></i> Novo tópico
		</h1>
		<p class="page-subtitle">Abra uma conversa clara para receber
			respostas melhores.</p>
	</div>
</div>

<div class="card">
	<div class="card-body">
		<%
		if (request.getAttribute("erro") != null) {
		%>
		<div class="alert alert-danger" role="alert"><%=request.getAttribute("erro")%></div>
		<%
		}
		%>
		<form action="${pageContext.request.contextPath}/forum" method="post">
			<input type="hidden" name="acao" value="salvar"> <input
				type="hidden" name="idUsuario" value="<%=usuario.getIdUsuario()%>">

			<div class="mb-3">
				<label class="form-label" for="tipoInteracao">Como as
					pessoas poderão participar?</label> <select class="form-select"
					id="tipoInteracao" name="tipoInteracao">
					<option value="RESPOSTAS">Somente respostas</option>
					<option value="ENQUETE">Somente enquete</option>
					<option value="AMBOS">Enquete e respostas</option>
				</select>
				<div class="form-text">Escolha antes de publicar o tópico.</div>
			</div>

			<div class="mb-3">
				<label class="form-label" for="titulo">Título</label> <input
					class="form-control" id="titulo" type="text" name="titulo"
					maxlength="120"
					placeholder="Ex.: Dúvida sobre calendário de provas" required>
			</div>

			<%
			if (Autorizacao.podePublicarInstitucional(usuario)) {
			%>
			<details class="mb-3" id="dadosEnquete">
				<summary class="fw-semibold">Adicionar enquete a este
					tópico (opcional)</summary>
				<div class="mt-3">
					<label class="form-label" for="perguntaEnquete">Pergunta</label> <input
						class="form-control" id="perguntaEnquete" name="perguntaEnquete"
						maxlength="200"
						placeholder="Ex.: Qual data é melhor para o evento?">
				</div>
				<div class="mt-3">
					<label class="form-label" for="descricaoEnquete">Contexto</label> <input
						class="form-control" id="descricaoEnquete" name="descricaoEnquete"
						maxlength="300">
				</div>
				<div class="mt-3">
					<label class="form-label" for="opcoesEnquete">Opções (uma
						por linha)</label>
					<textarea class="form-control" id="opcoesEnquete"
						name="opcoesEnquete" rows="4" placeholder="Opção 1&#10;Opção 2"></textarea>
				</div>
			</details>
			<%
			}
			%>

			<div class="mb-3">
				<label class="form-label" for="mensagem">Mensagem</label>
				<textarea class="form-control" id="mensagem" name="mensagem"
					rows="7"
					placeholder="Explique o contexto da sua dúvida ou ideia..."
					required></textarea>
			</div>

			<div class="action-row">
				<button type="submit" class="btn btn-primary">
					<i class="bi bi-send-fill" aria-hidden="true"></i> Publicar tópico
				</button>
				<a href="${pageContext.request.contextPath}/forum"
					class="btn btn-secondary">Cancelar</a>
			</div>
		</form>
	</div>
</div>

<script>
	const tipoInteracao = document.getElementById('tipoInteracao');
	const dadosEnquete = document.getElementById('dadosEnquete');
	const perguntaEnquete = document.getElementById('perguntaEnquete');
	const opcoesEnquete = document.getElementById('opcoesEnquete');
	function ajustarTipoInteracao() {
		const usaEnquete = tipoInteracao.value !== 'RESPOSTAS';
		dadosEnquete.hidden = !usaEnquete;
		dadosEnquete.open = usaEnquete;
		dadosEnquete.querySelector('summary').textContent = usaEnquete ? 'Dados da enquete (obrigatórios)'
				: 'Dados da enquete';
		perguntaEnquete.required = usaEnquete;
		opcoesEnquete.required = usaEnquete;
	}
	tipoInteracao.addEventListener('change', ajustarTipoInteracao);
	ajustarTipoInteracao();
</script>
<%@ include file="/WEB-INF/includes/footer.jsp"%>
