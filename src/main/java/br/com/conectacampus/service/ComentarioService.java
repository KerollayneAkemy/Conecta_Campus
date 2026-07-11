package br.com.conectacampus.service;

import java.util.List;

import br.com.conectacampus.dao.ComentarioDAO;
import br.com.conectacampus.model.Comentario;

public class ComentarioService {

    private ComentarioDAO dao = new ComentarioDAO();

    public boolean cadastrar(Comentario comentario) {
        return dao.inserir(comentario);
    }

    public boolean atualizar(Comentario comentario) {
        return dao.atualizar(comentario);
    }

    public boolean excluir(int id) {
        return dao.excluir(id);
    }

    public Comentario buscarPorId(int id) {
        return dao.buscarPorId(id);
    }

    public List<Comentario> listar() {
        return dao.listar();
    }

}