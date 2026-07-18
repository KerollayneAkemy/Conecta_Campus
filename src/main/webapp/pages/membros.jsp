<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="br.com.conectacampus.model.Membro"%>
<%
    List<Membro> membros = (List<Membro>) request.getAttribute("listaMembros");

    request.setAttribute("paginaAtiva", "membros");
    request.setAttribute("tituloPagina", "Membros - Conecta Campus");
%>
<%@ include file="/WEB-INF/includes/header.jsp" %>

<div class="page-header">
    <div>
        <p class="eyebrow">Administração</p>
        <h1 class="page-title"><i class="bi bi-person-badge-fill" aria-hidden="true"></i> Membros</h1>
        <p class="page-subtitle">Gerencie os membros cadastrados na plataforma.</p>
    </div>
    <a href="${pageContext.request.contextPath}/membros?acao=novo" class="btn btn-primary">
        <i class="bi bi-plus-lg" aria-hidden="true"></i> Novo membro
    </a>
</div>

<div class="row g-3 mb-4">
    <div class="col-md-4">
        <div class="card metric-card">
            <div class="card-body">
                <div>
                    <p class="metric-label">Total</p>
                    <p class="metric-value"><%=membros != null ? membros.size() : 0%></p>
                </div>
                <span class="metric-icon"><i class="bi bi-person-badge-fill" aria-hidden="true"></i></span>
            </div>
        </div>
    </div>
</div>

<div class="card">
    <div class="card-header d-flex flex-wrap justify-content-between align-items-center gap-3">
        <span>Lista de membros</span>
        <label class="visually-hidden" for="pesquisa">Pesquisar membro</label>
        <input id="pesquisa" class="form-control search-input" type="search" placeholder="Pesquisar..." aria-describedby="ajudaPesquisa">
        <span id="ajudaPesquisa" class="visually-hidden">Digite para filtrar a lista de membros exibida na tabela.</span>
    </div>
    <div class="table-responsive">
        <table class="table table-hover align-middle">
            <thead>
                <tr>
                    <th>Nome</th>
                    <th>E-mail</th>
                    <th>Telefone</th>
                    <th>Cargo</th>
                    <th>Ações</th>
                </tr>
            </thead>
            <tbody>
                <%
                if (membros != null && !membros.isEmpty()) {
                    for (Membro m : membros) {
                %>
                <tr class="membro-row">
                    <td><i class="bi bi-person-circle me-2" aria-hidden="true"></i><%=m.getNome()%></td>
                    <td><%=m.getEmail()%></td>
                    <td><%=m.getTelefone()%></td>
                    <td><%=m.getCargo() != null ? m.getCargo().getNome() : "-"%></td>
                    <td>
                        <div class="action-row">
                            <a class="btn btn-sm btn-outline-primary" aria-label="Editar membro <%=m.getNome()%>" href="${pageContext.request.contextPath}/membros?acao=editar&id=<%=m.getIdMembro()%>"><i class="bi bi-pencil" aria-hidden="true"></i></a>
                            <a class="btn btn-sm btn-outline-danger" aria-label="Excluir membro <%=m.getNome()%>" href="${pageContext.request.contextPath}/membros?acao=excluir&id=<%=m.getIdMembro()%>" onclick="return confirm('Deseja realmente excluir o membro <%=m.getNome()%>?');"><i class="bi bi-trash" aria-hidden="true"></i></a>
                        </div>
                    </td>
                </tr>
                <%
                    }
                } else {
                %>
                <tr>
                    <td colspan="5">
                        <div class="empty-state">
                            <i class="bi bi-person-x display-5" aria-hidden="true"></i>
                            <h2 class="h5 mt-3">Nenhum membro encontrado</h2>
                        </div>
                    </td>
                </tr>
                <% } %>
            </tbody>
        </table>
    </div>
</div>

<script>
document.getElementById("pesquisa").addEventListener("input", function () {
    var termo = this.value.toLowerCase();
    document.querySelectorAll(".membro-row").forEach(function (linha) {
        linha.hidden = !linha.textContent.toLowerCase().includes(termo);
    });
});
</script>

<%@ include file="/WEB-INF/includes/footer.jsp" %>