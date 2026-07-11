package br.com.conectacampus.service;

import java.util.List;

import br.com.conectacampus.dao.CategoriaDAO;
import br.com.conectacampus.model.Categoria;

public class CategoriaService {

    private CategoriaDAO dao = new CategoriaDAO();

    public boolean cadastrar(Categoria categoria) {
        return dao.inserir(categoria);
    }

    public boolean atualizar(Categoria categoria) {
        return dao.atualizar(categoria);
    }

    public boolean excluir(int id) {
        return dao.excluir(id);
    }

    public Categoria buscarPorId(int id) {
        return dao.buscarPorId(id);
    }

    public List<Categoria> listar() {
        return dao.listar();
    }

}