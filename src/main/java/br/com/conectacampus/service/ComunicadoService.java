package br.com.conectacampus.service;

import java.util.List;

import br.com.conectacampus.dao.ComunicadoDAO;
import br.com.conectacampus.model.Comunicado;

public class ComunicadoService {

    private ComunicadoDAO comunicadoDAO;

    public ComunicadoService() {
        this.comunicadoDAO = new ComunicadoDAO();
    }

    public boolean cadastrar(Comunicado comunicado) {
        if (comunicado == null) return false;
        if (comunicado.getTitulo() == null || comunicado.getTitulo().isBlank()) return false;
        if (comunicado.getMensagem() == null || comunicado.getMensagem().isBlank()) return false;
        return comunicadoDAO.inserir(comunicado);
    }

    public boolean atualizar(Comunicado comunicado) {
        if (comunicado == null || comunicado.getIdComunicado() <= 0) return false;
        return comunicadoDAO.atualizar(comunicado);
    }

    public boolean excluir(int idComunicado) {
        if (idComunicado <= 0) return false;
        return comunicadoDAO.excluir(idComunicado);
    }

    public boolean incrementarVisualizacao(int idComunicado) {
        return comunicadoDAO.incrementarVisualizacao(idComunicado);
    }

    public Comunicado buscarPorId(int idComunicado) {
        return comunicadoDAO.buscarPorId(idComunicado);
    }

    public List<Comunicado> listar() {
        return comunicadoDAO.listar();
    }

    public List<Comunicado> listarAtivos() {
        return comunicadoDAO.listarAtivos();
    }

}