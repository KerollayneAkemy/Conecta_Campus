package br.com.conectacampus.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.conectacampus.model.Enquete;
import br.com.conectacampus.model.OpcaoEnquete;

public class OpcaoEnqueteDAO {

    // INSERIR
    public boolean inserir(OpcaoEnquete opcao) {

        String sql = """
                INSERT INTO opcoes_enquete
                (descricao,id_enquete)
                VALUES (?,?)
                """;

        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, opcao.getDescricao());
            stmt.setInt(2, opcao.getEnquete().getIdEnquete());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // ATUALIZAR
    public boolean atualizar(OpcaoEnquete opcao) {

        String sql = """
                UPDATE opcoes_enquete
                SET descricao=?
                WHERE id_opcao=?
                """;

        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, opcao.getDescricao());
            stmt.setInt(2, opcao.getIdOpcao());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // EXCLUIR
    public boolean excluir(int idOpcao) {

        String sql = "DELETE FROM opcoes_enquete WHERE id_opcao=?";

        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idOpcao);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // BUSCAR POR ID
    public OpcaoEnquete buscarPorId(int idOpcao) {

        OpcaoEnquete opcao = null;

        String sql = """
                SELECT o.*, e.titulo
                FROM opcoes_enquete o
                INNER JOIN enquetes e
                ON o.id_enquete = e.id_enquete
                WHERE id_opcao=?
                """;

        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idOpcao);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Enquete enquete = new Enquete();
                enquete.setIdEnquete(rs.getInt("id_enquete"));
                enquete.setTitulo(rs.getString("titulo"));

                opcao = new OpcaoEnquete();

                opcao.setIdOpcao(rs.getInt("id_opcao"));
                opcao.setDescricao(rs.getString("descricao"));
                opcao.setEnquete(enquete);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return opcao;
    }

    // LISTAR TODAS
    public List<OpcaoEnquete> listar() {

        List<OpcaoEnquete> lista = new ArrayList<>();

        String sql = """
                SELECT o.*, e.titulo
                FROM opcoes_enquete o
                INNER JOIN enquetes e
                ON o.id_enquete = e.id_enquete
                ORDER BY o.id_opcao
                """;

        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Enquete enquete = new Enquete();
                enquete.setIdEnquete(rs.getInt("id_enquete"));
                enquete.setTitulo(rs.getString("titulo"));

                OpcaoEnquete opcao = new OpcaoEnquete();

                opcao.setIdOpcao(rs.getInt("id_opcao"));
                opcao.setDescricao(rs.getString("descricao"));
                opcao.setEnquete(enquete);

                lista.add(opcao);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // LISTAR POR ENQUETE
    public List<OpcaoEnquete> listarPorEnquete(int idEnquete) {

        List<OpcaoEnquete> lista = new ArrayList<>();

        String sql = """
                SELECT *
                FROM opcoes_enquete
                WHERE id_enquete=?
                ORDER BY id_opcao
                """;

        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEnquete);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Enquete enquete = new Enquete();
                enquete.setIdEnquete(idEnquete);

                OpcaoEnquete opcao = new OpcaoEnquete();

                opcao.setIdOpcao(rs.getInt("id_opcao"));
                opcao.setDescricao(rs.getString("descricao"));
                opcao.setEnquete(enquete);

                lista.add(opcao);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

}
