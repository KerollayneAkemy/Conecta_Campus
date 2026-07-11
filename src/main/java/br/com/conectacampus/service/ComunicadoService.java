package br.com.conectacampus.service;

import java.util.List;

import br.com.conectacampus.dao.ComunicadoDAO;
import br.com.conectacampus.model.Comunicado;

public class ComunicadoService {

    private ComunicadoDAO comunicadoDAO;

    public ComunicadoService() {
        comunicadoDAO = new ComunicadoDAO();
    }

    // ==========================
    // CADASTRAR
    // ==========================

    public boolean cadastrar(Comunicado comunicado) {

        if (comunicado == null)
            return false;

        if (comunicado.getTitulo() == null || comunicado.getTitulo().isBlank())
            return false;

        if (comunicado.getMensagem() == null || comunicado.getMensagem().isBlank())
            return false;

        if (comunicado.getUsuario() == null)
            return false;

        if (comunicado.getCategoria() == null)
            return false;

        return comunicadoDAO.inserir(comunicado);
    }

    // ==========================
    // ATUALIZAR
    // ==========================

    public boolean atualizar(Comunicado comunicado) {

        if (comunicado == null)
            return false;

        if (comunicado.getIdComunicado() <= 0)
            return false;

        return comunicadoDAO.atualizar(comunicado);
    }

    // ==========================
    // EXCLUIR
    // ==========================

    public boolean excluir(int idComunicado) {

        if (idComunicado <= 0)
            return false;

        return comunicadoDAO.excluir(idComunicado);
    }

    // ==========================
    // BUSCAR POR ID
    // ==========================

    public Comunicado buscarPorId(int idComunicado) {

        if (idComunicado <= 0)
            return null;

        return comunicadoDAO.buscarPorId(idComunicado);
    }

    // ==========================
    // LISTAR
    // ==========================

    public List<Comunicado> listar() {

        return comunicadoDAO.listar();
    }

    // ==========================
    // REGISTRAR VISUALIZAÇÃO
    // ==========================

    public void registrarVisualizacao(int idComunicado) {

        if (idComunicado > 0) {
            comunicadoDAO.incrementarVisualizacao(idComunicado);
        }

    }

}