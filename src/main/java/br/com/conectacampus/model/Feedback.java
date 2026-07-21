package br.com.conectacampus.model;

import java.time.LocalDateTime;

public class Feedback {

	private int idFeedback;
	private String assunto;
	private String tipo;
	private String mensagem;
	private boolean anonimo;
	private LocalDateTime dataEnvio;

	private Usuario usuario;

	public Feedback() {
	}

	public Feedback(int idFeedback, String assunto, String tipo, String mensagem, boolean anonimo, LocalDateTime dataEnvio, Usuario usuario) {
		this.idFeedback = idFeedback;
		this.assunto = assunto;
		this.tipo = tipo;
		this.mensagem = mensagem;
		this.anonimo = anonimo;
		this.dataEnvio = dataEnvio;
		this.usuario = usuario;
	}

	public int getIdFeedback() {
		return idFeedback;
	}

	public void setIdFeedback(int idFeedback) {
		this.idFeedback = idFeedback;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public boolean isAnonimo() {
		return anonimo;
	}

	public void setAnonimo(boolean anonimo) {
		this.anonimo = anonimo;
	}

	public LocalDateTime getDataEnvio() {
		return dataEnvio;
	}

	public void setDataEnvio(LocalDateTime dataEnvio) {
		this.dataEnvio = dataEnvio;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return "Feedback [idFeedback=" + idFeedback + ", tipo=" + tipo + "]";
	}

}