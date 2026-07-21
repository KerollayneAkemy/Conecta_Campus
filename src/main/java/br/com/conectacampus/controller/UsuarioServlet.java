package br.com.conectacampus.controller;

import java.io.IOException;
import java.util.List;

import br.com.conectacampus.model.Perfil;
import br.com.conectacampus.model.Usuario;
import br.com.conectacampus.service.PerfilService;
import br.com.conectacampus.service.UsuarioService;
import br.com.conectacampus.util.Autorizacao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/usuarios")
public class UsuarioServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private UsuarioService usuarioService;
    private PerfilService perfilService;

    @Override
    public void init() {
        usuarioService = new UsuarioService();
        perfilService = new PerfilService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      
    	if (!ehAdministrador(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            
            return;
        }

        String acao = request.getParameter("acao");
        
        if (acao == null) acao = "listar";

        switch (acao) {
        
            case "editar" -> editar(request, response);
            case "desativar" -> alterarStatus(request, response, false);
            case "reativar" -> alterarStatus(request, response, true);
            case "listar" -> listar(request, response);
            default -> redirecionarParaLista(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    	if (!ehAdministrador(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
           
            return;
        }

        if (!"atualizar".equals(request.getParameter("acao"))) {
            redirecionarParaLista(request, response);
           
            return;
        }

        atualizar(request, response);
    }

    private void listar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    	List<Usuario> usuarios = usuarioService.listar();
        request.setAttribute("listaUsuarios", usuarios);
        request.getRequestDispatcher("/pages/usuarios.jsp").forward(request, response);
    }

    private void editar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Usuario usuario = buscarUsuarioDaRequisicao(request);
        
        if (usuario == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            
            return;
        }

        request.setAttribute("usuario", usuario);
        request.getRequestDispatcher("/pages/editarUsuario.jsp").forward(request, response);
    }

    private void alterarStatus(HttpServletRequest request, HttpServletResponse response, boolean ativo) throws IOException {
        Usuario usuario = buscarUsuarioDaRequisicao(request);
        Usuario usuarioLogado = (Usuario) request.getSession().getAttribute("usuarioLogado");

        if (usuario == null) {
            request.getSession().setAttribute("msgErro", "Usuário não encontrado.");
            
        } else if (!ativo && usuario.getIdUsuario() == usuarioLogado.getIdUsuario()) {
        	request.getSession().setAttribute("msgErro", "Você não pode desativar a própria conta.");
       
        } else {
            
        	boolean alterou = usuarioService.atualizarAtivo(usuario.getIdUsuario(), ativo);
            String sucesso = ativo ? "Usuário reativado com sucesso." : "Usuário desativado com sucesso.";
            String erro = ativo ? "Não foi possível reativar o usuário." : "Não foi possível desativar o usuário.";
            
            request.getSession().setAttribute(alterou ? "msgSucesso" : "msgErro", alterou ? sucesso : erro);
        }
        
        redirecionarParaLista(request, response);
    }

    private void atualizar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Usuario usuarioAtual = buscarUsuarioDaRequisicao(request);
       
        if (usuarioAtual == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            
            return;
        }

        Perfil perfil = perfilService.buscarPorNome(request.getParameter("perfil"));
        if (perfil == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Perfil inválido.");
           
            return;
        }

        Usuario usuario = montarUsuarioAtualizado(request, usuarioAtual, perfil);
        boolean atualizou = usuarioService.atualizar(usuario);
        
        request.getSession().setAttribute(atualizou ? "msgSucesso" : "msgErro",
                atualizou ? "Usuário atualizado com sucesso." : "Não foi possível atualizar o usuário.");
        redirecionarParaLista(request, response);
    }

    private Usuario montarUsuarioAtualizado(HttpServletRequest request, Usuario usuarioAtual, Perfil perfil) {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(usuarioAtual.getIdUsuario());
        usuario.setNome(request.getParameter("nome"));
        usuario.setEmail(request.getParameter("email"));
        usuario.setCurso(request.getParameter("curso"));
        usuario.setAtivo(Boolean.parseBoolean(request.getParameter("ativo")));
        usuario.setPerfil(perfil);

        // Dados administrativos não são alterados nesta tela.
        usuario.setSenha(usuarioAtual.getSenha());
        usuario.setSetorInstitucional(usuarioAtual.getSetorInstitucional());
        usuario.setEmailInstitucional(usuarioAtual.getEmailInstitucional());
        usuario.setCargo(usuarioAtual.getCargo());
        
        return usuario;
    }

    private Usuario buscarUsuarioDaRequisicao(HttpServletRequest request) {
        
    	try {
            return usuarioService.buscarPorId(Integer.parseInt(request.getParameter("id")));
       
    	} catch (NumberFormatException e) {
            return null;
        }
    }

    private boolean ehAdministrador(HttpServletRequest request) {
        Usuario usuarioLogado = (Usuario) request.getSession().getAttribute("usuarioLogado");
       
        return Autorizacao.ehAdministrador(usuarioLogado);
    }

    private void redirecionarParaLista(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() + "/usuarios?acao=listar");
    }
}
