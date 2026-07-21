<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    request.setAttribute("paginaAtiva", "home");
    request.setAttribute("tituloPagina", "Home - Conecta Campus");
%>
<%@ include file="/WEB-INF/includes/header.jsp" %>

<section class="hero-panel">
    <div>
        <p class="eyebrow">Bem-vindo</p>
        <h1>Olá, ${usuarioLogado.nome}</h1>
        <p class="mb-0">Acompanhe comunicados, participe de discussões e envie feedbacks para melhorar a experiência no campus.</p>
    </div>
    <a class="btn btn-light" href="${pageContext.request.contextPath}/dashboard"><i class="bi bi-graph-up-arrow" aria-hidden="true"></i> Ver relatório financeiro</a>
</section>

<div class="row g-4">
    <div class="col-md-6 col-xl-3">
        <a class="card topic-card h-100" href="${pageContext.request.contextPath}/comunicados">
            <div class="card-body">
                <span class="metric-icon mb-3"><i class="bi bi-megaphone-fill" aria-hidden="true"></i></span>
                <h3 class="h5">Comunicados</h3>
                <p class="page-subtitle">Avisos importantes organizados por prioridade.</p>
            </div>
        </a>
    </div>
    <div class="col-md-6 col-xl-3">
        <a class="card topic-card h-100" href="${pageContext.request.contextPath}/forum">
            <div class="card-body">
                <span class="metric-icon mb-3"><i class="bi bi-chat-left-text-fill" aria-hidden="true"></i></span>
                            <h3 class="h5">Fórum</h3>
                <p class="page-subtitle">Espaço para dúvidas, ideias e conversas acadêmicas.</p>
            </div>
        </a>
    </div>
    <div class="col-md-6 col-xl-3">
        <a class="card topic-card h-100" href="${pageContext.request.contextPath}/feedback">
            <div class="card-body">
                <span class="metric-icon mb-3"><i class="bi bi-envelope-heart-fill" aria-hidden="true"></i></span>
                <h3 class="h5">Feedback</h3>
                <p class="page-subtitle">Envie sugestões, dúvidas, elogios ou reclamações.</p>
            </div>
        </a>
    </div>
</div>

<%@ include file="/WEB-INF/includes/footer.jsp" %>
