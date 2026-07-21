package br.com.conectacampus.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import br.com.conectacampus.model.Perfil;
import br.com.conectacampus.model.Cargo;
import br.com.conectacampus.model.Usuario;

public class UsuarioDAO {

	// INSERIR USUÁRIO
	public boolean inserir(Usuario usuario) {

		String sql = """
				INSERT INTO usuarios
				(nome,email,senha,curso,setor_institucional,email_institucional,ativo,id_perfil,id_cargo)
				VALUES (?,?,?,?,?,?,?,?,?)
				""";

		try (Connection conn = ConexaoFactory.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, usuario.getNome());
			stmt.setString(2, usuario.getEmail());
			stmt.setString(3, usuario.getSenha());
			stmt.setString(4, usuario.getCurso());
			stmt.setString(5, usuario.getSetorInstitucional());
			stmt.setString(6, usuario.getEmailInstitucional());
			stmt.setBoolean(7, usuario.isAtivo());
			stmt.setInt(8, usuario.getPerfil().getIdPerfil());

			if (usuario.getCargo() != null && usuario.getCargo().getIdCargo() > 0) {
				stmt.setInt(9, usuario.getCargo().getIdCargo());

			} else {
				stmt.setNull(9, java.sql.Types.INTEGER);
			}

			return stmt.executeUpdate() > 0;

		} catch (SQLException e) {

			e.printStackTrace();

		}

