package br.com.conectacampus.model;

public class OpcaoVotacaoEquipe {

    private int idOpcao;
    private String descricao;
    private int totalVotos;

    public int getIdOpcao() { return idOpcao; }
    public void setIdOpcao(int idOpcao) { this.idOpcao = idOpcao; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public int getTotalVotos() { return totalVotos; }
    public void setTotalVotos(int totalVotos) { this.totalVotos = totalVotos; }
}
