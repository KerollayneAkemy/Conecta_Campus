package br.com.conectacampus.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import br.com.conectacampus.model.Comunicado;
import br.com.conectacampus.model.Usuario;

public class ComunicadoDAO {

    // INSERIR
    public boolean inserir(Comunicado comunicado) {

        String sql = """
                INSERT INTO comunicados
                (titulo, mensagem, imagem, prioridade, status, id_usuario, id_categoria)
                VALUES (?,?,?,?,?,?,?)
                """;

        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, comunicado.getTitulo());
            stmt.setString(2, comunicado.getMensagem());
            stmt.setString(3, comunicado.getImagem());
            stmt.setString(4, comunicado.getPrioridade());
            stmt.setString(5, comunicado.getStatus() != null ? comunicado.getStatus() : "ATIVO");
            stmt.setInt(6, comunicado.getUsuario().getIdUsuario());
            stmt.setInt(7, comunicado.getIdCategoria());

            boolean sucesso = stmt.executeUpdate() > 0;

            if (sucesso) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        comunicado.setIdComunicado(rs.getInt(1));
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
    public boolean atualizar(Comunicado comunicado) {

        String sql = """
                UPDATE comunicados
                SET titulo=?,
                    mensagem=?,
                    imagem=?,
                    prioridade=?,
                    status=?,
                    id_categoria=?,
                    data_atualizacao=NOW()
                WHERE id_comunicado=?
                """;

        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, comunicado.getTitulo());
            stmt.setString(2, comunicado.getMensagem());
            stmt.setString(3, comunicado.getImagem());
            stmt.setString(4, comunicado.getPrioridade());
            stmt.setString(5, comunicado.getStatus());
            stmt.setInt(6, comunicado.getIdCategoria());
            stmt.setInt(7, comunicado.getIdComunicado());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // EXCLUIR
    public boolean excluir(int idComunicado) {

        String sql = "DELETE FROM comunicados WHERE id_comunicado=?";

        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idComunicado);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // BUSCAR POR ID
    public Comunicado buscarPorId(int idComunicado) {

        Comunicado comunicado = null;

        String sql = """
                SELECT c.*, u.nome AS usuario_nome, cat.nome AS categoria_nome
                FROM comunicados c
                INNER JOIN usuarios u
                    ON c.id_usuario = u.id_usuario
                INNER JOIN categorias cat ON c.id_categoria = cat.id_categoria
                WHERE c.id_comunicado = ?
                """;

        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idComunicado);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    comunicado = mapear(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return comunicado;
    }

    // LISTAR TODOS
    public List<Comunicado> listar() {

        List<Comunicado> lista = new ArrayList<>();

        String sql = """
                SELECT c.*, u.nome AS usuario_nome, cat.nome AS categoria_nome
                FROM comunicados c
                INNER JOIN usuarios u
                    ON c.id_usuario = u.id_usuario
                INNER JOIN categorias cat ON c.id_categoria = cat.id_categoria
                ORDER BY c.data_publicacao DESC
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

    // LISTAR ATIVOS
    public List<Comunicado> listarAtivos() {

        List<Comunicado> lista = new ArrayList<>();

        String sql = """
                SELECT c.*, u.nome AS usuario_nome, cat.nome AS categoria_nome
                FROM comunicados c
                INNER JOIN usuarios u
                    ON c.id_usuario = u.id_usuario
                INNER JOIN categorias cat ON c.id_categoria = cat.id_categoria
                WHERE c.status = 'ATIVO'
                ORDER BY c.data_publicacao DESC
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

    // MAPEAR RESULTSET -> COMUNICADO
    private Comunicado mapear(ResultSet rs) throws SQLException {

        Comunicado comunicado = new Comunicado();

        comunicado.setIdComunicado(rs.getInt("id_comunicado"));
        comunicado.setTitulo(rs.getString("titulo"));
        comunicado.setMensagem(rs.getString("mensagem"));
        comunicado.setImagem(rs.getString("imagem"));
        comunicado.setPrioridade(rs.getString("prioridade"));
        comunicado.setStatus(rs.getString("status"));
        comunicado.setVisualizacoes(rs.getInt("visualizacoes"));
        comunicado.setIdCategoria(rs.getInt("id_categoria"));
        comunicado.setNomeCategoria(rs.getString("categoria_nome"));

        Timestamp publicacao = rs.getTimestamp("data_publicacao");
        if (publicacao != null) {
            comunicado.setDataPublicacao(publicacao.toLocalDateTime());
        }

        Timestamp atualizacao = rs.getTimestamp("data_atualizacao");
        if (atualizacao != null) {
            comunicado.setDataAtualizacao(atualizacao.toLocalDateTime());
        }

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(rs.getInt("id_usuario"));
        usuario.setNome(rs.getString("usuario_nome"));
        comunicado.setUsuario(usuario);

        return comunicado;
    }

}
