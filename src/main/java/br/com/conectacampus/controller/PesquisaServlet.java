package br.com.conectacampus.controller;

import java.io.IOException;
import java.time.LocalDate;
import br.com.conectacampus.model.Pesquisa;
import br.com.conectacampus.model.Usuario;
import br.com.conectacampus.service.PesquisaService;
import br.com.conectacampus.util.Autorizacao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/pesquisas")
public class PesquisaServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private PesquisaService pesquisaService;

	@Override public void init() {
		pesquisaService = new PesquisaService();
	}

	@Override protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogado");

		if ("editar".equals(request.getParameter("acao")) && !Autorizacao.podePublicarInstitucional(usuario)) { response.sendError(403); 

		return;
		}

		if ("editar".equals(request.getParameter("acao"))) {
			request.setAttribute("pesquisaEdicao", pesquisaService.buscarPorId(Integer.parseInt(request.getParameter("id"))));
			request.getRequestDispatcher("/pages/editarPesquisa.jsp").forward(request, response);

			return;
		}

		request.setAttribute("listaPesquisas", pesquisaService.listar());
		request.setAttribute("pesquisasRespondidas", pesquisaService.listarRespondidasPorUsuario(usuario.getIdUsuario()));
		request.getRequestDispatcher("/pages/pesquisas.jsp").forward(request, response);
	}
	@Override protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogado");

		if ("marcarRespondida".equals(request.getParameter("acao"))) {
			if (!Autorizacao.ehAluno(usuario)) { response.sendError(403); 
			return;
			}

			boolean marcou = pesquisaService.marcarComoRespondida(Integer.parseInt(request.getParameter("idPesquisa")), usuario.getIdUsuario());
			request.getSession().setAttribute(marcou ? "msgSucesso" : "msgErro", marcou ? "Pesquisa marcada como respondida." : "Esta pesquisa já foi marcada como respondida.");
			response.sendRedirect(request.getContextPath() + "/pesquisas");

			return;
		}

		if (!Autorizacao.podePublicarInstitucional(usuario)) { response.sendError(403); 
		return;
		}

		if ("atualizar".equals(request.getParameter("acao"))) {
			Pesquisa pesquisa = pesquisaService.buscarPorId(Integer.parseInt(request.getParameter("idPesquisa")));

			if (pesquisa != null) { preencherPesquisa(request, pesquisa); boolean atualizou = pesquisaService.atualizar(pesquisa); request.getSession().setAttribute(atualizou ? "msgSucesso" : "msgErro", atualizou ? "Pesquisa atualizada com sucesso." : "Não foi possível atualizar a pesquisa."); }
			response.sendRedirect(request.getContextPath() + "/pesquisas");

			return;
		}

		Pesquisa pesquisa = new Pesquisa(); pesquisa.setTitulo(request.getParameter("titulo")); pesquisa.setDescricao(request.getParameter("descricao")); pesquisa.setLinkFormulario(request.getParameter("linkFormulario")); pesquisa.setUsuario(usuario);
		String dataLimite = request.getParameter("dataLimite"); if (dataLimite != null && !dataLimite.isBlank()) pesquisa.setDataLimite(LocalDate.parse(dataLimite));
		boolean cadastrou = pesquisaService.cadastrar(pesquisa);

		request.getSession().setAttribute(cadastrou ? "msgSucesso" : "msgErro",
				cadastrou ? "Pesquisa cadastrada com sucesso." : "Não foi possível cadastrar a pesquisa.");

		response.sendRedirect(request.getContextPath() + "/pesquisas");
	}
	private void preencherPesquisa(HttpServletRequest request, Pesquisa pesquisa) { pesquisa.setTitulo(request.getParameter("titulo")); pesquisa.setDescricao(request.getParameter("descricao")); pesquisa.setLinkFormulario(request.getParameter("linkFormulario")); String data = request.getParameter("dataLimite"); pesquisa.setDataLimite(data == null || data.isBlank() ? null : LocalDate.parse(data)); pesquisa.setStatus(request.getParameter("status")); }
}
