package br.com.conectacampus.service;

import br.com.conectacampus.dao.OpcaoEnqueteDAO;
import br.com.conectacampus.model.OpcaoEnquete;

public class OpcaoEnqueteService {

    private OpcaoEnqueteDAO dao = new OpcaoEnqueteDAO();

    public boolean cadastrar(OpcaoEnquete opcao) {
        return dao.inserir(opcao);
    }

}
