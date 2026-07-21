<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="br.com.conectacampus.model.Comunicado"%>
<%
Comunicado comunicado = (Comunicado) request.getAttribute("comunicado");
%>
<%@ include file="/WEB-INF/includes/header.jsp"%>
<article class="card mx-auto"
	style="max-width: 880px; overflow: hidden;">
	<%
	if (comunicado != null && comunicado.getImagem() != null && !comunicado.getImagem().isBlank()) {
	%><img
		src="${pageContext.request.contextPath}/<%=comunicado.getImagem()%>"
		alt="Imagem do comunicado: <%=comunicado.getTitulo()%>"
		style="width: 100%; max-height: 420px; object-fit: cover">
	<%
	}
	%>
	<div class="card-body p-4 p-md-5">
		<div class="d-flex flex-wrap gap-2 mb-3">
			<span class="badge bg-light text-dark border"><%=comunicado.getNomeCategoria()%></span><span
				class="badge <%="ALTA".equals(comunicado.getPrioridade()) ? "bg-danger"
		: ("MEDIA".equals(comunicado.getPrioridade()) ? "bg-warning text-dark" : "bg-secondary")%>">Prioridade:
				<%=comunicado.getPrioridade()%></span>
		</div>
		<h1 class="page-title mb-3"><%=comunicado.getTitulo()%></h1>
		<p
			style="white-space: pre-line; line-height: 1.75; font-size: 1.05rem"><%=comunicado.getMensagem()%></p>
		<hr class="my-4">
		<a class="btn btn-outline-primary"
			href="${pageContext.request.contextPath}/comunicados"><i
			class="bi bi-arrow-left" aria-hidden="true"></i> Voltar aos
			comunicados</a>
	</div>
</article>
<%@ include file="/WEB-INF/includes/footer.jsp"%>
