package br.com.conectacampus.filter;

import java.io.IOException;

import br.com.conectacampus.model.Usuario;
import br.com.conectacampus.util.Autorizacao;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter("/*")
public class VisualizacaoAlunoFilter extends HttpFilter {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpSession session = request.getSession(false);
        Usuario usuario = session == null ? null : (Usuario) session.getAttribute("usuarioLogado");
        boolean modoAluno = session != null
                && Boolean.TRUE.equals(session.getAttribute("modoAlunoAtivo"))
                && Autorizacao.ehAdministradorReal(usuario);

        if (modoAluno) {
            Autorizacao.iniciarVisualizacaoAluno();
        }

        try {
            chain.doFilter(request, response);
        } finally {
            Autorizacao.encerrarVisualizacaoAluno();
        }
    }
}
