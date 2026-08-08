package br.com.conectacampus.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import br.com.conectacampus.model.Comunicado;
import br.com.conectacampus.model.Usuario;
import br.com.conectacampus.service.CategoriaService;
import br.com.conectacampus.service.ComunicadoService;
import br.com.conectacampus.service.EmailService;
import br.com.conectacampus.service.UsuarioService;
import br.com.conectacampus.util.Autorizacao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet("/comunicados")
@MultipartConfig(maxFileSize = 3 * 1024 * 1024, maxRequestSize = 4 * 1024 * 1024)
public class ComunicadoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ComunicadoService comunicadoService;
	private CategoriaService categoriaService;
	private UsuarioService usuarioService;
	private EmailService emailService;
	private ExecutorService executorNotificacoes;
	private static final Set<String> TIPOS_IMAGEM = Set.of("image/jpeg", "image/png", "image/gif", "image/webp");

	@Override
	public void init() {
		comunicadoService = new ComunicadoService();
		categoriaService = new CategoriaService();
		usuarioService = new UsuarioService();
		emailService = new EmailService();
		executorNotificacoes = Executors.newSingleThreadExecutor(r -> {
			Thread thread = new Thread(r, "notificacoes-comunicados");
			thread.setDaemon(true);
			return thread;
		});
	}

	@Override
	public void destroy() {
		if (executorNotificacoes != null) executorNotificacoes.shutdown();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String acao = request.getParameter("acao");

		if (acao == null || acao.isBlank()) acao = "listar";

		Usuario usuarioLogado = (Usuario) request.getSession().getAttribute("usuarioLogado");

		switch (acao) {

		case "listar":

			List<Comunicado> lista = Autorizacao.ehAluno(usuarioLogado)
			? comunicadoService.listarAtivos() : comunicadoService.listar();
			request.setAttribute("listaComunicados", lista);
			request.setAttribute("listaCategorias", categoriaService.listar());
			request.getRequestDispatcher("/pages/comunicados.jsp").forward(request, response);

			return;

		case "novo":

			if (!Autorizacao.podeGerenciarComunicados(usuarioLogado)) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
				return;
			}

			request.setAttribute("listaCategorias", categoriaService.listar());
			request.getRequestDispatcher("/pages/novoComunicado.jsp").forward(request, response);

			return;

		case "visualizar":

			try {

				Comunicado comunicado = comunicadoService.buscarPorId(Integer.parseInt(request.getParameter("id")));

				if (comunicado == null || (Autorizacao.ehAluno(usuarioLogado) && !"ATIVO".equals(comunicado.getStatus()))) {
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
					return;
				}

				request.setAttribute("comunicado", comunicado);
				request.getRequestDispatcher("/pages/detalheComunicado.jsp").forward(request, response);

			} catch (NumberFormatException e) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			}

			return;

		case "editar":

			if (!Autorizacao.podeGerenciarComunicados(usuarioLogado)) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
				return;
			}

			try {

				Comunicado comunicado = comunicadoService.buscarPorId(Integer.parseInt(request.getParameter("id")));

				if (comunicado == null) {
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
					return;
				}

				request.setAttribute("comunicado", comunicado);
				request.setAttribute("listaCategorias", categoriaService.listar());
				request.getRequestDispatcher("/pages/editarComunicado.jsp").forward(request, response);

			} catch (NumberFormatException e) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			}

			return;

		case "excluir":

			if (!Autorizacao.ehAdministrador(usuarioLogado)) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
				return;
			}

			try {

				boolean excluiu = comunicadoService.excluir(Integer.parseInt(request.getParameter("id")));

				request.getSession().setAttribute(excluiu ? "msgSucesso" : "msgErro",
						excluiu ? "Comunicado excluído com sucesso." : "Não foi possível excluir o comunicado.");

				response.sendRedirect(request.getContextPath() + "/comunicados?acao=listar");

			} catch (NumberFormatException e) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			}

			return;

		default:
			response.sendRedirect(request.getContextPath() + "/comunicados?acao=listar");
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Usuario usuarioLogado = (Usuario) request.getSession().getAttribute("usuarioLogado");

		if (!Autorizacao.podeGerenciarComunicados(usuarioLogado)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);

			return;
		}

		try {

			Comunicado comunicado = new Comunicado();
			comunicado.setTitulo(request.getParameter("titulo"));
			comunicado.setMensagem(request.getParameter("mensagem"));
			comunicado.setPrioridade(request.getParameter("prioridade"));
			comunicado.setStatus(request.getParameter("status") == null ? "ATIVO" : request.getParameter("status"));
			comunicado.setIdCategoria(Integer.parseInt(request.getParameter("idCategoria")));
			comunicado.setUsuario(usuarioLogado);

			boolean atualizar = "atualizar".equals(request.getParameter("acao"));

			if (atualizar) {
				comunicado.setIdComunicado(Integer.parseInt(request.getParameter("idComunicado")));

				Comunicado atual = comunicadoService.buscarPorId(comunicado.getIdComunicado());

				if (atual != null) comunicado.setImagem(atual.getImagem());
			}

			Part imagem = request.getPart("imagem");

			if (imagem != null && imagem.getSize() > 0) {
				if (!TIPOS_IMAGEM.contains(imagem.getContentType())) {

					request.getSession().setAttribute("msgErro", "Envie uma imagem JPG, PNG, GIF ou WEBP.");

					response.sendRedirect(request.getContextPath() + "/comunicados");

					return;
				}
				String extensao = imagem.getContentType().substring(imagem.getContentType().lastIndexOf('/') + 1);

				Path pasta = Path.of(getServletContext().getRealPath("/uploads/comunicados"));

				Files.createDirectories(pasta);

				String arquivo = UUID.randomUUID() + "." + extensao;

				try (InputStream conteudo = imagem.getInputStream()) {
					Files.copy(conteudo, pasta.resolve(arquivo), StandardCopyOption.REPLACE_EXISTING);
				}

				comunicado.setImagem("uploads/comunicados/" + arquivo);
			}
			boolean sucesso = atualizar ? comunicadoService.atualizar(comunicado) : comunicadoService.cadastrar(comunicado);

			if (sucesso && !atualizar && "ATIVO".equalsIgnoreCase(comunicado.getStatus())) {
				agendarNotificacoes(request, comunicado);
			}

			request.getSession().setAttribute(sucesso ? "msgSucesso" : "msgErro",
					sucesso ? (atualizar ? "Comunicado atualizado com sucesso." : "Comunicado publicado com sucesso.")
							: "Não foi possível salvar o comunicado.");

		} catch (NumberFormatException e) {

			request.getSession().setAttribute("msgErro", "Categoria ou comunicado inválido.");
		}

		response.sendRedirect(request.getContextPath() + "/comunicados?acao=listar");
	}

	private void agendarNotificacoes(HttpServletRequest request, Comunicado comunicado) {
		List<Usuario> destinatarios = usuarioService.listarInscritosComunicados();
		if (destinatarios.isEmpty()) return;

		String link = urlBase(request) + request.getContextPath()
				+ "/comunicados?acao=visualizar&id=" + comunicado.getIdComunicado();
		String titulo = comunicado.getTitulo();
		String mensagem = comunicado.getMensagem();

		executorNotificacoes.submit(() -> {
			for (Usuario destinatario : destinatarios) {
				emailService.enviarComunicado(destinatario.getEmail(), destinatario.getNome(), titulo, mensagem, link);
			}
		});
	}

	private String urlBase(HttpServletRequest request) {
		StringBuilder url = new StringBuilder(request.getScheme()).append("://").append(request.getServerName());
		int porta = request.getServerPort();
		if (("http".equalsIgnoreCase(request.getScheme()) && porta != 80)
				|| ("https".equalsIgnoreCase(request.getScheme()) && porta != 443)) {
			url.append(':').append(porta);
		}
		return url.toString();
	}
}
