package br.com.conectacampus.service;

import java.util.List;
import java.util.Map;

import br.com.conectacampus.dao.VotoDAO;
import br.com.conectacampus.model.Voto;

public class VotoService {

	private final VotoDAO dao;

	public VotoService() {
	    dao = new VotoDAO();
	}
	
    public boolean votar(Voto voto) {
        return dao.inserir(voto);
    }

    public boolean excluir(int id) {
        return dao.excluir(id);
    }

    public Voto buscarPorId(int id) {
        return dao.buscarPorId(id);
    }

    public List<Voto> listar() {
        return dao.listar();
    }

    public boolean usuarioJaVotou(int idUsuario, int idOpcao) {
        return dao.usuarioJaVotou(idUsuario, idOpcao);
    }

    public boolean removerVoto(int idUsuario, int idEnquete) {
        return idUsuario > 0 && idEnquete > 0 && dao.removerPorUsuarioEnquete(idUsuario, idEnquete);
    }

    public boolean usuarioJaVotouNaEnquete(int idUsuario, int idEnquete) {
        return dao.usuarioJaVotouNaEnquete(idUsuario, idEnquete);
    }

    public Map<Integer, Integer> contarVotosPorOpcao(int idEnquete) {
        return dao.contarVotosPorOpcao(idEnquete);
    }

}
