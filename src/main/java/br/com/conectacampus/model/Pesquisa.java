package br.com.conectacampus.model;

import java.time.LocalDate;

public class Pesquisa {
	private int idPesquisa;
	private String titulo;
	private String descricao;
	private String linkFormulario;
	private LocalDate dataLimite;
	private String status;
	private Usuario usuario;

	public int getIdPesquisa() {
		return idPesquisa; 
		}
	
	public void setIdPesquisa(int idPesquisa) {
		this.idPesquisa = idPesquisa;
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
	
	public String getLinkFormulario() {
		return linkFormulario;
		}
	
	public void setLinkFormulario(String linkFormulario) { 
		this.linkFormulario = linkFormulario; 
		}
	
	public LocalDate getDataLimite() { 
		return dataLimite;
		}
	
	public void setDataLimite(LocalDate dataLimite) { 
		this.dataLimite = dataLimite; 
		}
	
	public String getStatus() {
		return status; 
		}
	
	public void setStatus(String status) {
		this.status = status; 
		}
	
	public Usuario getUsuario() { 
		return usuario; 
		}
	
	public void setUsuario(Usuario usuario) { 
		this.usuario = usuario;
		}
}

