package br.com.conectacampus.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Mantém URLs antigas compatíveis. As enquetes agora pertencem a tópicos do Fórum.
 */
@WebServlet("/enquetes")
public class EnqueteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    	response.sendRedirect(request.getContextPath() + "/forum");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
    	response.sendRedirect(request.getContextPath() + "/forum");
    }
}
