package br.com.conectacampus.controller;

import java.io.IOException;
import java.util.List;

import br.com.conectacampus.model.Cargo;
import br.com.conectacampus.model.Membro;
import br.com.conectacampus.service.CargoService;
import br.com.conectacampus.service.MembroService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/membros")
public class MembroServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private MembroService membroService;
    private CargoService cargoService;

    @Override
    public void init() throws ServletException {
        membroService = new MembroService();
        cargoService = new CargoService();
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        String acao = request.getParameter("acao");
        if (acao == null) {
            acao = "listar";
        }
        switch (acao) {
        case "listar":
            List<Membro> lista = membroService.listar();
            request.setAttribute("listaMembros", lista);
            request.getRequestDispatcher("/pages/membros.jsp")
                    .forward(request, response);
            break;
        case "novo":
            List<Cargo> cargosNovo = cargoService.listar();
            request.setAttribute("listaCargos", cargosNovo);
            request.getRequestDispatcher("/pages/cadastroMembro.jsp")
                    .forward(request, response);
            break;
        case "editar":
            int id = Integer.parseInt(request.getParameter("id"));
            Membro membro = membroService.buscarPorId(id);
            List<Cargo> cargosEditar = cargoService.listar();
            request.setAttribute("membro", membro);
            request.setAttribute("listaCargos", cargosEditar);
            request.getRequestDispatcher("/pages/cadastroMembro.jsp")
                    .forward(request, response);
            break;
        case "excluir":
            boolean excluiu = membroService.excluir(
                    Integer.parseInt(request.getParameter("id")));
            if (excluiu) {
                request.getSession().setAttribute("msgSucesso", "Membro excluído com sucesso.");
            } else {
                request.getSession().setAttribute("msgErro", "Não foi possível excluir o membro.");
            }
            response.sendRedirect(request.getContextPath()
                    + "/membros?acao=listar");
            break;
        default:
            response.sendRedirect(request.getContextPath()
                    + "/membros?acao=listar");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        String acao = request.getParameter("acao");

        Membro membro = new Membro();
        membro.setNome(request.getParameter("nome"));
        membro.setEmail(request.getParameter("email"));
        membro.setTelefone(request.getParameter("telefone"));

        String idCargoParam = request.getParameter("idCargo");
        if (idCargoParam != null && !idCargoParam.isEmpty()) {
            Cargo cargo = new Cargo();
            cargo.setIdCargo(Integer.parseInt(idCargoParam));
            membro.setCargo(cargo);
        }

        boolean sucesso;
        if ("atualizar".equals(acao)) {
            membro.setIdMembro(
                    Integer.parseInt(request.getParameter("idMembro")));
            sucesso = membroService.atualizar(membro);
        } else {
            sucesso = membroService.cadastrar(membro);
        }

        if (sucesso) {
            request.getSession().setAttribute("msgSucesso",
                    "atualizar".equals(acao) ? "Membro atualizado com sucesso." : "Membro cadastrado com sucesso.");
        } else {
            request.getSession().setAttribute("msgErro",
                    "Não foi possível salvar o membro. Verifique os dados informados.");
        }

        response.sendRedirect(request.getContextPath()
                + "/membros?acao=listar");
    }
}