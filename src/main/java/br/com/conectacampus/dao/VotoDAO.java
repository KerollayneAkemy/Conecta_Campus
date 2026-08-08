package br.com.conectacampus.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import br.com.conectacampus.model.Voto;

public class VotoDAO {

    public boolean inserir(Voto voto) {
        String sql = """
                INSERT INTO votos (id_usuario,id_opcao,id_enquete)
                SELECT ?, o.id_opcao, o.id_enquete
                FROM opcoes_enquete o
                WHERE o.id_opcao=?
                """;
        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, voto.getUsuario().getIdUsuario());
            stmt.setInt(2, voto.getOpcaoEnquete().getIdOpcao());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removerPorUsuarioEnquete(int idUsuario, int idEnquete) {
        String sql = "DELETE FROM votos WHERE id_usuario=? AND id_enquete=?";
        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idEnquete);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean usuarioJaVotouNaEnquete(int idUsuario, int idEnquete) {
        String sql = "SELECT 1 FROM votos WHERE id_usuario=? AND id_enquete=?";
        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idEnquete);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Map<Integer, Integer> contarVotosPorOpcao(int idEnquete) {
        Map<Integer, Integer> totais = new LinkedHashMap<>();
        String sql = """
                SELECT o.id_opcao, COUNT(v.id_voto) AS total
                FROM opcoes_enquete o
                LEFT JOIN votos v ON v.id_opcao = o.id_opcao
                WHERE o.id_enquete=?
                GROUP BY o.id_opcao
                """;
        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idEnquete);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) totais.put(rs.getInt("id_opcao"), rs.getInt("total"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totais;
    }
}
