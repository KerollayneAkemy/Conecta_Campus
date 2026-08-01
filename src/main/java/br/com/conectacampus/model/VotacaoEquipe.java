package br.com.conectacampus.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VotacaoEquipe {

    private int idVotacao;
    private String titulo;
    private String descricao;
    private String status;
    private LocalDateTime dataLimite;
    private Usuario criador;
    private List<OpcaoVotacaoEquipe> opcoes = new ArrayList<>();

    public int getIdVotacao() { return idVotacao; }
    public void setIdVotacao(int idVotacao) { this.idVotacao = idVotacao; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getDataLimite() { return dataLimite; }
    public void setDataLimite(LocalDateTime dataLimite) { this.dataLimite = dataLimite; }
    public Usuario getCriador() { return criador; }
    public void setCriador(Usuario criador) { this.criador = criador; }
    public List<OpcaoVotacaoEquipe> getOpcoes() { return opcoes; }
    public void setOpcoes(List<OpcaoVotacaoEquipe> opcoes) { this.opcoes = opcoes; }
}
