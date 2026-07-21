package br.com.conectacampus.model;

import java.time.LocalDateTime;

public class RespostaForum {

    private int idResposta;
    private String resposta;
    private LocalDateTime dataResposta;
    private boolean anonimo;

    private Usuario usuario;
    private Forum forum;

    public RespostaForum() {
    }

    public RespostaForum(int idResposta, String resposta, LocalDateTime dataResposta, Usuario usuario, Forum forum) {
        this.idResposta = idResposta;
        this.resposta = resposta;
        this.dataResposta = dataResposta;
        this.usuario = usuario;
        this.forum = forum;
    }

    public int getIdResposta() {
        return idResposta;
    }

    public void setIdResposta(int idResposta) {
        this.idResposta = idResposta;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public LocalDateTime getDataResposta() {
        return dataResposta;
    }

    public void setDataResposta(LocalDateTime dataResposta) {
        this.dataResposta = dataResposta;
    }

    public boolean isAnonimo() {
        return anonimo;
    }

    public void setAnonimo(boolean anonimo) {
        this.anonimo = anonimo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Forum getForum() {
        return forum;
    }

    public void setForum(Forum forum) {
        this.forum = forum;
    }

    @Override
    public String toString() {
        return "RespostaForum [idResposta=" + idResposta + "]";
    }

}
