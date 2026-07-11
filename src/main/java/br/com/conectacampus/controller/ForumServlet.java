package br.com.conectacampus.controller;

import java.io.IOException;
import java.util.List;

import br.com.conectacampus.model.Forum;
import br.com.conectacampus.model.Usuario;
import br.com.conectacampus.service.ForumService;
import br.com.conectacampus.service.RespostaForumService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/forum")
public class ForumServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ForumService forumService;
    private RespostaForumService respostaForumService;

    @Override
    public void init() throws ServletException {
        forumService = new ForumService();
        respostaForumService = new RespostaForumService();
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("acao");

        if (acao == null)
            acao = "listar";

        switch (acao) {

        case "listar":

            List<Forum> lista = forumService.listar();

            request.setAttribute("foruns", lista);

            request.getRequestDispatcher("/pages/forum.jsp")
                    .forward(request, response);

            break;

        case "novo":

            request.getRequestDispatcher("/pages/novoTopico.jsp")
                    .forward(request, response);

            break;

        case "visualizar":

            int idVisualizar = Integer.parseInt(request.getParameter("id"));

            forumService.registrarVisualizacao(idVisualizar);

            request.setAttribute("forum",
                    forumService.buscarPorId(idVisualizar));
            request.setAttribute("respostas",
                    respostaForumService.listarPorForum(idVisualizar));

            request.getRequestDispatcher("/pages/topico.jsp")
                    .forward(request, response);

            break;

        case "editar":

            int id = Integer.parseInt(request.getParameter("id"));

            Forum forum = forumService.buscarPorId(id);

            request.setAttribute("forum", forum);

            request.getRequestDispatcher("/pages/topico.jsp")
                    .forward(request, response);

            break;

        case "excluir":

            forumService.excluir(Integer.parseInt(request.getParameter("id")));

            response.sendRedirect(request.getContextPath()
                    + "/forum?acao=listar");

            break;

        default:

            response.sendRedirect(request.getContextPath()
                    + "/forum?acao=listar");

        }

    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("acao");

        Forum forum = new Forum();

        forum.setTitulo(request.getParameter("titulo"));
        forum.setMensagem(request.getParameter("mensagem"));

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(
                Integer.parseInt(request.getParameter("idUsuario")));

        forum.setUsuario(usuario);

        if ("salvar".equals(acao)) {

            forumService.cadastrar(forum);

        } else if ("atualizar".equals(acao)) {

            forum.setIdForum(
                    Integer.parseInt(request.getParameter("id")));

            forumService.atualizar(forum);

        }

        response.sendRedirect(request.getContextPath()
                + "/forum?acao=listar");

    }

}
