package com.ggl.gerenciadorestudantil.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ggl.gerenciadorestudantil.entities.Disciplina;
import com.ggl.gerenciadorestudantil.repositories.DisciplinaRepository;
import com.ggl.gerenciadorestudantil.services.DisciplinaService;

@Service
public class DisciplinaServiceImpl implements DisciplinaService {

	private static final Logger log = LoggerFactory.getLogger(CursoServiceImpl.class);

	@Autowired
	private DisciplinaRepository disciplinaRepository;
	
	public Page<Disciplina> buscarPorAlunoCursoId(Integer alunoId, Integer cursoId, PageRequest pageRequest) {
		log.info("Buscando disciplinas para o aluno ID {}", alunoId);
		return this.disciplinaRepository.findByAlunoAndCursoId(alunoId, cursoId, pageRequest);
	}
	
	public Optional<Disciplina> buscarPorId(Integer id) {
		log.info("Buscando uma disciplina pelo ID {}", id);
		return this.disciplinaRepository.findById(id);
	}
	
	public Disciplina persistir(Disciplina disciplina) {
		log.info("Persistindo a disciplina: {}", disciplina);
		return this.disciplinaRepository.save(disciplina);
	}
	
	public void remover(Integer id) {
		log.info("Removendo o lançamento ID {}", id);
		this.disciplinaRepository.deleteById(id);
	}
	
}
