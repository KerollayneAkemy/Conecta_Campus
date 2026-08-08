<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="br.com.conectacampus.model.Usuario"%>
<%@ page import="br.com.conectacampus.util.Autorizacao"%>
<%
Usuario usuarioMenu = (Usuario) session.getAttribute("usuarioLogado");
boolean adminMenu = Autorizacao.ehAdministrador(usuarioMenu);
boolean equipeMenu = Autorizacao.ehEquipe(usuarioMenu);
boolean alunoMenu = Autorizacao.ehAluno(usuarioMenu);
String paginaAtivaMenu = (String) request.getAttribute("paginaAtiva");
%>
<aside class="sidebar" id="sidebarMenu" aria-label="Menu principal">
	<ul>
		<li><a href="${pageContext.request.contextPath}/pages/home.jsp" class="<%="home".equals(paginaAtivaMenu) ? "active" : ""%>" <% if ("home".equals(paginaAtivaMenu)) { %>aria-current="page"<% } %>><i
				class="bi bi-house-door-fill"></i> <span class="link-text">Home</span></a></li>
		<%
		if (adminMenu) {
		%>
		<li><a href="${pageContext.request.contextPath}/usuarios" class="<%="usuarios".equals(paginaAtivaMenu) ? "active" : ""%>" <% if ("usuarios".equals(paginaAtivaMenu)) { %>aria-current="page"<% } %>><i
				class="bi bi-people-fill"></i> <span class="link-text">Usuários</span></a></li>
		<li><a href="${pageContext.request.contextPath}/cargos" class="<%="cargos".equals(paginaAtivaMenu) ? "active" : ""%>" <% if ("cargos".equals(paginaAtivaMenu)) { %>aria-current="page"<% } %>><i
				class="bi bi-briefcase-fill"></i> <span class="link-text">Cargos</span></a></li>
		<li><a href="${pageContext.request.contextPath}/membros" class="<%="membros".equals(paginaAtivaMenu) ? "active" : ""%>" <% if ("membros".equals(paginaAtivaMenu)) { %>aria-current="page"<% } %>><i
				class="bi bi-person-badge-fill"></i> <span class="link-text">Membros</span></a></li>
		<%
		}
		%>
		<%
		if (adminMenu || equipeMenu) {
		%>
		<li><a href="${pageContext.request.contextPath}/categorias" class="<%="categorias".equals(paginaAtivaMenu) ? "active" : ""%>" <% if ("categorias".equals(paginaAtivaMenu)) { %>aria-current="page"<% } %>><i
				class="bi bi-tags-fill"></i> <span class="link-text">Categorias</span></a></li>
		<%
		}
		%>
		<li><a href="${pageContext.request.contextPath}/membros-vitrine" class="<%="membros-vitrine".equals(paginaAtivaMenu) ? "active" : ""%>" <% if ("membros-vitrine".equals(paginaAtivaMenu)) { %>aria-current="page"<% } %>><i
				class="bi bi-people-fill"></i> <span class="link-text">Conheça
					a equipe</span></a></li>
		<li><a href="${pageContext.request.contextPath}/comunicados" class="<%="comunicados".equals(paginaAtivaMenu) ? "active" : ""%>" <% if ("comunicados".equals(paginaAtivaMenu)) { %>aria-current="page"<% } %>><i
				class="bi bi-megaphone-fill"></i> <span class="link-text">Comunicados</span></a></li>
		<li><a href="${pageContext.request.contextPath}/forum" class="<%="forum".equals(paginaAtivaMenu) ? "active" : ""%>" <% if ("forum".equals(paginaAtivaMenu)) { %>aria-current="page"<% } %>><i
				class="bi bi-chat-left-text-fill"></i> <span class="link-text">Fórum</span></a></li>
		<li><a href="${pageContext.request.contextPath}/pesquisas" class="<%="pesquisas".equals(paginaAtivaMenu) ? "active" : ""%>" <% if ("pesquisas".equals(paginaAtivaMenu)) { %>aria-current="page"<% } %>><i
				class="bi bi-clipboard2-check-fill"></i> <span class="link-text">Pesquisas</span></a></li>
		<%
		if (adminMenu || equipeMenu) {
		%>
		<li><a href="${pageContext.request.contextPath}/equipe-interna" class="<%="equipe-interna".equals(paginaAtivaMenu) ? "active" : ""%>" <% if ("equipe-interna".equals(paginaAtivaMenu)) { %>aria-current="page"<% } %>><i
				class="bi bi-calendar2-week-fill"></i> <span class="link-text">Espaço da equipe</span></a></li>
		<%
		}
		%>
		<%
		if (alunoMenu) {
		%>
		<li><a href="${pageContext.request.contextPath}/feedback" class="<%="feedback".equals(paginaAtivaMenu) ? "active" : ""%>" <% if ("feedback".equals(paginaAtivaMenu)) { %>aria-current="page"<% } %>><i
				class="bi bi-envelope-fill"></i> <span class="link-text">Feedback</span></a></li>
		<%
		}
		%>
		<%
		if (adminMenu || equipeMenu) {
		%>
		<li><a href="${pageContext.request.contextPath}/feedbackview" class="<%="feedbackview".equals(paginaAtivaMenu) ? "active" : ""%>" <% if ("feedbackview".equals(paginaAtivaMenu)) { %>aria-current="page"<% } %>><i
				class="bi bi-eye-fill"></i> <span class="link-text">Ver
					feedbacks</span></a></li>
		<%
		}
		%>
		<%
		if (adminMenu || equipeMenu || alunoMenu) {
		%>
		<li><a href="${pageContext.request.contextPath}/dashboard" class="<%="dashboard".equals(paginaAtivaMenu) ? "active" : ""%>" <% if ("dashboard".equals(paginaAtivaMenu)) { %>aria-current="page"<% } %>><i
				class="bi bi-bar-chart-fill"></i> <span class="link-text">Relatório
					financeiro</span></a></li>
		<%
		}
		%>
	</ul>
</aside>
