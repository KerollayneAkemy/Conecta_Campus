package br.com.conectacampus.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.conectacampus.model.Comentario;
import br.com.conectacampus.model.Comunicado;
import br.com.conectacampus.model.Usuario;

public class ComentarioDAO {

    // INSERIR
    public boolean inserir(Comentario comentario) {

        String sql = """
                INSERT INTO comentarios
                (mensagem,id_usuario,id_comunicado)
                VALUES (?,?,?)
                """;

        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, comentario.getMensagem());
            stmt.setInt(2, comentario.getUsuario().getIdUsuario());
            stmt.setInt(3, comentario.getComunicado().getIdComunicado());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;
    }

    // ATUALIZAR
    public boolean atualizar(Comentario comentario) {

        String sql = """
                UPDATE comentarios
                SET mensagem=?
                WHERE id_comentario=?
                """;

        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, comentario.getMensagem());
            stmt.setInt(2, comentario.getIdComentario());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;
    }

    // EXCLUIR
    public boolean excluir(int idComentario) {

        String sql = "DELETE FROM comentarios WHERE id_comentario=?";

        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idComentario);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;
    }

    // BUSCAR POR ID
    public Comentario buscarPorId(int idComentario) {

        Comentario comentario = null;

        String sql = """
                SELECT c.*,
                       u.nome AS usuario,
                       co.titulo AS comunicado
                FROM comentarios c
                INNER JOIN usuarios u
                    ON c.id_usuario=u.id_usuario
                INNER JOIN comunicados co
                    ON c.id_comunicado=co.id_comunicado
                WHERE c.id_comentario=?
                """;

        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idComentario);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setNome(rs.getString("usuario"));

                Comunicado comunicado = new Comunicado();
                comunicado.setIdComunicado(rs.getInt("id_comunicado"));
                comunicado.setTitulo(rs.getString("comunicado"));

                comentario = new Comentario();

                comentario.setIdComentario(rs.getInt("id_comentario"));
                comentario.setMensagem(rs.getString("mensagem"));
                comentario.setDataComentario(rs.getTimestamp("data_comentario").toLocalDateTime());
                comentario.setUsuario(usuario);
                comentario.setComunicado(comunicado);

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return comentario;

    }

    // LISTAR TODOS
    public List<Comentario> listar() {

        List<Comentario> lista = new ArrayList<>();

        String sql = """
                SELECT c.*,
                       u.nome AS usuario,
                       co.titulo AS comunicado
                FROM comentarios c
                INNER JOIN usuarios u
                    ON c.id_usuario=u.id_usuario
                INNER JOIN comunicados co
                    ON c.id_comunicado=co.id_comunicado
                ORDER BY c.data_comentario DESC
                """;

        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setNome(rs.getString("usuario"));

                Comunicado comunicado = new Comunicado();
                comunicado.setIdComunicado(rs.getInt("id_comunicado"));
                comunicado.setTitulo(rs.getString("comunicado"));

                Comentario comentario = new Comentario();

                comentario.setIdComentario(rs.getInt("id_comentario"));
                comentario.setMensagem(rs.getString("mensagem"));
                comentario.setDataComentario(rs.getTimestamp("data_comentario").toLocalDateTime());
                comentario.setUsuario(usuario);
                comentario.setComunicado(comunicado);

                lista.add(comentario);

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return lista;

    }

}
