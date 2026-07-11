package br.com.conectacampus.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import br.com.conectacampus.model.Categoria;
import br.com.conectacampus.model.Comunicado;
import br.com.conectacampus.model.Usuario;

public class ComunicadoDAO {

    // ==========================
    // INSERIR
    // ==========================

    public boolean inserir(Comunicado comunicado) {

        String sql = """
                INSERT INTO comunicados
                (titulo,mensagem,prioridade,status,id_usuario,id_categoria)
                VALUES (?,?,?,?,?,?)
                """;

        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, comunicado.getTitulo());
            stmt.setString(2, comunicado.getMensagem());
            stmt.setString(3, comunicado.getPrioridade());
            stmt.setString(4, comunicado.getStatus());
            stmt.setInt(5, comunicado.getUsuario().getIdUsuario());
            stmt.setInt(6, comunicado.getCategoria().getIdCategoria());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;
    }

    // ==========================
    // ATUALIZAR
    // ==========================

    public boolean atualizar(Comunicado comunicado) {

        String sql = """
                UPDATE comunicados
                SET titulo=?,
                    mensagem=?,
                    prioridade=?,
                    status=?,
                    data_atualizacao=NOW(),
                    id_categoria=?
                WHERE id_comunicado=?
                """;

        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, comunicado.getTitulo());
            stmt.setString(2, comunicado.getMensagem());
            stmt.setString(3, comunicado.getPrioridade());
            stmt.setString(4, comunicado.getStatus());
            stmt.setInt(5, comunicado.getCategoria().getIdCategoria());
            stmt.setInt(6, comunicado.getIdComunicado());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;
    }

    // ==========================
    // EXCLUIR
    // ==========================

    public boolean excluir(int id) {

        String sql = "DELETE FROM comunicados WHERE id_comunicado=?";

        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;
    }

    // ==========================
    // BUSCAR POR ID
    // ==========================

    public Comunicado buscarPorId(int id) {

        Comunicado comunicado = null;

        String sql = """
            SELECT c.*,
                   u.nome AS usuario,
                   cat.nome AS categoria
            FROM comunicados c
            INNER JOIN usuarios u
            ON c.id_usuario=u.id_usuario
            INNER JOIN categorias cat
            ON c.id_categoria=cat.id_categoria
            WHERE c.id_comunicado=?
            """;

        try(Connection conn = ConexaoFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setInt(1,id);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()){

                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setNome(rs.getString("usuario"));

                Categoria categoria = new Categoria();
                categoria.setIdCategoria(rs.getInt("id_categoria"));
                categoria.setNome(rs.getString("categoria"));

                comunicado = new Comunicado();

                comunicado.setIdComunicado(rs.getInt("id_comunicado"));
                comunicado.setTitulo(rs.getString("titulo"));
                comunicado.setMensagem(rs.getString("mensagem"));
                comunicado.setPrioridade(rs.getString("prioridade"));
                comunicado.setStatus(rs.getString("status"));
                comunicado.setVisualizacoes(rs.getInt("visualizacoes"));
                comunicado.setUsuario(usuario);
                comunicado.setCategoria(categoria);

            }

        }catch(SQLException e){

            e.printStackTrace();

        }

        return comunicado;

    }

    // ==========================
    // LISTAR
    // ==========================

    public List<Comunicado> listar(){

        List<Comunicado> lista = new ArrayList<>();

        String sql = """
            SELECT c.*,
                   u.nome AS usuario,
                   cat.nome AS categoria
            FROM comunicados c
            INNER JOIN usuarios u
            ON c.id_usuario=u.id_usuario
            INNER JOIN categorias cat
            ON c.id_categoria=cat.id_categoria
            ORDER BY c.data_publicacao DESC
            """;

        try(Connection conn = ConexaoFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()){

            while(rs.next()){

                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setNome(rs.getString("usuario"));

                Categoria categoria = new Categoria();
                categoria.setIdCategoria(rs.getInt("id_categoria"));
                categoria.setNome(rs.getString("categoria"));

                Comunicado comunicado = new Comunicado();

                comunicado.setIdComunicado(rs.getInt("id_comunicado"));
                comunicado.setTitulo(rs.getString("titulo"));
                comunicado.setMensagem(rs.getString("mensagem"));
                comunicado.setPrioridade(rs.getString("prioridade"));
                comunicado.setStatus(rs.getString("status"));
                comunicado.setVisualizacoes(rs.getInt("visualizacoes"));
                comunicado.setUsuario(usuario);
                comunicado.setCategoria(categoria);

                lista.add(comunicado);

            }

        }catch(SQLException e){

            e.printStackTrace();

        }

        return lista;

    }

    // ==========================
    // CONTAR VISUALIZAÇÃO
    // ==========================

    public void incrementarVisualizacao(int id){

        String sql="""
                UPDATE comunicados
                SET visualizacoes=visualizacoes+1
                WHERE id_comunicado=?
                """;

        try(Connection conn=ConexaoFactory.getConnection();
            PreparedStatement stmt=conn.prepareStatement(sql)){

            stmt.setInt(1,id);

            stmt.executeUpdate();

        }catch(SQLException e){

            e.printStackTrace();

        }

    }

}