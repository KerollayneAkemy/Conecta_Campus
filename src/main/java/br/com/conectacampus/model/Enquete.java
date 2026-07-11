package br.com.conectacampus.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Enquete {

    private int idEnquete;
    private String titulo;
    private String descricao;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String status;
    private List<OpcaoEnquete> opcoes = new ArrayList<>();

    public Enquete() {
    }

    public Enquete(int idEnquete, String titulo, String descricao,
            LocalDate dataInicio, LocalDate dataFim, String status) {

        this.idEnquete = idEnquete;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.status = status;
    }

    public int getIdEnquete() {
        return idEnquete;
    }

    public void setIdEnquete(int idEnquete) {
        this.idEnquete = idEnquete;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OpcaoEnquete> getOpcoes() {
        return opcoes;
    }

    public void setOpcoes(List<OpcaoEnquete> opcoes) {
        this.opcoes = opcoes;
    }

    @Override
    public String toString() {
        return "Enquete [idEnquete=" + idEnquete + ", titulo=" + titulo + "]";
    }

}
