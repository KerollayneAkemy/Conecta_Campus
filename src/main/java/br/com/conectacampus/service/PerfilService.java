package br.com.conectacampus.service;

import java.util.List;

import br.com.conectacampus.dao.PerfilDAO;
import br.com.conectacampus.model.Perfil;

public class PerfilService {

    private final PerfilDAO dao;

    public PerfilService() {
        this.dao = new PerfilDAO();
    }

    public Perfil buscarPorId(int id) {
        return dao.buscarPorId(id);
    }

    public List<Perfil> listar() {
        return dao.listar();
    }

    public Perfil buscarPorNome(String nome) {
        for (Perfil perfil : listar()) {
            if (perfil.getNome().equalsIgnoreCase(nome)) {
                return perfil;
            }
        }
        return null;
    }

}
