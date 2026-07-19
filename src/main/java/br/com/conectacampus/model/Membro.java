package br.com.conectacampus.model;

public class Membro {

    private int idMembro;
    private String nome;
    private String email;
    private String telefone;
    private Cargo cargo;

    public Membro() {
    }

    public Membro(int idMembro, String nome, String email, String telefone, Cargo cargo) {
        this.idMembro = idMembro;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.cargo = cargo;
    }

    public int getIdMembro() {
        return idMembro;
    }

    public void setIdMembro(int idMembro) {
        this.idMembro = idMembro;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }
}