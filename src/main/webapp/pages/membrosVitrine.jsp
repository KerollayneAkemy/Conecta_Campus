<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="br.com.conectacampus.model.Membro"%>
<%
    List<Membro> membros = (List<Membro>) request.getAttribute("listaMembros");
    if (membros == null) {
        membros = new ArrayList<>();
    }

    // Agrupa os membros pelo nome do cargo, preservando a ordem de aparição
    LinkedHashMap<String, List<Membro>> grupos = new LinkedHashMap<>();
    LinkedHashMap<String, String> descricoesCargo = new LinkedHashMap<>();

    for (Membro m : membros) {
        String nomeCargo = (m.getCargo() != null && m.getCargo().getNome() != null)
                ? m.getCargo().getNome() : "Sem cargo";
        String descricaoCargo = (m.getCargo() != null && m.getCargo().getDescricao() != null)
                ? m.getCargo().getDescricao() : "";

        grupos.computeIfAbsent(nomeCargo, k -> new ArrayList<>()).add(m);
        descricoesCargo.putIfAbsent(nomeCargo, descricaoCargo);
    }

    request.setAttribute("paginaAtiva", "membros-vitrine");
    request.setAttribute("tituloPagina", "Membros - Conecta Campus");
%>
<%@ include file="/WEB-INF/includes/header.jsp"%>

<div class="page-header">
	<div>
		<p class="eyebrow">Comunidade</p>
		<h1 class="page-title">
			<i class="bi bi-people-fill" aria-hidden="true"></i> Conheça os
			membros
		</h1>
		<p class="page-subtitle">Cada membro tem uma função específica.
			Encontre quem pode te ajudar.</p>
	</div>
</div>

<% if (grupos.isEmpty()) { %>
<div class="card">
	<div class="empty-state">
		<i class="bi bi-people display-5" aria-hidden="true"></i>
		<h2 class="h5 mt-3">Nenhum membro cadastrado ainda</h2>
	</div>
</div>
<% } else { %>

<div class="abas-cargo">
	<%
    boolean primeiro = true;
    for (String nomeCargo : grupos.keySet()) {
        String idGrupo = nomeCargo.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
    %>
	<button type="button"
		class="aba-cargo-btn <%=primeiro ? "ativo" : ""%>"
		onclick="mostrarGrupoCargo('grupo-<%=idGrupo%>', this)">
		<i class="bi bi-diagram-3-fill" aria-hidden="true"></i>
		<%=nomeCargo%>
	</button>
	<%
        primeiro = false;
    }
    %>
</div>

<%
primeiro = true;
for (Map.Entry<String, List<Membro>> entry : grupos.entrySet()) {
    String nomeCargo = entry.getKey();
    List<Membro> membrosDoGrupo = entry.getValue();
    String descricaoCargo = descricoesCargo.get(nomeCargo);
    String idGrupo = nomeCargo.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
%>
<section class="grupo-cargo <%=primeiro ? "ativo" : ""%>"
	id="grupo-<%=idGrupo%>">

	<% if (descricaoCargo != null && !descricaoCargo.isBlank()) { %>
	<div class="box-cargo-info">
		<span class="icone"><i class="bi bi-bank2" aria-hidden="true"></i></span>
		<div>
			<h3><%=nomeCargo%></h3>
			<p><%=descricaoCargo%></p>
		</div>
	</div>
	<% } %>

	<div class="grade-membros-vitrine">
		<%
        for (Membro m : membrosDoGrupo) {
            String nome = m.getNome() != null ? m.getNome() : "";
            String[] partes = nome.trim().split("\\s+");
            String iniciais = "";
            if (partes.length > 0 && !partes[0].isEmpty()) {
                iniciais += partes[0].charAt(0);
            }
            if (partes.length > 1) {
                iniciais += partes[partes.length - 1].charAt(0);
            }
            iniciais = iniciais.toUpperCase();
        %>
		<div class="card-membro-vitrine">
			<div class="avatar-iniciais-vitrine"><%=iniciais%></div>
			<h4><%=nome%></h4>
			<p class="funcao-membro"><%=nomeCargo%></p>
			<div class="info-contato-vitrine">
				<span><i class="bi bi-envelope-fill" aria-hidden="true"></i>
					<%=m.getEmail()%></span> <span><i class="bi bi-whatsapp"
					aria-hidden="true"></i> <%=m.getTelefone()%></span>
			</div>
		</div>
		<%
        }
        %>
	</div>
</section>
<%
    primeiro = false;
}
%>

<% } %>

<script>
    function mostrarGrupoCargo(idAlvo, botao) {
        document.querySelectorAll('.grupo-cargo').forEach(function (grupo) {
            grupo.classList.remove('ativo');
        });
        document.querySelectorAll('.aba-cargo-btn').forEach(function (btn) {
            btn.classList.remove('ativo');
        });

        document.getElementById(idAlvo).classList.add('ativo');
        botao.classList.add('ativo');
    }
</script>

<%@ include file="/WEB-INF/includes/footer.jsp"%>