<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="br.com.conectacampus.model.Cargo"%>
<%@ page import="br.com.conectacampus.model.Membro"%>
<%@ page import="br.com.conectacampus.model.Usuario"%>
<%@ page import="java.util.List"%>
<%
Membro membro = (Membro) request.getAttribute("membro");
List<Cargo> listaCargos = (List<Cargo>) request.getAttribute("listaCargos");
List<Usuario> listaUsuariosEquipe = (List<Usuario>) request.getAttribute("listaUsuariosEquipe");

String telefoneAtual = membro != null ? membro.getTelefone() : "";
String idMembroAtual = membro != null ? String.valueOf(membro.getIdMembro()) : "";
int idCargoAtual = membro != null && membro.getCargo() != null ? membro.getCargo().getIdCargo() : 0;

request.setAttribute("paginaAtiva", "membros");
request.setAttribute("tituloPagina", (membro != null ? "Editar" : "Cadastrar") + " membro - Conecta Campus");
%>
<%@ include file="/WEB-INF/includes/header.jsp"%>

<div class="page-header">
	<div>
		<p class="eyebrow">Administração</p>
		<h1 class="page-title">
			<i class="bi bi-person-badge-fill" aria-hidden="true"></i>
			<%=membro != null ? "Editar membro" : "Cadastrar membro"%>
		</h1>
		<p class="page-subtitle">Vincule uma conta institucional da equipe
			a um cargo da plataforma.</p>
	</div>
	<a href="${pageContext.request.contextPath}/membros"
		class="btn btn-secondary"> <i class="bi bi-list-ul"
		aria-hidden="true"></i> Ver membros
	</a>
</div>

<div class="row">
	<div class="col-lg-7">
		<div class="card">
			<div class="card-header">
				<i class="bi bi-person-vcard" aria-hidden="true"></i> Dados do
				membro
			</div>
			<div class="card-body">
				<form method="post"
					action="${pageContext.request.contextPath}/membros">
					<input type="hidden" name="acao"
						value="<%=membro != null ? "atualizar" : "cadastrar"%>">
					<%
					if (membro != null) {
					%>
					<input type="hidden" name="idMembro" value="<%=idMembroAtual%>">
					<%
					}
					%>

					<div class="mb-3">
						<label for="idUsuarioEquipe" class="form-label">Conta
							institucional da equipe <span class="text-danger">*</span>
						</label> <select class="form-select" id="idUsuarioEquipe"
							name="idUsuarioEquipe" required>
							<option value="">Selecione uma pessoa da equipe...</option>
							<%
							if (listaUsuariosEquipe != null)
								for (Usuario usuarioEquipe : listaUsuariosEquipe) {
									boolean selecionado = membro != null && usuarioEquipe.getEmail().equalsIgnoreCase(membro.getEmail());
							%>
							<option value="<%=usuarioEquipe.getIdUsuario()%>"
								data-nome="<%=usuarioEquipe.getNome()%>"
								data-email="<%=usuarioEquipe.getEmail()%>"
								<%=selecionado ? "selected" : ""%>>
								<%=usuarioEquipe.getNome()%> —
								<%=usuarioEquipe.getEmail()%>
							</option>
							<%
							}
							%>
						</select>
						<div class="form-text">A lista mostra apenas contas ativas
							com e-mail @conecta.com.br.</div>
					</div>

					<div class="row g-3 mb-3" aria-live="polite">
						<div class="col-md-6">
							<span class="form-label d-block">Nome</span><span
								id="nomeSelecionado" class="form-control bg-light">Selecione
								uma conta</span>
						</div>
						<div class="col-md-6">
							<span class="form-label d-block">E-mail</span><span
								id="emailSelecionado" class="form-control bg-light">—</span>
						</div>
					</div>

					<div class="mb-3">
						<label for="telefone" class="form-label">Telefone <span
							class="text-danger">*</span></label> <input type="tel"
							class="form-control" id="telefone" name="telefone"
							value="<%=telefoneAtual%>" inputmode="numeric" autocomplete="tel"
							maxlength="15" pattern="[()][0-9]{2}[)] [0-9]{5}-[0-9]{4}"
							title="Informe o telefone no formato (00) 00000-0000" required
							placeholder="(00) 00000-0000">
						<div class="form-text">Informe DDD e 9 dígitos.</div>
					</div>

					<div class="mb-4">
						<label for="idCargo" class="form-label">Cargo <span
							class="text-danger">*</span></label> <select class="form-select"
							id="idCargo" name="idCargo" required>
							<option value="">Selecione um cargo...</option>
							<%
							if (listaCargos != null)
								for (Cargo cargo : listaCargos) {
							%>
							<option value="<%=cargo.getIdCargo()%>"
								<%=cargo.getIdCargo() == idCargoAtual ? "selected" : ""%>><%=cargo.getNome()%></option>
							<%
							}
							%>
						</select>
					</div>

					<div class="d-flex gap-2">
						<button type="submit" class="btn btn-primary">
							<i class="bi bi-check-lg" aria-hidden="true"></i>
							<%=membro != null ? "Salvar alterações" : "Cadastrar membro"%></button>
						<a href="${pageContext.request.contextPath}/membros"
							class="btn btn-outline-secondary">Cancelar</a>
					</div>
				</form>
			</div>
		</div>
	</div>

	<div class="col-lg-5">
		<div class="card">
			<div class="card-body">
				<span class="metric-icon mb-3"><i
					class="bi bi-lightbulb-fill" aria-hidden="true"></i></span>
				<h2 class="h5">Como funciona</h2>
				<p class="page-subtitle mb-0">
					Primeiro crie a conta institucional em <strong>Usuários</strong>.
					Depois, selecione-a aqui, informe o telefone e escolha o cargo.
				</p>
			</div>
		</div>
	</div>
</div>

<%@ include file="/WEB-INF/includes/footer.jsp"%>
<script>
(() => {
    const seletor = document.getElementById('idUsuarioEquipe');
    const nome = document.getElementById('nomeSelecionado');
    const email = document.getElementById('emailSelecionado');
    const telefone = document.getElementById('telefone');

    function atualizarConta() {
        const opcao = seletor.options[seletor.selectedIndex];
        nome.textContent = opcao?.dataset.nome || 'Selecione uma conta';
        email.textContent = opcao?.dataset.email || '—';
    }

    function mascararTelefone() {
        let valor = telefone.value.replace(/\D/g, '').slice(0, 11);
        if (valor.length > 6) valor = valor.replace(/^(\d{2})(\d{5})(\d{0,4}).*/, '($1) $2-$3');
        else if (valor.length > 2) valor = valor.replace(/^(\d{2})(\d+)/, '($1) $2');
        else if (valor.length) valor = '(' + valor;
        telefone.value = valor;
    }

    seletor.addEventListener('change', atualizarConta);
    telefone.addEventListener('input', mascararTelefone);
    atualizarConta();
    mascararTelefone();
})();
</script>
