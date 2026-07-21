package br.com.conectacampus.model;

public class Cargo {

	private int idCargo;
	private String nome;
	private String descricao;

	public Cargo() {
	}

	public Cargo(int idCargo, String nome, String descricao) {
		this.idCargo = idCargo;
		this.nome = nome;
		this.descricao = descricao;
	}

	public int getIdCargo() {
		return idCargo;
	}

	public void setIdCargo(int idCargo) {
		this.idCargo = idCargo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}