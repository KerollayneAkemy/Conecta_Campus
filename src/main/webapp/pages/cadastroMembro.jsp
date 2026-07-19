<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="br.com.conectacampus.model.Cargo"%>
<%@ page import="br.com.conectacampus.model.Membro"%>
<%
    Membro membro = (Membro) request.getAttribute("membro");
    List<Cargo> listaCargos = (List<Cargo>) request.getAttribute("listaCargos");

    String nomeAtual = (membro != null) ? membro.getNome() : "";
    String emailAtual = (membro != null) ? membro.getEmail() : "";
    String telefoneAtual = (membro != null) ? membro.getTelefone() : "";
    String idMembroAtual = (membro != null) ? String.valueOf(membro.getIdMembro()) : "";
    int idCargoAtual = (membro != null && membro.getCargo() != null) ? membro.getCargo().getIdCargo() : 0;

    request.setAttribute("paginaAtiva", "membros");
    request.setAttribute("tituloPagina", "Cadastrar Membro - Conecta Campus");
%>
<%@ include file="/WEB-INF/includes/header.jsp" %>

<div class="page-header">
    <div>
        <p class="eyebrow">Administração</p>
        <h1 class="page-title"><i class="bi bi-person-badge-fill" aria-hidden="true"></i>
            <%=(membro != null) ? "Editar Membro" : "Cadastrar Membro"%>
        </h1>
        <p class="page-subtitle">Cadastre os membros vinculados a um cargo da plataforma.</p>
    </div>
    <a href="${pageContext.request.contextPath}/membros" class="btn btn-secondary">
        <i class="bi bi-list-ul" aria-hidden="true"></i> Ver todos os membros
    </a>
</div>

<div class="row">
    <div class="col-lg-7">
        <div class="card">
            <div class="card-header">
                <i class="bi bi-pencil-square" aria-hidden="true"></i> Dados do membro
            </div>
            <div class="card-body">
                <form method="post" action="${pageContext.request.contextPath}/membros" novalidate>
                    <% if (membro != null) { %>
                    <input type="hidden" name="idMembro" value="<%=idMembroAtual%>">
                    <input type="hidden" name="acao" value="atualizar">
                    <% } else { %>
                    <input type="hidden" name="acao" value="cadastrar">
                    <% } %>

                    <div class="mb-3">
                        <label for="nome" class="form-label">Nome <span class="text-danger">*</span></label>
                        <input type="text" class="form-control" id="nome" name="nome"
                               value="<%=nomeAtual%>" maxlength="150" required
                               placeholder="Nome completo do membro">
                    </div>

                    <div class="mb-3">
                        <label for="email" class="form-label">E-mail <span class="text-danger">*</span></label>
                        <input type="email" class="form-control" id="email" name="email"
                               value="<%=emailAtual%>" maxlength="150" required
                               placeholder="exemplo@conectacampus.com">
                    </div>

                    <div class="mb-3">
                        <label for="telefone" class="form-label">Telefone <span class="text-danger">*</span></label>
                        <input type="text" class="form-control" id="telefone" name="telefone"
                               value="<%=telefoneAtual%>" maxlength="14" required
                               placeholder="(00) 00000-0000">
                    </div>

                    <div class="mb-3">
                        <label for="idCargo" class="form-label">Cargo</label>
                        <select class="form-select" id="idCargo" name="idCargo">
                            <option value="">Selecione um cargo...</option>
                            <%
                            if (listaCargos != null) {
                                for (Cargo c : listaCargos) {
                                    boolean selecionado = (c.getIdCargo() == idCargoAtual);
                            %>
                            <option value="<%=c.getIdCargo()%>" <%=selecionado ? "selected" : ""%>>
                                <%=c.getNome()%>
                            </option>
                            <%
                                }
                            }
                            %>
                        </select>
                    </div>

                    <div class="d-flex gap-2">
                        <button type="submit" class="btn btn-primary">
                            <i class="bi bi-check-lg" aria-hidden="true"></i>
                            <%=(membro != null) ? "Salvar alterações" : "Cadastrar membro"%>
                        </button>
                        <a href="${pageContext.request.contextPath}/membros" class="btn btn-outline-secondary">
                            Cancelar
                        </a>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <div class="col-lg-5">
        <div class="card">
            <div class="card-body">
                <span class="metric-icon mb-3"><i class="bi bi-info-circle-fill" aria-hidden="true"></i></span>
                <h3 class="h5">Dica</h3>
                <p class="page-subtitle mb-0">
                    O cargo é opcional no cadastro, mas ajuda a organizar os membros por função.
                    Cadastre primeiro os cargos na tela de <a href="${pageContext.request.contextPath}/cargos">Cargos</a> para que fiquem disponíveis aqui na lista.
                </p>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/includes/footer.jsp" %>