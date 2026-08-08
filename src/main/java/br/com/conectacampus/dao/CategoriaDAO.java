package br.com.conectacampus.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.conectacampus.model.Categoria;

public class CategoriaDAO {

	public boolean inserir(Categoria categoria) {
		String sql = "INSERT INTO categorias (nome) VALUES (?)";
		
		try (Connection conn = ConexaoFactory.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, categoria.getNome());
			
			return stmt.executeUpdate() > 0;
		
		} catch (SQLException e) {
			e.printStackTrace();
			
			return false;
		}
	}

	public boolean excluir(int idCategoria) {
		String sql = "DELETE FROM categorias WHERE id_categoria=?";
		
		try (Connection conn = ConexaoFactory.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, idCategoria);
		
			return stmt.executeUpdate() > 0;
		
		} catch (SQLException e) {
			e.printStackTrace();
			
			return false;
		}
	}

	// LISTAR TODAS
	public List<Categoria> listar() {

		List<Categoria> lista = new ArrayList<>();

		String sql = "SELECT id_categoria, nome FROM categorias ORDER BY nome";

		try (Connection conn = ConexaoFactory.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				Categoria categoria = new Categoria();
				categoria.setIdCategoria(rs.getInt("id_categoria"));
				categoria.setNome(rs.getString("nome"));
				lista.add(categoria);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

}
