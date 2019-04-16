package com.ggl.gerenciadorestudantil.services;

import java.util.Optional;

import com.ggl.gerenciadorestudantil.entities.Aluno;

public interface AlunoService {

	/**
	 * Persiste um aluno na base de dados
	 * 
	 * @param aluno
	 * @return  Aluno
	 */
	Aluno persistir(Aluno aluno);
	
	/**
	 * Busca e retorna um aluno por nome
	 * 
	 * @param nome
	 * @return Optional<Aluno>
	 */
	Optional<Aluno> buscarPorNome(String nome);
	
	/**
	 * Busca e retorna um aluno por ID
	 * 
	 * @param id
	 * @return Optional<Aluno>
	 */
	Optional<Aluno> buscarPorId(int id);
}
