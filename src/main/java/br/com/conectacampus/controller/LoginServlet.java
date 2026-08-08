package br.com.conectacampus.controller;

import java.io.IOException;

import br.com.conectacampus.model.Usuario;
import br.com.conectacampus.service.LoginService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private LoginService loginService;

    @Override
    public void init() throws ServletException {
        loginService = new LoginService();
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String mensagem = (String) session.getAttribute("msgLogin");
        if (mensagem != null) {
            request.setAttribute("sucesso", mensagem);
            session.removeAttribute("msgLogin");
        }

        if (Boolean.TRUE.equals(session.getAttribute("cadastroConcluido"))) {
            request.setAttribute("cadastroConcluido", Boolean.TRUE);
            session.removeAttribute("cadastroConcluido");
        }

        request.getRequestDispatcher("/pages/login.jsp")
               .forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        Usuario usuario = loginService.autenticar(email, senha);

        if (usuario != null) {

            HttpSession session = request.getSession();

            session.setAttribute("usuarioLogado", usuario);

            response.sendRedirect(request.getContextPath()+"/pages/home.jsp");
            
        } else {

            request.setAttribute("erro", "E-mail ou senha inválidos.");

            request.getRequestDispatcher("/pages/login.jsp")
                   .forward(request, response);

        }

    }

}
