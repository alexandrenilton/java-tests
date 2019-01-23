package com.edu.abelem.entity;

import java.util.Date;
import java.util.List;

public class MovieRental {

	private User usuario;
	private List<Movie> filmes;
	private Date dataLocacao;
	private Date dataRetorno;
	private Double valor;
	
	public User getUsuario() {
		return usuario;
	}
	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}
	public Date getDataLocacao() {
		return dataLocacao;
	}
	public void setDataLocacao(Date dataLocacao) {
		this.dataLocacao = dataLocacao;
	}
	public Date getDataRetorno() {
		return dataRetorno;
	}
	public void setDataRetorno(Date dataRetorno) {
		this.dataRetorno = dataRetorno;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public List<Movie> getFilmes() {
		return filmes;
	}
	public void setFilmes(List<Movie> filmes) {
		this.filmes = filmes;
	}

}