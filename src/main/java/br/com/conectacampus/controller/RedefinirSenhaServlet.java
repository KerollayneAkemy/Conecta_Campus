package br.com.conectacampus.controller;

import java.io.IOException;

import br.com.conectacampus.service.RecuperacaoSenhaService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/redefinir-senha")
public class RedefinirSenhaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private RecuperacaoSenhaService recuperacaoSenhaService;

    @Override
    public void init() {
        recuperacaoSenhaService = new RecuperacaoSenhaService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (tokenInvalido(request.getParameter("token"))) {
            request.setAttribute("erro", "O link de recuperação é inválido.");
        }
        request.getRequestDispatcher("/pages/redefinirSenha.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getParameter("token");
        String senha = request.getParameter("senha");
        String confirmacao = request.getParameter("confirmacao");

        if (tokenInvalido(token) || senha == null || senha.length() < 6 || !senha.equals(confirmacao)) {
            request.setAttribute("erro", "A senha deve ter pelo menos 6 caracteres e ser confirmada corretamente.");
            request.getRequestDispatcher("/pages/redefinirSenha.jsp").forward(request, response);
            return;
        }

        if (recuperacaoSenhaService.redefinir(token, senha)) {
            request.getSession().setAttribute("msgLogin", "Senha redefinida com sucesso. Entre com sua nova senha.");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        request.setAttribute("erro", "Este link expirou, já foi utilizado ou é inválido. Solicite outro link.");
        request.getRequestDispatcher("/pages/redefinirSenha.jsp").forward(request, response);
    }

    private boolean tokenInvalido(String token) {
        return token == null || token.isBlank() || token.length() > 100;
    }
}
