<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="br.com.conectacampus.model.Categoria"%>
<%
    List<Categoria> categorias = (List<Categoria>) request.getAttribute("listaCategorias");

    request.setAttribute("paginaAtiva", "comunicados");
    request.setAttribute("tituloPagina", "Novo Comunicado - Conecta Campus");
%>
<%@ include file="/WEB-INF/includes/header.jsp" %>

<div class="page-header">
    <div>
        <p class="eyebrow">Publicação</p>
        <h1 class="page-title"><i class="bi bi-megaphone-fill" aria-hidden="true"></i> Novo comunicado</h1>
        <p class="page-subtitle">Crie um aviso claro para a comunidade acadêmica.</p>
    </div>
    <a href="${pageContext.request.contextPath}/comunicados" class="btn btn-secondary">
        <i class="bi bi-arrow-left" aria-hidden="true"></i> Voltar
    </a>
</div>

<div class="card">
    <div class="card-body">
        <form action="${pageContext.request.contextPath}/comunicados" method="post">
            <input type="hidden" name="acao" value="salvar">

            <div class="mb-3">
                <label class="form-label" for="titulo">Título</label>
                <input type="text" id="titulo" name="titulo" class="form-control" maxlength="200" required>
            </div>

            <div class="mb-3">
                <label class="form-label" for="mensagem">Mensagem</label>
                <textarea id="mensagem" name="mensagem" rows="6" class="form-control" required></textarea>
            </div>

            <div class="row g-3">
                <div class="col-md-6">
                    <label class="form-label" for="categoria">Categoria</label>
                    <select id="categoria" name="idCategoria" class="form-select" required>
                        <option value="">Selecione...</option>
                        <%
                        if (categorias != null && !categorias.isEmpty()) {
                            for (Categoria cat : categorias) {
                        %>
                        <option value="<%=cat.getIdCategoria()%>"><%=cat.getNome()%></option>
                        <%
                            }
                        } else {
                        %>
                        <option value="" disabled>Nenhuma categoria cadastrada</option>
                        <% } %>
                    </select>
                    <% if (categorias == null || categorias.isEmpty()) { %>
                    <div class="form-text text-danger">
                        Nenhuma categoria encontrada no banco. Cadastre categorias antes de publicar comunicados.
                    </div>
                    <% } %>
                </div>
                <div class="col-md-6">
                    <label class="form-label" for="prioridade">Prioridade</label>
                    <select id="prioridade" name="prioridade" class="form-select">
                        <option value="BAIXA">BAIXA</option>
                        <option value="MEDIA" selected>MÉDIA</option>
                        <option value="ALTA">ALTA</option>
                    </select>
                </div>
            </div>

            <div class="mt-4 action-row">
                <button type="submit" class="btn btn-primary"><i class="bi bi-check-circle" aria-hidden="true"></i> Salvar</button>
                <a href="${pageContext.request.contextPath}/comunicados" class="btn btn-outline-secondary">Cancelar</a>
            </div>
        </form>
    </div>
</div>

<%@ include file="/WEB-INF/includes/footer.jsp" %>