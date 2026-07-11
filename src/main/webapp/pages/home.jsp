<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="br.com.conectacampus.model.Usuario"%>
<%
Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
if (usuario == null) {
	response.sendRedirect(request.getContextPath() + "/login");
	return;
}
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Home - Conecta Campus</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<a class="skip-link" href="#conteudo">Ir para o conteúdo</a>

<nav class="navbar">
    <div class="container-fluid">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/pages/home.jsp"><i class="bi bi-mortarboard-fill" aria-hidden="true"></i> Conecta Campus</a>
        <div class="ms-auto d-flex align-items-center gap-3">
            <span class="navbar-user"><i class="bi bi-person-circle" aria-hidden="true"></i> <%=usuario.getNome()%></span>
            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/logout">Sair</a>
        </div>
    </div>
</nav>

<div class="app-shell">
    <aside class="sidebar" aria-label="Menu principal">
        <ul>
            <li><a class="active" href="${pageContext.request.contextPath}/pages/home.jsp" aria-current="page"><i class="bi bi-house-door-fill" aria-hidden="true"></i> Home</a></li>
            <li><a href="${pageContext.request.contextPath}/usuarios"><i class="bi bi-people-fill" aria-hidden="true"></i> Usuários</a></li>
            <li><a href="${pageContext.request.contextPath}/comunicados"><i class="bi bi-megaphone-fill" aria-hidden="true"></i> Comunicados</a></li>
            <li><a href="${pageContext.request.contextPath}/forum"><i class="bi bi-chat-left-text-fill" aria-hidden="true"></i> Fórum</a></li>
            <li><a href="${pageContext.request.contextPath}/enquetes"><i class="bi bi-ui-checks-grid" aria-hidden="true"></i> Enquetes</a></li>
            <li><a href="${pageContext.request.contextPath}/feedback"><i class="bi bi-envelope-fill" aria-hidden="true"></i> Feedback</a></li>
            <li><a href="${pageContext.request.contextPath}/dashboard"><i class="bi bi-bar-chart-fill" aria-hidden="true"></i> Dashboard</a></li>
        </ul>
    </aside>

    <main class="content" id="conteudo">
        <section class="hero-panel">
            <div>
                <p class="eyebrow">Bem-vindo</p>
                <h1>Olá, <%=usuario.getNome()%></h1>
                <p class="mb-0">Acompanhe comunicados, participe de discussões, responda enquetes e envie feedbacks para melhorar a experiência no campus.</p>
            </div>
            <a class="btn btn-light" href="${pageContext.request.contextPath}/dashboard"><i class="bi bi-graph-up-arrow" aria-hidden="true"></i> Ver indicadores</a>
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
                <a class="card topic-card h-100" href="${pageContext.request.contextPath}/enquetes">
                    <div class="card-body">
                        <span class="metric-icon mb-3"><i class="bi bi-ui-checks-grid" aria-hidden="true"></i></span>
                        <h3 class="h5">Enquetes</h3>
                        <p class="page-subtitle">Votações rápidas para ouvir a comunidade.</p>
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
    </main>
</div>
</body>
</html>
