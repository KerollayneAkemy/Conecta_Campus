package br.com.conectacampus.controller;

import java.io.IOException;

import br.com.conectacampus.model.Usuario;
import br.com.conectacampus.service.RecuperacaoSenhaService;
import br.com.conectacampus.service.UsuarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/esqueci-senha")
public class EsqueciSenhaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private UsuarioService usuarioService;
    private RecuperacaoSenhaService recuperacaoSenhaService;

    @Override
    public void init() {
        usuarioService = new UsuarioService();
        recuperacaoSenhaService = new RecuperacaoSenhaService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/pages/esqueciSenha.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        Usuario usuario = usuarioService.buscarPorEmail(email == null ? "" : email.trim().toLowerCase());
        boolean enviado = usuario != null && usuario.isAtivo()
                && recuperacaoSenhaService.solicitar(usuario, urlRedefinicao(request));

        if (usuario == null || !usuario.isAtivo()) {
            request.setAttribute("sucesso", "Se houver uma conta ativa com esse e-mail, você receberá um link para redefinir a senha.");
        } else if (enviado) {
            request.setAttribute("sucesso", "Enviamos um link de recuperação para seu e-mail. Ele vale por 30 minutos.");
        } else {
            request.setAttribute("erro", "Não foi possível enviar o e-mail de recuperação agora. Tente novamente mais tarde.");
        }
        request.getRequestDispatcher("/pages/esqueciSenha.jsp").forward(request, response);
    }

    private String urlRedefinicao(HttpServletRequest request) {
        String configurada = System.getenv("APP_BASE_URL");
        if (configurada != null && !configurada.isBlank()) {
            return configurada.replaceAll("/+$", "") + request.getContextPath() + "/redefinir-senha";
        }
        String url = request.getRequestURL().toString();
        return url.substring(0, url.lastIndexOf("/")) + "/redefinir-senha";
    }
}
