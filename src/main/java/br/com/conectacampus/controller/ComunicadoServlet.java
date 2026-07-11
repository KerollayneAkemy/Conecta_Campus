package br.com.conectacampus.controller;

import java.io.IOException;
import java.util.List;

import br.com.conectacampus.model.Categoria;
import br.com.conectacampus.model.Comunicado;
import br.com.conectacampus.model.Usuario;
import br.com.conectacampus.service.ComunicadoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/comunicados")
public class ComunicadoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ComunicadoService comunicadoService;

    @Override
    public void init() throws ServletException {
        comunicadoService = new ComunicadoService();
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("acao");

        if (acao == null)
            acao = "listar";

        switch (acao) {

        case "listar":

            List<Comunicado> lista = comunicadoService.listar();

            request.setAttribute("listaComunicados", lista);

            request.getRequestDispatcher("/pages/comunicados.jsp")
                    .forward(request, response);

            break;

        case "novo":

            request.getRequestDispatcher("/pages/novoComunicado.jsp")
                    .forward(request, response);

            break;

        case "editar":

            int id = Integer.parseInt(request.getParameter("id"));

            request.setAttribute("comunicado",
                    comunicadoService.buscarPorId(id));

            request.getRequestDispatcher("/pages/editarComunicado.jsp")
                    .forward(request, response);

            break;

        case "excluir":

            comunicadoService.excluir(
                    Integer.parseInt(request.getParameter("id")));

            response.sendRedirect(request.getContextPath()
                    + "/comunicados?acao=listar");

            break;

        default:

            response.sendRedirect(request.getContextPath()
                    + "/comunicados?acao=listar");

        }

    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("acao");

        Comunicado comunicado = new Comunicado();

        comunicado.setTitulo(request.getParameter("titulo"));
        comunicado.setMensagem(request.getParameter("mensagem"));
        comunicado.setPrioridade(request.getParameter("prioridade"));

        Categoria categoria = new Categoria();
        categoria.setIdCategoria(Integer.parseInt(request.getParameter("idCategoria")));
        comunicado.setCategoria(categoria);

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(Integer.parseInt(request.getParameter("idUsuario")));
        comunicado.setUsuario(usuario);

        if ("salvar".equals(acao)) {

            comunicadoService.cadastrar(comunicado);

        } else if ("atualizar".equals(acao)) {

            String id = request.getParameter("id");
            if (id == null || id.isBlank()) {
                id = request.getParameter("idComunicado");
            }

            comunicado.setIdComunicado(Integer.parseInt(id));

            comunicadoService.atualizar(comunicado);

        }

        response.sendRedirect(request.getContextPath()
                + "/comunicados?acao=listar");

    }

}
