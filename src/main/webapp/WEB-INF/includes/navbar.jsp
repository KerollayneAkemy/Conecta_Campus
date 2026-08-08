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
    boolean administradorReal = Autorizacao.ehAdministradorReal(usuarioNavbar);
    boolean modoAlunoAtivo = administradorReal && Boolean.TRUE.equals(session.getAttribute("modoAlunoAtivo"));
    if (modoAlunoAtivo) perfilUsuario = "VISÃO DE ALUNO";
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
					<a href="${pageContext.request.contextPath}/perfil"><i
						class="bi bi-person-gear" aria-hidden="true"></i> Meu perfil</a>
					<% if (administradorReal) { %>
					<form action="${pageContext.request.contextPath}/modo-aluno" method="post"
						class="account-view-form">
						<button type="submit" class="account-view-button">
							<i class="bi <%=modoAlunoAtivo ? "bi-shield-check" : "bi-mortarboard"%>"
								aria-hidden="true"></i>
							<%=modoAlunoAtivo ? "Voltar para visão de admin" : "Visualizar como aluno"%>
						</button>
					</form>
					<% } %>
					<a
						class="account-menu-exit"
						href="${pageContext.request.contextPath}/logout"><i
						class="bi bi-box-arrow-right" aria-hidden="true"></i> Sair</a>
				</div>
			</details>
		</div>
	</div>
</nav>
