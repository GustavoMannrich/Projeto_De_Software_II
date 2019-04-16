package com.ggl.gerenciadorestudantil.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ggl.gerenciadorestudantil.entities.Aluno;
import com.ggl.gerenciadorestudantil.repositories.AlunoRepository;
import com.ggl.gerenciadorestudantil.services.AlunoService;

@Service
public class AlunoServiceImpl implements AlunoService {

	private static final Logger log = LoggerFactory.getLogger(AlunoServiceImpl.class);
	
	@Autowired
	private AlunoRepository alunoRepository;
	
	public Aluno persistir(Aluno aluno) {
		log.info("Persistindo aluno: {}", aluno);
		return this.alunoRepository.save(aluno);
	}

	public Optional<Aluno> buscarPorNome(String nome) {
		log.info("Buscando aluno pelo nome {}", nome);
		return Optional.ofNullable(this.alunoRepository.findByNome(nome));
	}
	public Optional<Aluno> buscarPorId(int id) {
		log.info("Buscando aluno pelo ID {}", id);
		return this.alunoRepository.findById(id);
	}
	
}
