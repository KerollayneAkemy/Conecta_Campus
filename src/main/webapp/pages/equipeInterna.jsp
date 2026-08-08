<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<%@ page import="java.util.List,java.util.Set"%>
<%@ page import="br.com.conectacampus.model.ReuniaoEquipe,br.com.conectacampus.model.VotacaoEquipe,br.com.conectacampus.model.OpcaoVotacaoEquipe,br.com.conectacampus.model.Usuario"%>
<%@ page import="br.com.conectacampus.util.Autorizacao"%>
<%
List<ReuniaoEquipe> reunioes = (List<ReuniaoEquipe>) request.getAttribute("reunioes");
List<VotacaoEquipe> votacoes = (List<VotacaoEquipe>) request.getAttribute("votacoes");
Set<Integer> votacoesRespondidas = (Set<Integer>) request.getAttribute("votacoesRespondidas");
Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
boolean admin = Autorizacao.ehAdministrador(usuario);
DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm");
request.setAttribute("tituloPagina", "Área da equipe - Conecta Campus");
%>
<% request.setAttribute("paginaAtiva", "equipe-interna"); %>
<%@ include file="/WEB-INF/includes/header.jsp"%>

<div class="page-header">
    <div>
        <p class="eyebrow">Área restrita</p>
        <h1 class="page-title"><i class="bi bi-people-fill"></i> Espaço da equipe</h1>
        <p class="page-subtitle">Organize reuniões e decida assuntos internos com a equipe institucional.</p>
    </div>
</div>

<div class="row g-4">
    <div class="col-xl-5">
        <section class="card h-100">
            <div class="card-header"><i class="bi bi-calendar-event"></i> Agendar reunião</div>
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/equipe-interna" method="post">
                    <input type="hidden" name="acao" value="criarReuniao">
                    <div class="mb-3"><label class="form-label" for="tituloReuniao">Título</label><input class="form-control" id="tituloReuniao" name="titulo" maxlength="200" required></div>
                    <div class="row g-3">
                        <div class="col-md-7"><label class="form-label" for="dataHora">Data e horário</label><input class="form-control" id="dataHora" type="datetime-local" name="dataHora" required></div>
                        <div class="col-md-5"><label class="form-label" for="localReuniao">Local ou link</label><input class="form-control" id="localReuniao" name="localReuniao" maxlength="200" placeholder="Sala ou Meet"></div>
                    </div>
                    <div class="mt-3"><label class="form-label" for="descricaoReuniao">Pauta</label><textarea class="form-control" id="descricaoReuniao" name="descricao" rows="3" placeholder="Assuntos que serão tratados"></textarea></div>
                    <button class="btn btn-primary mt-3"><i class="bi bi-calendar-plus"></i> Agendar reunião</button>
                </form>
            </div>
        </section>
    </div>
    <div class="col-xl-7">
        <section class="card h-100">
            <div class="card-header"><i class="bi bi-bar-chart-steps"></i> Criar votação interna</div>
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/equipe-interna" method="post">
                    <input type="hidden" name="acao" value="criarVotacao">
                    <div class="row g-3">
                        <div class="col-md-8"><label class="form-label" for="tituloVotacao">Pergunta ou decisão</label><input class="form-control" id="tituloVotacao" name="titulo" maxlength="200" required></div>
                        <div class="col-md-4"><label class="form-label" for="dataLimite">Encerrar em (opcional)</label><input class="form-control" id="dataLimite" type="datetime-local" name="dataLimite"></div>
                        <div class="col-12"><label class="form-label" for="descricaoVotacao">Contexto</label><textarea class="form-control" id="descricaoVotacao" name="descricao" rows="2"></textarea></div>
                        <div class="col-12"><label class="form-label" for="opcoes">Opções</label><textarea class="form-control" id="opcoes" name="opcoes" rows="3" required placeholder="Uma opção por linha&#10;Ex.: Aprovar proposta&#10;Ex.: Revisar proposta"></textarea><div class="form-text">Cada integrante poderá escolher apenas uma opção.</div></div>
                    </div>
                    <button class="btn btn-primary mt-3"><i class="bi bi-plus-circle"></i> Publicar votação</button>
                </form>
            </div>
        </section>
    </div>
