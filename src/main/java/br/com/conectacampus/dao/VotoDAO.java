package br.com.conectacampus.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.conectacampus.model.OpcaoEnquete;
import br.com.conectacampus.model.Usuario;
import br.com.conectacampus.model.Voto;

public class VotoDAO {

    // ==========================
    // INSERIR VOTO
    // ==========================
    public boolean inserir(Voto voto) {

        String sql = """
                INSERT INTO votos
                (id_usuario,id_opcao,id_enquete)
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
        }

        return false;
    }

    // ==========================
    // EXCLUIR
    // ==========================
    public boolean excluir(int idVoto) {

        String sql = "DELETE FROM votos WHERE id_voto=?";

        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idVoto);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // ==========================
    // BUSCAR POR ID
    // ==========================
    public Voto buscarPorId(int idVoto) {

        Voto voto = null;

        String sql = """
                SELECT v.*,
                       u.nome usuario,
                       o.descricao opcao
                FROM votos v
                INNER JOIN usuarios u
                    ON v.id_usuario=u.id_usuario
                INNER JOIN opcoes_enquete o
                    ON v.id_opcao=o.id_opcao
                WHERE id_voto=?
                """;

        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idVoto);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setNome(rs.getString("usuario"));

                OpcaoEnquete opcao = new OpcaoEnquete();
                opcao.setIdOpcao(rs.getInt("id_opcao"));
                opcao.setDescricao(rs.getString("opcao"));

                voto = new Voto();

                voto.setIdVoto(rs.getInt("id_voto"));
                voto.setDataVoto(rs.getTimestamp("data_voto").toLocalDateTime());
                voto.setUsuario(usuario);
                voto.setOpcaoEnquete(opcao);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return voto;
    }

    // ==========================
    // LISTAR TODOS
    // ==========================
    public List<Voto> listar() {

        List<Voto> lista = new ArrayList<>();

        String sql = """
                SELECT v.*,
                       u.nome usuario,
                       o.descricao opcao
                FROM votos v
                INNER JOIN usuarios u
                    ON v.id_usuario=u.id_usuario
                INNER JOIN opcoes_enquete o
                    ON v.id_opcao=o.id_opcao
                ORDER BY data_voto DESC
                """;

        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setNome(rs.getString("usuario"));

                OpcaoEnquete opcao = new OpcaoEnquete();
                opcao.setIdOpcao(rs.getInt("id_opcao"));
                opcao.setDescricao(rs.getString("opcao"));

                Voto voto = new Voto();

                voto.setIdVoto(rs.getInt("id_voto"));
                voto.setDataVoto(rs.getTimestamp("data_voto").toLocalDateTime());
                voto.setUsuario(usuario);
                voto.setOpcaoEnquete(opcao);

                lista.add(voto);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // ==========================
    // VERIFICAR SE JÁ VOTOU
    // ==========================
    public boolean usuarioJaVotou(int idUsuario, int idOpcao) {

        String sql = """
                SELECT *
                FROM votos
                WHERE id_usuario=? AND id_opcao=?
                """;

        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idOpcao);

            ResultSet rs = stmt.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}
