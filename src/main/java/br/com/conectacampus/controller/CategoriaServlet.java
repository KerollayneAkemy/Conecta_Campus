package br.com.conectacampus.controller;

import java.io.IOException;
import br.com.conectacampus.model.Categoria;
import br.com.conectacampus.model.Usuario;
import br.com.conectacampus.service.CategoriaService;
import br.com.conectacampus.util.Autorizacao;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/categorias")
public class CategoriaServlet extends HttpServlet {
	private CategoriaService categoriaService;
	@Override public void init() { categoriaService = new CategoriaService(); }
	private boolean podeGerenciar(HttpServletRequest request) {
		Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogado");
		return Autorizacao.ehAdministrador(usuario) || Autorizacao.ehEquipe(usuario);
	}
	@Override protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, jakarta.servlet.ServletException {
	
		if (!podeGerenciar(request)) { response.sendError(403); 
		return;
		}
		
		if ("excluir".equals(request.getParameter("acao"))) {
			Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogado");
			
			if (!Autorizacao.ehAdministrador(usuario)) { response.sendError(403); 
			return;			
			}
			
			try {
				boolean excluiu = categoriaService.excluir(Integer.parseInt(request.getParameter("id")));
				request.getSession().setAttribute(excluiu ? "msgSucesso" : "msgErro", excluiu ? "Categoria excluída com sucesso." : "Não foi possível excluir a categoria.");
			
			} catch (NumberFormatException e) { request.getSession().setAttribute("msgErro", "Categoria inválida.");
			
			}
			response.sendRedirect(request.getContextPath() + "/categorias"); return;
		}
		
		request.setAttribute("listaCategorias", categoriaService.listar());
		request.getRequestDispatcher("/pages/categorias.jsp").forward(request, response);
	}
	
	@Override protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		if (!podeGerenciar(request)) { response.sendError(403); 
		return;
		}
		
		Categoria categoria = new Categoria(); categoria.setNome(request.getParameter("nome"));
		
		boolean cadastrou = categoriaService.cadastrar(categoria);
		
		request.getSession().setAttribute(cadastrou ? "msgSucesso" : "msgErro", cadastrou ? "Categoria criada com sucesso." : "Não foi possível criar a categoria.");
		
		response.sendRedirect(request.getContextPath() + "/categorias");
	}
}
