package br.com.conectacampus.service;

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
