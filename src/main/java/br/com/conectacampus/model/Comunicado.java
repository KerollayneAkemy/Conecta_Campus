package br.com.conectacampus.model;

import java.time.LocalDateTime;

public class Comunicado {

    private int idComunicado;
    private String titulo;
    private String mensagem;
    private String prioridade;
    private String status;
    private int visualizacoes;
    private LocalDateTime dataPublicacao;
    private LocalDateTime dataAtualizacao;

    private Usuario usuario;
    private Categoria categoria;

    public Comunicado() {
    }

    public Comunicado(int idComunicado, String titulo, String mensagem,
            String prioridade, String status, int visualizacoes,
            LocalDateTime dataPublicacao,
            LocalDateTime dataAtualizacao,
            Usuario usuario, Categoria categoria) {

        this.idComunicado = idComunicado;
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.prioridade = prioridade;
        this.status = status;
        this.visualizacoes = visualizacoes;
        this.dataPublicacao = dataPublicacao;
        this.dataAtualizacao = dataAtualizacao;
        this.usuario = usuario;
        this.categoria = categoria;
    }

    public int getIdComunicado() {
        return idComunicado;
    }

    public void setIdComunicado(int idComunicado) {
        this.idComunicado = idComunicado;
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

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getVisualizacoes() {
        return visualizacoes;
    }

    public void setVisualizacoes(int visualizacoes) {
        this.visualizacoes = visualizacoes;
    }

    public LocalDateTime getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(LocalDateTime dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Comunicado [idComunicado=" + idComunicado + ", titulo=" + titulo + "]";
    }

}