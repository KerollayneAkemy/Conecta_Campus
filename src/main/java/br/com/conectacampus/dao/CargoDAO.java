package br.com.conectacampus.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.conectacampus.model.Cargo;

public class CargoDAO {

    // INSERIR CARGO
    public boolean inserir(Cargo cargo) {

        String sql = """
                INSERT INTO cargo
                (nome, descricao)
                VALUES (?,?)
                """;

        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, cargo.getNome());
            stmt.setString(2, cargo.getDescricao());

            boolean sucesso = stmt.executeUpdate() > 0;

            if (sucesso) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        cargo.setIdCargo(rs.getInt(1));
                    }
                }
            }

            return sucesso;

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;

    }

    // ATUALIZAR
    public boolean atualizar(Cargo cargo) {

        String sql = """
                UPDATE cargo
                SET nome=?,
                    descricao=?
                WHERE id_cargo=?
                """;

        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cargo.getNome());
            stmt.setString(2, cargo.getDescricao());
            stmt.setInt(3, cargo.getIdCargo());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;

    }

    // EXCLUIR
    public boolean excluir(int id) {

        String sql = "DELETE FROM cargo WHERE id_cargo=?";

        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;

    }

    // BUSCAR POR ID
    public Cargo buscarPorId(int id) {

        Cargo cargo = null;

        String sql = """
                SELECT id_cargo, nome, descricao
                FROM cargo
                WHERE id_cargo = ?
                """;

        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    cargo = new Cargo();

                    cargo.setIdCargo(rs.getInt("id_cargo"));
                    cargo.setNome(rs.getString("nome"));
                    cargo.setDescricao(rs.getString("descricao"));

                }

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return cargo;

    }

    // LISTAR TODOS
    public List<Cargo> listar() {

        List<Cargo> lista = new ArrayList<>();

        String sql = """
                SELECT id_cargo, nome, descricao
                FROM cargo
                ORDER BY nome
                """;

        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Cargo cargo = new Cargo();

                cargo.setIdCargo(rs.getInt("id_cargo"));
                cargo.setNome(rs.getString("nome"));
                cargo.setDescricao(rs.getString("descricao"));

                lista.add(cargo);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;

    }

    // QUANTIDADE DE CARGOS
    public int quantidadeCargos() {

        String sql = "SELECT COUNT(*) FROM cargo";

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

}