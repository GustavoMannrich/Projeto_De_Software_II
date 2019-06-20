package com.ggl.gerenciadorestudantil.dtos;

import java.util.Optional;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class AlunoDto {

	private int id;
	private String email;
	private String nome;
	private Optional<String> senha = Optional.empty();
	private boolean receber_notificacoes;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@NotEmpty(message = "Email não pode ser vazio.")
	@Length(min = 5, max = 200, message = "Email deve conter entre 5 e 200 caracteres")
	@Email(message = "Email inválido.")
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	@NotEmpty(message = "Nome não pode ser vazio.")
	@Length(min = 3, max = 60, message = "Nome deve conter entre 3 e 60 caracteres.")
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Optional<String> getSenha() {
		return senha;
	}
	
	public void setSenha(Optional<String> senha) {
		this.senha = senha;
	}
	
	public boolean getReceber_notificacoes() {
		return receber_notificacoes;
	}

	public void setReceber_notificacoes(boolean receber_notificacoes) {
		this.receber_notificacoes = receber_notificacoes;
	}

	@Override
	public String toString() {
		return "AlunoDto [id=" + id + ", email=" + email + ", nome=" + nome + ", senha=" + senha
				+ ", receber_notificacoes=" + receber_notificacoes + "]";
	}
	
}
