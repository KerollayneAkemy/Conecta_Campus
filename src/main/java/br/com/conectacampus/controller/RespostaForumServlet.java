package br.com.conectacampus.controller;

import java.io.IOException;

import br.com.conectacampus.model.Forum;
import br.com.conectacampus.model.RespostaForum;
import br.com.conectacampus.model.Usuario;
import br.com.conectacampus.service.RespostaForumService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/respostasForum")
public class RespostaForumServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private RespostaForumService respostaService;

    @Override
    public void init() throws ServletException {
        respostaService = new RespostaForumService();
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            RespostaForum resposta = new RespostaForum();

            resposta.setResposta(request.getParameter("resposta"));

            Usuario usuario = new Usuario();
            usuario.setIdUsuario(
                    Integer.parseInt(request.getParameter("idUsuario")));

            Forum forum = new Forum();
            forum.setIdForum(
                    Integer.parseInt(request.getParameter("idForum")));

            resposta.setUsuario(usuario);
            resposta.setForum(forum);

            if (respostaService.cadastrar(resposta)) {

                response.sendRedirect(request.getContextPath()
                        + "/forum?acao=visualizar&id=" + forum.getIdForum());

            } else {

                request.setAttribute("erro",
                        "Não foi possível cadastrar a resposta.");

                request.getRequestDispatcher("/pages/topico.jsp")
                        .forward(request, response);

            }

        } catch (Exception e) {

            throw new ServletException("Erro ao cadastrar resposta.", e);

        }

    }

}
