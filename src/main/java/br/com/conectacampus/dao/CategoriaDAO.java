package br.com.conectacampus.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.conectacampus.model.Categoria;

public class CategoriaDAO {

    // ==========================
    // LISTAR TODAS
    // ==========================
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

    // ==========================
    // BUSCAR POR ID
    // ==========================
    public Categoria buscarPorId(int id) {

        Categoria categoria = null;

        String sql = "SELECT id_categoria, nome FROM categorias WHERE id_categoria = ?";

        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    categoria = new Categoria();
                    categoria.setIdCategoria(rs.getInt("id_categoria"));
                    categoria.setNome(rs.getString("nome"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categoria;
    }

}