package br.com.conectacampus.model;

import java.time.LocalDateTime;

public class Forum {

    private int idForum;
    private String titulo;
    private String mensagem;
    private int visualizacoes;
    private LocalDateTime dataPostagem;

    private Usuario usuario;

    public Forum() {
    }

    public Forum(int idForum, String titulo, String mensagem,
            int visualizacoes,
            LocalDateTime dataPostagem,
            Usuario usuario) {

        this.idForum = idForum;
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.visualizacoes = visualizacoes;
        this.dataPostagem = dataPostagem;
        this.usuario = usuario;
    }

    public int getIdForum() {
        return idForum;
    }

    public void setIdForum(int idForum) {
        this.idForum = idForum;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public int getVisualizacoes() {
        return visualizacoes;
    }

    public void setVisualizacoes(int visualizacoes) {
        this.visualizacoes = visualizacoes;
    }

    public LocalDateTime getDataPostagem() {
        return dataPostagem;
    }

    public void setDataPostagem(LocalDateTime dataPostagem) {
        this.dataPostagem = dataPostagem;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Forum [idForum=" + idForum + ", titulo=" + titulo + "]";
    }

}