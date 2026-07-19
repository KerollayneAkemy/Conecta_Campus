package br.com.conectacampus.model;

import java.math.BigDecimal;

public class MovimentoFinanceiro {
    private String competencia;
    private String data;
    private String tipo;
    private String categoria;
    private String descricao;
    private BigDecimal valor;

    public String getCompetencia() { return competencia; }
    public void setCompetencia(String competencia) { this.competencia = competencia; }
    public String getData() { return data; }
    public void setData(String data) { this.data = data; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
}
