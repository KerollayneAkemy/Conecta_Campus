package br.com.conectacampus.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import br.com.conectacampus.model.Enquete;

public class EnqueteDAO {

    private final OpcaoEnqueteDAO opcaoEnqueteDAO = new OpcaoEnqueteDAO();

    // ==========================
    // INSERIR
    // ==========================
    public boolean inserir(Enquete enquete) {

        String sql = """
                INSERT INTO enquetes
                (titulo,descricao,data_inicio,data_fim,status,id_usuario,id_forum)
                VALUES (?,?,?,?,?,?,?)
                """;

        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, enquete.getTitulo());
            stmt.setString(2, enquete.getDescricao());
            stmt.setDate(3, Date.valueOf(enquete.getDataInicio()));
            
            if(enquete.getDataFim()!=null)
                stmt.setDate(4, Date.valueOf(enquete.getDataFim()));
            else
                stmt.setNull(4, Types.DATE);

            stmt.setString(5, enquete.getStatus());
            stmt.setInt(6, enquete.getUsuario().getIdUsuario());
            if (enquete.getIdForum() > 0) {
                stmt.setInt(7, enquete.getIdForum());
            } else {
                stmt.setNull(7, Types.INTEGER);
            }

            int linhas = stmt.executeUpdate();

            if (linhas > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        enquete.setIdEnquete(rs.getInt(1));
                    }
                }
                return true;
            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;

    }

    // ==========================
    // ATUALIZAR
    // ==========================
    public boolean atualizar(Enquete enquete) {

        String sql = """
                UPDATE enquetes
                SET titulo=?,
                    descricao=?,
                    data_inicio=?,
                    data_fim=?,
                    status=?
                WHERE id_enquete=?
                """;

        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, enquete.getTitulo());
            stmt.setString(2, enquete.getDescricao());
            stmt.setDate(3, Date.valueOf(enquete.getDataInicio()));

            if(enquete.getDataFim()!=null)
                stmt.setDate(4, Date.valueOf(enquete.getDataFim()));
            else
                stmt.setNull(4, Types.DATE);

            stmt.setString(5, enquete.getStatus());
            stmt.setInt(6, enquete.getIdEnquete());

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

        String sql = "DELETE FROM enquetes WHERE id_enquete=?";

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
    public Enquete buscarPorId(int id){

        Enquete enquete=null;

        String sql="SELECT * FROM enquetes WHERE id_enquete=?";

        try(Connection conn=ConexaoFactory.getConnection();
            PreparedStatement stmt=conn.prepareStatement(sql)){

            stmt.setInt(1,id);

            ResultSet rs=stmt.executeQuery();

            if(rs.next()){

                enquete=new Enquete();

                enquete.setIdEnquete(rs.getInt("id_enquete"));
                enquete.setTitulo(rs.getString("titulo"));
                enquete.setDescricao(rs.getString("descricao"));
                enquete.setDataInicio(rs.getDate("data_inicio").toLocalDate());

                if(rs.getDate("data_fim")!=null)
                    enquete.setDataFim(rs.getDate("data_fim").toLocalDate());

                enquete.setStatus(rs.getString("status"));
                enquete.setIdForum(rs.getInt("id_forum"));
                enquete.setOpcoes(opcaoEnqueteDAO.listarPorEnquete(enquete.getIdEnquete()));

            }

        }catch(SQLException e){

            e.printStackTrace();

        }

        return enquete;

    }

    // ==========================
    // LISTAR
    // ==========================
    public List<Enquete> listar(){

        List<Enquete> lista=new ArrayList<>();

        String sql="""
                SELECT *
                FROM enquetes
                ORDER BY data_inicio DESC
                """;

        try(Connection conn=ConexaoFactory.getConnection();
            PreparedStatement stmt=conn.prepareStatement(sql);
            ResultSet rs=stmt.executeQuery()){

            while(rs.next()){

                Enquete enquete=new Enquete();

                enquete.setIdEnquete(rs.getInt("id_enquete"));
                enquete.setTitulo(rs.getString("titulo"));
                enquete.setDescricao(rs.getString("descricao"));
                enquete.setDataInicio(rs.getDate("data_inicio").toLocalDate());

                if(rs.getDate("data_fim")!=null)
                    enquete.setDataFim(rs.getDate("data_fim").toLocalDate());

                enquete.setStatus(rs.getString("status"));
                enquete.setIdForum(rs.getInt("id_forum"));
                enquete.setOpcoes(opcaoEnqueteDAO.listarPorEnquete(enquete.getIdEnquete()));

                lista.add(enquete);

            }

        }catch(SQLException e){

            e.printStackTrace();

        }

        return lista;

    }

    public Enquete buscarPorForum(int idForum) {
        String sql = "SELECT id_enquete FROM enquetes WHERE id_forum=? ORDER BY id_enquete DESC LIMIT 1";
        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idForum);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? buscarPorId(rs.getInt(1)) : null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
