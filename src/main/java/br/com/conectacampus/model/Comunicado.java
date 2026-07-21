package br.com.conectacampus.model;

import java.time.LocalDateTime;

public class Comunicado {

    private int idComunicado;
    private String titulo;
    private String mensagem;
    private String prioridade; // BAIXA, MEDIA, ALTA
    private String status;     // ATIVO, INATIVO
    private int visualizacoes;
    private LocalDateTime dataPublicacao;
    private LocalDateTime dataAtualizacao;
    private Usuario usuario;
    private int idCategoria;
    private String nomeCategoria;
    private String imagem;

    public Comunicado() {
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

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    public String getImagem() { 
    	return imagem; 
    	}
    
    public void setImagem(String imagem) {
    	this.imagem = imagem; 
    	}
}
