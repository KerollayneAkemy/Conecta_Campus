package br.com.conectacampus.controller;

import java.io.IOException;
import java.util.List;

import br.com.conectacampus.model.Comunicado;
import br.com.conectacampus.model.Usuario;
import br.com.conectacampus.service.CategoriaService;
import br.com.conectacampus.service.ComunicadoService;
import br.com.conectacampus.util.Autorizacao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/comunicados")
public class ComunicadoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ComunicadoService comunicadoService;
    private CategoriaService categoriaService;

    @Override
    public void init() throws ServletException {
        comunicadoService = new ComunicadoService();
        categoriaService = new CategoriaService();
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        String acao = request.getParameter("acao");
        if (acao == null) {
        Usuario usuarioLogado = (Usuario) request.getSession().getAttribute("usuarioLogado");

        if (acao == null)
            acao = "listar";
        }
        switch (acao) {
        case "listar":
            List<Comunicado> lista = comunicadoService.listar();
            request.setAttribute("listaComunicados", lista);
            request.getRequestDispatcher("/pages/comunicados.jsp")
                    .forward(request, response);
            break;
        case "novo":
            request.setAttribute("listaCategorias", categoriaService.listar());

            if (!Autorizacao.podePublicarInstitucional(usuarioLogado)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            request.getRequestDispatcher("/pages/novoComunicado.jsp")
                    .forward(request, response);
            break;
        case "editar":

            if (!Autorizacao.podePublicarInstitucional(usuarioLogado)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            int id = Integer.parseInt(request.getParameter("id"));
            Comunicado comunicado = comunicadoService.buscarPorId(id);
            request.setAttribute("comunicado", comunicado);
            request.setAttribute("listaCategorias", categoriaService.listar());
            request.getRequestDispatcher("/pages/editarComunicado.jsp")
                    .forward(request, response);
            break;
        case "excluir":
            boolean excluiu = comunicadoService.excluir(

            if (!Autorizacao.podePublicarInstitucional(usuarioLogado)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            comunicadoService.excluir(
                    Integer.parseInt(request.getParameter("id")));
            if (excluiu) {
                request.getSession().setAttribute("msgSucesso", "Comunicado excluído com sucesso.");
            } else {
                request.getSession().setAttribute("msgErro", "Não foi possível excluir o comunicado.");
            }
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

        Usuario usuarioLogado = (Usuario) request.getSession().getAttribute("usuarioLogado");
        if (usuarioLogado == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        Usuario usuarioLogado = (Usuario) request.getSession().getAttribute("usuarioLogado");
        if (!Autorizacao.podePublicarInstitucional(usuarioLogado)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        Comunicado comunicado = new Comunicado();
        comunicado.setTitulo(request.getParameter("titulo"));
        comunicado.setMensagem(request.getParameter("mensagem"));
        comunicado.setPrioridade(request.getParameter("prioridade"));
        comunicado.setUsuario(usuarioLogado);

        String idCategoriaParam = request.getParameter("idCategoria");
        if (idCategoriaParam != null && !idCategoriaParam.isEmpty()) {
            comunicado.setIdCategoria(Integer.parseInt(idCategoriaParam));
        }

        boolean sucesso;
        if ("atualizar".equals(acao)) {
            comunicado.setIdComunicado(
                    Integer.parseInt(request.getParameter("idComunicado")));
            String status = request.getParameter("status");
            comunicado.setStatus(status != null ? status : "ATIVO");
            sucesso = comunicadoService.atualizar(comunicado);
        } else {
            comunicado.setStatus("ATIVO");
            sucesso = comunicadoService.cadastrar(comunicado);
        }

        if (sucesso) {
            request.getSession().setAttribute("msgSucesso",
                    "atualizar".equals(acao) ? "Comunicado atualizado com sucesso." : "Comunicado publicado com sucesso.");
        } else {
            request.getSession().setAttribute("msgErro",
                    "Não foi possível salvar o comunicado. Verifique se a categoria selecionada existe.");

        Categoria categoria = new Categoria();
        categoria.setIdCategoria(Integer.parseInt(request.getParameter("idCategoria")));
        comunicado.setCategoria(categoria);

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(usuarioLogado.getIdUsuario());
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