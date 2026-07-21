<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="br.com.conectacampus.model.Comunicado"%>
<%@ page import="br.com.conectacampus.model.Categoria"%>
<%@ page import="br.com.conectacampus.model.Usuario"%>
<%@ page import="br.com.conectacampus.util.Autorizacao"%>
<%
    List<Comunicado> comunicados = (List<Comunicado>) request.getAttribute("listaComunicados");
    List<Categoria> categorias = (List<Categoria>) request.getAttribute("listaCategorias");
    Usuario usuarioVisual = (Usuario) session.getAttribute("usuarioLogado");
    boolean podeGerenciarComunicados = Autorizacao.podeGerenciarComunicados(usuarioVisual);
    boolean adminComunicados = Autorizacao.ehAdministrador(usuarioVisual);

    request.setAttribute("paginaAtiva", "comunicados");
    request.setAttribute("tituloPagina", "Comunicados - Conecta Campus");
%>
<%@ include file="/WEB-INF/includes/header.jsp"%>

<style>
.comunicados-filtros {
	display: grid;
	grid-template-columns: minmax(220px, 1fr) minmax(180px, 280px)
		minmax(220px, 300px);
	align-items: end;
	gap: 1rem;
}

.campo-filtro {
	display: grid;
	gap: .35rem;
}

.comunicado-row td {
	padding-top: 1.25rem;
	padding-bottom: 1.25rem;
}

<%
if ( !podeGerenciarComunicados) { %>.
	comunicado-row td: nth-last-child(-n +2){ display: none;
}

<%
}
%>
.comunicado-row details summary {
	cursor: pointer;
	font-weight: 600;
}

.comunicado-row details[open] {
	background: #f4fbf8;
	border-radius: .5rem;
	padding: .65rem .8rem;
}

.tabela-comunicados-legada {
	display: none;
}

.mural-noticias {
	display: grid;
	grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
	gap: 1.25rem;
	padding: 1.25rem;
	background: #f7faf9;
}

.noticia-card {
	position: relative;
	overflow: hidden;
	border: 1px solid var(--line);
	border-radius: 14px;
	background: #fff;
	box-shadow: 0 5px 16px rgba(19, 54, 45, .07);
	transition: transform .18s ease, box-shadow .18s ease;
}

.noticia-card:hover {
	transform: translateY(-4px);
	box-shadow: 0 14px 28px rgba(19, 54, 45, .13);
}

.noticia-faixa {
	display: none;
}

.noticia-card[data-prioridade="ALTA"] {
	border-top: 4px solid #dc3545;
}

.noticia-card[data-prioridade="MEDIA"] {
	border-top: 4px solid #f0ad00;
}

.noticia-conteudo {
	padding: 1.1rem 1.2rem 1.2rem;
}

.noticia-imagem {
	width: 100%;
	height: 205px;
	margin: 0;
	object-fit: cover;
	display: block;
}

.noticia-autor {
	display: none;
}

.noticia-meta {
	display: flex;
	flex-wrap: wrap;
	gap: .45rem;
	margin-bottom: .85rem;
}

.noticia-titulo {
	font-size: 1.12rem;
	line-height: 1.3;
	margin: 0 0 .65rem;
	color: var(--ink);
}

.noticia-mensagem {
	color: #4e615c;
	line-height: 1.55;
	margin: 0;
	white-space: pre-line;
	display: -webkit-box;
	-webkit-line-clamp: 3;
	-webkit-box-orient: vertical;
	overflow: hidden;
}

