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
	href="${pageContext.request.contextPath}/css/style.css">

<style>
#appShell {
	display: block;
}

/* Sidebar agora nasce em top:0, por trás da navbar (z-index menor),
   com padding-top empurrando o conteúdo dela para baixo da navbar.
   Isso elimina a costura entre navbar e sidebar. */
#sidebarMenu {
	position: fixed;
	top: 0;
	left: 0;
	height: 100vh;
	width: 76px;
	padding-top: 110px;
	background: var(--surface);
	transition: width 0.28s ease;
	z-index: 1020;
	box-shadow: none;
	overflow-x: hidden;
	overflow-y: auto;
}

#appShell.sidebar-aberta #sidebarMenu {
	width: 272px;
	box-shadow: 8px 0 30px rgba(0, 0, 0, .22);
}

#sidebarMenu a {
	justify-content: center;
	padding-left: 0;
	padding-right: 0;
}

#sidebarMenu .link-text {
	display: none;
	white-space: nowrap;
}

#appShell.sidebar-aberta #sidebarMenu a {
	justify-content: flex-start;
	padding-left: 14px;
	padding-right: 14px;
}

#appShell.sidebar-aberta #sidebarMenu .link-text {
	display: inline;
}

#sidebarOverlay {
	position: fixed;
	top: 72px;
	left: 0;
	right: 0;
	bottom: 0;
	background: rgba(15, 23, 20, .45);
	z-index: 1015;
	opacity: 0;
	pointer-events: none;
	transition: opacity 0.28s ease;
}

#appShell.sidebar-aberta #sidebarOverlay {
	opacity: 1;
	pointer-events: auto;
}

main.content {
	width: 100%;
	margin-left: 76px;
}

#sidebarToggle {
	display: inline-flex;
	align-items: center;
	justify-content: center;
	width: 40px;
	height: 40px;
	padding: 0;
	margin: 0;
	background: transparent;
	border: 1px solid transparent;
	border-radius: 8px;
	font-size: 1.3rem;
	line-height: 1;
	cursor: pointer;
}

#sidebarToggle:hover {
	background: #e8f7f1;
	border-color: #dbe5df;
}

/* Navbar acima da sidebar em z-index, mesma cor/sem borda,
   para as duas parecerem uma peça só */
.navbar {
	position: fixed;
	top: 0;
	left: 0;
	right: 0;
	width: 100%;
	z-index: 1030;
	border-bottom: none;
	box-shadow: none;
	background: var(--surface);
}

body {
	padding-top: 72px;
}

@media (max-width: 992px) {
	main.content {
		margin-left: 0;
	}
	#sidebarMenu {
		width: 0;
	}
	#appShell.sidebar-aberta #sidebarMenu {
		width: 272px;
	}
}
</style>
</head>
<body>

	<%@ include file="navbar.jsp"%>

	<div class="app-shell" id="appShell">
		<%@ include file="sidebar.jsp"%>
		<div id="sidebarOverlay" onclick="if (typeof toggleSidebar === 'function') toggleSidebar(false)"></div>
		<main class="content" id="conteudo">
			<%@ include file="alerta.jsp"%>
			<%@ include file="confirmModal.jsp"%>