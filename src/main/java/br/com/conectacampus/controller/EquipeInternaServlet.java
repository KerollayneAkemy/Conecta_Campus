package br.com.conectacampus.controller;

import java.io.IOException;
import java.time.LocalDateTime;

import br.com.conectacampus.model.ReuniaoEquipe;
import br.com.conectacampus.model.Usuario;
import br.com.conectacampus.model.VotacaoEquipe;
import br.com.conectacampus.service.EquipeInternaService;
import br.com.conectacampus.util.Autorizacao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/equipe-interna")
public class EquipeInternaServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private EquipeInternaService service;

	@Override
	public void init() {
		service = new EquipeInternaService();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Usuario usuario = usuarioLogado(request);

		if (!Autorizacao.podeAcessarEquipeInterna(usuario)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);

			return;
		}

		if ("excluirReuniao".equals(request.getParameter("acao")) || "excluirVotacao".equals(request.getParameter("acao"))) {
			if (!Autorizacao.ehAdministrador(usuario)) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN);

				return;
			}

			boolean excluiu = "excluirReuniao".equals(request.getParameter("acao"))
					? service.excluirReuniao(numero(request, "id"))
							: service.excluirVotacao(numero(request, "id"));
			mensagem(request, excluiu, "Registro excluído com sucesso.", "Não foi possível excluir o registro.");
			response.sendRedirect(request.getContextPath() + "/equipe-interna");

			return;
		}

		request.setAttribute("reunioes", service.listarReunioes());
		request.setAttribute("votacoes", service.listarVotacoes());
		request.setAttribute("votacoesRespondidas", service.listarVotacoesDoUsuario(usuario.getIdUsuario()));
		request.getRequestDispatcher("/pages/equipeInterna.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Usuario usuario = usuarioLogado(request);

		if (!Autorizacao.podeAcessarEquipeInterna(usuario)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);

			return;
		}

		String acao = request.getParameter("acao");
		boolean sucesso;
		String sucessoMensagem;
		String erroMensagem;

		try {
			switch (acao == null ? "" : acao) {

			case "criarReuniao":
				ReuniaoEquipe reuniao = new ReuniaoEquipe();
				reuniao.setTitulo(request.getParameter("titulo"));
				reuniao.setDescricao(request.getParameter("descricao"));
				reuniao.setLocalReuniao(request.getParameter("localReuniao"));
				reuniao.setDataHora(LocalDateTime.parse(request.getParameter("dataHora")));
				reuniao.setCriador(usuario);
				sucesso = service.cadastrarReuniao(reuniao);
				sucessoMensagem = "Reunião agendada com sucesso.";
				erroMensagem = "Preencha os dados obrigatórios da reunião.";
				break;

			case "criarVotacao":
				VotacaoEquipe votacao = new VotacaoEquipe();
				votacao.setTitulo(request.getParameter("titulo"));
				votacao.setDescricao(request.getParameter("descricao"));
				String dataLimite = request.getParameter("dataLimite");

				if (dataLimite != null && !dataLimite.isBlank()) votacao.setDataLimite(LocalDateTime.parse(dataLimite));

				votacao.setCriador(usuario);
				sucesso = service.cadastrarVotacao(votacao, request.getParameter("opcoes"));
				sucessoMensagem = "Votação criada com sucesso.";
				erroMensagem = "Informe um título e pelo menos duas opções.";
				break;

			case "votar":
				sucesso = service.votar(numero(request, "idVotacao"), numero(request, "idOpcao"), usuario.getIdUsuario());
				sucessoMensagem = "Voto registrado com sucesso.";
				erroMensagem = "Seu voto não pôde ser registrado. Talvez você já tenha votado ou a votação foi encerrada.";
				break;

			case "alterarStatusReuniao":
				sucesso = service.atualizarStatusReuniao(numero(request, "idReuniao"), request.getParameter("status"));
				sucessoMensagem = "Status da reunião atualizado.";
				erroMensagem = "Não foi possível atualizar o status da reunião.";
				break;

			case "alterarStatusVotacao":
				sucesso = service.atualizarStatusVotacao(numero(request, "idVotacao"), request.getParameter("status"));
				sucessoMensagem = "Status da votação atualizado.";
				erroMensagem = "Não foi possível atualizar o status da votação.";
				break;

			default:
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);

				return;
			}

		} catch (RuntimeException e) {
			sucesso = false;
			sucessoMensagem = "";
			erroMensagem = "Verifique os dados informados e tente novamente.";
		}

		mensagem(request, sucesso, sucessoMensagem, erroMensagem);
		response.sendRedirect(request.getContextPath() + "/equipe-interna");
	}

	private Usuario usuarioLogado(HttpServletRequest request) {
		return (Usuario) request.getSession().getAttribute("usuarioLogado");
	}

	private int numero(HttpServletRequest request, String nome) {
		return Integer.parseInt(request.getParameter(nome));
	}

	private void mensagem(HttpServletRequest request, boolean sucesso, String mensagemSucesso, String mensagemErro) {
		request.getSession().setAttribute(sucesso ? "msgSucesso" : "msgErro", sucesso ? mensagemSucesso : mensagemErro);
	}
}
