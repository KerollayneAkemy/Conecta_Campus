<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page
	import="br.com.conectacampus.model.Usuario,br.com.conectacampus.model.Cargo,java.util.List"%>
<%
Usuario usuario = (Usuario) request.getAttribute("usuario");
if (usuario == null)
	usuario = new Usuario();
Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
List<Cargo> listaCargos = (List<Cargo>) request.getAttribute("listaCargos");
if (usuarioLogado == null) {
	response.sendRedirect(request.getContextPath() + "/login");
	return;
}
String nomeExibicao = usuario.getNome() == null || usuario.getNome().isBlank() ? "Usuário" : usuario.getNome();
String inicial = String.valueOf(nomeExibicao.trim().charAt(0)).toUpperCase();
boolean contaAtiva = usuario.isAtivo();
%>
<%@ include file="/WEB-INF/includes/header.jsp"%>
<style>
.editar-usuario {
	max-width: 980px
}

.resumo-conta {
	display: flex;
	align-items: center;
	gap: 1rem;
	padding: 1.1rem 1.25rem;
	margin-bottom: 1rem;
	border: 1px solid var(--line);
	border-radius: 14px;
	background: linear-gradient(110deg, #f3fbf8, #fff)
}

.resumo-avatar {
	width: 52px;
	height: 52px;
	border-radius: 50%;
	display: grid;
	place-items: center;
	background: var(--brand-dark);
	color: #fff;
	font-weight: 800;
	font-size: 1.15rem
}

.resumo-conta p {
	margin: 0;
	color: var(--muted)
}

.form-secao {
	padding: 1.15rem 0;
	border-top: 1px solid var(--line)
}

.form-secao:first-of-type {
	border-top: 0;
	padding-top: .25rem
}

.form-secao-titulo {
	margin: 0 0 .25rem;
	font-size: 1rem;
	font-weight: 800
}

.form-secao-ajuda {
	margin: 0 0 1rem;
	color: var(--muted);
	font-size: .9rem
}
</style>
<div class="editar-usuario">
	<div class="page-header">
		<div>
			<p class="eyebrow">Administração</p>
			<h1 class="page-title">
				<i class="bi bi-person-gear"></i> Editar usuário
			</h1>
			<p class="page-subtitle">Atualize os dados e as permissões desta
				conta.</p>
		</div>
		<a href="${pageContext.request.contextPath}/usuarios"
			class="btn btn-outline-secondary"><i class="bi bi-arrow-left"></i>
			Voltar</a>
	</div>
	<div class="resumo-conta">
		<span class="resumo-avatar"><%=inicial%></span>
		<div>
			<strong><%=nomeExibicao%></strong>
			<p><%=usuario.getEmail() != null ? usuario.getEmail() : "E-mail não informado"%>
				·
				<%=contaAtiva ? "Conta ativa" : "Conta inativa"%></p>
		</div>
	</div>
	<div class="card">
		<div class="card-body p-4">
			<form action="${pageContext.request.contextPath}/usuarios"
				method="post">
				<input type="hidden" name="acao" value="atualizar"><input
					type="hidden" name="id" value="<%=usuario.getIdUsuario()%>">
				<section class="form-secao">
					<h2 class="form-secao-titulo">
						<i class="bi bi-person-vcard"></i> Dados pessoais
					</h2>
					<p class="form-secao-ajuda">Informações de identificação e
						contato.</p>
					<div class="row g-3">
						<div class="col-md-6">
							<label class="form-label" for="nome">Nome completo</label><input
								class="form-control" id="nome" name="nome"
								value="<%=usuario.getNome() != null ? usuario.getNome() : ""%>"
								required>
						</div>
						<div class="col-md-6">
							<label class="form-label" for="email">E-mail de acesso</label><input
								class="form-control" id="email" type="email" name="email"
								value="<%=usuario.getEmail() != null ? usuario.getEmail() : ""%>"
								required>
						</div>
						<div class="col-12">
							<label class="form-label" for="curso">Curso</label><input
								class="form-control" id="curso" name="curso"
								value="<%=usuario.getCurso() != null ? usuario.getCurso() : ""%>"
								required>
						</div>
					</div>
				</section>
				<section class="form-secao">
					<h2 class="form-secao-titulo">
						<i class="bi bi-shield-lock"></i> Acesso e permissões
					</h2>
					<p class="form-secao-ajuda">Defina o nível de acesso e o status
						da conta.</p>
					<div class="row g-3">
						<div class="col-md-6">
							<label class="form-label" for="idPerfil">Perfil</label><select
								class="form-select" id="idPerfil" name="perfil"><option
									value="ALUNO"
									<%=usuario.getPerfil() != null && "ALUNO".equals(usuario.getPerfil().getNome()) ? "selected" : ""%>>Aluno</option>
								<option value="EQUIPE_INSTITUCIONAL"
									<%=usuario.getPerfil() != null && "EQUIPE_INSTITUCIONAL".equals(usuario.getPerfil().getNome()) ? "selected" : ""%>>Equipe
									institucional</option>
								<option value="ADMINISTRADOR"
									<%=usuario.getPerfil() != null && "ADMINISTRADOR".equals(usuario.getPerfil().getNome()) ? "selected" : ""%>>Administrador</option></select>
						</div>
						<div class="col-md-6">
							<label class="form-label" for="ativo">Status da conta</label><select
								class="form-select" id="ativo" name="ativo"><option
									value="true" <%=contaAtiva?"selected":""%>>Ativo</option>
								<option value="false" <%=!contaAtiva?"selected":""%>>Inativo</option></select>
						</div>
					</div>
				</section>
				<div class="mt-4 action-row">
					<button class="btn btn-primary" type="submit">
						<i class="bi bi-check-circle"></i> Salvar alterações
					</button>
					<a href="${pageContext.request.contextPath}/usuarios"
						class="btn btn-outline-secondary">Cancelar</a>
				</div>
			</form>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/includes/footer.jsp"%>
