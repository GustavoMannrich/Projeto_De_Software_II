package com.ggl.gerenciadorestudantil.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ggl.gerenciadorestudantil.entities.Curso;
import com.ggl.gerenciadorestudantil.repositories.CursoRepository;
import com.ggl.gerenciadorestudantil.services.CursoService;

@Service
public class CursoServiceImpl implements CursoService {

	private static final Logger log = LoggerFactory.getLogger(CursoServiceImpl.class);

	@Autowired
	private CursoRepository cursoRepository;
	
	public Page<Curso> buscarPorAlunoId(Integer alunoId, PageRequest pageRequest) {
		log.info("Buscando cursos para o aluno ID {}", alunoId);
		return this.cursoRepository.findByAlunoId(alunoId, pageRequest);
	}
	
	public Optional<Curso> buscarPorId(Integer id) {
		log.info("Buscando um curso pelo ID {}", id);
		return this.cursoRepository.findById(id);
	}
	
	public Curso persistir(Curso curso) {
		log.info("Persistindo o curso: {}", curso);
		return this.cursoRepository.save(curso);
	}
	
	public void remover(Integer id) {
		log.info("Removendo o lançamento ID {}", id);
		this.cursoRepository.deleteById(id);
	}
	
}
