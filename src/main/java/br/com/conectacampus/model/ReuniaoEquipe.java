package br.com.conectacampus.model;

import java.time.LocalDateTime;

public class ReuniaoEquipe {

    private int idReuniao;
    private String titulo;
    private String descricao;
    private LocalDateTime dataHora;
    private String localReuniao;
    private String status;
    private Usuario criador;

    public int getIdReuniao() { return idReuniao; }
    public void setIdReuniao(int idReuniao) { this.idReuniao = idReuniao; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
    public String getLocalReuniao() { return localReuniao; }
    public void setLocalReuniao(String localReuniao) { this.localReuniao = localReuniao; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Usuario getCriador() { return criador; }
    public void setCriador(Usuario criador) { this.criador = criador; }
}
