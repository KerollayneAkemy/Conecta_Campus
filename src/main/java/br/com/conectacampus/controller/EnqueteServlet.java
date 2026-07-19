package br.com.conectacampus.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import br.com.conectacampus.model.Enquete;
import br.com.conectacampus.model.OpcaoEnquete;
import br.com.conectacampus.service.EnqueteService;
import br.com.conectacampus.service.OpcaoEnqueteService;
import br.com.conectacampus.model.Usuario;
import br.com.conectacampus.util.Autorizacao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/enquetes")
public class EnqueteServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private EnqueteService enqueteService;
    private OpcaoEnqueteService opcaoEnqueteService;

    @Override
    public void init() throws ServletException {
        enqueteService = new EnqueteService();
        opcaoEnqueteService = new OpcaoEnqueteService();
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("acao");
        Usuario usuarioLogado = (Usuario) request.getSession().getAttribute("usuarioLogado");

        if (acao == null)
            acao = "listar";

        switch (acao) {

        case "listar":

            List<Enquete> lista = enqueteService.listar();

            request.setAttribute("listaEnquetes", lista);

            request.getRequestDispatcher("/pages/enquetes.jsp")
                    .forward(request, response);

            break;

        case "novo":

            if (!Autorizacao.podePublicarInstitucional(usuarioLogado)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            request.getRequestDispatcher("/pages/novaEnquete.jsp")
                    .forward(request, response);

            break;

        case "editar":

            if (!Autorizacao.podePublicarInstitucional(usuarioLogado)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            request.setAttribute(
                    "enquete",
                    enqueteService.buscarPorId(
                            Integer.parseInt(request.getParameter("id"))));

            request.getRequestDispatcher("/pages/enquetes.jsp")
                    .forward(request, response);

            break;

        case "excluir":

            if (!Autorizacao.podePublicarInstitucional(usuarioLogado)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            enqueteService.excluir(
                    Integer.parseInt(request.getParameter("id")));

            response.sendRedirect(request.getContextPath()
                    + "/enquetes?acao=listar");

            break;

        default:

            response.sendRedirect(request.getContextPath()
                    + "/enquetes?acao=listar");

        }

    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("acao");
        Usuario usuarioLogado = (Usuario) request.getSession().getAttribute("usuarioLogado");
        if (!Autorizacao.podePublicarInstitucional(usuarioLogado)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        Enquete enquete = new Enquete();

        enquete.setTitulo(request.getParameter("titulo"));
        enquete.setDescricao(request.getParameter("descricao"));
        enquete.setDataInicio(LocalDate.now());
        enquete.setStatus("ABERTA");
        enquete.setUsuario(usuarioLogado);

        if (enqueteService.cadastrar(enquete)) {
            String opcoes = request.getParameter("opcoes");

            if (opcoes != null && !opcoes.isBlank()) {
                String[] linhas = opcoes.split("\\R");

                for (String linha : linhas) {
                    String descricao = linha.trim();

                    if (!descricao.isBlank()) {
                        OpcaoEnquete opcao = new OpcaoEnquete();
                        opcao.setDescricao(descricao);
                        opcao.setEnquete(enquete);
                        opcaoEnqueteService.cadastrar(opcao);
                    }
                }
            }
        }

        response.sendRedirect(request.getContextPath()
                + "/enquetes?acao=listar");

    }

}
