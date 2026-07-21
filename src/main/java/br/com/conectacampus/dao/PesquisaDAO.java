package br.com.conectacampus.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.conectacampus.model.Pesquisa;
import br.com.conectacampus.model.Usuario;

public class PesquisaDAO {
	public List<Pesquisa> listar() {
		
		List<Pesquisa> pesquisas = new ArrayList<>();
		String sql = "SELECT p.*, u.nome AS usuario_nome FROM pesquisas p JOIN usuarios u ON u.id_usuario=p.id_usuario ORDER BY p.status, p.data_limite IS NULL, p.data_limite";
		
		try (Connection conexao = ConexaoFactory.getConnection(); PreparedStatement stmt = conexao.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
		
			while (rs.next()) pesquisas.add(mapear(rs));
		
		} catch (SQLException e) { e.printStackTrace(); }
		
		return pesquisas;
	}

	public boolean inserir(Pesquisa pesquisa) {
		String sql = "INSERT INTO pesquisas (titulo, descricao, link_formulario, data_limite, status, id_usuario) VALUES (?, ?, ?, ?, 'ABERTA', ?)";
		
		try (Connection conexao = ConexaoFactory.getConnection(); PreparedStatement stmt = conexao.prepareStatement(sql)) {
			stmt.setString(1, pesquisa.getTitulo()); stmt.setString(2, pesquisa.getDescricao()); stmt.setString(3, pesquisa.getLinkFormulario());
			
			if (pesquisa.getDataLimite() == null) stmt.setNull(4, Types.DATE); else stmt.setDate(4, java.sql.Date.valueOf(pesquisa.getDataLimite()));
			stmt.setInt(5, pesquisa.getUsuario().getIdUsuario());
			
			return stmt.executeUpdate() > 0;
		
		} catch (SQLException e) { e.printStackTrace(); 
		
		return false; 
		
		}
	}

	public Pesquisa buscarPorId(int idPesquisa) {
		String sql = "SELECT p.*, u.nome AS usuario_nome FROM pesquisas p JOIN usuarios u ON u.id_usuario=p.id_usuario WHERE p.id_pesquisa=?";
		
		try (Connection conexao = ConexaoFactory.getConnection(); PreparedStatement stmt = conexao.prepareStatement(sql)) {
			stmt.setInt(1, idPesquisa);
			
			try (ResultSet rs = stmt.executeQuery()) {
				
				return rs.next() ? mapear(rs) : null;
			}
		} 
		catch (SQLException e) { e.printStackTrace();
		
		return null;
		
		}
	}

	public boolean atualizar(Pesquisa pesquisa) {
		String sql = "UPDATE pesquisas SET titulo=?, descricao=?, link_formulario=?, data_limite=?, status=? WHERE id_pesquisa=?";
		
		try (Connection conexao = ConexaoFactory.getConnection(); PreparedStatement stmt = conexao.prepareStatement(sql)) {
			stmt.setString(1, pesquisa.getTitulo()); stmt.setString(2, pesquisa.getDescricao()); stmt.setString(3, pesquisa.getLinkFormulario());
			
			if (pesquisa.getDataLimite() == null) 
			stmt.setNull(4, Types.DATE); else stmt.setDate(4, java.sql.Date.valueOf(pesquisa.getDataLimite()));
			stmt.setString(5, pesquisa.getStatus()); stmt.setInt(6, pesquisa.getIdPesquisa());
			
			return stmt.executeUpdate() > 0;
			
		} catch (SQLException e) { e.printStackTrace(); 
	
		return false;
		
		}
	}

	public boolean marcarComoRespondida(int idPesquisa, int idUsuario) {
		String sql = "INSERT IGNORE INTO pesquisa_respostas (id_pesquisa, id_usuario) VALUES (?, ?)";
		
		try (Connection conexao = ConexaoFactory.getConnection(); PreparedStatement stmt = conexao.prepareStatement(sql)) {
			stmt.setInt(1, idPesquisa);
			stmt.setInt(2, idUsuario);
			
			return stmt.executeUpdate() > 0;
		
		} catch (SQLException e) { e.printStackTrace(); 
		
		return false; }
	}

	public Set<Integer> listarRespondidasPorUsuario(int idUsuario) {
		Set<Integer> ids = new HashSet<>();
		String sql = "SELECT id_pesquisa FROM pesquisa_respostas WHERE id_usuario=?";
		
		try (Connection conexao = ConexaoFactory.getConnection(); PreparedStatement stmt = conexao.prepareStatement(sql)) {
			stmt.setInt(1, idUsuario);
		
			try (ResultSet rs = stmt.executeQuery()) { while (rs.next()) ids.add(rs.getInt("id_pesquisa")); }
		
		} catch (SQLException e) { e.printStackTrace(); }
		
		return ids;
	}

	private Pesquisa mapear(ResultSet rs) throws SQLException {
		Pesquisa pesquisa = new Pesquisa();
		pesquisa.setIdPesquisa(rs.getInt("id_pesquisa")); pesquisa.setTitulo(rs.getString("titulo")); pesquisa.setDescricao(rs.getString("descricao"));
		pesquisa.setLinkFormulario(rs.getString("link_formulario")); pesquisa.setStatus(rs.getString("status"));
		
		java.sql.Date data = rs.getDate("data_limite"); if (data != null) pesquisa.setDataLimite(data.toLocalDate());
		Usuario usuario = new Usuario(); usuario.setIdUsuario(rs.getInt("id_usuario")); usuario.setNome(rs.getString("usuario_nome")); pesquisa.setUsuario(usuario);
		
		return pesquisa;
	}
}
