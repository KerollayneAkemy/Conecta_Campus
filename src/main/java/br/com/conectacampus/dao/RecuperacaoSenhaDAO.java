package br.com.conectacampus.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RecuperacaoSenhaDAO {

	public boolean criarToken(int idUsuario, String tokenHash) {
		String removerAntigos = "DELETE FROM recuperacoes_senha WHERE id_usuario=?";
		String inserir = "INSERT INTO recuperacoes_senha (id_usuario, token_hash, expira_em) VALUES (?, ?, DATE_ADD(NOW(), INTERVAL 30 MINUTE))";

		Connection conexao = null;
		try {
			conexao = ConexaoFactory.getConnection();
			conexao.setAutoCommit(false);
			try (PreparedStatement limpar = conexao.prepareStatement(removerAntigos);
					PreparedStatement stmt = conexao.prepareStatement(inserir)) {
				limpar.setInt(1, idUsuario);
				limpar.executeUpdate();
				stmt.setInt(1, idUsuario);
				stmt.setString(2, tokenHash);
				stmt.executeUpdate();
			}
			conexao.commit();
			return true;
		} catch (SQLException e) {
			reverter(conexao);
			e.printStackTrace();
			return false;
		} finally {
			fechar(conexao);
		}
	}

	public boolean invalidarToken(String tokenHash) {
		String sql = "DELETE FROM recuperacoes_senha WHERE token_hash=?";
		try (Connection conexao = ConexaoFactory.getConnection(); PreparedStatement stmt = conexao.prepareStatement(sql)) {
			stmt.setString(1, tokenHash);
			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean redefinirSenha(String tokenHash, String senhaCriptografada) {
		String buscar = "SELECT id_recuperacao, id_usuario FROM recuperacoes_senha WHERE token_hash=? AND usado_em IS NULL AND expira_em >= NOW() FOR UPDATE";
		String atualizarSenha = "UPDATE usuarios SET senha=? WHERE id_usuario=?";
		String usarToken = "UPDATE recuperacoes_senha SET usado_em=NOW() WHERE id_recuperacao=?";

		Connection conexao = null;
		try {
			conexao = ConexaoFactory.getConnection();
			conexao.setAutoCommit(false);
			int idRecuperacao;
			int idUsuario;
			try (PreparedStatement stmt = conexao.prepareStatement(buscar)) {
				stmt.setString(1, tokenHash);
				try (ResultSet rs = stmt.executeQuery()) {
					if (!rs.next()) {
						conexao.rollback();
						return false;
					}
					idRecuperacao = rs.getInt("id_recuperacao");
					idUsuario = rs.getInt("id_usuario");
				}
			}
			try (PreparedStatement senha = conexao.prepareStatement(atualizarSenha);
					PreparedStatement token = conexao.prepareStatement(usarToken)) {
				senha.setString(1, senhaCriptografada);
				senha.setInt(2, idUsuario);
				senha.executeUpdate();
				token.setInt(1, idRecuperacao);
				token.executeUpdate();
			}
			conexao.commit();
			return true;
		} catch (SQLException e) {
			reverter(conexao);
			e.printStackTrace();
			return false;
		} finally {
			fechar(conexao);
		}
	}

	private void reverter(Connection conexao) {
		try { if (conexao != null) conexao.rollback(); } catch (SQLException ignored) { }
	}

	private void fechar(Connection conexao) {
		try { if (conexao != null) conexao.close(); } catch (SQLException ignored) { }
	}
}
