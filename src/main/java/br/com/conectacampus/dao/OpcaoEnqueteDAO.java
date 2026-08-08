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

    public boolean inserir(OpcaoEnquete opcao) {
        String sql = "INSERT INTO opcoes_enquete (descricao,id_enquete) VALUES (?,?)";
        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, opcao.getDescricao());
            stmt.setInt(2, opcao.getEnquete().getIdEnquete());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<OpcaoEnquete> listarPorEnquete(int idEnquete) {
        List<OpcaoEnquete> lista = new ArrayList<>();
        String sql = "SELECT * FROM opcoes_enquete WHERE id_enquete=? ORDER BY id_opcao";
        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idEnquete);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Enquete enquete = new Enquete();
                    enquete.setIdEnquete(idEnquete);
                    OpcaoEnquete opcao = new OpcaoEnquete();
                    opcao.setIdOpcao(rs.getInt("id_opcao"));
                    opcao.setDescricao(rs.getString("descricao"));
                    opcao.setEnquete(enquete);
                    lista.add(opcao);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