</div>

<section class="mt-4">
    <div class="d-flex align-items-center justify-content-between mb-3"><h2 class="h4 mb-0"><i class="bi bi-calendar3"></i> Reuniões</h2><span class="text-muted"><%= reunioes == null ? 0 : reunioes.size() %> registro(s)</span></div>
    <div class="row g-3">
    <% if (reunioes != null && !reunioes.isEmpty()) { for (ReuniaoEquipe reuniao : reunioes) { %>
        <div class="col-md-6 col-xl-4"><article class="card h-100"><div class="card-body d-flex flex-column">
            <div class="d-flex justify-content-between gap-2"><span class="badge <%= "AGENDADA".equals(reuniao.getStatus()) ? "text-bg-primary" : "CANCELADA".equals(reuniao.getStatus()) ? "text-bg-secondary" : "text-bg-success" %>"><%= reuniao.getStatus() %></span><small class="text-muted"><%= reuniao.getDataHora().format(formato) %></small></div>
            <h3 class="h5 mt-3"><%= reuniao.getTitulo() %></h3><p class="text-muted flex-grow-1"><%= reuniao.getDescricao() == null || reuniao.getDescricao().isBlank() ? "Sem pauta cadastrada." : reuniao.getDescricao() %></p>
            <% if (reuniao.getLocalReuniao() != null && !reuniao.getLocalReuniao().isBlank()) { %><span class="small mb-3"><i class="bi bi-geo-alt"></i> <%= reuniao.getLocalReuniao() %></span><% } %>
            <small class="text-muted mb-2">Agendada por <%= reuniao.getCriador().getNome() %></small>
            <form action="${pageContext.request.contextPath}/equipe-interna" method="post" class="d-flex gap-2"><input type="hidden" name="acao" value="alterarStatusReuniao"><input type="hidden" name="idReuniao" value="<%= reuniao.getIdReuniao() %>"><select class="form-select form-select-sm" name="status"><option value="AGENDADA" <%= "AGENDADA".equals(reuniao.getStatus()) ? "selected" : "" %>>Agendada</option><option value="REALIZADA" <%= "REALIZADA".equals(reuniao.getStatus()) ? "selected" : "" %>>Realizada</option><option value="CANCELADA" <%= "CANCELADA".equals(reuniao.getStatus()) ? "selected" : "" %>>Cancelada</option></select><button class="btn btn-outline-primary btn-sm">Salvar</button></form>
            <% if (admin) { %><a class="btn btn-outline-danger btn-sm mt-2" data-confirm="true" data-confirm-message="Deseja realmente excluir esta reunião?" href="${pageContext.request.contextPath}/equipe-interna?acao=excluirReuniao&id=<%= reuniao.getIdReuniao() %>"><i class="bi bi-trash"></i> Excluir</a><% } %>
        </div></article></div>
    <% } } else { %><div class="col-12"><div class="empty-state"><i class="bi bi-calendar-x"></i><h3 class="h5">Nenhuma reunião agendada</h3><p>Agende a primeira reunião da equipe acima.</p></div></div><% } %>
    </div>
</section>

