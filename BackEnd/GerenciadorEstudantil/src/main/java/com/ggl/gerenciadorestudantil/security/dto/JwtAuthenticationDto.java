package com.ggl.gerenciadorestudantil.security.dto;

import javax.validation.constraints.NotEmpty;

public class JwtAuthenticationDto {
	
	private String nome;
	private String senha;

	public JwtAuthenticationDto() {
	}

	@NotEmpty(message = "Nome não pode ser vazio.")
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@NotEmpty(message = "Senha não pode ser vazia.")
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public String toString() {
		return "JwtAuthenticationRequestDto [email=" + nome + ", senha=" + senha + "]";
	}

}