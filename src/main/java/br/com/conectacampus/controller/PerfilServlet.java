package br.com.conectacampus.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Set;
import java.util.UUID;

import org.mindrot.jbcrypt.BCrypt;

import br.com.conectacampus.model.Usuario;
import br.com.conectacampus.service.UsuarioService;
import br.com.conectacampus.util.UploadStorage;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet("/perfil")
@MultipartConfig(maxFileSize = 2 * 1024 * 1024, maxRequestSize = 3 * 1024 * 1024)
public class PerfilServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Set<String> TIPOS_IMAGEM = Set.of("image/jpeg", "image/png", "image/gif", "image/webp");
	private UsuarioService usuarioService;

	@Override
	public void init() {
		usuarioService = new UsuarioService();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Usuario sessao = (Usuario) request.getSession().getAttribute("usuarioLogado");
		
		request.setAttribute("usuario", usuarioService.buscarPorId(sessao.getIdUsuario()));
		request.getRequestDispatcher("/pages/perfil.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Usuario sessao = (Usuario) request.getSession().getAttribute("usuarioLogado");
		Usuario usuario = usuarioService.buscarPorId(sessao.getIdUsuario());

		if ("senha".equals(request.getParameter("acao"))) {
			atualizarSenha(request, usuario);
		
		} else {
			atualizarDados(request, usuario);
		}
		
		response.sendRedirect(request.getContextPath() + "/perfil");
	}

	private void atualizarDados(HttpServletRequest request, Usuario usuario) throws IOException, ServletException {
		
		String nome = request.getParameter("nome");
		String email = request.getParameter("email");
		
		if (nome == null || nome.isBlank() || email == null || email.isBlank()) {
			request.getSession().setAttribute("perfilErro", "Nome e e-mail são obrigatórios.");
			
			return;
		}
		
		usuario.setNome(nome.trim());
		usuario.setEmail(email.trim().toLowerCase());
		usuario.setNotificarComunicados("true".equals(request.getParameter("notificarComunicados")));

		Part foto = request.getPart("foto");
		
		if (foto != null && foto.getSize() > 0) {
			
			if (!TIPOS_IMAGEM.contains(foto.getContentType())) {
				
				request.getSession().setAttribute("perfilErro", "Envie JPG, PNG, GIF ou WEBP de até 2 MB.");
				
				return;
			}
			
			String extensao = foto.getContentType().substring(foto.getContentType().lastIndexOf('/') + 1);
			Path pasta = UploadStorage.pasta(getServletContext(), "perfis");
			String arquivo = UUID.randomUUID() + "." + extensao;
			
			try (InputStream conteudo = foto.getInputStream()) {
				Files.copy(conteudo, pasta.resolve(arquivo), StandardCopyOption.REPLACE_EXISTING);
			}
			
			usuario.setFotoPerfil("uploads/perfis/" + arquivo);
		}

		if (usuarioService.atualizarPerfil(usuario)) {
			
			request.getSession().setAttribute("usuarioLogado", usuarioService.buscarPorId(usuario.getIdUsuario()));
			request.getSession().setAttribute("perfilSucesso", "Perfil atualizado com sucesso.");
		
		} else {
			
			request.getSession().setAttribute("perfilErro", "Não foi possível salvar. O e-mail pode já estar em uso.");
		}
	}

	private void atualizarSenha(HttpServletRequest request, Usuario usuario) {
		
		String senhaAtual = request.getParameter("senhaAtual");
		String novaSenha = request.getParameter("novaSenha");
		String confirmacao = request.getParameter("confirmacaoSenha");
		
		if (senhaAtual == null || !BCrypt.checkpw(senhaAtual, usuario.getSenha())) {
			
			request.getSession().setAttribute("perfilErro", "A senha atual está incorreta.");
		
		} else if (novaSenha == null || novaSenha.length() < 6 || !novaSenha.equals(confirmacao)) {
		
			request.getSession().setAttribute("perfilErro", "A nova senha deve ter ao menos 6 caracteres e ser confirmada corretamente.");
		
		} else if (usuarioService.atualizarSenha(usuario.getIdUsuario(), BCrypt.hashpw(novaSenha, BCrypt.gensalt(12)))) {
		
			request.getSession().setAttribute("perfilSucesso", "Senha alterada com sucesso.");
		
		} else {
			request.getSession().setAttribute("perfilErro", "Não foi possível alterar a senha.");
		}
	}
}
