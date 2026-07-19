package br.com.conectacampus.service;

import java.util.List;

import br.com.conectacampus.dao.MembroDAO;
import br.com.conectacampus.model.Membro;

public class MembroService {

    private MembroDAO membroDAO;

    public MembroService() {
        this.membroDAO = new MembroDAO();
    }

    public boolean cadastrar(Membro membro) {
        return membroDAO.inserir(membro);
    }

    public boolean atualizar(Membro membro) {
        return membroDAO.atualizar(membro);
    }

    public boolean excluir(int id) {
        return membroDAO.excluir(id);
    }

    public Membro buscarPorId(int id) {
        return membroDAO.buscarPorId(id);
    }

    public List<Membro> listar() {
        return membroDAO.listar();
    }

    public int quantidadeMembros() {
        return membroDAO.quantidadeMembros();
    }

}
