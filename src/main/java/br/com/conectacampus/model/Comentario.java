package br.com.conectacampus.model;

import java.time.LocalDateTime;

public class Comentario {

	private int idComentario;
	private String mensagem;
	private LocalDateTime dataComentario;
	private Usuario usuario;
	private Comunicado comunicado;

	public Comentario() {
	}

	public Comentario(int idComentario, String mensagem, LocalDateTime dataComentario, Usuario usuario, Comunicado comunicado) {
		this.idComentario = idComentario;
		this.mensagem = mensagem;
		this.dataComentario = dataComentario;
		this.usuario = usuario;
		this.comunicado = comunicado;
	}

	public int getIdComentario() {
		return idComentario;
	}

	public void setIdComentario(int idComentario) {
		this.idComentario = idComentario;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public LocalDateTime getDataComentario() {
		return dataComentario;
	}

	public void setDataComentario(LocalDateTime dataComentario) {
		this.dataComentario = dataComentario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Comunicado getComunicado() {
		return comunicado;
	}

	public void setComunicado(Comunicado comunicado) {
		this.comunicado = comunicado;
	}

	@Override
	public String toString() {
		return "Comentario [idComentario=" + idComentario + "]";
	}

}