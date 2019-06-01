package com.ggl.gerenciadorestudantil.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ggl.gerenciadorestudantil.dtos.EventoDto;
import com.ggl.gerenciadorestudantil.entities.Aluno;
import com.ggl.gerenciadorestudantil.entities.Evento;
import com.ggl.gerenciadorestudantil.response.Response;
import com.ggl.gerenciadorestudantil.services.AlunoService;
import com.ggl.gerenciadorestudantil.services.EventoService;

@RestController
@RequestMapping("/api/eventos")
@CrossOrigin(origins = "*")
public class EventoController {

	private static final Logger log = LoggerFactory.getLogger(EventoController.class);
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
	private EventoService eventoService;
	
	@Autowired
	private AlunoService alunoService;
	
	@Value("${paginacao.qtd_por_pagina}")
	private int qtdPorPagina;
	
	/**
	 * Retorna a listagem de eventos de um aluno
	 * 
	 * @param alunoId
	 * @param pag
	 * @param ord
	 * @param dir
	 * @return ResponseEntity<Response<Page<EventoDto>>>
	 */
	@GetMapping(value = "/aluno/{alunoId}")
	public ResponseEntity<Response<Page<EventoDto>>> listarPorAluno(
			@PathVariable("alunoId") int alunoId,
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir) {
		log.info("Buscando eventos por ID do aluno: {}, página: {}", alunoId, pag);
		Response<Page<EventoDto>> response = new Response<Page<EventoDto>>();
		
		@SuppressWarnings("deprecation")
		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<Evento> eventos = this.eventoService.buscarPorAlunoId(alunoId, pageRequest);
		Page<EventoDto> eventosDto = eventos.map(evento -> this.converterEventoDto(evento));
		
		response.setData(eventosDto);
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Retorna um evento por ID
	 * 
	 * @param id
	 * @return ResponseEntity<Response<EventoDto>>
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<EventoDto>> listarPorId(@PathVariable("id") int id) {
		log.info("Buscando evento por ID: {}", id);
		Response<EventoDto> response = new Response<EventoDto>();
		Optional<Evento> evento = this.eventoService.buscarPorId(id);
		
		if (!evento.isPresent()) {
			log.info("Evento não encontrado para o ID: {}", id);
			response.getErrors().add("Evento não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}
		
		response.setData(this.converterEventoDto(evento.get()));
		
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Adiciona um novo evento
	 * 
	 * @param eventoDto
	 * @param result
	 * @return ResponseEntity<Response<EventoDto>>
	 * @throws ParseException
	 */
	@PostMapping
	public ResponseEntity<Response<EventoDto>> adicionar(@Valid @RequestBody EventoDto eventoDto,
														 BindingResult result) throws ParseException {
		log.info("Adicionando evento: {}", eventoDto.toString());
		Response<EventoDto> response = new Response<EventoDto>();
		validarAluno(eventoDto, result);
		Evento evento = this.converterDtoParaEvento(eventoDto, result);
		
		if (result.hasErrors()) {
			log.error("Erro validando evento: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
	
		evento = this.eventoService.persistir(evento);
		response.setData(this.converterEventoDto(evento));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Atualiza os dados de um evento
	 * 
	 * @param id
	 * @param eventoDto
	 * @param result
	 * @return ResponseEntity<Response<EventoDto>>
	 * @throws ParseException
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<EventoDto>> atualizar(@PathVariable("id") int id,
			@Valid @RequestBody EventoDto eventoDto, BindingResult result) throws ParseException {
		log.info("Atualizando evento: {}", eventoDto.toString());
		Response<EventoDto> response = new Response<EventoDto>();
		validarAluno(eventoDto, result);
		eventoDto.setId(Optional.of(id));
		Evento evento = this.converterDtoParaEvento(eventoDto, result);

		if (result.hasErrors()) {
			log.error("Erro validando evento: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		evento = this.eventoService.persistir(evento);
		response.setData(this.converterEventoDto(evento));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Remove um evento por ID.
	 * 
	 * @param id
	 * @return ResponseEntity<Response<Evento>>
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response<String>> remover(@PathVariable("id") int id) {
		log.info("Removendo evento: {}", id);
		Response<String> response = new Response<String>();
		Optional<Evento> evento = this.eventoService.buscarPorId(id);

		if (!evento.isPresent()) {
			log.info("Erro ao remover devido ao evento ID: {} ser inválido.", id);
			response.getErrors().add("Erro ao remover evento. Registro não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		this.eventoService.remover(id);
		return ResponseEntity.ok(new Response<String>());
	}
	
	/**
	 * Converte uma entidade evento para seu respectivo DTO
	 * 
	 * @param evento
	 * @return
	 */
	private EventoDto converterEventoDto(Evento evento) {
		EventoDto eventoDto = new EventoDto();
		
		eventoDto.setId(Optional.of(evento.getId()));
		eventoDto.setTitulo(evento.getTitulo());
		eventoDto.setDescricao(evento.getDescricao());
		eventoDto.setData(this.dateFormat.format(evento.getData()));
		eventoDto.setAlunoId(evento.getAluno().getId());
		
		return eventoDto;
	}
	
	/**
	 * Valida um aluno, verificando se ele é existente e válido no sistema
	 * 
	 * @param eventoDto
	 * @param result
	 */
	private void validarAluno(EventoDto eventoDto, BindingResult result) {
		if (eventoDto.getAlunoId() == null) {
			result.addError(new ObjectError("aluno", "Aluno não informado."));
			return;
		}
		
		log.info("Validando aluno id: {}", eventoDto.getAlunoId());
		Optional<Aluno> aluno = this.alunoService.buscarPorId(eventoDto.getAlunoId());
		if (!aluno.isPresent()) {
			result.addError(new ObjectError("aluno", "Aluno não encontrado. ID inexistente."));
		}
	}
	
	/**
	 * Converte um eventoDto para uma entidade Evento
	 * 
	 * @param eventoDto
	 * @param result
	 * @return Evento
	 * @throws ParseException
	 */
	private Evento converterDtoParaEvento(EventoDto eventoDto, BindingResult result) throws ParseException {
		Evento evento = new Evento();
		
		if (eventoDto.getId().isPresent()) {
			Optional<Evento> evt = this.eventoService.buscarPorId(eventoDto.getId().get());
			if (evt.isPresent()) {
				evento = evt.get();				
			} else {
				result.addError(new ObjectError("evento", "EVento não encontrado."));
			}
		} else {
			evento.setAluno(new Aluno());
			evento.getAluno().setId(eventoDto.getAlunoId());
		}
		
		evento.setTitulo(eventoDto.getTitulo());
		evento.setDescricao(eventoDto.getDescricao());
		evento.setData(this.dateFormat.parse(eventoDto.getData()));
		
		return evento;
	}
}
