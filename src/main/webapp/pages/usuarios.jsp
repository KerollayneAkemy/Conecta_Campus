<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="br.com.conectacampus.model.Usuario"%>
<%@ page import="br.com.conectacampus.util.Autorizacao"%>
<%
Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
if (usuarioLogado == null) {
	response.sendRedirect(request.getContextPath() + "/login");
	return;
}
boolean limparRascunhoCadastro = Boolean.TRUE.equals(
		session.getAttribute("limparRascunhoCadastroAdministrativo"));
if (limparRascunhoCadastro) {
	session.removeAttribute("limparRascunhoCadastroAdministrativo");
}
List<Usuario> usuarios = (List<Usuario>) request.getAttribute("listaUsuarios");
if (usuarios == null) {
	usuarios = (List<Usuario>) request.getAttribute("usuarios");
}
int totalAtivos = 0;
int totalInativos = 0;
if (usuarios != null) for (Usuario item : usuarios) {
    if (item.isAtivo()) totalAtivos++; else totalInativos++;
}
%>
<% request.setAttribute("paginaAtiva", "usuarios"); %>
<%@ include file="/WEB-INF/includes/header.jsp"%>
<div class="page-header">
	<div>
		<p class="eyebrow"><%=Autorizacao.ehAdministrador(usuarioLogado) ? "Administração" : "Representação estudantil"%></p>
		<h1 class="page-title">
			<i class="bi bi-people-fill" aria-hidden="true"></i> Usuários
		</h1>
		<p class="page-subtitle"><%=Autorizacao.ehAdministrador(usuarioLogado) ? "Gerencie as pessoas cadastradas na plataforma." : "Conheça os representantes da Reitoria e do Grêmio."%></p>
	</div>
	<% if (Autorizacao.ehAdministrador(usuarioLogado)) { %>
	<a href="${pageContext.request.contextPath}/cadastro"
		class="btn btn-primary"> <i class="bi bi-person-plus-fill"
		aria-hidden="true"></i> Novo usuário
	</a>
	<% } %>
</div>

<div class="row g-3 mb-4">
	<div class="col-md-4">
		<div class="card metric-card">
			<div class="card-body">
				<div>
					<p class="metric-label">Total</p>
					<p class="metric-value"><%=usuarios != null ? usuarios.size() : 0%></p>
				</div>
				<span class="metric-icon"><i class="bi bi-people-fill"
					aria-hidden="true"></i></span>
			</div>
		</div>
	</div>
	<div class="col-md-4">
		<div class="card metric-card">
			<div class="card-body">
				<div>
					<p class="metric-label">Ativos</p>
					<p class="metric-value"><%=totalAtivos%></p>
				</div>
				<span class="metric-icon"><i class="bi bi-person-check-fill"
					aria-hidden="true"></i></span>
			</div>
		</div>
	</div>
	<div class="col-md-4">
		<div class="card metric-card">
			<div class="card-body">
				<div>
					<p class="metric-label">Inativos</p>
					<p class="metric-value"><%=totalInativos%></p>
				</div>
				<span class="metric-icon"><i class="bi bi-person-x-fill"
					aria-hidden="true"></i></span>
			</div>
		</div>
	</div>
</div>

<div class="card">
	<div
		class="card-header d-flex flex-wrap justify-content-between align-items-center gap-3">
		<span>Lista de usuários</span> <label class="visually-hidden"
			for="pesquisa">Pesquisar usuário</label> <input id="pesquisa"
			class="form-control search-input" type="search"
			placeholder="Pesquisar..." aria-describedby="ajudaPesquisa">
		<span id="ajudaPesquisa" class="visually-hidden">Digite para
			filtrar a lista de usuários exibida na tabela.</span>
	</div>
	<div class="table-responsive">
		<table class="table table-hover align-middle">
			<thead>
				<tr class="usuario-row">
					<th>Nome</th>
					<th>E-mail</th>
					<th>Curso</th>
					<th>Perfil</th>
					<th>Status</th>
					<% if (Autorizacao.ehAdministrador(usuarioLogado)) { %>
					<th>Ações</th>
					<% } %>
				</tr>
			</thead>
			<tbody>
				<%
                    if (usuarios != null && !usuarios.isEmpty()) {
                        for (Usuario u : usuarios) {
                    %>
				<tr>
					<td><i class="bi bi-person-circle me-2" aria-hidden="true"></i><%=u.getNome()%></td>
					<td><%=u.getEmail()%></td>
					<td><%=u.getCurso()%></td>
					<td><%=u.getPerfil() != null ? u.getPerfil().getNome() : "-"%></td>
					<td><span
						class="badge <%=u.isAtivo() ? "bg-success" : "bg-danger"%>"><%=u.isAtivo() ? "Ativo" : "Inativo"%></span></td>
					<% if (Autorizacao.ehAdministrador(usuarioLogado)) { %>
					<td>
						<div class="action-row">
							<a class="btn btn-sm btn-outline-primary"
								aria-label="Editar usuário <%=u.getNome()%>"
								href="${pageContext.request.contextPath}/usuarios?acao=editar&id=<%=u.getIdUsuario()%>"><i
								class="bi bi-pencil" aria-hidden="true"></i></a>
							<% if (u.isAtivo()) { %><a class="btn btn-sm btn-outline-warning"
								aria-label="Desativar usuário <%=u.getNome()%>"
								href="${pageContext.request.contextPath}/usuarios?acao=desativar&id=<%=u.getIdUsuario()%>"
								data-confirm="true"
								data-confirm-message="Deseja desativar o usuário <%=u.getNome()%>?"><i
								class="bi bi-person-dash" aria-hidden="true"></i></a>
							<% } else { %><a class="btn btn-sm btn-outline-success"
								aria-label="Reativar usuário <%=u.getNome()%>"
								href="${pageContext.request.contextPath}/usuarios?acao=reativar&id=<%=u.getIdUsuario()%>"
								data-confirm="true"
								data-confirm-message="Deseja reativar o usuário <%=u.getNome()%>?"><i
								class="bi bi-person-check" aria-hidden="true"></i></a>
							<% } %>
						</div>
					</td>
					<% } %>
				</tr>
				<%
                        }
                    } else {
                    %>
				<tr>
					<td colspan="6">
						<div class="empty-state">
							<i class="bi bi-person-x display-5" aria-hidden="true"></i>
							<h2 class="h5 mt-3">Nenhum usuário encontrado</h2>
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
    document.querySelectorAll(".usuario-row").forEach(function (linha) {
        linha.hidden = !linha.textContent.toLowerCase().includes(termo);
    });
});
</script>
<% if (limparRascunhoCadastro) { %>
<script>
sessionStorage.removeItem('conectaCampus.cadastro.rascunho');
sessionStorage.removeItem('conectaCampus.cadastro.rascunho.administrativo');
</script>
<% } %>
<%@ include file="/WEB-INF/includes/footer.jsp"%>
