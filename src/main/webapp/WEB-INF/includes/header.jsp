<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title><%= request.getAttribute("tituloPagina") != null ? request.getAttribute("tituloPagina") : "Conecta Campus" %></title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css?v=11">
<% if (request.getAttribute("cssPagina") != null) { %>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/<%=request.getAttribute("cssPagina")%>?v=11">
<% } %>
</head>
<body>

	<%@ include file="navbar.jsp"%>

	<div class="app-shell" id="appShell">
		<script>
			(function () {
				try {
					if (sessionStorage.getItem('conectaCampus.sidebarAberta') === 'true') {
						var shell = document.getElementById('appShell');
						var botao = document.getElementById('sidebarToggle');
						shell.classList.add('sidebar-aberta');
						if (botao) {
							botao.setAttribute('aria-expanded', 'true');
							botao.setAttribute('aria-label', 'Fechar menu');
						}
					}
				} catch (e) {
					/* A sidebar continua funcionando mesmo sem sessionStorage. */
				}
			})();
		</script>
		<%@ include file="sidebar.jsp"%>
		<div id="sidebarOverlay" onclick="if (typeof toggleSidebar === 'function') toggleSidebar(false)"></div>
		<main class="content" id="conteudo">
			<%@ include file="alerta.jsp"%>
			<%@ include file="confirmModal.jsp"%>
