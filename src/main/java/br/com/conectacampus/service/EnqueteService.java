package br.com.conectacampus.service;

import br.com.conectacampus.dao.EnqueteDAO;
import br.com.conectacampus.model.Enquete;

public class EnqueteService {

    private EnqueteDAO enqueteDAO;

    public EnqueteService() {

        enqueteDAO = new EnqueteDAO();

    }

    // CADASTRAR

    public boolean cadastrar(Enquete enquete) {

        if (enquete == null)
            return false;

        if (enquete.getTitulo() == null || enquete.getTitulo().isBlank())
            return false;

        if (enquete.getDataInicio() == null)
            return false;

        if (enquete.getUsuario() == null || enquete.getUsuario().getIdUsuario() <= 0)
            return false;

        return enqueteDAO.inserir(enquete);

    }

    public Enquete buscarPorForum(int idForum) {
        return idForum > 0 ? enqueteDAO.buscarPorForum(idForum) : null;
    }

}
