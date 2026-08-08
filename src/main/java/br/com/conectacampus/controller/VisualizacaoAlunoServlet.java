package br.com.conectacampus.controller;

import java.io.IOException;

import br.com.conectacampus.model.Usuario;
import br.com.conectacampus.util.Autorizacao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/modo-aluno")
public class VisualizacaoAlunoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		Usuario usuario = session == null ? null : (Usuario) session.getAttribute("usuarioLogado");

		if (!Autorizacao.ehAdministradorReal(usuario)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);

			return;
		}

		boolean ativo = Boolean.TRUE.equals(session.getAttribute("modoAlunoAtivo"));

		if (ativo) {
			session.removeAttribute("modoAlunoAtivo");
			session.setAttribute("msgSucesso", "Visão administrativa restaurada.");

		} else {
			session.setAttribute("modoAlunoAtivo", true);
			session.setAttribute("msgSucesso", "Você está visualizando o sistema como aluno.");
		}

		response.sendRedirect(request.getContextPath() + "/pages/home.jsp");
	}
}
