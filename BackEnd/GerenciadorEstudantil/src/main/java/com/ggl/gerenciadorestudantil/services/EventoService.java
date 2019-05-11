package com.ggl.gerenciadorestudantil.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.ggl.gerenciadorestudantil.entities.Evento;

public interface EventoService {
	
	/**
	 * Retorna uma lista paginada de eventos de um determinado aluno
	 * 
	 * @param alunoId
	 * @param pageRequest
	 * @return Page<Evento>
	 */
	Page<Evento> buscarPorAlunoId(Integer alunoId, PageRequest pageRequest);
	
	/**
	 * Retorna um evento por ID
	 * 
	 * @param id
	 * @return Optional<Evento>
	 */
	Optional<Evento> buscarPorId(Integer id);
	
	/**
	 * Persiste um evento na base de dados
	 * 
	 * @param evento
	 * @return
	 */
	Evento persistir(Evento evento);
	
	/**
	 * Remove um evento da base de dados
	 * 
	 * @param id
	 */
	void remover(Integer id);
}
