<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="br.com.conectacampus.model.MovimentoFinanceiro"%>
<%@ page import="br.com.conectacampus.model.Usuario"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%
Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
if (usuario == null) {
    response.sendRedirect(request.getContextPath() + "/login");
    return;
}

List<MovimentoFinanceiro> movimentos = (List<MovimentoFinanceiro>) request.getAttribute("movimentosFinanceiros");
String competenciaFinanceira = (String) request.getAttribute("competenciaFinanceira");
BigDecimal totalEntradas = (BigDecimal) request.getAttribute("totalEntradasFinanceiras");
BigDecimal totalSaidas = (BigDecimal) request.getAttribute("totalSaidasFinanceiras");
double entradas = totalEntradas != null ? totalEntradas.doubleValue() : 0;
double saidas = totalSaidas != null ? totalSaidas.doubleValue() : 0;
Map<String, BigDecimal> saldoMensal = (Map<String, BigDecimal>) request.getAttribute("saldoMensal");

request.setAttribute("paginaAtiva", "dashboard");
request.setAttribute("tituloPagina", "Relatório financeiro - Conecta Campus");
%>
<%@ include file="/WEB-INF/includes/header.jsp"%>

<div class="page-header">
	<h1>Relatório financeiro</h1>
	<p>Resumo de entradas e saídas da plataforma.</p>
</div>

<section class="card mt-4" aria-labelledby="tituloFinanceiro">
	<div
		class="card-header d-flex justify-content-between align-items-center">
		<span id="tituloFinanceiro"> <i class="bi bi-cash-stack"
			aria-hidden="true"></i> Transparência financeira
		</span>

		<form method="get"
			action="${pageContext.request.contextPath}/dashboard"
			class="d-flex gap-2">
			<label class="visually-hidden" for="competencia">Competência</label>
			<input class="form-control" id="competencia" type="month"
				name="competencia"
				value="<%=competenciaFinanceira != null ? competenciaFinanceira : ""%>">
			<button class="btn btn-outline-primary">Filtrar</button>
		</form>
	</div>

	<div class="card-body">
		<div class="row align-items-center g-4 mb-4">
			<div class="col-lg-5">
				<div style="position: relative; height: 240px;">
					<canvas id="graficoFinanceiro" role="img"
						aria-label="Gráfico de entradas e saídas financeiras."></canvas>
				</div>
			</div>

			<div class="col-lg-7">
				<h2 class="h5 mb-3">Entradas e saídas</h2>
				<p class="text-muted mb-3">Resumo dos lançamentos recebidos da
					planilha financeira para o período selecionado.</p>
				<div class="d-flex flex-wrap gap-3">
					<span class="badge text-bg-success p-3"> <i
						class="bi bi-arrow-down-circle me-1"></i> Entradas: R$ <%=totalEntradas != null ? totalEntradas : "0.00"%>
					</span> <span class="badge text-bg-danger p-3"> <i
						class="bi bi-arrow-up-circle me-1"></i> Saídas: R$ <%=totalSaidas != null ? totalSaidas : "0.00"%>
					</span>
				</div>
			</div>
		</div>

		<section class="border-top pt-4 mb-4" aria-labelledby="tituloSaldoMensal">
			<h2 class="h5" id="tituloSaldoMensal"><i class="bi bi-graph-up-arrow" aria-hidden="true"></i> Saldo acumulado por mês</h2>
			<p class="text-muted">O valor que sobra em um mês é levado para o próximo e reduzido pelas saídas seguintes.</p>
			<div style="position: relative; height: 300px;"><canvas id="graficoSaldoMensal" role="img" aria-label="Gráfico de linha com o saldo financeiro acumulado de cada mês."></canvas></div>
		</section>

		<% if (movimentos != null && !movimentos.isEmpty()) { %>
		<div class="table-responsive">
			<table class="table">
				<thead>
					<tr>
						<th>Data</th>
						<th>Tipo</th>
						<th>Categoria</th>
						<th>Descrição</th>
						<th class="text-end">Valor</th>
					</tr>
				</thead>
				<tbody>
					<% for (MovimentoFinanceiro movimento : movimentos) { %>
					<tr>
						<td><%=movimento.getData()%></td>
						<td><%=movimento.getTipo()%></td>
						<td><%=movimento.getCategoria()%></td>
						<td><%=movimento.getDescricao()%></td>
						<td class="text-end">R$ <%=movimento.getValor()%></td>
					</tr>
					<% } %>
				</tbody>
			</table>
		</div>
		<% } else { %>
		<p class="text-muted">Nenhum lançamento financeiro encontrado para
			este período.</p>
		<% } %>
	</div>
</section>

</div>

<script
	src="https://cdn.jsdelivr.net/npm/chart.js@4.4.7/dist/chart.umd.min.js"></script>
<script>
(() => {
    const canvas = document.getElementById('graficoFinanceiro');
    if (!canvas || typeof Chart === 'undefined') return;

    new Chart(canvas, {
        type: 'doughnut',
        data: {
            labels: ['Entradas', 'Saídas'],
            datasets: [{
                data: [<%=entradas%>, <%=saidas%>],
                backgroundColor: ['#008a6b', '#dc3545'],
                borderWidth: 0,
                hoverOffset: 8
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            cutout: '65%',
            plugins: {
                legend: {
                    position: 'bottom',
                    labels: { usePointStyle: true, padding: 18 }
                },
                tooltip: {
                    callbacks: {
                        label(context) {
                            const valor = context.parsed.toLocaleString('pt-BR', {
                                minimumFractionDigits: 2,
                                maximumFractionDigits: 2
                            });
                            return context.label + ': R$ ' + valor;
                        }
                    }
                }
            }
        }
    });
})();

(() => {
    const canvas = document.getElementById('graficoSaldoMensal');
    if (!canvas || typeof Chart === 'undefined') return;
    const labels = [<% if (saldoMensal != null) for (String competencia : saldoMensal.keySet()) { %>'<%=competencia%>',<% } %>];
    const valores = [<% if (saldoMensal != null) for (BigDecimal saldo : saldoMensal.values()) { %><%=saldo.doubleValue()%>,<% } %>];

    new Chart(canvas, {
        type: 'line',
        data: { labels: labels, datasets: [{ label: 'Saldo final', data: valores, borderColor: '#008a6b', backgroundColor: 'rgba(0, 138, 107, 0.12)', fill: true, tension: 0.3, pointRadius: 4 }] },
        options: { responsive: true, maintainAspectRatio: false, plugins: { tooltip: { callbacks: { label(context) { return 'Saldo: R$ ' + context.parsed.y.toLocaleString('pt-BR', { minimumFractionDigits: 2, maximumFractionDigits: 2 }); } } } }, scales: { y: { ticks: { callback(value) { return 'R$ ' + value; } }, grid: { color: '#e9efec' } }, x: { grid: { display: false } } } }
    });
})();
</script>

<%@ include file="/WEB-INF/includes/footer.jsp"%>
