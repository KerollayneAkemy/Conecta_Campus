package br.com.conectacampus.controller;

import java.io.IOException;

import org.mindrot.jbcrypt.BCrypt;

import br.com.conectacampus.model.Perfil;
import br.com.conectacampus.model.Usuario;
import br.com.conectacampus.service.PerfilService;
import br.com.conectacampus.service.UsuarioService;
import br.com.conectacampus.util.Autorizacao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/cadastro")
public class CadastroServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UsuarioService usuarioService;
    private PerfilService perfilService;

    @Override
    public void init() {
        usuarioService = new UsuarioService();
        perfilService = new PerfilService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/pages/cadastro.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        Usuario administrador = (Usuario) request.getSession().getAttribute("usuarioLogado");
        boolean cadastroAdministrativo = Autorizacao.ehAdministrador(administrador);

        if (nome == null || nome.isBlank() || senha == null || senha.length() < 6) {
            exibirErro(request, response, "Preencha todos os campos. A senha deve ter pelo menos 6 caracteres.");
            return;
        }

        String nomePerfil = cadastroAdministrativo ? request.getParameter("perfil") : Autorizacao.ALUNO;
        if (cadastroAdministrativo && !Autorizacao.EQUIPE.equalsIgnoreCase(nomePerfil)
                && !Autorizacao.ALUNO.equalsIgnoreCase(nomePerfil)) {
            exibirErro(request, response, "O cadastro administrativo permite somente equipe ou aluno.");
            return;
        }

        Perfil perfil = perfilService.buscarPorNome(nomePerfil);
        if (perfil == null) {
            exibirErro(request, response, "Perfil de acesso inválido.");
            return;
        }

        if (email == null || email.isBlank()) {
            exibirErro(request, response, "Informe o e-mail da conta.");
            return;
        }
        email = email.trim().toLowerCase();
        boolean equipe = Autorizacao.EQUIPE.equalsIgnoreCase(nomePerfil);
        if (equipe && !email.endsWith("@conecta.com.br")) {
            exibirErro(request, response, "Para a equipe, use um e-mail institucional @conecta.com.br.");
            return;
        }
        if (!equipe && !email.endsWith("@gmail.com")) {
            exibirErro(request, response, "O cadastro de aluno aceita somente e-mails @gmail.com.");
            return;
        }

        Usuario usuario = new Usuario();
        usuario.setNome(nome.trim());
        usuario.setEmail(email);
        usuario.setSenha(BCrypt.hashpw(senha, BCrypt.gensalt(12)));
        usuario.setCurso(request.getParameter("curso"));
        usuario.setAtivo(true);
        usuario.setPerfil(perfil);
        if (equipe) usuario.setEmailInstitucional(email);

        try {
            if (usuarioService.cadastrar(usuario)) response.sendRedirect(request.getContextPath() + "/login");
            else exibirErro(request, response, "E-mail já cadastrado ou dados inválidos.");
        } catch (Exception e) {
            getServletContext().log("Erro ao cadastrar usuário", e);
            exibirErro(request, response, "Não foi possível concluir o cadastro. Verifique se o banco foi atualizado.");
        }
    }

    private void exibirErro(HttpServletRequest request, HttpServletResponse response, String mensagem)
            throws ServletException, IOException {
        request.setAttribute("erro", mensagem);
        request.getRequestDispatcher("/pages/cadastro.jsp").forward(request, response);
    }
}
