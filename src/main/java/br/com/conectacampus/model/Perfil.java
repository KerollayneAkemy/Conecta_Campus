package br.com.conectacampus.model;

public class Perfil {

    private int idPerfil;
    private String nome;

    public Perfil() {
    }

    public Perfil(int idPerfil, String nome) {
        this.idPerfil = idPerfil;
        this.nome = nome;
    }

    public int getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(int idPerfil) {
        this.idPerfil = idPerfil;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Perfil [idPerfil=" + idPerfil + ", nome=" + nome + "]";
    }
}