package br.com.conectacampus.controller;

import java.io.IOException;

import br.com.conectacampus.model.Usuario;
import br.com.conectacampus.util.Autorizacao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/visualizacao")
public class VisualizacaoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        // Só o administrador de verdade pode trocar o modo de visualização
        if (usuarioLogado == null || !Autorizacao.ehAdministrador(usuarioLogado)) {
            response.sendRedirect(request.getContextPath() + "/pages/home.jsp");
            return;
        }

        String papel = request.getParameter("papel");

        if (papel == null || "ADMINISTRADOR".equalsIgnoreCase(papel)) {
            // Volta ao normal: remove a simulação
            session.removeAttribute("papelSimulado");
        } else if ("EQUIPE".equalsIgnoreCase(papel) || "ALUNO".equalsIgnoreCase(papel)) {
            session.setAttribute("papelSimulado", papel.toUpperCase());
        }

        String origem = request.getParameter("origem");
        response.sendRedirect(origem != null && !origem.isBlank()
                ? origem
                : request.getContextPath() + "/pages/home.jsp");
    }
}