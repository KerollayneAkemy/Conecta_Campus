package br.com.conectacampus.model;

import java.time.LocalDateTime;

public class Usuario {

    private int idUsuario;
    private String nome;
    private String email;
    private String senha;
    private String curso;
    private boolean ativo;
    private LocalDateTime ultimoAcesso;
    private LocalDateTime dataCadastro;
    private Perfil perfil;

    public Usuario() {
    }

    public Usuario(int idUsuario, String nome, String email, String senha,
            String curso, boolean ativo,
            LocalDateTime ultimoAcesso,
            LocalDateTime dataCadastro,
            Perfil perfil) {

        this.idUsuario = idUsuario;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.curso = curso;
        this.ativo = ativo;
        this.ultimoAcesso = ultimoAcesso;
        this.dataCadastro = dataCadastro;
        this.perfil = perfil;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public LocalDateTime getUltimoAcesso() {
        return ultimoAcesso;
    }

    public void setUltimoAcesso(LocalDateTime ultimoAcesso) {
        this.ultimoAcesso = ultimoAcesso;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    @Override
    public String toString() {
        return "Usuario [idUsuario=" + idUsuario +
                ", nome=" + nome +
                ", email=" + email +
                ", curso=" + curso +
                ", ativo=" + ativo +
                ", perfil=" + perfil + "]";
    }
}