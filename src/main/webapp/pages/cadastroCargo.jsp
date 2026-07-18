<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="br.com.conectacampus.model.Cargo"%>
<%
    Cargo cargo = (Cargo) request.getAttribute("cargo");
    String nomeAtual = (cargo != null) ? cargo.getNome() : "";
    String descricaoAtual = (cargo != null) ? cargo.getDescricao() : "";
    String idCargoAtual = (cargo != null) ? String.valueOf(cargo.getIdCargo()) : "";

    request.setAttribute("paginaAtiva", "cargos");
    request.setAttribute("tituloPagina", "Cadastrar Cargo - Conecta Campus");
%>
<%@ include file="/WEB-INF/includes/header.jsp" %>

<div class="page-header">
    <div>
        <p class="eyebrow">Administração</p>
        <h1 class="page-title"><i class="bi bi-briefcase-fill" aria-hidden="true"></i>
            <%=(cargo != null) ? "Editar Cargo" : "Cadastrar Cargo"%>
        </h1>
        <p class="page-subtitle">Cadastre os cargos disponíveis para os usuários da plataforma.</p>
    </div>
    <a href="${pageContext.request.contextPath}/cargos" class="btn btn-secondary">
        <i class="bi bi-list-ul" aria-hidden="true"></i> Ver todos os cargos
    </a>
</div>

<div class="row">
    <div class="col-lg-7">
        <div class="card">
            <div class="card-header">
                <i class="bi bi-pencil-square" aria-hidden="true"></i> Dados do cargo
            </div>
            <div class="card-body">
                <form method="post" action="${pageContext.request.contextPath}/cargos" novalidate>
                    <% if (cargo != null) { %>
                    <input type="hidden" name="idCargo" value="<%=idCargoAtual%>">
                    <input type="hidden" name="acao" value="atualizar">
                    <% } else { %>
                    <input type="hidden" name="acao" value="cadastrar">
                    <% } %>

                    <div class="mb-3">
                        <label for="nome" class="form-label">Nome do cargo <span class="text-danger">*</span></label>
                        <input type="text" class="form-control" id="nome" name="nome"
                               value="<%=nomeAtual%>" maxlength="150" required
                               placeholder="Ex: Coordenador, Professor, Secretário...">
                    </div>

                    <div class="mb-3">
                        <label for="descricao" class="form-label">Descrição</label>
                        <textarea class="form-control" id="descricao" name="descricao" rows="4"
                                  placeholder="Descreva as responsabilidades ou observações sobre este cargo"><%=descricaoAtual%></textarea>
                    </div>

                    <div class="d-flex gap-2">
                        <button type="submit" class="btn btn-primary">
                            <i class="bi bi-check-lg" aria-hidden="true"></i>
                            <%=(cargo != null) ? "Salvar alterações" : "Cadastrar cargo"%>
                        </button>
                        <a href="${pageContext.request.contextPath}/cargos" class="btn btn-outline-secondary">
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
                    O nome do cargo deve ser único e claro (ex: "Professor", "Coordenador de Curso").
                    A descrição é opcional, mas ajuda a documentar as permissões e responsabilidades associadas.
                </p>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/includes/footer.jsp" %>