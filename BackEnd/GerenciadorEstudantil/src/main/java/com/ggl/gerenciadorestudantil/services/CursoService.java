package com.ggl.gerenciadorestudantil.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.ggl.gerenciadorestudantil.entities.Curso;

public interface CursoService {

	/**
	 * Retorna uma lista paginada de cursos de um determinado aluno
	 * 
	 * @param alunoId
	 * @param pageRequest
	 * @return Page<Evento>
	 */
	Page<Curso> buscarPorAlunoId(Integer alunoId, PageRequest pageRequest);
	
	/**
	 * Retorna um curso por ID
	 * 
	 * @param id
	 * @return Optional<Evento>
	 */
	Optional<Curso> buscarPorId(Integer id);
	
	/**
	 * Persiste um curso na base de dados
	 * 
	 * @param evento
	 * @return
	 */
	Curso persistir(Curso curso);
	
	/**
	 * Remove um curso da base de dados
	 * 
	 * @param id
	 */
	void remover(Integer id);
}
