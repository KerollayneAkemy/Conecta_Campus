package br.com.conectacampus.controller;

import java.io.IOException;
import java.util.List;

import br.com.conectacampus.model.Cargo;
import br.com.conectacampus.model.Membro;
import br.com.conectacampus.model.Usuario;
import br.com.conectacampus.service.CargoService;
import br.com.conectacampus.service.MembroService;
import br.com.conectacampus.service.UsuarioService;
import br.com.conectacampus.util.Autorizacao;
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
    private UsuarioService usuarioService;

    @Override
    public void init() {
        membroService = new MembroService();
        cargoService = new CargoService();
        usuarioService = new UsuarioService();
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
        
            case "novo" -> exibirFormulario(request, response, null);
            case "editar" -> exibirFormulario(request, response, membroService.buscarPorId(Integer.parseInt(request.getParameter("id"))));
            case "excluir" -> excluir(request, response);
            default -> listar(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	
        if (!ehAdministrador(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            
            return;
        }

        boolean atualizar = "atualizar".equals(request.getParameter("acao"));
        Usuario usuarioEquipe = buscarUsuarioEquipe(request.getParameter("idUsuarioEquipe"));
       
        if (usuarioEquipe == null) {
            redirecionarComErro(request, response, atualizar, "Selecione uma conta institucional ativa da equipe.");
            
            return;
        }

        String telefone = normalizarTelefone(request.getParameter("telefone"));
        
        if (!telefone.matches("\\d{11}")) {
        	
            redirecionarComErro(request, response, atualizar, "Informe um telefone com DDD e 9 dígitos.");
            
            return;
        }

        Cargo cargo = criarCargo(request.getParameter("idCargo"));
      
        if (cargo == null) {
            redirecionarComErro(request, response, atualizar, "Selecione o cargo do membro.");
            
            return;
        }

        Membro membro = new Membro();
        membro.setNome(usuarioEquipe.getNome());
        membro.setEmail(usuarioEquipe.getEmail());
        membro.setTelefone(telefone);
        membro.setCargo(cargo);
       
        if (atualizar) membro.setIdMembro(Integer.parseInt(request.getParameter("idMembro")));

        boolean sucesso = atualizar ? membroService.atualizar(membro) : membroService.cadastrar(membro);
        
        request.getSession().setAttribute(sucesso ? "msgSucesso" : "msgErro", sucesso
                ? (atualizar ? "Membro atualizado com sucesso." : "Membro cadastrado com sucesso.")
                : "Não foi possível salvar o membro. Verifique os dados informados.");
        
        response.sendRedirect(request.getContextPath() + "/membros?acao=listar");
    }

    private void listar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
    	request.setAttribute("listaMembros", membroService.listar());
        request.getRequestDispatcher("/pages/membros.jsp").forward(request, response);
    }

    private void exibirFormulario(HttpServletRequest request, HttpServletResponse response, Membro membro) throws ServletException, IOException {
       
    	request.setAttribute("membro", membro);
        request.setAttribute("listaCargos", cargoService.listar());
        request.setAttribute("listaUsuariosEquipe", listarUsuariosEquipe());
        request.getRequestDispatcher("/pages/cadastroMembro.jsp").forward(request, response);
    }

    private void excluir(HttpServletRequest request, HttpServletResponse response) throws IOException {
     
    	boolean excluiu = membroService.excluir(Integer.parseInt(request.getParameter("id")));
        
    	request.getSession().setAttribute(excluiu ? "msgSucesso" : "msgErro",
                excluiu ? "Membro excluído com sucesso." : "Não foi possível excluir o membro.");
        response.sendRedirect(request.getContextPath() + "/membros?acao=listar");
    }

    private boolean ehAdministrador(HttpServletRequest request) {
        Usuario usuarioLogado = (Usuario) request.getSession().getAttribute("usuarioLogado");
        
        return Autorizacao.ehAdministrador(usuarioLogado);
    }

    private List<Usuario> listarUsuariosEquipe() {
      
    	return usuarioService.listar().stream()
                .filter(Autorizacao::ehEquipe)
                .filter(Usuario::isAtivo)
                .filter(usuario -> possuiEmailInstitucional(usuario))
                .toList();
    }

    private Usuario buscarUsuarioEquipe(String idUsuario) {
      
    	try {
            Usuario usuario = usuarioService.buscarPorId(Integer.parseInt(idUsuario));
           
            return usuario != null && usuario.isAtivo() && Autorizacao.ehEquipe(usuario) && possuiEmailInstitucional(usuario) ? usuario : null;
        
    	} catch (NumberFormatException e) {
            return null;
        }
    }

    private boolean possuiEmailInstitucional(Usuario usuario) {
     
    	return usuario.getEmail() != null && usuario.getEmail().toLowerCase().endsWith("@conecta.com.br");
    }

    private Cargo criarCargo(String idCargo) {
    	
        try {
        
        	if (idCargo == null || idCargo.isBlank()) 
        		return null;
        	
            Cargo cargo = new Cargo();
            cargo.setIdCargo(Integer.parseInt(idCargo));
            
            return cargo;
            
        } catch (NumberFormatException e) {
           
        	return null;
        }
    }

    private String normalizarTelefone(String telefone) {
    	
        return telefone == null ? "" : telefone.replaceAll("\\D", "");
    }

    private void redirecionarComErro(HttpServletRequest request, HttpServletResponse response, boolean atualizar, String mensagem) throws IOException {
        
    	request.getSession().setAttribute("msgErro", mensagem);
       
    	String destino = atualizar
                ? "/membros?acao=editar&id=" + request.getParameter("idMembro")
                : "/membros?acao=novo";
       
    	response.sendRedirect(request.getContextPath() + destino);
    }
}
