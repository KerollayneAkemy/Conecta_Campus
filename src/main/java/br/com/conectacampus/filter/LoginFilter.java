package br.com.conectacampus.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter("/*")
public class LoginFilter extends HttpFilter implements Filter {

    private static final long serialVersionUID = 1L;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(HttpServletRequest request,
                         HttpServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        String uri = request.getRequestURI();

        boolean paginaLivre =
                uri.endsWith("login")
             || uri.endsWith("cadastro")
             || uri.contains("/css/")
             || uri.contains("/js/")
             || uri.contains("/img/")
             || uri.endsWith(".css")
             || uri.endsWith(".js")
             || uri.endsWith(".png")
             || uri.endsWith(".jpg")
             || uri.endsWith(".jpeg")
             || uri.endsWith(".gif")
             || uri.endsWith(".ico");
        
        HttpSession session = request.getSession(false);

        boolean logado =
                session != null &&
                session.getAttribute("usuarioLogado") != null;

        if (paginaLivre || logado) {

            chain.doFilter(request, response);

        } else {

            response.sendRedirect(
                    request.getContextPath() + "/login");

        }

    }

}