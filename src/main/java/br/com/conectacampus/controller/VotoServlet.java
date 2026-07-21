package br.com.conectacampus.controller;

import java.io.IOException;

import br.com.conectacampus.model.OpcaoEnquete;
import br.com.conectacampus.model.Usuario;
import br.com.conectacampus.model.Voto;
import br.com.conectacampus.service.VotoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/votos")
public class VotoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private VotoService votoService;

    @Override
    public void init() throws ServletException {
        votoService = new VotoService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogado");
      
        if (usuario == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        int idForum;
       
        try {
            idForum = Integer.parseInt(request.getParameter("idForum"));
        
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
          
            return;
        }

        OpcaoEnquete opcao = new OpcaoEnquete();
        
        try {
            opcao.setIdOpcao(Integer.parseInt(request.getParameter("idOpcao")));
       
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/forum?acao=visualizar&id=" + idForum);
            
            return;
        }

        Voto voto = new Voto();
        voto.setUsuario(usuario);
        voto.setOpcaoEnquete(opcao);

        boolean registrado = votoService.votar(voto);

        response.sendRedirect(request.getContextPath() + "/forum?acao=visualizar&id=" + idForum
                + (registrado ? "&voto=registrado" : "&voto=ja-realizado"));
    }
}
