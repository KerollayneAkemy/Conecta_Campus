<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="br.com.conectacampus.model.Feedback"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<%
    Feedback feedback = (Feedback) request.getAttribute("feedback");
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm");

    if (feedback == null) {
        response.sendRedirect(request.getContextPath() + "/feedbackview?acao=listar");
        return;
    }

    String badgeClasse;
    switch (feedback.getTipo()) {
        case "ELOGIO": badgeClasse = "bg-success"; break;
        case "RECLAMACAO": badgeClasse = "bg-danger"; break;
        default: badgeClasse = "bg-info";
    }

    request.setAttribute("paginaAtiva", "feedbackview");
    request.setAttribute("tituloPagina", "Detalhe do Feedback - Conecta Campus");
%>
<%@ include file="/WEB-INF/includes/header.jsp" %>

<div class="page-header">
    <div>
        <p class="eyebrow">Administração</p>
        <h1 class="page-title"><i class="bi bi-envelope-open-fill" aria-hidden="true"></i> Detalhe do Feedback</h1>
        <p class="page-subtitle">Visualize o conteúdo completo do feedback enviado.</p>
    </div>
    <a href="${pageContext.request.contextPath}/feedbackview?acao=listar" class="btn btn-secondary">
        <i class="bi bi-arrow-left" aria-hidden="true"></i> Voltar
    </a>
</div>

<div class="row">
    <div class="col-lg-8">
        <div class="card">
            <div class="card-header d-flex justify-content-between align-items-center">
                <span><%=feedback.getAssunto() != null ? feedback.getAssunto() : "(sem assunto)"%></span>
                <span class="badge <%=badgeClasse%>"><%=feedback.getTipo()%></span>
            </div>
            <div class="card-body">
                <p style="white-space: pre-wrap;"><%=feedback.getMensagem()%></p>
            </div>
        </div>
    </div>

    <div class="col-lg-4">
        <div class="card">
            <div class="card-body">
                <span class="metric-icon mb-3"><i class="bi bi-person-circle" aria-hidden="true"></i></span>
                <h3 class="h5">Remetente</h3>
                <% if (feedback.isAnonimo()) { %>
                    <p class="page-subtitle mb-0"><i class="bi bi-incognito me-1" aria-hidden="true"></i> Envio anônimo</p>
                <% } else if (feedback.getUsuario() != null) { %>
                    <p class="mb-0"><strong><%=feedback.getUsuario().getNome()%></strong></p>
                <% } else { %>
                    <p class="page-subtitle mb-0">Não informado</p>
                <% } %>
            </div>
        </div>

        <div class="card mt-3">
            <div class="card-body">
                <span class="metric-icon mb-3"><i class="bi bi-calendar-event" aria-hidden="true"></i></span>
                <h3 class="h5">Data de envio</h3>
                <p class="page-subtitle mb-0">
                    <%=feedback.getDataEnvio() != null ? feedback.getDataEnvio().format(fmt) : "-"%>
                </p>
            </div>
        </div>

        <a href="${pageContext.request.contextPath}/feedbackview?acao=excluir&id=<%=feedback.getIdFeedback()%>"
           class="btn btn-outline-danger w-100 mt-3"
           onclick="return confirm('Deseja realmente excluir este feedback?');">
            <i class="bi bi-trash" aria-hidden="true"></i> Excluir feedback
        </a>
    </div>
</div>

<%@ include file="/WEB-INF/includes/footer.jsp" %>