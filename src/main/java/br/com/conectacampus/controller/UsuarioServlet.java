package br.com.conectacampus.controller;

import java.io.IOException;
import java.util.List;

import br.com.conectacampus.model.Perfil;
import br.com.conectacampus.model.Usuario;
import br.com.conectacampus.service.UsuarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/usuarios")
public class UsuarioServlet extends HttpServlet {

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

        String acao = request.getParameter("acao");

        if (acao == null) {
            acao = "listar";
        }

        switch (acao) {

        case "listar":

            List<Usuario> lista = usuarioService.listar();

            request.setAttribute("listaUsuarios", lista);

            request.getRequestDispatcher("/pages/usuarios.jsp")
                    .forward(request, response);

            break;

        case "editar":

            int id = Integer.parseInt(request.getParameter("id"));

            Usuario usuario = usuarioService.buscarPorId(id);

            request.setAttribute("usuario", usuario);

            request.getRequestDispatcher("/pages/editarUsuario.jsp")
                    .forward(request, response);

            break;

        case "excluir":

            usuarioService.excluir(
                    Integer.parseInt(request.getParameter("id")));

            response.sendRedirect(request.getContextPath()
                    + "/usuarios?acao=listar");

            break;

        default:

            response.sendRedirect(request.getContextPath()
                    + "/usuarios?acao=listar");

        }

    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("acao");

        if ("atualizar".equals(acao)) {

            Usuario usuario = new Usuario();

            usuario.setIdUsuario(
                    Integer.parseInt(request.getParameter("id")));

            usuario.setNome(request.getParameter("nome"));
            usuario.setEmail(request.getParameter("email"));
            usuario.setSenha(request.getParameter("senha"));
            usuario.setCurso(request.getParameter("curso"));
            usuario.setAtivo(Boolean.parseBoolean(request.getParameter("ativo")));

            Perfil perfil = new Perfil();
            perfil.setIdPerfil(
                    Integer.parseInt(request.getParameter("idPerfil")));

            usuario.setPerfil(perfil);

            usuarioService.atualizar(usuario);

        }

        response.sendRedirect(request.getContextPath()
                + "/usuarios?acao=listar");

    }

}