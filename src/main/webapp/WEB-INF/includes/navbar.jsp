<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="br.com.conectacampus.model.Usuario"%>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
    if (usuario == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
    request.setAttribute("usuarioLogado", usuario);
%>
<a class="skip-link" href="#conteudo">Ir para o conteúdo</a>

<nav class="navbar">
    <div class="container-fluid">
        <div class="d-flex align-items-center gap-2">
            <button type="button" id="sidebarToggle" onclick="toggleSidebar()" aria-label="Recolher menu" aria-expanded="true" aria-controls="sidebarMenu">
                <i class="bi bi-list" aria-hidden="true"></i>
            </button>
            <a class="navbar-brand" href="${pageContext.request.contextPath}/pages/home.jsp"><i class="bi bi-mortarboard-fill" aria-hidden="true"></i> Conecta Campus</a>
        </div>
        <div class="ms-auto d-flex align-items-center gap-3">
            <span class="navbar-user"><i class="bi bi-person-circle" aria-hidden="true"></i> <%=usuario.getNome()%></span>
            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/logout">Sair</a>
        </div>
    </div>
</nav>