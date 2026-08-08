package br.com.conectacampus.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.conectacampus.model.Cargo;
import br.com.conectacampus.model.Membro;

public class MembroDAO {

    // INSERIR MEMBRO
    public boolean inserir(Membro membro) {

        String sql = """
                INSERT INTO membro
                (nome, email, telefone, id_cargo)
                VALUES (?,?,?,?)
                """;

        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, membro.getNome());
            stmt.setString(2, membro.getEmail());
            stmt.setString(3, membro.getTelefone());

            if (membro.getCargo() != null && membro.getCargo().getIdCargo() > 0) {
                stmt.setInt(4, membro.getCargo().getIdCargo());
            } else {
                stmt.setNull(4, java.sql.Types.INTEGER);
            }

            boolean sucesso = stmt.executeUpdate() > 0;

            if (sucesso) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        membro.setIdMembro(rs.getInt(1));
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
    public boolean atualizar(Membro membro) {

        String sql = """
                UPDATE membro
                SET nome=?,
                    email=?,
                    telefone=?,
                    id_cargo=?
                WHERE id_membro=?
                """;

        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, membro.getNome());
            stmt.setString(2, membro.getEmail());
            stmt.setString(3, membro.getTelefone());

            if (membro.getCargo() != null && membro.getCargo().getIdCargo() > 0) {
                stmt.setInt(4, membro.getCargo().getIdCargo());
            } else {
                stmt.setNull(4, java.sql.Types.INTEGER);
            }

            stmt.setInt(5, membro.getIdMembro());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;

    }

    // EXCLUIR
    public boolean excluir(int id) {

        String sql = "DELETE FROM membro WHERE id_membro=?";

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
    public Membro buscarPorId(int id) {

        Membro membro = null;

        String sql = """
                SELECT m.*, c.nome AS cargo_nome
                FROM membro m
                LEFT JOIN cargo c
                    ON m.id_cargo = c.id_cargo
                WHERE m.id_membro = ?
                """;

        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    membro = mapear(rs);
                }

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return membro;

    }

    // LISTAR TODOS
    public List<Membro> listar() {

        List<Membro> lista = new ArrayList<>();

        String sql = """
                SELECT m.*, c.nome AS cargo_nome
                FROM membro m
                LEFT JOIN cargo c
                    ON m.id_cargo = c.id_cargo
                ORDER BY m.nome
                """;

        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(mapear(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;

    }

    // MAPEAR RESULTSET -> MEMBRO
    private Membro mapear(ResultSet rs) throws SQLException {

        Membro membro = new Membro();

        membro.setIdMembro(rs.getInt("id_membro"));
        membro.setNome(rs.getString("nome"));
        membro.setEmail(rs.getString("email"));
        membro.setTelefone(rs.getString("telefone"));

        int idCargo = rs.getInt("id_cargo");
       
        if (!rs.wasNull()) {
            Cargo cargo = new Cargo();
            cargo.setIdCargo(idCargo);
            cargo.setNome(rs.getString("cargo_nome"));
            membro.setCargo(cargo);
        }

        return membro;

    }

}
