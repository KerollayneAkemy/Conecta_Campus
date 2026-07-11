package br.com.conectacampus.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import br.com.conectacampus.model.Forum;
import br.com.conectacampus.model.Usuario;

public class ForumDAO {

    // ==========================
    // INSERIR
    // ==========================

    public boolean inserir(Forum forum){

        String sql="""
                INSERT INTO forum
                (titulo,mensagem,id_usuario)
                VALUES (?,?,?)
                """;

        try(Connection conn=ConexaoFactory.getConnection();
            PreparedStatement stmt=conn.prepareStatement(sql)){

            stmt.setString(1,forum.getTitulo());
            stmt.setString(2,forum.getMensagem());
            stmt.setInt(3,forum.getUsuario().getIdUsuario());

            return stmt.executeUpdate()>0;

        }catch(SQLException e){

            e.printStackTrace();

        }

        return false;

    }

    // ==========================
    // ATUALIZAR
    // ==========================

    public boolean atualizar(Forum forum){

        String sql="""
                UPDATE forum
                SET titulo=?,
                    mensagem=?
                WHERE id_forum=?
                """;

        try(Connection conn=ConexaoFactory.getConnection();
            PreparedStatement stmt=conn.prepareStatement(sql)){

            stmt.setString(1,forum.getTitulo());
            stmt.setString(2,forum.getMensagem());
            stmt.setInt(3,forum.getIdForum());

            return stmt.executeUpdate()>0;

        }catch(SQLException e){

            e.printStackTrace();

        }

        return false;

    }

    // ==========================
    // EXCLUIR
    // ==========================

    public boolean excluir(int id){

        String sql="DELETE FROM forum WHERE id_forum=?";

        try(Connection conn=ConexaoFactory.getConnection();
            PreparedStatement stmt=conn.prepareStatement(sql)){

            stmt.setInt(1,id);

            return stmt.executeUpdate()>0;

        }catch(SQLException e){

            e.printStackTrace();

        }

        return false;

    }

    // ==========================
    // BUSCAR POR ID
    // ==========================

    public Forum buscarPorId(int id){

        Forum forum=null;

        String sql="""
                SELECT f.*,
                       u.nome usuario
                FROM forum f
                INNER JOIN usuarios u
                ON f.id_usuario=u.id_usuario
                WHERE id_forum=?
                """;

        try(Connection conn=ConexaoFactory.getConnection();
            PreparedStatement stmt=conn.prepareStatement(sql)){

            stmt.setInt(1,id);

            ResultSet rs=stmt.executeQuery();

            if(rs.next()){

                Usuario usuario=new Usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setNome(rs.getString("usuario"));

                forum=new Forum();

                forum.setIdForum(rs.getInt("id_forum"));
                forum.setTitulo(rs.getString("titulo"));
                forum.setMensagem(rs.getString("mensagem"));
                forum.setVisualizacoes(rs.getInt("visualizacoes"));
                forum.setDataPostagem(rs.getTimestamp("data_postagem").toLocalDateTime());
                forum.setUsuario(usuario);

            }

        }catch(SQLException e){

            e.printStackTrace();

        }

        return forum;

    }

    // ==========================
    // LISTAR
    // ==========================

    public List<Forum> listar(){

        List<Forum> lista=new ArrayList<>();

        String sql="""
                SELECT f.*,
                       u.nome usuario
                FROM forum f
                INNER JOIN usuarios u
                ON f.id_usuario=u.id_usuario
                ORDER BY data_postagem DESC
                """;

        try(Connection conn=ConexaoFactory.getConnection();
            PreparedStatement stmt=conn.prepareStatement(sql);
            ResultSet rs=stmt.executeQuery()){

            while(rs.next()){

                Usuario usuario=new Usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setNome(rs.getString("usuario"));

                Forum forum=new Forum();

                forum.setIdForum(rs.getInt("id_forum"));
                forum.setTitulo(rs.getString("titulo"));
                forum.setMensagem(rs.getString("mensagem"));
                forum.setVisualizacoes(rs.getInt("visualizacoes"));
                forum.setDataPostagem(rs.getTimestamp("data_postagem").toLocalDateTime());
                forum.setUsuario(usuario);

                lista.add(forum);

            }

        }catch(SQLException e){

            e.printStackTrace();

        }

        return lista;

    }

    // ==========================
    // CONTAR VISUALIZAÇÕES
    // ==========================

    public void incrementarVisualizacao(int id){

        String sql="""
                UPDATE forum
                SET visualizacoes=visualizacoes+1
                WHERE id_forum=?
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