package com.ggl.gerenciadorestudantil.dtos;

import java.util.Optional;

import javax.validation.constraints.NotEmpty;

public class CursoDto {

	private Optional<Integer> id = Optional.empty();
	private Integer alunoId;
	private String nome;
	
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
	
	public Integer getAlunoId() {
		return alunoId;
	}
	
	public void setAlunoId(Integer alunoId) {
		this.alunoId = alunoId;
	}

	@Override
	public String toString() {
		return "CursoDto [id=" + id + ", nome=" + nome + ", aluno=" + alunoId + "]";
	}
	
}
