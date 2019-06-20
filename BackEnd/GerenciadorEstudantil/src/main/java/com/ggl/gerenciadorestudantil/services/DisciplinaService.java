package com.ggl.gerenciadorestudantil.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.ggl.gerenciadorestudantil.entities.Disciplina;

public interface DisciplinaService {

	/**
	 * Retorna uma lista paginada de disciplinas de um determinado aluno e curso
	 * 
	 * @param alunoId
	 * @param pageRequest
	 * @return Page<Evento>
	 */
	Page<Disciplina> buscarPorCursoId(Integer cursoId, PageRequest pageRequest);
	
	/**
	 * Retorna uma disciplina por ID
	 * 
	 * @param id
	 * @return Optional<Evento>
	 */
	Optional<Disciplina> buscarPorId(Integer id);
	
	/**
	 * Persiste uma disciplina na base de dados
	 * 
	 * @param evento
	 * @return
	 */
	Disciplina persistir(Disciplina disciplina);
	
	/**
	 * Remove uma disciplina da base de dados
	 * 
	 * @param id
	 */
	void remover(Integer id);
	
}