		return false;

	}

	// ATUALIZAR
	public boolean atualizar(Usuario usuario) {

		String sql = """
				UPDATE usuarios
				SET nome=?,
				    email=?,
				    senha=?,
				    curso=?,
				    setor_institucional=?,
				    email_institucional=?,
				    ativo=?,
				    id_perfil=?,
				    id_cargo=?
				WHERE id_usuario=?
				""";

		try (Connection conn = ConexaoFactory.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, usuario.getNome());
			stmt.setString(2, usuario.getEmail());
			stmt.setString(3, usuario.getSenha());
			stmt.setString(4, usuario.getCurso());
			stmt.setString(5, usuario.getSetorInstitucional());
			stmt.setString(6, usuario.getEmailInstitucional());
			stmt.setBoolean(7, usuario.isAtivo());
			stmt.setInt(8, usuario.getPerfil().getIdPerfil());

			if (usuario.getCargo() != null && usuario.getCargo().getIdCargo() > 0) {
				stmt.setInt(9, usuario.getCargo().getIdCargo());

			} else {
				stmt.setNull(9, java.sql.Types.INTEGER);
			}

			stmt.setInt(10, usuario.getIdUsuario());

			return stmt.executeUpdate() > 0;

		} catch (SQLException e) {

			e.printStackTrace();

		}

		return false;

	}

	public boolean atualizarAtivo(int idUsuario, boolean ativo) {
		String sql = "UPDATE usuarios SET ativo=? WHERE id_usuario=?";

		try (Connection conn = ConexaoFactory.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setBoolean(1, ativo);
			stmt.setInt(2, idUsuario);

			return stmt.executeUpdate() > 0;

		} catch (SQLException e) {
			e.printStackTrace();

			return false;
		}
	}

	// BUSCAR POR ID
	public Usuario buscarPorId(int id) {

		Usuario usuario = null;

		String sql = """
				SELECT u.*, p.nome AS perfil
				FROM usuarios u
				INNER JOIN perfis p
				    ON u.id_perfil = p.id_perfil
				WHERE u.id_usuario = ?
				""";

		try (Connection conn = ConexaoFactory.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, id);

			try (ResultSet rs = stmt.executeQuery()) {

				if (rs.next()) {

					Perfil perfil = new Perfil();
					perfil.setIdPerfil(rs.getInt("id_perfil"));
					perfil.setNome(rs.getString("perfil"));

					usuario = new Usuario();

					usuario.setIdUsuario(rs.getInt("id_usuario"));
					usuario.setNome(rs.getString("nome"));
					usuario.setEmail(rs.getString("email"));
					usuario.setSenha(rs.getString("senha"));
					usuario.setCurso(rs.getString("curso"));
					usuario.setSetorInstitucional(rs.getString("setor_institucional"));
					usuario.setEmailInstitucional(rs.getString("email_institucional"));
					usuario.setFotoPerfil(rs.getString("foto_perfil"));
					usuario.setCargo(mapearCargo(rs));
					usuario.setAtivo(rs.getBoolean("ativo"));

					Timestamp ultimo = rs.getTimestamp("ultimo_acesso");

					if (ultimo != null) {
						usuario.setUltimoAcesso(ultimo.toLocalDateTime());
					}

					Timestamp cadastro = rs.getTimestamp("data_cadastro");

					if (cadastro != null) {
						usuario.setDataCadastro(cadastro.toLocalDateTime());
					}

					usuario.setPerfil(perfil);

				}

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		return usuario;

	}

	// BUSCAR POR EMAIL
	public Usuario buscarPorEmail(String email) {

		Usuario usuario = null;

		String sql = """
				SELECT u.*, p.nome AS perfil
				FROM usuarios u
				INNER JOIN perfis p
				    ON u.id_perfil = p.id_perfil
				WHERE u.email = ?
				""";

		try (Connection conn = ConexaoFactory.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, email);

			try (ResultSet rs = stmt.executeQuery()) {

				if (rs.next()) {

					Perfil perfil = new Perfil();
					perfil.setIdPerfil(rs.getInt("id_perfil"));
					perfil.setNome(rs.getString("perfil"));

					usuario = new Usuario();

					usuario.setIdUsuario(rs.getInt("id_usuario"));
					usuario.setNome(rs.getString("nome"));
					usuario.setEmail(rs.getString("email"));
					usuario.setSenha(rs.getString("senha"));
					usuario.setCurso(rs.getString("curso"));
					usuario.setSetorInstitucional(rs.getString("setor_institucional"));
					usuario.setEmailInstitucional(rs.getString("email_institucional"));
					usuario.setFotoPerfil(rs.getString("foto_perfil"));
					usuario.setCargo(mapearCargo(rs));
					usuario.setAtivo(rs.getBoolean("ativo"));

					Timestamp ultimo = rs.getTimestamp("ultimo_acesso");

					if (ultimo != null) {
						usuario.setUltimoAcesso(ultimo.toLocalDateTime());
					}

					Timestamp cadastro = rs.getTimestamp("data_cadastro");

					if (cadastro != null) {
						usuario.setDataCadastro(cadastro.toLocalDateTime());
					}

					usuario.setPerfil(perfil);

				}

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		return usuario;

	}

	// LISTAR TODOS
	public List<Usuario> listar() {

		List<Usuario> lista = new ArrayList<>();

		String sql = """
				SELECT u.*, p.nome AS perfil
				FROM usuarios u
				INNER JOIN perfis p
				    ON u.id_perfil = p.id_perfil
				ORDER BY u.nome
				""";

		try (Connection conn = ConexaoFactory.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {

				Perfil perfil = new Perfil();
				perfil.setIdPerfil(rs.getInt("id_perfil"));
				perfil.setNome(rs.getString("perfil"));

				Usuario usuario = new Usuario();

				usuario.setIdUsuario(rs.getInt("id_usuario"));
				usuario.setNome(rs.getString("nome"));
				usuario.setEmail(rs.getString("email"));
				usuario.setSenha(rs.getString("senha"));
				usuario.setCurso(rs.getString("curso"));
				usuario.setSetorInstitucional(rs.getString("setor_institucional"));
				usuario.setEmailInstitucional(rs.getString("email_institucional"));
				usuario.setFotoPerfil(rs.getString("foto_perfil"));
				usuario.setCargo(mapearCargo(rs));
				usuario.setAtivo(rs.getBoolean("ativo"));

				Timestamp ultimo = rs.getTimestamp("ultimo_acesso");

				if (ultimo != null) {
					usuario.setUltimoAcesso(ultimo.toLocalDateTime());
				}

				Timestamp cadastro = rs.getTimestamp("data_cadastro");

				if (cadastro != null) {
					usuario.setDataCadastro(cadastro.toLocalDateTime());
				}

				usuario.setPerfil(perfil);

				lista.add(usuario);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;

	}

	// QUANTIDADE DE USUÁRIOS
	public int quantidadeUsuarios() {

		String sql = "SELECT COUNT(*) FROM usuarios";

		try (Connection conn = ConexaoFactory.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			if (rs.next()) {
				return rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;

	}

	//ATUALIZAR ÚLTIMO ACESSO
	public boolean atualizarUltimoAcesso(int idUsuario) {

		String sql = """
				UPDATE usuarios
				SET ultimo_acesso = NOW()
				WHERE id_usuario = ?
				""";

		try (Connection conn = ConexaoFactory.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, idUsuario);

			return stmt.executeUpdate() > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean atualizarPerfil(Usuario usuario) {

		String sql = """
				UPDATE usuarios
				SET nome=?, email=?, curso=?, foto_perfil=?
				WHERE id_usuario=?
				""";

		try (Connection conn = ConexaoFactory.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, usuario.getNome());
			stmt.setString(2, usuario.getEmail());
			stmt.setString(3, usuario.getCurso());
			stmt.setString(4, usuario.getFotoPerfil());
			stmt.setInt(5, usuario.getIdUsuario());

			return stmt.executeUpdate() > 0;

		} catch (SQLException e) {
			e.printStackTrace();

			return false;
		}
	}

	public boolean atualizarSenha(int idUsuario, String senhaCriptografada) {
		String sql = "UPDATE usuarios SET senha=? WHERE id_usuario=?";

		try (Connection conn = ConexaoFactory.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, senhaCriptografada);
			stmt.setInt(2, idUsuario);

			return stmt.executeUpdate() > 0;

		} catch (SQLException e) {
			e.printStackTrace();

			return false;
		}
	}

	private Cargo mapearCargo(ResultSet rs) throws SQLException {
		int idCargo = rs.getInt("id_cargo");

		if (rs.wasNull()) 
			return null;

		Cargo cargo = new Cargo();
		cargo.setIdCargo(idCargo);

		return cargo;
	}
}
