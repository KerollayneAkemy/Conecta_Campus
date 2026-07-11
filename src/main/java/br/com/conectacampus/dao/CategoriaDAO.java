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
    // INSERIR
    // ==========================
    public boolean inserir(Categoria categoria) {

        String sql = "INSERT INTO categorias(nome, descricao) VALUES (?, ?)";

        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, categoria.getNome());
            stmt.setString(2, categoria.getDescricao());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // ==========================
    // ATUALIZAR
    // ==========================
    public boolean atualizar(Categoria categoria) {

        String sql = "UPDATE categorias SET nome=?, descricao=? WHERE id_categoria=?";

        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, categoria.getNome());
            stmt.setString(2, categoria.getDescricao());
            stmt.setInt(3, categoria.getIdCategoria());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // ==========================
    // EXCLUIR
    // ==========================
    public boolean excluir(int idCategoria) {

        String sql = "DELETE FROM categorias WHERE id_categoria=?";

        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCategoria);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // ==========================
    // BUSCAR POR ID
    // ==========================
    public Categoria buscarPorId(int idCategoria) {

        Categoria categoria = null;

        String sql = "SELECT * FROM categorias WHERE id_categoria=?";

        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCategoria);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                categoria = new Categoria();

                categoria.setIdCategoria(rs.getInt("id_categoria"));
                categoria.setNome(rs.getString("nome"));
                categoria.setDescricao(rs.getString("descricao"));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categoria;
    }

    // ==========================
    // LISTAR
    // ==========================
    public List<Categoria> listar() {

        List<Categoria> lista = new ArrayList<>();

        String sql = "SELECT * FROM categorias ORDER BY nome";

        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Categoria categoria = new Categoria();

                categoria.setIdCategoria(rs.getInt("id_categoria"));
                categoria.setNome(rs.getString("nome"));
                categoria.setDescricao(rs.getString("descricao"));

                lista.add(categoria);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

}