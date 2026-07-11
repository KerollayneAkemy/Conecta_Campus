package br.com.conectacampus.controller;

import java.io.IOException;

import br.com.conectacampus.service.ComunicadoService;
import br.com.conectacampus.service.EnqueteService;
import br.com.conectacampus.service.FeedbackService;
import br.com.conectacampus.service.ForumService;
import br.com.conectacampus.service.UsuarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private UsuarioService usuarioService;
    private ComunicadoService comunicadoService;
    private ForumService forumService;
    private EnqueteService enqueteService;
    private FeedbackService feedbackService;

    @Override
    public void init() throws ServletException {
        usuarioService = new UsuarioService();
        comunicadoService = new ComunicadoService();
        forumService = new ForumService();
        enqueteService = new EnqueteService();
        feedbackService = new FeedbackService();
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("totalUsuarios", usuarioService.listar().size());
        request.setAttribute("totalComunicados", comunicadoService.listar().size());
        request.setAttribute("totalTopicos", forumService.listar().size());
        request.setAttribute("totalEnquetes", enqueteService.listar().size());
        request.setAttribute("totalFeedbacks", feedbackService.listar().size());

        request.getRequestDispatcher("/pages/dashboard.jsp")
                .forward(request, response);
    }
}
