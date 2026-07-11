package br.com.conectacampus.service;

import java.util.List;

import br.com.conectacampus.dao.OpcaoEnqueteDAO;
import br.com.conectacampus.model.OpcaoEnquete;

public class OpcaoEnqueteService {

    private OpcaoEnqueteDAO dao = new OpcaoEnqueteDAO();

    public boolean cadastrar(OpcaoEnquete opcao) {
        return dao.inserir(opcao);
    }

    public boolean atualizar(OpcaoEnquete opcao) {
        return dao.atualizar(opcao);
    }

    public boolean excluir(int id) {
        return dao.excluir(id);
    }

    public OpcaoEnquete buscarPorId(int id) {
        return dao.buscarPorId(id);
    }

    public List<OpcaoEnquete> listar() {
        return dao.listar();
    }

}