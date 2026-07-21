package br.com.conectacampus.service;

import java.util.List;

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

    // ATUALIZAR

    public boolean atualizar(Enquete enquete) {

        if (enquete == null)
            return false;

        if (enquete.getIdEnquete() <= 0)
            return false;

        return enqueteDAO.atualizar(enquete);

    }

    // EXCLUIR

    public boolean excluir(int idEnquete) {

        if (idEnquete <= 0)
            return false;

        return enqueteDAO.excluir(idEnquete);

    }

    // BUSCAR POR ID

    public Enquete buscarPorId(int idEnquete) {

        if (idEnquete <= 0)
            return null;

        return enqueteDAO.buscarPorId(idEnquete);

    }

    // LISTAR

    public List<Enquete> listar() {

        return enqueteDAO.listar();

    }

    public Enquete buscarPorForum(int idForum) {
        return idForum > 0 ? enqueteDAO.buscarPorForum(idForum) : null;
    }

}
