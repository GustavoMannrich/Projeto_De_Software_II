package com.ggl.gerenciadorestudantil.dtos;

import java.util.Optional;

import javax.validation.constraints.NotEmpty;

public class DisciplinaDto {

	private Optional<Integer> id = Optional.empty();
	private String nome;
	private Integer cursoId;
	
	public Optional<Integer> getId() {
		return id;
	}
	
	public void setId(Optional<Integer> id) {
		this.id = id;
	}
	
	@NotEmpty(message = "Nome não pode ser vazio.")
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Integer getCursoId() {
		return cursoId;
	}
	
	public void setCursoId(Integer cursoId) {
		this.cursoId = cursoId;
	}

	@Override
	public String toString() {
		return "DisciplinaDto [id=" + id + ", nome=" + nome + ", curso=" + cursoId + "]";
	}
	
}
