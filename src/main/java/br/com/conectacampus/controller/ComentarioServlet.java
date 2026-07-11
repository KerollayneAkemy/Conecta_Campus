package br.com.conectacampus.controller;

import java.io.IOException;

import br.com.conectacampus.model.Comentario;
import br.com.conectacampus.model.Comunicado;
import br.com.conectacampus.model.Usuario;
import br.com.conectacampus.service.ComentarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/comentarios")
public class ComentarioServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ComentarioService comentarioService;

    @Override
    public void init() throws ServletException {
        comentarioService = new ComentarioService();
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            Comentario comentario = new Comentario();

            comentario.setMensagem(request.getParameter("mensagem"));

            Usuario usuario = new Usuario();
            usuario.setIdUsuario(
                    Integer.parseInt(request.getParameter("idUsuario")));

            Comunicado comunicado = new Comunicado();
            comunicado.setIdComunicado(
                    Integer.parseInt(request.getParameter("idComunicado")));

            comentario.setUsuario(usuario);
            comentario.setComunicado(comunicado);

            if (comentarioService.cadastrar(comentario)) {

                response.sendRedirect(request.getContextPath()
                        + "/comunicados?acao=listar");

            } else {

                request.setAttribute("erro",
                        "Não foi possível cadastrar o comentário.");

                request.getRequestDispatcher("/pages/comunicados.jsp")
                        .forward(request, response);

            }

        } catch (Exception e) {

            throw new ServletException("Erro ao cadastrar comentário.", e);

        }

    }

}