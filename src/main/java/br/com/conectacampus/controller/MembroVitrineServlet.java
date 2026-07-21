package br.com.conectacampus.controller;

import java.io.IOException;
import java.util.List;

import br.com.conectacampus.model.Membro;
import br.com.conectacampus.service.MembroService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/membros-vitrine")
public class MembroVitrineServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private MembroService membroService;

    @Override
    public void init() throws ServletException {
        membroService = new MembroService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    	List<Membro> lista = membroService.listar();
        request.setAttribute("listaMembros", lista);
        request.getRequestDispatcher("/pages/membrosVitrine.jsp").forward(request, response);
    }
}