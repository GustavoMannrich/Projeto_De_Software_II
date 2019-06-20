package com.ggl.gerenciadorestudantil.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TemporalType;
import javax.persistence.Temporal;

@Entity
@Table(name = "evento")
public class Evento implements Serializable {

	private static final long serialVersionUID = -3230926380061374138L;
	
	private int id;
	private String titulo = "";
	private String descricao = "";
	private Date data;
	private Disciplina disciplina;
	private Boolean enviouNotificacao;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "titulo", nullable = false)
	public String getTitulo() {
		return titulo;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	@Column(name = "descricao", nullable = false)
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data", nullable = false)
	public Date getData() {
		return data;
	}
	
	public void setData(Date data) {
		this.data = data;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="disciplina_id")
	public Disciplina getDisciplina() {
		return disciplina;
	}
	
	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}
	
	@Column(name = "enviou_notificacao", nullable = false)
	public Boolean getEnviouNotificacao() {
		return enviouNotificacao;
	}

	public void setEnviouNotificacao(Boolean enviouNotificacao) {
		this.enviouNotificacao = enviouNotificacao;
	}

	@Override
	public String toString() {
		return "Evento [id=" + id + ", titulo=" + titulo + ", descricao=" + descricao + ", data=" + data + ", disciplina="
				+ disciplina + "]";
	}
	
}
