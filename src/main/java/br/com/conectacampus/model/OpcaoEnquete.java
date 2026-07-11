package br.com.conectacampus.model;

public class OpcaoEnquete {

    private int idOpcao;
    private String descricao;

    private Enquete enquete;

    public OpcaoEnquete() {
    }

    public OpcaoEnquete(int idOpcao, String descricao, Enquete enquete) {
        this.idOpcao = idOpcao;
        this.descricao = descricao;
        this.enquete = enquete;
    }

    public int getIdOpcao() {
        return idOpcao;
    }

    public void setIdOpcao(int idOpcao) {
        this.idOpcao = idOpcao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Enquete getEnquete() {
        return enquete;
    }

    public void setEnquete(Enquete enquete) {
        this.enquete = enquete;
    }

    @Override
    public String toString() {
        return "OpcaoEnquete [idOpcao=" + idOpcao + ", descricao=" + descricao + "]";
    }

}