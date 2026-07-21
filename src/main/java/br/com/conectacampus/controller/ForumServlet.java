package br.com.conectacampus.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import br.com.conectacampus.model.Enquete;
import br.com.conectacampus.model.Forum;
import br.com.conectacampus.model.OpcaoEnquete;
import br.com.conectacampus.model.Usuario;
import br.com.conectacampus.service.EnqueteService;
import br.com.conectacampus.service.ForumService;
import br.com.conectacampus.service.OpcaoEnqueteService;
import br.com.conectacampus.service.RespostaForumService;
import br.com.conectacampus.service.VotoService;
import br.com.conectacampus.util.Autorizacao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/forum")
public class ForumServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ForumService forumService;
    private RespostaForumService respostaForumService;
    private EnqueteService enqueteService;
    private OpcaoEnqueteService opcaoEnqueteService;
    private VotoService votoService;

    @Override
    public void init() {
        forumService = new ForumService();
        respostaForumService = new RespostaForumService();
        enqueteService = new EnqueteService();
        opcaoEnqueteService = new OpcaoEnqueteService();
        votoService = new VotoService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      
    	String acao = request.getParameter("acao");
        
    	if (acao == null || acao.isBlank()) acao = "listar";
        
    	Usuario usuarioLogado = (Usuario) request.getSession().getAttribute("usuarioLogado");

        try {
        	
            switch (acao) {
            
            case "listar":
                List<Forum> lista = forumService.listar();
                request.setAttribute("foruns", lista);
                request.getRequestDispatcher("/pages/forum.jsp").forward(request, response);
               
                return;
                
            case "novo":
               
            	if (!Autorizacao.podeGerenciarForum(usuarioLogado)) { response.sendError(403);
            
            	return; 
            	
            	}
            	
                request.getRequestDispatcher("/pages/novoTopico.jsp").forward(request, response);
               
                return;
                
            case "visualizar":
            	
                int idVisualizar = Integer.parseInt(request.getParameter("id"));
                forumService.registrarVisualizacao(idVisualizar);
                
                request.setAttribute("forum", forumService.buscarPorId(idVisualizar));
                request.setAttribute("respostas", respostaForumService.listarPorForum(idVisualizar));
                
                Enquete enquete = enqueteService.buscarPorForum(idVisualizar);
                request.setAttribute("enquete", enquete);
               
                if (enquete != null) {
                    Map<Integer, Integer> votosPorOpcao = votoService.contarVotosPorOpcao(enquete.getIdEnquete());
                   
                    request.setAttribute("votosPorOpcao", votosPorOpcao);
                    request.setAttribute("usuarioJaVotou", usuarioLogado != null
                            && votoService.usuarioJaVotouNaEnquete(usuarioLogado.getIdUsuario(), enquete.getIdEnquete()));
                }
              
                request.getRequestDispatcher("/pages/topico.jsp").forward(request, response);
               
                return;
                
            case "editar":
                if (!Autorizacao.podeGerenciarForum(usuarioLogado)) { response.sendError(403); 
              
                return; 
                
                }
                
               
                Forum forum = forumService.buscarPorId(Integer.parseInt(request.getParameter("id")));
               
                if (forum == null) { response.sendError(404); 
               
                return; 
                
                }
                
                request.setAttribute("forum", forum);
                request.getRequestDispatcher("/pages/topico.jsp").forward(request, response);
               
                return;
                
            case "excluir":
               
            	if (!Autorizacao.ehAdministrador(usuarioLogado)) { response.sendError(403); 
            	
            	return; 
            	
            	}
            	
                boolean excluiu = forumService.excluir(Integer.parseInt(request.getParameter("id")));
                
                request.getSession().setAttribute(excluiu ? "msgSucesso" : "msgErro", excluiu ? "Tópico excluído com sucesso." : "Não foi possível excluir o tópico.");
                response.sendRedirect(request.getContextPath() + "/forum?acao=listar");
                
                return;
                
            default:
                response.sendRedirect(request.getContextPath() + "/forum?acao=listar");
            }
            
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Usuario usuarioLogado = (Usuario) request.getSession().getAttribute("usuarioLogado");
        
        if (!Autorizacao.podeGerenciarForum(usuarioLogado)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
           
            return;
        }
        
        String acao = request.getParameter("acao");
        Forum forum = new Forum();
        forum.setTitulo(request.getParameter("titulo"));
        forum.setMensagem(request.getParameter("mensagem"));
        forum.setUsuario(usuarioLogado);

        try {
          
        	boolean sucesso = false;
           
        	if ("salvar".equals(acao)) {
                String tipoInteracao = request.getParameter("tipoInteracao");
                
                if (!"ENQUETE".equals(tipoInteracao) && !"AMBOS".equals(tipoInteracao)) {
                    tipoInteracao = "RESPOSTAS";
                }
                
                if (!"RESPOSTAS".equals(tipoInteracao)
                        && (request.getParameter("perguntaEnquete") == null
                        || request.getParameter("perguntaEnquete").isBlank()
                        || request.getParameter("opcoesEnquete") == null
                        || request.getParameter("opcoesEnquete").isBlank())) {
                    request.setAttribute("erro", "Informe a pergunta e as opções da enquete.");
                    request.getRequestDispatcher("/pages/novoTopico.jsp").forward(request, response);
                    
                    return;
                }
               
                forum.setTipoInteracao(tipoInteracao);
                
                if (!forumService.cadastrar(forum)) {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    
                    return;
                }
                
                criarEnqueteSeInformada(request, usuarioLogado, forum.getIdForum());
                sucesso = true;
                
            } else if ("atualizar".equals(acao)) {
              
            	forum.setIdForum(Integer.parseInt(request.getParameter("id")));
                sucesso = forumService.atualizar(forum);
            }
        	
            request.getSession().setAttribute(sucesso ? "msgSucesso" : "msgErro",
                    sucesso ? ("atualizar".equals(acao) ? "Tópico atualizado com sucesso." : "Tópico publicado com sucesso.") : "Não foi possível salvar o tópico.");
        
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            
            return;
        }
       
        response.sendRedirect(request.getContextPath() + "/forum?acao=listar");
    }

    private void criarEnqueteSeInformada(HttpServletRequest request, Usuario usuario, int idForum) {
        String tipoInteracao = request.getParameter("tipoInteracao");
        
        if (!"ENQUETE".equals(tipoInteracao) && !"AMBOS".equals(tipoInteracao)) 
   
        	return;
        
        String pergunta = request.getParameter("perguntaEnquete");
        
        if (pergunta == null || pergunta.isBlank()) 
        	
        	return;
        
        Enquete enquete = new Enquete();
        enquete.setTitulo(pergunta.trim());
        enquete.setDescricao(request.getParameter("descricaoEnquete"));
        enquete.setDataInicio(LocalDate.now());
        enquete.setStatus("ABERTA");
        enquete.setUsuario(usuario);
        enquete.setIdForum(idForum);
        
        if (!enqueteService.cadastrar(enquete)) 
        	
        	return;
        
        String opcoes = request.getParameter("opcoesEnquete");
        
        if (opcoes == null)
        	
        	return;
        
        for (String linha : opcoes.split("\\R")) {
        	
            if (!linha.isBlank()) {
                OpcaoEnquete opcao = new OpcaoEnquete();
                opcao.setDescricao(linha.trim());
                opcao.setEnquete(enquete);
                opcaoEnqueteService.cadastrar(opcao);
            }
        }
    }
}
