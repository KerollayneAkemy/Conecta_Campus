package br.com.conectacampus.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import br.com.conectacampus.dao.EquipeInternaDAO;
import br.com.conectacampus.model.OpcaoVotacaoEquipe;
import br.com.conectacampus.model.ReuniaoEquipe;
import br.com.conectacampus.model.VotacaoEquipe;

public class EquipeInternaService {

	private final EquipeInternaDAO dao = new EquipeInternaDAO();

	public List<ReuniaoEquipe> listarReunioes() {
		return dao.listarReunioes(); 	
	}

	public List<VotacaoEquipe> listarVotacoes() {
		return dao.listarVotacoes(); 
	}

	public Set<Integer> listarVotacoesDoUsuario(int idUsuario) { 
		return dao.listarVotacoesDoUsuario(idUsuario);
	}

	public boolean cadastrarReuniao(ReuniaoEquipe reuniao) {
		return reuniao != null && textoValido(reuniao.getTitulo()) && reuniao.getDataHora() != null && reuniao.getCriador() != null && dao.inserirReuniao(reuniao);
	}

	public boolean cadastrarVotacao(VotacaoEquipe votacao, String opcoesTexto) {

		if (votacao == null || !textoValido(votacao.getTitulo()) || votacao.getCriador() == null) return false;
		List<OpcaoVotacaoEquipe> opcoes = new ArrayList<>();

		for (String linha : opcoesTexto == null ? new String[0] : opcoesTexto.split("\\R")) {
			if (!linha.isBlank()) { OpcaoVotacaoEquipe opcao = new OpcaoVotacaoEquipe(); opcao.setDescricao(linha.trim()); opcoes.add(opcao); }
		}

		if (opcoes.size() < 2)
			return false;

		votacao.setOpcoes(opcoes);
		return dao.inserirVotacao(votacao);
	}

	public boolean votar(int idVotacao, int idOpcao, int idUsuario) { 
		return idVotacao > 0 && idOpcao > 0 && idUsuario > 0 && dao.votar(idVotacao, idOpcao, idUsuario); 
	}

	public boolean atualizarStatusReuniao(int idReuniao, String status) {
		return idReuniao > 0 && statusReuniaoValido(status) && dao.atualizarStatusReuniao(idReuniao, status); 
	}

	public boolean atualizarStatusVotacao(int idVotacao, String status) { 
		return idVotacao > 0 && ("ABERTA".equals(status) || "ENCERRADA".equals(status)) && dao.atualizarStatusVotacao(idVotacao, status);
	}

	public boolean excluirReuniao(int idReuniao) {
		return idReuniao > 0 && dao.excluirReuniao(idReuniao);
	}

	public boolean excluirVotacao(int idVotacao) { 
		return idVotacao > 0 && dao.excluirVotacao(idVotacao);
	}

	private boolean textoValido(String texto) {
		return texto != null && !texto.isBlank();
	}

	private boolean statusReuniaoValido(String status) {
		return "AGENDADA".equals(status) || "REALIZADA".equals(status) || "CANCELADA".equals(status);
	}
}
