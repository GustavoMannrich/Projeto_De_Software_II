package com.ggl.gerenciadorestudantil.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ggl.gerenciadorestudantil.entities.Evento;
import com.ggl.gerenciadorestudantil.repositories.EventoRepository;
import com.ggl.gerenciadorestudantil.services.EventoService;

@Service
public class EventoServiceImpl implements EventoService {

	private static final Logger log = LoggerFactory.getLogger(EventoServiceImpl.class);

	@Autowired
	private EventoRepository eventoRepository;
	
	public Page<Evento> buscarPorAlunoId(Integer alunoId, PageRequest pageRequest) {
		log.info("Buscando eventos para o aluno ID {}", alunoId);
		return this.eventoRepository.findByAlunoId(alunoId, pageRequest);
	}
	
	public Optional<Evento> buscarPorId(Integer id) {
		log.info("Buscando um evento pelo ID {}", id);
		return this.eventoRepository.findById(id);
	}
	
	public Evento persistir(Evento evento) {
		log.info("Persistindo o evento: {}", evento);
		return this.eventoRepository.save(evento);
	}
	
	public void remover(Integer id) {
		log.info("Removendo o lançamento ID {}", id);
		this.eventoRepository.deleteById(id);
	}
	
}
