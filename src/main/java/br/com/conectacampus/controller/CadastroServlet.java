package br.com.conectacampus.controller;

import java.io.IOException;
import org.mindrot.jbcrypt.BCrypt;
import br.com.conectacampus.model.Perfil;
import br.com.conectacampus.model.Usuario;
import br.com.conectacampus.service.UsuarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/cadastro")
public class CadastroServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private UsuarioService usuarioService;

    @Override
    public void init() throws ServletException {
        usuarioService = new UsuarioService();
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/pages/cadastro.jsp")
               .forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            Usuario usuario = new Usuario();

            usuario.setNome(request.getParameter("nome"));
            usuario.setEmail(request.getParameter("email"));
            String senha = request.getParameter("senha");

            String senhaCriptografada = BCrypt.hashpw(
                    senha,
                    BCrypt.gensalt(12)
            );

            usuario.setSenha(senhaCriptografada);            usuario.setCurso(request.getParameter("curso"));
            usuario.setAtivo(true);

            // Todo usuário cadastrado será ALUNO
            Perfil perfil = new Perfil();
            perfil.setIdPerfil(1);

            usuario.setPerfil(perfil);

            if (usuarioService.cadastrar(usuario)) {

                response.sendRedirect(request.getContextPath() + "/login");

            } else {

                request.setAttribute("erro",
                        "Não foi possível realizar o cadastro.");

                request.getRequestDispatcher("/pages/cadastro.jsp")
                       .forward(request, response);

            }

        } catch (Exception e) {

            throw new ServletException("Erro ao cadastrar usuário.", e);

        }

    }

}