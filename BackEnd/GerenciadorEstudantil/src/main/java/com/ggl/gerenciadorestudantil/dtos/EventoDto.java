package com.ggl.gerenciadorestudantil.dtos;

import java.util.Optional;

import javax.validation.constraints.NotEmpty;


public class EventoDto {

	private Optional<Integer> id = Optional.empty();
	private Integer disciplinaId;
	private String titulo;
	private String descricao;
	private String data;
	
	public Optional<Integer> getId() {
		return id;
	}
	
	public void setId(Optional<Integer> id) {
		this.id = id;
	}
	
	public Integer getDisciplinaId() {
		return disciplinaId;
	}
	
	public void setDisciplinaId(Integer disciplinaId) {
		this.disciplinaId = disciplinaId;
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
	
	@NotEmpty(message = "Data não pode ser vazia.")
	public String getData() {
		return data;
	}
	
	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "EventoDto [id=" + id + ", disciplinaId=" + disciplinaId + ", titulo=" + titulo + ", descricao=" + descricao
				+ ", data=" + data + "]";
	}
	
}
