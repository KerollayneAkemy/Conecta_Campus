package br.com.conectacampus.service;

import java.util.List;
import java.util.Set;
import java.util.Map;
import br.com.conectacampus.dao.PesquisaDAO;
import br.com.conectacampus.model.Pesquisa;

public class PesquisaService {
	private final PesquisaDAO pesquisaDAO = new PesquisaDAO();
	
	public List<Pesquisa> listar() {
		
		return pesquisaDAO.listar();
		
	}
	
	public boolean cadastrar(Pesquisa pesquisa) {
		
		return pesquisa != null && pesquisa.getTitulo() != null && !pesquisa.getTitulo().isBlank()
				&& pesquisa.getLinkFormulario() != null && pesquisa.getLinkFormulario().startsWith("http")
				&& pesquisa.getUsuario() != null && pesquisaDAO.inserir(pesquisa);
	}
	
	public Pesquisa buscarPorId(int idPesquisa) {
		return pesquisaDAO.buscarPorId(idPesquisa);
		}
	
	public boolean atualizar(Pesquisa pesquisa) { 
		return pesquisa != null && pesquisa.getIdPesquisa() > 0 && pesquisaDAO.atualizar(pesquisa);
		
	}
	
	public boolean marcarComoRespondida(int idPesquisa, int idUsuario) {
		return idPesquisa > 0 && idUsuario > 0 && pesquisaDAO.marcarComoRespondida(idPesquisa, idUsuario);
		
	}
	
	public Set<Integer> listarRespondidasPorUsuario(int idUsuario) {
		return pesquisaDAO.listarRespondidasPorUsuario(idUsuario);
	}
	public Map<Integer, Integer> contarRespondidasPorPesquisa() { return pesquisaDAO.contarRespondidasPorPesquisa(); }
}
