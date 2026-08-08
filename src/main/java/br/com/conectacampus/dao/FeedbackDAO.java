package br.com.conectacampus.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.conectacampus.model.Feedback;
import br.com.conectacampus.model.Usuario;

public class FeedbackDAO {

    // INSERIR
    public boolean inserir(Feedback feedback) {

        String sql = """
                INSERT INTO feedbacks
                (assunto,tipo,mensagem,anonimo,id_usuario)
                VALUES (?,?,?,?,?)
                """;

        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, feedback.getAssunto());
            stmt.setString(2, feedback.getTipo());
            stmt.setString(3, feedback.getMensagem());
            stmt.setBoolean(4, feedback.isAnonimo());

            if (feedback.getUsuario() != null)
                stmt.setInt(5, feedback.getUsuario().getIdUsuario());
            else
                stmt.setNull(5, java.sql.Types.INTEGER);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // EXCLUIR
    public boolean excluir(int idFeedback) {

        String sql = "DELETE FROM feedbacks WHERE id_feedback=?";

        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idFeedback);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // BUSCAR POR ID
    public Feedback buscarPorId(int idFeedback) {

        Feedback feedback = null;

        String sql = """
                SELECT f.*, u.nome usuario
                FROM feedbacks f
                LEFT JOIN usuarios u
                ON f.id_usuario = u.id_usuario
                WHERE id_feedback=?
                """;

        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idFeedback);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                feedback = new Feedback();

                feedback.setIdFeedback(rs.getInt("id_feedback"));
                feedback.setAssunto(rs.getString("assunto"));
                feedback.setTipo(rs.getString("tipo"));
                feedback.setMensagem(rs.getString("mensagem"));
                feedback.setAnonimo(rs.getBoolean("anonimo"));
                feedback.setDataEnvio(rs.getTimestamp("data_envio").toLocalDateTime());

                if (rs.getObject("id_usuario") != null) {

                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(rs.getInt("id_usuario"));
                    usuario.setNome(rs.getString("usuario"));

                    feedback.setUsuario(usuario);

                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return feedback;
    }

    // LISTAR
    public List<Feedback> listar() {

        List<Feedback> lista = new ArrayList<>();

        String sql = """
                SELECT f.*, u.nome usuario
                FROM feedbacks f
                LEFT JOIN usuarios u
                ON f.id_usuario = u.id_usuario
                ORDER BY data_envio DESC
                """;

        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Feedback feedback = new Feedback();

                feedback.setIdFeedback(rs.getInt("id_feedback"));
                feedback.setAssunto(rs.getString("assunto"));
                feedback.setTipo(rs.getString("tipo"));
                feedback.setMensagem(rs.getString("mensagem"));
                feedback.setAnonimo(rs.getBoolean("anonimo"));
                feedback.setDataEnvio(rs.getTimestamp("data_envio").toLocalDateTime());

                if (rs.getObject("id_usuario") != null) {

                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(rs.getInt("id_usuario"));
                    usuario.setNome(rs.getString("usuario"));

                    feedback.setUsuario(usuario);

                }

                lista.add(feedback);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

}
