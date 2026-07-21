package br.com.conectacampus.controller;

import java.io.IOException;

import br.com.conectacampus.model.Forum;
import br.com.conectacampus.model.RespostaForum;
import br.com.conectacampus.model.Usuario;
import br.com.conectacampus.service.RespostaForumService;
import br.com.conectacampus.service.ForumService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/respostasForum")
public class RespostaForumServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private RespostaForumService respostaService;
    private ForumService forumService;

    @Override
    public void init() throws ServletException {
        respostaService = new RespostaForumService();
        forumService = new ForumService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {

            RespostaForum resposta = new RespostaForum();

            resposta.setResposta(request.getParameter("resposta"));
            resposta.setAnonimo("true".equals(request.getParameter("anonimo")));

            Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogado");
            
            if (usuario == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                
                return;
            }

            Forum forum = new Forum();
            forum.setIdForum(Integer.parseInt(request.getParameter("idForum")));

            Forum forumAtual = forumService.buscarPorId(forum.getIdForum());
            
            if (forumAtual == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
              
                return;
            }
           
            if ("ENQUETE".equals(forumAtual.getTipoInteracao())) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
               
                return;
            }

            resposta.setUsuario(usuario);
            resposta.setForum(forum);

            if (respostaService.cadastrar(resposta)) {

                request.getSession().setAttribute("msgSucesso", "Resposta publicada com sucesso.");

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
