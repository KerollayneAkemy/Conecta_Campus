package br.com.conectacampus.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.conectacampus.model.OpcaoVotacaoEquipe;
import br.com.conectacampus.model.ReuniaoEquipe;
import br.com.conectacampus.model.Usuario;
import br.com.conectacampus.model.VotacaoEquipe;

public class EquipeInternaDAO {

    public List<ReuniaoEquipe> listarReunioes() {
        List<ReuniaoEquipe> reunioes = new ArrayList<>();
        String sql = "SELECT r.*, u.nome AS criador_nome FROM reunioes_equipe r JOIN usuarios u ON u.id_usuario = r.id_usuario ORDER BY r.data_hora";
        try (Connection conexao = ConexaoFactory.getConnection(); PreparedStatement stmt = conexao.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) reunioes.add(mapearReuniao(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return reunioes;
    }

    public boolean inserirReuniao(ReuniaoEquipe reuniao) {
        String sql = "INSERT INTO reunioes_equipe (titulo, descricao, data_hora, local_reuniao, status, id_usuario) VALUES (?, ?, ?, ?, 'AGENDADA', ?)";
        try (Connection conexao = ConexaoFactory.getConnection(); PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, reuniao.getTitulo());
            stmt.setString(2, reuniao.getDescricao());
            stmt.setTimestamp(3, Timestamp.valueOf(reuniao.getDataHora()));
            stmt.setString(4, reuniao.getLocalReuniao());
            stmt.setInt(5, reuniao.getCriador().getIdUsuario());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean atualizarStatusReuniao(int idReuniao, String status) {
        String sql = "UPDATE reunioes_equipe SET status=? WHERE id_reuniao=?";
        try (Connection conexao = ConexaoFactory.getConnection(); PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, idReuniao);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean excluirReuniao(int idReuniao) {
        return executarExclusao("DELETE FROM reunioes_equipe WHERE id_reuniao=?", idReuniao);
    }

    public List<VotacaoEquipe> listarVotacoes() {
        Map<Integer, VotacaoEquipe> votacoes = new HashMap<>();
        String sql = "SELECT v.*, u.nome AS criador_nome, o.id_opcao_votacao, o.descricao AS opcao_descricao, COUNT(vv.id_voto_equipe) AS total_votos "
                + "FROM votacoes_equipe v JOIN usuarios u ON u.id_usuario=v.id_usuario "
                + "LEFT JOIN opcoes_votacao_equipe o ON o.id_votacao_equipe=v.id_votacao_equipe "
                + "LEFT JOIN votos_votacao_equipe vv ON vv.id_opcao_votacao=o.id_opcao_votacao "
                + "GROUP BY v.id_votacao_equipe, o.id_opcao_votacao ORDER BY v.status, v.data_criacao DESC";
        try (Connection conexao = ConexaoFactory.getConnection(); PreparedStatement stmt = conexao.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id_votacao_equipe");
                VotacaoEquipe votacao = votacoes.get(id);
                if (votacao == null) { votacao = mapearVotacao(rs); votacoes.put(id, votacao); }
                if (rs.getObject("id_opcao_votacao") != null) {
                    OpcaoVotacaoEquipe opcao = new OpcaoVotacaoEquipe();
                    opcao.setIdOpcao(rs.getInt("id_opcao_votacao"));
                    opcao.setDescricao(rs.getString("opcao_descricao"));
                    opcao.setTotalVotos(rs.getInt("total_votos"));
                    votacao.getOpcoes().add(opcao);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return new ArrayList<>(votacoes.values());
    }

    public boolean inserirVotacao(VotacaoEquipe votacao) {
        String sql = "INSERT INTO votacoes_equipe (titulo, descricao, data_limite, id_usuario) VALUES (?, ?, ?, ?)";
        Connection conexao = null;
        try {
            conexao = ConexaoFactory.getConnection();
            conexao.setAutoCommit(false);
            try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, votacao.getTitulo()); stmt.setString(2, votacao.getDescricao());
                if (votacao.getDataLimite() == null) stmt.setNull(3, Types.TIMESTAMP); else stmt.setTimestamp(3, Timestamp.valueOf(votacao.getDataLimite()));
                stmt.setInt(4, votacao.getCriador().getIdUsuario());
                stmt.executeUpdate();
                try (ResultSet chaves = stmt.getGeneratedKeys()) {
                    if (!chaves.next()) { conexao.rollback(); return false; }
                    inserirOpcoes(conexao, chaves.getInt(1), votacao.getOpcoes());
                }
            }
            conexao.commit();
            return true;
        } catch (SQLException e) {
            try { if (conexao != null) conexao.rollback(); } catch (SQLException ignored) { }
            e.printStackTrace(); return false;
        } finally { try { if (conexao != null) conexao.close(); } catch (SQLException ignored) { } }
    }

    public boolean votar(int idVotacao, int idOpcao, int idUsuario) {
        String sql = "INSERT INTO votos_votacao_equipe (id_votacao_equipe, id_opcao_votacao, id_usuario) "
                + "SELECT ?, o.id_opcao_votacao, ? FROM opcoes_votacao_equipe o JOIN votacoes_equipe v ON v.id_votacao_equipe=o.id_votacao_equipe "
                + "WHERE o.id_opcao_votacao=? AND o.id_votacao_equipe=? AND v.status='ABERTA' AND (v.data_limite IS NULL OR v.data_limite >= NOW())";
        try (Connection conexao = ConexaoFactory.getConnection(); PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idVotacao); stmt.setInt(2, idUsuario); stmt.setInt(3, idOpcao); stmt.setInt(4, idVotacao);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { return false; }
    }

    public Set<Integer> listarVotacoesDoUsuario(int idUsuario) {
        Set<Integer> ids = new HashSet<>();
        String sql = "SELECT id_votacao_equipe FROM votos_votacao_equipe WHERE id_usuario=?";
        try (Connection conexao = ConexaoFactory.getConnection(); PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            try (ResultSet rs = stmt.executeQuery()) { while (rs.next()) ids.add(rs.getInt(1)); }
        } catch (SQLException e) { e.printStackTrace(); }
        return ids;
    }

    public boolean atualizarStatusVotacao(int idVotacao, String status) {
        String sql = "UPDATE votacoes_equipe SET status=? WHERE id_votacao_equipe=?";
        try (Connection conexao = ConexaoFactory.getConnection(); PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, status); stmt.setInt(2, idVotacao);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean excluirVotacao(int idVotacao) {
        return executarExclusao("DELETE FROM votacoes_equipe WHERE id_votacao_equipe=?", idVotacao);
    }

    private void inserirOpcoes(Connection conexao, int idVotacao, List<OpcaoVotacaoEquipe> opcoes) throws SQLException {
        String sql = "INSERT INTO opcoes_votacao_equipe (descricao, id_votacao_equipe) VALUES (?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            for (OpcaoVotacaoEquipe opcao : opcoes) { stmt.setString(1, opcao.getDescricao()); stmt.setInt(2, idVotacao); stmt.addBatch(); }
            stmt.executeBatch();
        }
    }

    private boolean executarExclusao(String sql, int id) {
        try (Connection conexao = ConexaoFactory.getConnection(); PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id); return stmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    private ReuniaoEquipe mapearReuniao(ResultSet rs) throws SQLException {
        ReuniaoEquipe reuniao = new ReuniaoEquipe();
        reuniao.setIdReuniao(rs.getInt("id_reuniao")); reuniao.setTitulo(rs.getString("titulo")); reuniao.setDescricao(rs.getString("descricao"));
        reuniao.setDataHora(rs.getTimestamp("data_hora").toLocalDateTime()); reuniao.setLocalReuniao(rs.getString("local_reuniao")); reuniao.setStatus(rs.getString("status"));
        reuniao.setCriador(criador(rs)); return reuniao;
    }

    private VotacaoEquipe mapearVotacao(ResultSet rs) throws SQLException {
        VotacaoEquipe votacao = new VotacaoEquipe();
        votacao.setIdVotacao(rs.getInt("id_votacao_equipe")); votacao.setTitulo(rs.getString("titulo")); votacao.setDescricao(rs.getString("descricao"));
        votacao.setStatus(rs.getString("status")); Timestamp data = rs.getTimestamp("data_limite"); if (data != null) votacao.setDataLimite(data.toLocalDateTime());
        votacao.setCriador(criador(rs)); return votacao;
    }

    private Usuario criador(ResultSet rs) throws SQLException {
        Usuario criador = new Usuario(); criador.setIdUsuario(rs.getInt("id_usuario")); criador.setNome(rs.getString("criador_nome")); return criador;
    }
}
