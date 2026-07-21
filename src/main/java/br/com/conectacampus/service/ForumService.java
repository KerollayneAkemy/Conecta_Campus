package br.com.conectacampus.service;

import java.util.List;

import br.com.conectacampus.dao.ForumDAO;
import br.com.conectacampus.model.Forum;

public class ForumService {

    private ForumDAO forumDAO;

    public ForumService() {
        forumDAO = new ForumDAO();
    }

    // CADASTRAR

    public boolean cadastrar(Forum forum) {

        if (forum == null)
            return false;

        if (forum.getTitulo() == null || forum.getTitulo().isBlank())
            return false;

        if (forum.getMensagem() == null || forum.getMensagem().isBlank())
            return false;

        if (forum.getUsuario() == null)
            return false;

        return forumDAO.inserir(forum);

    }

    // ATUALIZAR

    public boolean atualizar(Forum forum) {

        if (forum == null)
            return false;

        if (forum.getIdForum() <= 0)
            return false;

        return forumDAO.atualizar(forum);

    }

    // EXCLUIR

    public boolean excluir(int idForum) {

        if (idForum <= 0)
            return false;

        return forumDAO.excluir(idForum);

    }

    // BUSCAR POR ID

    public Forum buscarPorId(int idForum) {

        if (idForum <= 0)
            return null;

        return forumDAO.buscarPorId(idForum);

    }

    // LISTAR

    public List<Forum> listar() {

        return forumDAO.listar();

    }

    // REGISTRAR VISUALIZAÇÃO

    public void registrarVisualizacao(int idForum) {

        if (idForum > 0) {
            forumDAO.incrementarVisualizacao(idForum);
        }

    }

}
