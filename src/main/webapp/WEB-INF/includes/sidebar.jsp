<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="br.com.conectacampus.model.Usuario"%>
<%@ page import="br.com.conectacampus.util.Autorizacao"%>
<%
Usuario usuarioMenu = (Usuario) session.getAttribute("usuarioLogado");

// Papel real do usuário (nunca muda)
boolean adminRealMenu = Autorizacao.ehAdministrador(usuarioMenu);

// Papel simulado (só existe se o admin escolheu "ver como")
String papelSimuladoMenu = (String) session.getAttribute("papelSimulado");

// Papel EFETIVO usado para montar o menu:
// se for admin de verdade e tiver escolhido simular, usa o simulado;
// caso contrário, usa o papel real.
boolean adminMenu = adminRealMenu && papelSimuladoMenu == null;
boolean equipeMenu = (adminRealMenu && "EQUIPE".equals(papelSimuladoMenu)) || (!adminRealMenu && Autorizacao.ehEquipe(usuarioMenu));
boolean alunoMenu = (adminRealMenu && "ALUNO".equals(papelSimuladoMenu)) || (!adminRealMenu && Autorizacao.ehAluno(usuarioMenu));
%>
<aside class="sidebar" id="sidebarMenu" aria-label="Menu principal">
	<ul>
		<li><a href="${pageContext.request.contextPath}/pages/home.jsp"><i
				class="bi bi-house-door-fill"></i> <span class="link-text">Home</span></a></li>
		<%
		if (adminMenu) {
		%>
		<li><a href="${pageContext.request.contextPath}/usuarios"><i
				class="bi bi-people-fill"></i> <span class="link-text">Usuários</span></a></li>
		<li><a href="${pageContext.request.contextPath}/cargos"><i
				class="bi bi-briefcase-fill"></i> <span class="link-text">Cargos</span></a></li>
		<li><a href="${pageContext.request.contextPath}/membros"><i
				class="bi bi-person-badge-fill"></i> <span class="link-text">Membros</span></a></li>
		<%
		}
		%>
		<%
		if (adminMenu || equipeMenu) {
		%>
		<li><a href="${pageContext.request.contextPath}/categorias"><i
				class="bi bi-tags-fill"></i> <span class="link-text">Categorias</span></a></li>
		<%
		}
		%>
		<li><a href="${pageContext.request.contextPath}/membros-vitrine"><i
				class="bi bi-people-fill"></i> <span class="link-text">Conheça
					a equipe</span></a></li>
		<li><a href="${pageContext.request.contextPath}/comunicados"><i
				class="bi bi-megaphone-fill"></i> <span class="link-text">Comunicados</span></a></li>
		<li><a href="${pageContext.request.contextPath}/forum"><i
				class="bi bi-chat-left-text-fill"></i> <span class="link-text">Fórum</span></a></li>
		<li><a href="${pageContext.request.contextPath}/pesquisas"><i
				class="bi bi-clipboard2-check-fill"></i> <span class="link-text">Pesquisas</span></a></li>
		<%
		if (alunoMenu) {
		%>
		<li><a href="${pageContext.request.contextPath}/feedback"><i
				class="bi bi-envelope-fill"></i> <span class="link-text">Feedback</span></a></li>
		<%
		}
		%>
		<%
		if (adminMenu || equipeMenu) {
		%>
		<li><a href="${pageContext.request.contextPath}/feedbackview"><i
				class="bi bi-eye-fill"></i> <span class="link-text">Ver
					feedbacks</span></a></li>
		<%
		}
		%>
		<%
		if (adminMenu || equipeMenu || alunoMenu) {
		%>
		<li><a href="${pageContext.request.contextPath}/dashboard"><i
				class="bi bi-bar-chart-fill"></i> <span class="link-text">Relatório
					financeiro</span></a></li>
		<%
		}
		%>
	</ul>
</aside>