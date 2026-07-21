package br.com.conectacampus.service;

import java.util.List;

import br.com.conectacampus.dao.CategoriaDAO;
import br.com.conectacampus.model.Categoria;

public class CategoriaService {

    private CategoriaDAO categoriaDAO;

    public CategoriaService() {
        this.categoriaDAO = new CategoriaDAO();
    }

    public List<Categoria> listar() {
        return categoriaDAO.listar();
    }

    public Categoria buscarPorId(int id) {
        return categoriaDAO.buscarPorId(id);
    }

    public boolean cadastrar(Categoria categoria) {
       
    	return categoria != null && categoria.getNome() != null && !categoria.getNome().isBlank() && categoriaDAO.inserir(categoria);
    }

    public boolean excluir(int idCategoria) {
        return idCategoria > 0 && categoriaDAO.excluir(idCategoria);
    }

}