<section class="mt-5 mb-4">
    <div class="d-flex align-items-center justify-content-between mb-3"><h2 class="h4 mb-0"><i class="bi bi-check2-square"></i> Votações internas</h2><span class="text-muted"><%= votacoes == null ? 0 : votacoes.size() %> registro(s)</span></div>
    <div class="row g-3">
    <% if (votacoes != null && !votacoes.isEmpty()) { for (VotacaoEquipe votacao : votacoes) { boolean votou = votacoesRespondidas != null && votacoesRespondidas.contains(votacao.getIdVotacao()); boolean aberta = "ABERTA".equals(votacao.getStatus()) && (votacao.getDataLimite() == null || !votacao.getDataLimite().isBefore(java.time.LocalDateTime.now())); int total = 0; for (OpcaoVotacaoEquipe opcao : votacao.getOpcoes()) total += opcao.getTotalVotos(); %>
        <div class="col-lg-6"><article class="card h-100"><div class="card-body">
            <div class="d-flex justify-content-between gap-2"><span class="badge <%= aberta ? "text-bg-success" : "text-bg-secondary" %>"><%= aberta ? "ABERTA" : "ENCERRADA" %></span><small class="text-muted"><%= votacao.getDataLimite() == null ? "Sem prazo" : "Até " + votacao.getDataLimite().format(formato) %></small></div>
            <h3 class="h5 mt-3"><%= votacao.getTitulo() %></h3><p class="text-muted"><%= votacao.getDescricao() == null || votacao.getDescricao().isBlank() ? "Escolha uma das opções abaixo." : votacao.getDescricao() %></p>
            <% if (aberta && !votou) { %><form action="${pageContext.request.contextPath}/equipe-interna" method="post"><input type="hidden" name="acao" value="votar"><input type="hidden" name="idVotacao" value="<%= votacao.getIdVotacao() %>"><% for (OpcaoVotacaoEquipe opcao : votacao.getOpcoes()) { %><div class="form-check border rounded p-2 mb-2"><input class="form-check-input" type="radio" name="idOpcao" id="opcao<%= opcao.getIdOpcao() %>" value="<%= opcao.getIdOpcao() %>" required><label class="form-check-label w-100" for="opcao<%= opcao.getIdOpcao() %>"><%= opcao.getDescricao() %></label></div><% } %><button class="btn btn-primary w-100 mt-2"><i class="bi bi-check-circle"></i> Registrar voto</button></form><% } else { %><p class="small fw-semibold <%= votou ? "text-success" : "text-muted" %>"><i class="bi <%= votou ? "bi-check2-circle" : "bi-lock" %>"></i> <%= votou ? "Seu voto foi registrado." : "Votação encerrada." %></p><% for (OpcaoVotacaoEquipe opcao : votacao.getOpcoes()) { int percentual = total == 0 ? 0 : (int) Math.round(opcao.getTotalVotos() * 100.0 / total); %><div class="mb-2"><div class="d-flex justify-content-between small"><span><%= opcao.getDescricao() %></span><span><%= opcao.getTotalVotos() %> voto(s)</span></div><div class="progress" role="progressbar" aria-label="<%= opcao.getDescricao() %>" aria-valuenow="<%= percentual %>" aria-valuemin="0" aria-valuemax="100"><div class="progress-bar" style="width: <%= percentual %>%"></div></div></div><% } %><% } %>
            <div class="d-flex gap-2 mt-3"><form action="${pageContext.request.contextPath}/equipe-interna" method="post" class="d-flex gap-2 flex-grow-1"><input type="hidden" name="acao" value="alterarStatusVotacao"><input type="hidden" name="idVotacao" value="<%= votacao.getIdVotacao() %>"><select class="form-select form-select-sm" name="status"><option value="ABERTA" <%= "ABERTA".equals(votacao.getStatus()) ? "selected" : "" %>>Aberta</option><option value="ENCERRADA" <%= "ENCERRADA".equals(votacao.getStatus()) ? "selected" : "" %>>Encerrada</option></select><button class="btn btn-outline-primary btn-sm">Salvar</button></form><% if (admin) { %><a class="btn btn-outline-danger btn-sm" data-confirm="true" data-confirm-message="Deseja realmente excluir esta votação e seus votos?" href="${pageContext.request.contextPath}/equipe-interna?acao=excluirVotacao&id=<%= votacao.getIdVotacao() %>"><i class="bi bi-trash"></i></a><% } %></div>
        </div></article></div>
    <% } } else { %><div class="col-12"><div class="empty-state"><i class="bi bi-check2-square"></i><h3 class="h5">Nenhuma votação disponível</h3><p>Crie uma votação para decidir algo com a equipe.</p></div></div><% } %>
    </div>
</section>
<%@ include file="/WEB-INF/includes/confirmModal.jsp"%>
<%@ include file="/WEB-INF/includes/footer.jsp"%>
