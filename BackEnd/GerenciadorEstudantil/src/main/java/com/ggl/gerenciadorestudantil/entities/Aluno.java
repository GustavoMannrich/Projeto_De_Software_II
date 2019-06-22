package com.ggl.gerenciadorestudantil.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "aluno")
public class Aluno implements Serializable{

	private static final long serialVersionUID = 5412204479416411437L;
	
	private int id;
	private String email = "";
	private String nome = "";
	private String senha;	
	private boolean receber_notificacoes;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "email", nullable = false)
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(name = "nome", nullable = false)
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Column(name = "senha", nullable = false)
	public String getSenha() {
		return senha;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}	
	
	@Column(name = "receber_notificacoes", nullable = false)
	public boolean getReceber_notificacoes() {
		return receber_notificacoes;
	}

	public void setReceber_notificacoes(boolean receber_notificacoes) {
		this.receber_notificacoes = receber_notificacoes;
	}

	@Override
	public String toString() {
		return "Aluno [id=" + id + ", email=" + email + ", nome=" + nome + ", senha=" + senha + ", receber_notificacoes="
				+ receber_notificacoes + "]";
	}
	
}