package br.com.conectacampus.controller;

import java.io.IOException;
import java.util.List;

import br.com.conectacampus.model.Cargo;
import br.com.conectacampus.service.CargoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/cargos")
public class CargoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CargoService cargoService;

    @Override
    public void init() throws ServletException {
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
            List<Cargo> lista = cargoService.listar();
            request.setAttribute("listaCargos", lista);
            request.getRequestDispatcher("/pages/cargos.jsp")
                    .forward(request, response);
            break;
        case "editar":
            int id = Integer.parseInt(request.getParameter("id"));
            Cargo cargo = cargoService.buscarPorId(id);
            request.setAttribute("cargo", cargo);
            request.getRequestDispatcher("/pages/cadastroCargo.jsp")
                    .forward(request, response);
            break;
        case "excluir":
            cargoService.excluir(
                    Integer.parseInt(request.getParameter("id")));
            response.sendRedirect(request.getContextPath()
                    + "/cargos?acao=listar");
            break;
        default:
            response.sendRedirect(request.getContextPath()
                    + "/cargos?acao=listar");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        String acao = request.getParameter("acao");

        Cargo cargo = new Cargo();
        cargo.setNome(request.getParameter("nome"));
        cargo.setDescricao(request.getParameter("descricao"));

        if ("atualizar".equals(acao)) {
            cargo.setIdCargo(
                    Integer.parseInt(request.getParameter("idCargo")));
            cargoService.atualizar(cargo);
        } else {
            cargoService.cadastrar(cargo);
        }

        response.sendRedirect(request.getContextPath()
                + "/cargos?acao=listar");
    }
}