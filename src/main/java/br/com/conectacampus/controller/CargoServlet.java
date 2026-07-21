package br.com.conectacampus.controller;

import java.io.IOException;
import java.util.List;

import br.com.conectacampus.model.Cargo;
import br.com.conectacampus.model.Usuario;
import br.com.conectacampus.service.CargoService;
import br.com.conectacampus.util.Autorizacao;
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Usuario usuarioLogado = (Usuario) request.getSession().getAttribute("usuarioLogado");
        
        if (!Autorizacao.ehAdministrador(usuarioLogado)) { response.sendError(HttpServletResponse.SC_FORBIDDEN); 
        
        return;  
        }
        
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
            boolean excluiu = cargoService.excluir(
                    Integer.parseInt(request.getParameter("id")));
            if (excluiu) {
                request.getSession().setAttribute("msgSucesso", "Cargo excluído com sucesso.");
            } else {
                request.getSession().setAttribute("msgErro",
                        "Não foi possível excluir o cargo. Verifique se ele não está vinculado a algum membro.");
            }
            response.sendRedirect(request.getContextPath()
                    + "/cargos?acao=listar");
            break;
        default:
            response.sendRedirect(request.getContextPath()
                    + "/cargos?acao=listar");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Usuario usuarioLogado = (Usuario) request.getSession().getAttribute("usuarioLogado");
       
        if (!Autorizacao.ehAdministrador(usuarioLogado)) { response.sendError(HttpServletResponse.SC_FORBIDDEN); return; }
        String acao = request.getParameter("acao");

        Cargo cargo = new Cargo();
        cargo.setNome(request.getParameter("nome"));
        cargo.setDescricao(request.getParameter("descricao"));

        boolean sucesso;
        if ("atualizar".equals(acao)) {
            cargo.setIdCargo(
                    Integer.parseInt(request.getParameter("idCargo")));
            sucesso = cargoService.atualizar(cargo);
        } else {
            sucesso = cargoService.cadastrar(cargo);
        }

        if (sucesso) {
            request.getSession().setAttribute("msgSucesso",
                    "atualizar".equals(acao) ? "Cargo atualizado com sucesso." : "Cargo cadastrado com sucesso.");
        } else {
            request.getSession().setAttribute("msgErro",
                    "Não foi possível salvar o cargo. Verifique os dados informados.");
        }

        response.sendRedirect(request.getContextPath()
                + "/cargos?acao=listar");
    }
}
