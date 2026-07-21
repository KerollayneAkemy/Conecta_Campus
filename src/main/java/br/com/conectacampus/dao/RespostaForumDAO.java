package br.com.conectacampus.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.conectacampus.model.Forum;
import br.com.conectacampus.model.RespostaForum;
import br.com.conectacampus.model.Usuario;

public class RespostaForumDAO {

    // INSERIR
    public boolean inserir(RespostaForum resposta) {

        String sql = """
                INSERT INTO respostas_forum
                (resposta,anonimo,id_usuario,id_forum)
                VALUES (?,?,?,?)
                """;

        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, resposta.getResposta());
            stmt.setBoolean(2, resposta.isAnonimo());
            stmt.setInt(3, resposta.getUsuario().getIdUsuario());
            stmt.setInt(4, resposta.getForum().getIdForum());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;

    }

    // ATUALIZAR
    public boolean atualizar(RespostaForum resposta) {

        String sql = """
                UPDATE respostas_forum
                SET resposta=?
                WHERE id_resposta=?
                """;

        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, resposta.getResposta());
            stmt.setInt(2, resposta.getIdResposta());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;

    }

    // EXCLUIR
    public boolean excluir(int idResposta) {

        String sql = "DELETE FROM respostas_forum WHERE id_resposta=?";

        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idResposta);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;

    }

    // BUSCAR POR ID
    public RespostaForum buscarPorId(int idResposta) {

        RespostaForum resposta = null;

        String sql = """
                SELECT r.*,
                       u.nome AS usuario,
                       f.titulo AS forum
                FROM respostas_forum r
                INNER JOIN usuarios u
                    ON r.id_usuario = u.id_usuario
                INNER JOIN forum f
                    ON r.id_forum = f.id_forum
                WHERE r.id_resposta = ?
                """;

        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idResposta);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setNome(rs.getString("usuario"));

                Forum forum = new Forum();
                forum.setIdForum(rs.getInt("id_forum"));
                forum.setTitulo(rs.getString("forum"));

                resposta = new RespostaForum();

                resposta.setIdResposta(rs.getInt("id_resposta"));
                resposta.setResposta(rs.getString("resposta"));
                resposta.setDataResposta(rs.getTimestamp("data_resposta").toLocalDateTime());
                resposta.setAnonimo(rs.getBoolean("anonimo"));
                resposta.setUsuario(usuario);
                resposta.setForum(forum);

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return resposta;

    }

    // LISTAR TODAS
    public List<RespostaForum> listar() {

        List<RespostaForum> lista = new ArrayList<>();

        String sql = """
                SELECT r.*,
                       u.nome AS usuario,
                       f.titulo AS forum
                FROM respostas_forum r
                INNER JOIN usuarios u
                    ON r.id_usuario = u.id_usuario
                INNER JOIN forum f
                    ON r.id_forum = f.id_forum
                ORDER BY r.data_resposta ASC
                """;

        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setNome(rs.getString("usuario"));

                Forum forum = new Forum();
                forum.setIdForum(rs.getInt("id_forum"));
                forum.setTitulo(rs.getString("forum"));

                RespostaForum resposta = new RespostaForum();

                resposta.setIdResposta(rs.getInt("id_resposta"));
                resposta.setResposta(rs.getString("resposta"));
                resposta.setDataResposta(rs.getTimestamp("data_resposta").toLocalDateTime());
                resposta.setAnonimo(rs.getBoolean("anonimo"));
                resposta.setUsuario(usuario);
                resposta.setForum(forum);

                lista.add(resposta);

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return lista;

    }

    // LISTAR POR FÓRUM
    public List<RespostaForum> listarPorForum(int idForum) {

        List<RespostaForum> lista = new ArrayList<>();

        String sql = """
                SELECT r.*,
                       u.nome AS usuario
                FROM respostas_forum r
                INNER JOIN usuarios u
                    ON r.id_usuario = u.id_usuario
                WHERE r.id_forum = ?
                ORDER BY r.data_resposta ASC
                """;

        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idForum);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setNome(rs.getString("usuario"));

                Forum forum = new Forum();
                forum.setIdForum(idForum);

                RespostaForum resposta = new RespostaForum();

                resposta.setIdResposta(rs.getInt("id_resposta"));
                resposta.setResposta(rs.getString("resposta"));
                resposta.setDataResposta(rs.getTimestamp("data_resposta").toLocalDateTime());
                resposta.setAnonimo(rs.getBoolean("anonimo"));
                resposta.setUsuario(usuario);
                resposta.setForum(forum);

                lista.add(resposta);

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return lista;

    }

}
