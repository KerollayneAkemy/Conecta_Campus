package br.com.conectacampus.controller;

import java.io.IOException;
import java.util.List;

import br.com.conectacampus.model.Feedback;
import br.com.conectacampus.model.Usuario;
import br.com.conectacampus.service.FeedbackService;
import br.com.conectacampus.util.Autorizacao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/feedback")
public class FeedbackServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private FeedbackService feedbackService;

    @Override
    public void init() throws ServletException {
        feedbackService = new FeedbackService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Usuario usuarioLogado = (Usuario) request.getSession().getAttribute("usuarioLogado");
       
        if (!Autorizacao.podeEnviarFeedback(usuarioLogado)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        
        List<Feedback> lista = Autorizacao.ehAdministrador(usuarioLogado)
                ? feedbackService.listar() : java.util.Collections.emptyList();

        request.setAttribute("listaFeedback", lista);

        request.getRequestDispatcher("/pages/feedback.jsp")
                .forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Usuario usuarioLogado = (Usuario) request.getSession().getAttribute("usuarioLogado");
        
        if (!Autorizacao.podeEnviarFeedback(usuarioLogado)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        Feedback feedback = new Feedback();

        feedback.setAssunto(request.getParameter("assunto"));
        feedback.setTipo(request.getParameter("tipo"));
        feedback.setMensagem(request.getParameter("mensagem"));

        feedback.setAnonimo(Boolean.parseBoolean(request.getParameter("anonimo")));

        if (!feedback.isAnonimo()) {

            feedback.setUsuario(usuarioLogado);

        }

        boolean cadastrou = feedbackService.cadastrar(feedback);
        
        request.getSession().setAttribute(cadastrou ? "msgSucesso" : "msgErro",
                cadastrou ? "Feedback enviado com sucesso." : "Não foi possível enviar o feedback.");

        response.sendRedirect(request.getContextPath() + "/feedback");

    }

}
