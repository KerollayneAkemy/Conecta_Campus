package br.com.conectacampus.model;

import java.time.LocalDateTime;

public class Voto {

    private int idVoto;
    private LocalDateTime dataVoto;

    private Usuario usuario;
    private OpcaoEnquete opcaoEnquete;

    public Voto() {
    }

    public Voto(int idVoto, LocalDateTime dataVoto,
            Usuario usuario,
            OpcaoEnquete opcaoEnquete) {

        this.idVoto = idVoto;
        this.dataVoto = dataVoto;
        this.usuario = usuario;
        this.opcaoEnquete = opcaoEnquete;
    }

    public int getIdVoto() {
        return idVoto;
    }

    public void setIdVoto(int idVoto) {
        this.idVoto = idVoto;
    }

    public LocalDateTime getDataVoto() {
        return dataVoto;
    }

    public void setDataVoto(LocalDateTime dataVoto) {
        this.dataVoto = dataVoto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public OpcaoEnquete getOpcaoEnquete() {
        return opcaoEnquete;
    }

    public void setOpcaoEnquete(OpcaoEnquete opcaoEnquete) {
        this.opcaoEnquete = opcaoEnquete;
    }

    @Override
    public String toString() {
        return "Voto [idVoto=" + idVoto + "]";
    }

}