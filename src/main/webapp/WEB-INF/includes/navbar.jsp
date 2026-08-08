<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="br.com.conectacampus.model.Usuario"%>
<%@ page import="br.com.conectacampus.util.Autorizacao"%>
<%
    Usuario usuarioNavbar = (Usuario) session.getAttribute("usuarioLogado");
    if (usuarioNavbar == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
    request.setAttribute("usuarioLogado", usuarioNavbar);
    String[] partesNome = usuarioNavbar.getNome() == null ? new String[0] : usuarioNavbar.getNome().trim().split("\\s+");
    String iniciaisUsuario = partesNome.length == 0 ? "U" : String.valueOf(partesNome[0].charAt(0));
    if (partesNome.length > 1) iniciaisUsuario += partesNome[partesNome.length - 1].charAt(0);
    String perfilUsuario = usuarioNavbar.getPerfil() == null ? "Conta" : usuarioNavbar.getPerfil().getNome().replace("_", " ");
    String fotoPerfilNavbar = usuarioNavbar.getFotoPerfil();
    boolean temFotoPerfil = fotoPerfilNavbar != null && !fotoPerfilNavbar.isBlank();

    boolean adminReal = Autorizacao.ehAdministrador(usuarioNavbar);
    String papelSimulado = (String) session.getAttribute("papelSimulado");
    String urlAtual = request.getRequestURI() + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
%>
<a class="skip-link" href="#conteudo">Ir para o conteúdo</a>

<nav class="navbar">
	<div class="container-fluid">
		<div class="navbar-start">
			<button type="button" id="sidebarToggle"
				onclick="if (typeof toggleSidebar === 'function') toggleSidebar()"
				aria-label="Recolher menu" aria-expanded="true"
				aria-controls="sidebarMenu">
				<i class="bi bi-list" aria-hidden="true"></i>
			</button>
			<a class="navbar-brand"
				href="${pageContext.request.contextPath}/pages/home.jsp"> <span
				class="brand-mark"><i class="bi bi-mortarboard-fill"
					aria-hidden="true"></i></span> <span><strong>Conecta
						Campus</strong><small>Comunidade acadêmica</small></span>
			</a>
		</div>
		<div class="navbar-actions">

			<% if (adminReal) { %>
			<details class="account-menu">
				<summary class="navbar-profile" aria-label="Trocar modo de visualização">
					<i class="bi bi-eye" aria-hidden="true"></i>
					<span class="navbar-profile-text"><strong>Visualizar como</strong><small><%= papelSimulado == null ? "Administrador" : ("EQUIPE".equals(papelSimulado) ? "Equipe" : "Aluno") %></small></span>
					<i class="bi bi-chevron-down navbar-profile-arrow" aria-hidden="true"></i>
				</summary>
				<div class="account-menu-panel">
					<p class="account-menu-title">Modo de visualização</p>
					<a href="${pageContext.request.contextPath}/visualizacao?papel=ADMINISTRADOR&origem=<%=java.net.URLEncoder.encode(urlAtual, "UTF-8")%>">
						<i class="bi bi-shield-lock-fill" aria-hidden="true"></i> Administrador
					</a>
					<a href="${pageContext.request.contextPath}/visualizacao?papel=EQUIPE&origem=<%=java.net.URLEncoder.encode(urlAtual, "UTF-8")%>">
						<i class="bi bi-person-badge-fill" aria-hidden="true"></i> Equipe
					</a>
					<a href="${pageContext.request.contextPath}/visualizacao?papel=ALUNO&origem=<%=java.net.URLEncoder.encode(urlAtual, "UTF-8")%>">
						<i class="bi bi-mortarboard-fill" aria-hidden="true"></i> Aluno
					</a>
				</div>
			</details>
			<% } %>

			<details class="account-menu">
				<summary class="navbar-profile" aria-label="Abrir opções da conta">
					<% if (temFotoPerfil) { %>
					<img class="navbar-profile-avatar"
						src="${pageContext.request.contextPath}/<%=fotoPerfilNavbar%>"
						alt="">
					<% } else { %>
					<span class="navbar-profile-avatar navbar-profile-initials"
						aria-hidden="true"><%= iniciaisUsuario.toUpperCase() %></span>
					<% } %>
					<span class="navbar-profile-text"><strong><%= usuarioNavbar.getNome() %></strong><small><%= perfilUsuario %></small></span>
					<i class="bi bi-chevron-down navbar-profile-arrow"
						aria-hidden="true"></i>
				</summary>
				<div class="account-menu-panel">
					<p class="account-menu-title">Minha conta</p>
					<a href="${pageContext.request.contextPath}/perfil"><i class="bi bi-person-gear" aria-hidden="true"></i> Meu perfil</a>
					<a class="account-menu-exit" href="${pageContext.request.contextPath}/logout"><i class="bi bi-box-arrow-right" aria-hidden="true"></i> Sair</a>
				</div>
			</details>
		</div>
	</div>
</nav>