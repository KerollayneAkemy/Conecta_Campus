<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="br.com.conectacampus.model.Usuario"%>
<%
Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
if (usuario == null) {
	response.sendRedirect(request.getContextPath() + "/login");
	return;
}
%>
<% request.setAttribute("paginaAtiva", "feedback"); %>
<%@ include file="/WEB-INF/includes/header.jsp"%>
<div class="row justify-content-center">
	<div class="col-lg-8">
		<div class="page-header">
			<div>
				<p class="eyebrow">Escuta ativa</p>
				<h1 class="page-title">
					<i class="bi bi-chat-square-heart-fill" aria-hidden="true"></i>
					Enviar feedback
				</h1>
				<p class="page-subtitle">Compartilhe sugestões, elogios, dúvidas
					ou reclamações.</p>
			</div>
		</div>

		<div class="card">
			<div class="card-body">
				<form action="${pageContext.request.contextPath}/feedback"
					method="post">
					<input type="hidden" name="idUsuario"
						value="<%=usuario.getIdUsuario()%>">

					<div class="mb-3">
						<label class="form-label" for="assunto">Assunto</label> <input
							type="text" id="assunto" name="assunto" class="form-control"
							maxlength="120" required>
					</div>

					<div class="mb-3">
						<label class="form-label" for="tipo">Tipo</label> <select
							id="tipo" name="tipo" class="form-select" required>
							<option value="SUGESTAO">Sugestão</option>
							<option value="RECLAMACAO">Reclamação</option>
							<option value="ELOGIO">Elogio</option>
							<option value="OUTROS">Outros</option>
						</select>
					</div>

					<div class="mb-3">
						<label class="form-label" for="mensagem">Mensagem</label>
						<textarea id="mensagem" name="mensagem" class="form-control"
							rows="6" placeholder="Escreva seu feedback..." required></textarea>
					</div>

					<div class="form-check mb-4">
						<input class="form-check-input" type="checkbox" name="anonimo"
							value="true" id="anonimo"> <label
							class="form-check-label" for="anonimo">Enviar como
							anônimo</label>
					</div>

					<div class="action-row">
						<button class="btn btn-primary" type="submit">
							<i class="bi bi-send-fill" aria-hidden="true"></i> Enviar
							feedback
						</button>
						<a href="${pageContext.request.contextPath}/pages/home.jsp"
							class="btn btn-secondary">Cancelar</a>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>

<%@ include file="/WEB-INF/includes/footer.jsp"%>
