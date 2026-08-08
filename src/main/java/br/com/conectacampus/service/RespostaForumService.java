package br.com.conectacampus.service;

import java.util.List;

import br.com.conectacampus.dao.RespostaForumDAO;
import br.com.conectacampus.model.RespostaForum;

public class RespostaForumService {

    private RespostaForumDAO dao = new RespostaForumDAO();

    public boolean cadastrar(RespostaForum resposta) {
        return dao.inserir(resposta);
    }

    public List<RespostaForum> listarPorForum(int idForum) {
        return dao.listarPorForum(idForum);
    }

}