.noticia-destaque {
	order: -1;
	grid-column: 1/-1;
	display: grid;
	grid-template-columns: minmax(280px, 42%) 1fr;
	background: linear-gradient(120deg, #fff9f9, #fff);
	border-color: rgba(220, 53, 69, .26);
}

.noticia-destaque .noticia-imagem {
	height: 100%;
	min-height: 260px;
}

.noticia-destaque .noticia-conteudo {
	align-self: center;
	padding: 2rem;
}

.noticia-destaque .noticia-titulo {
	font-size: 1.55rem;
}

.noticia-destaque .noticia-mensagem {
	-webkit-line-clamp: unset;
}

.noticia-ler-mais {
	margin-top: .75rem;
	color: var(--brand-dark);
}

.noticia-ler-mais summary {
	cursor: pointer;
	font-weight: 700;
}

.noticia-ler-mais p {
	margin: .7rem 0 0;
	color: #4e615c;
	line-height: 1.55;
	white-space: pre-line;
}

.noticia-acoes {
	display: flex;
	gap: .5rem;
	margin-top: 1rem;
	padding-top: .85rem;
	border-top: 1px solid var(--line);
}

@media ( max-width : 900px) {
	.comunicados-filtros {
		grid-template-columns: 1fr;
	}
	.table-responsive {
		font-size: .92rem;
	}
}

@media ( max-width : 700px) {
	.noticia-destaque {
		display: block;
	}
	.noticia-destaque .noticia-imagem {
		height: 210px;
		min-height: 0;
	}
	.noticia-destaque .noticia-conteudo {
		padding: 1.2rem;
	}
}
</style>

<div class="page-header">
	<div>
		<p class="eyebrow">Comunicação oficial</p>
		<h1 class="page-title">
			<i class="bi bi-megaphone-fill" aria-hidden="true"></i> Comunicados
		</h1>
		<p class="page-subtitle">Gerencie avisos, prioridades e
			publicações do campus.</p>
	</div>
	<% if (podeGerenciarComunicados) { %><a
		href="${pageContext.request.contextPath}/comunicados?acao=novo"
		class="btn btn-primary"> <i class="bi bi-plus-circle"
		aria-hidden="true"></i> Novo comunicado
	</a>
	<% } %>
</div>

<div class="row g-3 mb-4">
	<div class="col-md-4">
		<div class="card metric-card">
			<div class="card-body">
				<div>
					<p class="metric-label">Total</p>
					<p class="metric-value"><%=comunicados != null ? comunicados.size() : 0%></p>
				</div>
				<span class="metric-icon"><i class="bi bi-megaphone-fill"
					aria-hidden="true"></i></span>
			</div>
		</div>
	</div>
</div>

<div class="card">
	<div class="card-header comunicados-filtros">
		<div>
			<span class="d-block fw-semibold">Lista de comunicados</span><span
				class="small text-muted">Encontre avisos por assunto ou
				palavra-chave.</span>
		</div>
		<div class="campo-filtro">
			<label class="small fw-semibold" for="filtroCategoria">Categoria</label><select
				id="filtroCategoria" class="form-select"
				aria-label="Filtrar por categoria"><option value="">Todas
					as categorias</option>
				<% if (categorias != null) for (Categoria categoria : categorias) { %><option
					value="<%=categoria.getIdCategoria()%>"><%=categoria.getNome()%></option>
				<% } %></select>
		</div>
		<div class="campo-filtro">
			<label class="small fw-semibold" for="pesquisa">Pesquisar</label><input
				id="pesquisa" class="form-control" type="search"
				placeholder="Título, mensagem ou categoria"
				aria-describedby="ajudaPesquisa">
		</div>
		<span id="ajudaPesquisa" class="visually-hidden">Use a busca e
			o filtro de categoria para encontrar comunicados.</span>
	</div>
	<div class="mural-noticias" aria-live="polite">
		<% if (comunicados != null && !comunicados.isEmpty()) { for (Comunicado c : comunicados) { String classePrioridade = "ALTA".equals(c.getPrioridade()) ? "bg-danger" : ("MEDIA".equals(c.getPrioridade()) ? "bg-warning text-dark" : "bg-secondary"); %>
		<article
			class="noticia-card comunicado-row <%= "ALTA".equals(c.getPrioridade()) ? "noticia-destaque" : "" %>"
			data-categoria="<%=c.getIdCategoria()%>"
			data-prioridade="<%=c.getPrioridade()%>">
			<div class="noticia-faixa"></div>
			<% if (c.getImagem() != null && !c.getImagem().isBlank()) { %><img
				class="noticia-imagem"
				src="${pageContext.request.contextPath}/<%=c.getImagem()%>"
				alt="Imagem do comunicado: <%=c.getTitulo()%>">
			<% } %>
			<div class="noticia-conteudo">
				<div class="noticia-autor">
					<span class="noticia-avatar" aria-hidden="true">CC</span><span><strong>Conecta
							Campus</strong><small>Comunicado oficial</small></span>
				</div>
				<div class="noticia-meta">
					<span class="badge bg-light text-dark border"><%=c.getNomeCategoria()%></span><span
						class="badge <%=classePrioridade%>">Prioridade: <%=c.getPrioridade()%></span>
				</div>
				<h2 class="noticia-titulo"><%=c.getTitulo()%></h2>
				<p class="noticia-mensagem"><%=c.getMensagem()%></p>
				<% if (c.getMensagem() != null && (c.getMensagem().length() > 90 || c.getMensagem().contains("\n") || c.getMensagem().contains("\r"))) { %><a
					class="btn btn-sm btn-outline-primary mt-3"
					href="${pageContext.request.contextPath}/comunicados?acao=visualizar&id=<%=c.getIdComunicado()%>">Ler
					mais <i class="bi bi-arrow-right" aria-hidden="true"></i>
				</a>
				<% } %>
				<% if (podeGerenciarComunicados || adminComunicados) { %><div
					class="noticia-acoes">
					<% if (podeGerenciarComunicados) { %><a
						class="btn btn-sm btn-outline-primary"
						href="${pageContext.request.contextPath}/comunicados?acao=editar&id=<%=c.getIdComunicado()%>"><i
						class="bi bi-pencil" aria-hidden="true"></i> Editar</a>
					<% } %>
					<% if (adminComunicados) { %><a class="btn btn-sm btn-outline-danger"
						href="${pageContext.request.contextPath}/comunicados?acao=excluir&id=<%=c.getIdComunicado()%>"
						data-confirm="true"
						data-confirm-message="Deseja realmente excluir este comunicado?"><i
						class="bi bi-trash" aria-hidden="true"></i> Excluir</a>
					<% } %>
				</div>
				<% } %>
			</div>
		</article>
		<% } } else { %>
		<div class="empty-state">
			<i class="bi bi-inbox display-5" aria-hidden="true"></i>
			<h2 class="h5 mt-3">Nenhum comunicado encontrado</h2>
			<p class="page-subtitle">Ainda não há notícias publicadas.</p>
		</div>
		<% } %>
	</div>
	<div class="table-responsive tabela-comunicados-legada"
		aria-hidden="true">
		<table class="table table-hover align-middle">
			<thead>
				<tr>
					<th>Título</th>
					<th>Categoria</th>
					<th>Prioridade</th>
					<th>Status</th>
					<% if (podeGerenciarComunicados) { %><th>Visualizações</th>
					<th>Ações</th>
					<% } %>
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
				<tr class="comunicado-row" data-categoria="<%=c.getIdCategoria()%>">
					<td><strong class="d-block fs-6"><%=c.getTitulo()%></strong> <details
							class="mt-2">
							<summary class="text-primary">Ler comunicado completo</summary>
							<p class="mb-0 mt-2"><%=c.getMensagem()%></p>
						</details></td>
					<td><span class="badge bg-light text-dark border"><%=c.getNomeCategoria()%></span></td>
					<td><span class="badge <%=badgePrioridade%>">Prioridade:
							<%=c.getPrioridade()%></span></td>
					<td><span class="badge <%=badgeStatus%>"><%=c.getStatus()%></span></td>
					<td><%=c.getVisualizacoes()%></td>
					<td>
						<div class="action-row">
							<% if (podeGerenciarComunicados) { %><a
								class="btn btn-sm btn-outline-primary"
								aria-label="Editar comunicado <%=c.getTitulo()%>"
								href="${pageContext.request.contextPath}/comunicados?acao=editar&id=<%=c.getIdComunicado()%>"><i
								class="bi bi-pencil" aria-hidden="true"></i></a>
							<% } %>
							<% if (adminComunicados) { %><a
								class="btn btn-sm btn-outline-danger"
								aria-label="Excluir comunicado <%=c.getTitulo()%>"
								href="${pageContext.request.contextPath}/comunicados?acao=excluir&id=<%=c.getIdComunicado()%>"
								data-confirm="true"
								data-confirm-message="Deseja realmente excluir o comunicado <%=c.getTitulo()%>?"><i
								class="bi bi-trash" aria-hidden="true"></i></a>
							<% } %>
						</div>
					</td>
				</tr>
				<%
                    }
                } else {
                %>
				<tr>
					<td colspan="4">
						<div class="empty-state">
							<i class="bi bi-inbox display-5" aria-hidden="true"></i>
							<h2 class="h5 mt-3">Nenhum comunicado encontrado</h2>
							<p class="page-subtitle">Publique o primeiro aviso para a
								comunidade.</p>
						</div>
					</td>
				</tr>
				<% } %>
			</tbody>
		</table>
	</div>
</div>

<script>
function filtrarComunicados() {
    var termo = document.getElementById("pesquisa").value.toLowerCase();
    var categoria = document.getElementById("filtroCategoria").value;
    document.querySelectorAll(".comunicado-row").forEach(function (linha) {
        linha.hidden = !linha.textContent.toLowerCase().includes(termo)
            || (categoria && linha.dataset.categoria !== categoria);
    });
}
document.getElementById("pesquisa").addEventListener("input", filtrarComunicados);
document.getElementById("filtroCategoria").addEventListener("change", filtrarComunicados);
</script>

<%@ include file="/WEB-INF/includes/footer.jsp"%>
