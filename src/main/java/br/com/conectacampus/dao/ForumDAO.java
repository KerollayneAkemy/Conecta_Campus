package br.com.conectacampus.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import br.com.conectacampus.model.Forum;
import br.com.conectacampus.model.Usuario;

public class ForumDAO {

	// INSERIR

	public boolean inserir(Forum forum){

		String sql="""
				INSERT INTO forum
				(titulo,mensagem,tipo_interacao,id_usuario)
				VALUES (?,?,?,?)
				""";

		try(Connection conn=ConexaoFactory.getConnection();
				PreparedStatement stmt=conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

			stmt.setString(1,forum.getTitulo());
			stmt.setString(2,forum.getMensagem());
			stmt.setString(3,forum.getTipoInteracao());
			stmt.setInt(4,forum.getUsuario().getIdUsuario());

			boolean inseriu = stmt.executeUpdate() > 0;
			if (inseriu) {
				try (ResultSet chaves = stmt.getGeneratedKeys()) {
					if (chaves.next()) {
						forum.setIdForum(chaves.getInt(1));
					}
				}
			}
			return inseriu;

		}catch(SQLException e){

			e.printStackTrace();

		}

		return false;

	}

	// ATUALIZAR

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

	// EXCLUIR

	public boolean excluir(int id) {
		String excluirVotos = "DELETE v FROM votos v JOIN enquetes e ON e.id_enquete=v.id_enquete WHERE e.id_forum=?";
		String excluirOpcoes = "DELETE o FROM opcoes_enquete o JOIN enquetes e ON e.id_enquete=o.id_enquete WHERE e.id_forum=?";
		String excluirEnquetes = "DELETE FROM enquetes WHERE id_forum=?";
		String excluirRespostas = "DELETE FROM respostas_forum WHERE id_forum=?";
		String excluirForum = "DELETE FROM forum WHERE id_forum=?";

		try (Connection conexao = ConexaoFactory.getConnection()) {
			conexao.setAutoCommit(false);
			executarExclusao(conexao, excluirVotos, id);
			executarExclusao(conexao, excluirOpcoes, id);
			executarExclusao(conexao, excluirEnquetes, id);
			executarExclusao(conexao, excluirRespostas, id);
			boolean excluiu = executarExclusao(conexao, excluirForum, id) > 0;
			conexao.commit();
			
			return excluiu;
			
		} catch (SQLException e) {
			e.printStackTrace();
			
			return false;
		}
	}

	private int executarExclusao(Connection conexao, String sql, int idForum) throws SQLException {
		try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
			stmt.setInt(1, idForum);
			return stmt.executeUpdate();
		}
	}

	// BUSCAR POR ID

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
				forum.setTipoInteracao(rs.getString("tipo_interacao"));
				forum.setUsuario(usuario);

			}

		}catch(SQLException e){

			e.printStackTrace();

		}

		return forum;

	}

	// LISTAR

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
				forum.setTipoInteracao(rs.getString("tipo_interacao"));
				forum.setUsuario(usuario);

				lista.add(forum);

			}

		}catch(SQLException e){

			e.printStackTrace();

		}

		return lista;

	}

	// CONTAR VISUALIZAÇÕES

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
