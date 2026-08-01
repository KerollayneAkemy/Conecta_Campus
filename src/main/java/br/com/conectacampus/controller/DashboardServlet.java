package br.com.conectacampus.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.Normalizer;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import br.com.conectacampus.model.MovimentoFinanceiro;
import br.com.conectacampus.model.Usuario;
import br.com.conectacampus.service.FinanceiroGoogleSheetsService;
import br.com.conectacampus.util.Autorizacao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private FinanceiroGoogleSheetsService financeiroService;

	@Override
	public void init() {
		financeiroService = new FinanceiroGoogleSheetsService();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Usuario usuarioLogado = (Usuario) request.getSession().getAttribute("usuarioLogado");
		
		if (!Autorizacao.podeVisualizarDashboard(usuarioLogado)) {
			
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			
			return;
		}

		String competencia = request.getParameter("competencia");
		
		competencia = competencia == null ? "" : competencia;

		List<MovimentoFinanceiro> movimentos = financeiroService.listar(competencia);
		List<MovimentoFinanceiro> historicoFinanceiro = financeiroService.listar("");
		BigDecimal[] totais = calcularTotais(movimentos);
		Map<String, BigDecimal> saldoMensal = calcularSaldoMensal(historicoFinanceiro);

		request.setAttribute("competenciaFinanceira", competencia);
		request.setAttribute("movimentosFinanceiros", movimentos);
		request.setAttribute("totalEntradasFinanceiras", totais[0]);
		request.setAttribute("totalSaidasFinanceiras", totais[1]);
		request.setAttribute("saldoMensal", saldoMensal);
		request.getRequestDispatcher("/pages/dashboard.jsp").forward(request, response);
	}

	private BigDecimal[] calcularTotais(List<MovimentoFinanceiro> movimentos) {
		BigDecimal entradas = BigDecimal.ZERO;
		BigDecimal saidas = BigDecimal.ZERO;

		for (MovimentoFinanceiro movimento : movimentos) {
			if (movimento.getValor() == null || movimento.getTipo() == null) continue;

			switch (normalizarTipo(movimento.getTipo())) {
			case "ENTRADA" -> entradas = entradas.add(movimento.getValor());
			case "SAIDA" -> saidas = saidas.add(movimento.getValor());
			default -> {
				// Ignora tipos não reconhecidos na planilha.
			}
			}
		}
		return new BigDecimal[] { entradas, saidas };
	}

	private String normalizarTipo(String tipo) {
		
		return Normalizer.normalize(tipo, Normalizer.Form.NFD)
				.replaceAll("\\p{M}", "")
				.trim()
				.toUpperCase();
	}

	private Map<String, BigDecimal> calcularSaldoMensal(List<MovimentoFinanceiro> movimentos) {
		Map<String, BigDecimal> variacaoMensal = new TreeMap<>();
		for (MovimentoFinanceiro movimento : movimentos) {
			if (movimento.getCompetencia() == null || movimento.getCompetencia().isBlank()
					|| movimento.getValor() == null || movimento.getTipo() == null) {
				continue;
			}

			BigDecimal valor = "SAIDA".equals(normalizarTipo(movimento.getTipo()))
					? movimento.getValor().negate()
					: "ENTRADA".equals(normalizarTipo(movimento.getTipo()))
							? movimento.getValor()
							: BigDecimal.ZERO;
			variacaoMensal.merge(movimento.getCompetencia(), valor, BigDecimal::add);
		}

		Map<String, BigDecimal> saldoAcumuladoPorMes = new LinkedHashMap<>();
		BigDecimal saldoAcumulado = BigDecimal.ZERO;
		for (Map.Entry<String, BigDecimal> mes : variacaoMensal.entrySet()) {
			saldoAcumulado = saldoAcumulado.add(mes.getValue());
			saldoAcumuladoPorMes.put(mes.getKey(), saldoAcumulado);
		}
		return saldoAcumuladoPorMes;
	}
}
