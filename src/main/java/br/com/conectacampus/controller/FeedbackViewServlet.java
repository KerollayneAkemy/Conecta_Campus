package br.com.conectacampus.controller;

import java.io.IOException;
import java.util.List;

import br.com.conectacampus.model.Feedback;
import br.com.conectacampus.service.FeedbackViewService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/feedbackview")
public class FeedbackViewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private FeedbackViewService feedbackViewService;

    @Override
    public void init() throws ServletException {
        feedbackViewService = new FeedbackViewService();
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
            String tipo = request.getParameter("tipo");
            List<Feedback> lista;
            if (tipo != null && !tipo.isEmpty()) {
                lista = feedbackViewService.listarPorTipo(tipo);
            } else {
                lista = feedbackViewService.listar();
            }
            request.setAttribute("listaFeedbacks", lista);
            request.setAttribute("filtroTipo", tipo);
            request.setAttribute("totalFeedbacks", feedbackViewService.quantidadeTotal());
            request.setAttribute("totalSugestoes", feedbackViewService.quantidadePorTipo("SUGESTAO"));
            request.setAttribute("totalElogios", feedbackViewService.quantidadePorTipo("ELOGIO"));
            request.setAttribute("totalReclamacoes", feedbackViewService.quantidadePorTipo("RECLAMACAO"));
            request.getRequestDispatcher("/pages/feedbackView.jsp")
                    .forward(request, response);
            break;
        case "visualizar":
            int id = Integer.parseInt(request.getParameter("id"));
            Feedback feedback = feedbackViewService.buscarPorId(id);
            request.setAttribute("feedback", feedback);
            request.getRequestDispatcher("/pages/detalheFeedbackView.jsp")
                    .forward(request, response);
            break;
        case "excluir":
            feedbackViewService.excluir(
                    Integer.parseInt(request.getParameter("id")));
            response.sendRedirect(request.getContextPath()
                    + "/feedbackview?acao=listar");
            break;
        default:
            response.sendRedirect(request.getContextPath()
                    + "/feedbackview?acao=listar");
        }
    }
}