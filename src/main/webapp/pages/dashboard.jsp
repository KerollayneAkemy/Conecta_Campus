<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="br.com.conectacampus.model.Usuario"%>
<%
Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
if (usuario == null) {
	response.sendRedirect(request.getContextPath() + "/login");
	return;
}
Integer totalUsuarios = (Integer) request.getAttribute("totalUsuarios");
Integer totalComunicados = (Integer) request.getAttribute("totalComunicados");
Integer totalTopicos = (Integer) request.getAttribute("totalTopicos");
Integer totalEnquetes = (Integer) request.getAttribute("totalEnquetes");
Integer totalFeedbacks = (Integer) request.getAttribute("totalFeedbacks");
int usuarios = totalUsuarios != null ? totalUsuarios : 0;
int comunicados = totalComunicados != null ? totalComunicados : 0;
int topicos = totalTopicos != null ? totalTopicos : 0;
int enquetes = totalEnquetes != null ? totalEnquetes : 0;
int feedbacks = totalFeedbacks != null ? totalFeedbacks : 0;
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Dashboard - Conecta Campus</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script src="https://cdn.canvasjs.com/canvasjs.min.js"></script>
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
            <li><a href="${pageContext.request.contextPath}/pages/home.jsp"><i class="bi bi-house-door-fill" aria-hidden="true"></i> Home</a></li>
            <li><a href="${pageContext.request.contextPath}/usuarios"><i class="bi bi-people-fill" aria-hidden="true"></i> Usuários</a></li>
            <li><a href="${pageContext.request.contextPath}/comunicados"><i class="bi bi-megaphone-fill" aria-hidden="true"></i> Comunicados</a></li>
            <li><a href="${pageContext.request.contextPath}/forum"><i class="bi bi-chat-left-text-fill" aria-hidden="true"></i> Fórum</a></li>
            <li><a href="${pageContext.request.contextPath}/enquetes"><i class="bi bi-ui-checks-grid" aria-hidden="true"></i> Enquetes</a></li>
            <li><a href="${pageContext.request.contextPath}/feedback"><i class="bi bi-envelope-fill" aria-hidden="true"></i> Feedback</a></li>
            <li><a class="active" href="${pageContext.request.contextPath}/dashboard" aria-current="page"><i class="bi bi-bar-chart-fill" aria-hidden="true"></i> Dashboard</a></li>
        </ul>
    </aside>

    <main class="content" id="conteudo">
        <div class="page-header">
            <div>
                <p class="eyebrow">Indicadores</p>
                <h1 class="page-title"><i class="bi bi-bar-chart-fill" aria-hidden="true"></i> Dashboard</h1>
                <p class="page-subtitle">Resumo visual da participação no Conecta Campus.</p>
            </div>
        </div>

        <div class="row g-3 mb-4">
            <div class="col-md-6 col-xl">
                <div class="card metric-card"><div class="card-body"><div><p class="metric-label">Usuários</p><p class="metric-value"><%=usuarios%></p></div><span class="metric-icon"><i class="bi bi-people-fill" aria-hidden="true"></i></span></div></div>
            </div>
            <div class="col-md-6 col-xl">
                <div class="card metric-card"><div class="card-body"><div><p class="metric-label">Comunicados</p><p class="metric-value"><%=comunicados%></p></div><span class="metric-icon"><i class="bi bi-megaphone-fill" aria-hidden="true"></i></span></div></div>
            </div>
            <div class="col-md-6 col-xl">
                <div class="card metric-card"><div class="card-body"><div><p class="metric-label">Fórum</p><p class="metric-value"><%=topicos%></p></div><span class="metric-icon"><i class="bi bi-chat-dots-fill" aria-hidden="true"></i></span></div></div>
            </div>
            <div class="col-md-6 col-xl">
                <div class="card metric-card"><div class="card-body"><div><p class="metric-label">Enquetes</p><p class="metric-value"><%=enquetes%></p></div><span class="metric-icon"><i class="bi bi-ui-checks-grid" aria-hidden="true"></i></span></div></div>
            </div>
            <div class="col-md-6 col-xl">
                <div class="card metric-card"><div class="card-body"><div><p class="metric-label">Feedbacks</p><p class="metric-value"><%=feedbacks%></p></div><span class="metric-icon"><i class="bi bi-chat-heart-fill" aria-hidden="true"></i></span></div></div>
            </div>
        </div>

        <div class="row g-4">
            <div class="col-lg-7">
                <div class="card h-100">
                    <div class="card-header">Volume por módulo</div>
                    <div class="card-body">
                        <div id="graficoModulos" class="chart-box" role="img" aria-label="Gráfico de colunas com totais por módulo"></div>
                    </div>
                </div>
            </div>
            <div class="col-lg-5">
                <div class="card h-100">
                    <div class="card-header">Participação</div>
                    <div class="card-body">
                        <div id="graficoParticipacao" class="chart-box" role="img" aria-label="Gráfico de rosca com participação dos recursos"></div>
                    </div>
                </div>
            </div>
        </div>
    </main>
</div>

<script>
window.onload = function () {
    var chartModules = new CanvasJS.Chart("graficoModulos", {
        animationEnabled: true,
        theme: "light2",
        backgroundColor: "transparent",
        axisY: { includeZero: true, gridColor: "#dbe5df", labelFontColor: "#647067" },
        axisX: { labelFontColor: "#647067" },
        data: [{
            type: "column",
            color: "#007f5f",
            indexLabel: "{y}",
            dataPoints: [
                { label: "Usuários", y: <%=usuarios%> },
                { label: "Comunicados", y: <%=comunicados%> },
                { label: "Fórum", y: <%=topicos%> },
                { label: "Enquetes", y: <%=enquetes%> },
                { label: "Feedbacks", y: <%=feedbacks%> }
            ]
        }]
    });

    var chartParticipation = new CanvasJS.Chart("graficoParticipacao", {
        animationEnabled: true,
        theme: "light2",
        backgroundColor: "transparent",
        data: [{
            type: "doughnut",
            innerRadius: "62%",
            indexLabel: "{label}: {y}",
            toolTipContent: "<strong>{label}</strong>: {y}",
            dataPoints: [
                { label: "Comunicados", y: <%=comunicados%>, color: "#007f5f" },
                { label: "Fórum", y: <%=topicos%>, color: "#2457c5" },
                { label: "Enquetes", y: <%=enquetes%>, color: "#b7791f" },
                { label: "Feedbacks", y: <%=feedbacks%>, color: "#0f766e" }
            ]
        }]
    });

    chartModules.render();
    chartParticipation.render();
};
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
