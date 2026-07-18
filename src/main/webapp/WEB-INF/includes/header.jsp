<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title><%= request.getAttribute("tituloPagina") != null ? request.getAttribute("tituloPagina") : "Conecta Campus" %></title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

<style>
    #appShell {
        display: flex;
    }
    #sidebarMenu {
        width: 272px;
        transition: width 0.25s ease, padding 0.25s ease;
        overflow-x: hidden; /* AJUSTE: era "overflow: hidden", quebrava o overflow-y:auto do sidebar no style.css */
        flex-shrink: 0;
    }
    #appShell.sidebar-collapsed #sidebarMenu { width: 76px !important; }
    #appShell.sidebar-collapsed #sidebarMenu .link-text { display: none !important; }
    #appShell.sidebar-collapsed #sidebarMenu a {
        justify-content: center !important;
        padding-left: 0 !important;
        padding-right: 0 !important;
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
    #sidebarToggle:hover { background: #e8f7f1; border-color: #dbe5df; }

    /* NOVO: faz a navbar ficar fixa no topo ao rolar a página */
    .navbar {
        position: sticky;
        top: 0;
        z-index: 1030;
    }
</style>
</head>
<body>

<%@ include file="navbar.jsp" %>

<div class="app-shell" id="appShell">
<%@ include file="sidebar.jsp" %>
<main class="content" id="conteudo">