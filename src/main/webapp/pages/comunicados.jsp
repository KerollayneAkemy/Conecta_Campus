<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="br.com.conectacampus.model.Comunicado"%>
<%
    List<Comunicado> comunicados = (List<Comunicado>) request.getAttribute("listaComunicados");

    request.setAttribute("paginaAtiva", "comunicados");
    request.setAttribute("tituloPagina", "Comunicados - Conecta Campus");
%>
<%@ include file="/WEB-INF/includes/header.jsp" %>

<div class="page-header">
    <div>
        <p class="eyebrow">Comunicação oficial</p>
        <h1 class="page-title"><i class="bi bi-megaphone-fill" aria-hidden="true"></i> Comunicados</h1>
        <p class="page-subtitle">Gerencie avisos, prioridades e publicações do campus.</p>
    </div>
    <a href="${pageContext.request.contextPath}/comunicados?acao=novo" class="btn btn-primary">
        <i class="bi bi-plus-circle" aria-hidden="true"></i> Novo comunicado
    </a>
</div>

<div class="row g-3 mb-4">
    <div class="col-md-4">
        <div class="card metric-card">
            <div class="card-body">
                <div>
                    <p class="metric-label">Total</p>
                    <p class="metric-value"><%=comunicados != null ? comunicados.size() : 0%></p>
                </div>
                <span class="metric-icon"><i class="bi bi-megaphone-fill" aria-hidden="true"></i></span>
            </div>
        </div>
    </div>
</div>

<div class="card">
    <div class="card-header d-flex flex-wrap justify-content-between align-items-center gap-3">
        <span>Lista de comunicados</span>
        <label class="visually-hidden" for="pesquisa">Pesquisar comunicado</label>
        <input id="pesquisa" class="form-control search-input" type="search" placeholder="Pesquisar..." aria-describedby="ajudaPesquisa">
        <span id="ajudaPesquisa" class="visually-hidden">Digite para filtrar a lista de comunicados exibida na tabela.</span>
    </div>
    <div class="table-responsive">
        <table class="table table-hover align-middle">
            <thead>
                <tr>
                    <th>Título</th>
                    <th>Prioridade</th>
                    <th>Status</th>
                    <th>Visualizações</th>
                    <th>Ações</th>
                </tr>
            </thead>
            <tbody>
                <%
                if (comunicados != null && !comunicados.isEmpty()) {
                    for (Comunicado c : comunicados) {
                        String badgePrioridade;
                        switch (c.getPrioridade()) {
                            case "ALTA": badgePrioridade = "bg-danger"; break;
                            case "MEDIA": badgePrioridade = "bg-warning text-dark"; break;
                            default: badgePrioridade = "bg-secondary";
                        }
                        String badgeStatus = "ATIVO".equals(c.getStatus()) ? "bg-success" : "bg-secondary";
                %>
                <tr class="comunicado-row">
                    <td><strong><%=c.getTitulo()%></strong></td>
                    <td><span class="badge <%=badgePrioridade%>"><%=c.getPrioridade()%></span></td>
                    <td><span class="badge <%=badgeStatus%>"><%=c.getStatus()%></span></td>
                    <td><%=c.getVisualizacoes()%></td>
                    <td>
                        <div class="action-row">
                            <a class="btn btn-sm btn-outline-primary" aria-label="Editar comunicado <%=c.getTitulo()%>" href="${pageContext.request.contextPath}/comunicados?acao=editar&id=<%=c.getIdComunicado()%>"><i class="bi bi-pencil" aria-hidden="true"></i></a>
                            <a class="btn btn-sm btn-outline-danger" aria-label="Excluir comunicado <%=c.getTitulo()%>" href="${pageContext.request.contextPath}/comunicados?acao=excluir&id=<%=c.getIdComunicado()%>" data-confirm="true" data-confirm-message="Deseja realmente excluir o comunicado <%=c.getTitulo()%>?"><i class="bi bi-trash" aria-hidden="true"></i></a>
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
                            <i class="bi bi-inbox display-5" aria-hidden="true"></i>
                            <h2 class="h5 mt-3">Nenhum comunicado encontrado</h2>
                            <p class="page-subtitle">Publique o primeiro aviso para a comunidade.</p>
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
    document.querySelectorAll(".comunicado-row").forEach(function (linha) {
        linha.hidden = !linha.textContent.toLowerCase().includes(termo);
    });
});
</script>

<%@ include file="/WEB-INF/includes/footer.jsp" %>