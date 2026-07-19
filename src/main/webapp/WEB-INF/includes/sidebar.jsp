<%
    String paginaAtiva = (String) request.getAttribute("paginaAtiva");
    if (paginaAtiva == null) {
        paginaAtiva = "";
    }
%>
<aside class="sidebar" id="sidebarMenu" aria-label="Menu principal">
    <ul>
        <li>
            <a class="<%= "home".equals(paginaAtiva) ? "active" : "" %>" href="${pageContext.request.contextPath}/pages/home.jsp"
               <%= "home".equals(paginaAtiva) ? "aria-current=\"page\"" : "" %> title="Home">
                <i class="bi bi-house-door-fill" aria-hidden="true"></i> <span class="link-text">Home</span>
            </a>
        </li>
        <li>
            <a class="<%= "usuarios".equals(paginaAtiva) ? "active" : "" %>" href="${pageContext.request.contextPath}/usuarios" title="Usuários">
                <i class="bi bi-people-fill" aria-hidden="true"></i> <span class="link-text">Usuários</span>
            </a>
        </li>
        <li>
            <a class="<%= "cargos".equals(paginaAtiva) ? "active" : "" %>" href="${pageContext.request.contextPath}/cargos" title="Cargos">
                <i class="bi bi-briefcase-fill" aria-hidden="true"></i> <span class="link-text">Cargos</span>
            </a>
        </li>
        <li>
            <a class="<%= "membros".equals(paginaAtiva) ? "active" : "" %>" href="${pageContext.request.contextPath}/membros" title="Membros">
                <i class="bi bi-person-badge-fill" aria-hidden="true"></i> <span class="link-text">Membros</span>
            </a>
        </li>
        <li>
		    <a class="<%= "comunicados".equals(paginaAtiva) ? "active" : "" %>" href="${pageContext.request.contextPath}/comunicados" title="Comunicados">
		        <i class="bi bi-megaphone-fill" aria-hidden="true"></i> <span class="link-text">Comunicados</span>
		    </a>
		</li>
        <li>
            <a class="<%= "forum".equals(paginaAtiva) ? "active" : "" %>" href="${pageContext.request.contextPath}/forum" title="Fórum">
                <i class="bi bi-chat-left-text-fill" aria-hidden="true"></i> <span class="link-text">Fórum</span>
            </a>
        </li>
        <li>
            <a class="<%= "enquetes".equals(paginaAtiva) ? "active" : "" %>" href="${pageContext.request.contextPath}/enquetes" title="Enquetes">
                <i class="bi bi-ui-checks-grid" aria-hidden="true"></i> <span class="link-text">Enquetes</span>
            </a>
        </li>
        <li>
            <a class="<%= "feedback".equals(paginaAtiva) ? "active" : "" %>" href="${pageContext.request.contextPath}/feedback" title="Feedback">
                <i class="bi bi-envelope-fill" aria-hidden="true"></i> <span class="link-text">Feedback</span>
            </a>
        </li>
        <li>
            <a class="<%= "dashboard".equals(paginaAtiva) ? "active" : "" %>" href="${pageContext.request.contextPath}/dashboard" title="Dashboard">
                <i class="bi bi-bar-chart-fill" aria-hidden="true"></i> <span class="link-text">Dashboard</span>
            </a>
        </li>
        <li>
		    <a class="<%= "feedbackview".equals(paginaAtiva) ? "active" : "" %>" href="${pageContext.request.contextPath}/feedbackview" title="Visualizar Feedbacks">
		        <i class="bi bi-eye-fill" aria-hidden="true"></i> <span class="link-text">Ver Feedbacks</span>
		    </a>
		</li>
    </ul>
</aside>