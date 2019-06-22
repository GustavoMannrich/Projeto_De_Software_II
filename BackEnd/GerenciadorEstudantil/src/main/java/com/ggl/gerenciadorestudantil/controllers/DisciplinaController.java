package com.ggl.gerenciadorestudantil.controllers;

import java.text.ParseException;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpHeaders;
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

import com.ggl.gerenciadorestudantil.dtos.DisciplinaDto;
import com.ggl.gerenciadorestudantil.entities.Curso;
import com.ggl.gerenciadorestudantil.entities.Disciplina;
import com.ggl.gerenciadorestudantil.response.Response;
import com.ggl.gerenciadorestudantil.services.CursoService;
import com.ggl.gerenciadorestudantil.services.DisciplinaService;

@RestController
@RequestMapping("/api/disciplina")
@CrossOrigin(origins = "*")
public class DisciplinaController {

	private static final Logger log = LoggerFactory.getLogger(DisciplinaController.class);

	@Autowired
	private DisciplinaService disciplinaService;
			
	@Autowired
	private CursoService cursoService;
	
	@Value("${paginacao.qtd_por_pagina}")
	private int qtdPorPagina;
	
	/**
	 * Retorna a listagem de disciplinas de um aluno
	 * 
	 * @param alunoId
	 * @param pag
	 * @param ord
	 * @param dir
	 * @return ResponseEntity<Response<Page<DisciplinaDto>>>
	 */
	@GetMapping(value = "/curso/{cursoId}")
	public ResponseEntity<Response<Page<DisciplinaDto>>> listarPorCurso(
			@PathVariable("cursoId") int cursoId,
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir) {
		log.info("Buscando disciplinas por ID do curso: {}, página: {}", cursoId, pag);
		Response<Page<DisciplinaDto>> response = new Response<Page<DisciplinaDto>>();
		
		@SuppressWarnings("deprecation")
		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<Disciplina> disciplinas = this.disciplinaService.buscarPorCursoId(cursoId, pageRequest);
		Page<DisciplinaDto> disciplinasDto = disciplinas.map(disciplina -> this.converterDisciplinaDto(disciplina));
		
		response.setData(disciplinasDto);
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Retorna um disciplina por ID
	 * 
	 * @param id
	 * @return ResponseEntity<Response<DisciplinaDto>>
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<DisciplinaDto>> listarPorId(@PathVariable("id") int id) {
		log.info("Buscando disciplina por ID: {}", id);
		Response<DisciplinaDto> response = new Response<DisciplinaDto>();
		Optional<Disciplina> disciplina = this.disciplinaService.buscarPorId(id);
		
		if (!disciplina.isPresent()) {
			log.info("Disciplina não encontrado para o ID: {}", id);
			response.getErrors().add("Disciplina não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}
		
		 HttpHeaders responseHeaders = new HttpHeaders();
		    responseHeaders.set("Access-Control-Allow-Origin", 
		      "*");
		
		response.setData(this.converterDisciplinaDto(disciplina.get()));
		
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Adiciona um novo disciplina
	 * 
	 * @param disciplinaDto
	 * @param result
	 * @return ResponseEntity<Response<DisciplinaDto>>
	 * @throws ParseException
	 */
	@PostMapping
	public ResponseEntity<Response<DisciplinaDto>> adicionar(@Valid @RequestBody DisciplinaDto disciplinaDto,
														 BindingResult result) throws ParseException {
		log.info("Adicionando disciplina: {}", disciplinaDto.toString());
		Response<DisciplinaDto> response = new Response<DisciplinaDto>();
		validarAluno(disciplinaDto, result);
		Disciplina disciplina = this.converterDtoParaDisciplina(disciplinaDto, result);
		
		if (result.hasErrors()) {
			log.error("Erro validando disciplina: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
	
		disciplina = this.disciplinaService.persistir(disciplina);
		response.setData(this.converterDisciplinaDto(disciplina));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Atualiza os dados de um disciplina
	 * 
	 * @param id
	 * @param disciplinaDto
	 * @param result
	 * @return ResponseEntity<Response<DisciplinaDto>>
	 * @throws ParseException
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<DisciplinaDto>> atualizar(@PathVariable("id") int id,
			@Valid @RequestBody DisciplinaDto disciplinaDto, BindingResult result) throws ParseException {
		log.info("Atualizando disciplina: {}", disciplinaDto.toString());
		Response<DisciplinaDto> response = new Response<DisciplinaDto>();
		validarAluno(disciplinaDto, result);
		disciplinaDto.setId(Optional.of(id));
		Disciplina disciplina = this.converterDtoParaDisciplina(disciplinaDto, result);

		if (result.hasErrors()) {
			log.error("Erro validando disciplina: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		disciplina = this.disciplinaService.persistir(disciplina);
		response.setData(this.converterDisciplinaDto(disciplina));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Remove um disciplina por ID.
	 * 
	 * @param id
	 * @return ResponseEntity<Response<Disciplina>>
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response<String>> remover(@PathVariable("id") int id) {
		log.info("Removendo disciplina: {}", id);
		Response<String> response = new Response<String>();
		Optional<Disciplina> disciplina = this.disciplinaService.buscarPorId(id);

		if (!disciplina.isPresent()) {
			log.info("Erro ao remover devido ao disciplina ID: {} ser inválido.", id);
			response.getErrors().add("Erro ao remover disciplina. Registro não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		this.disciplinaService.remover(id);
		return ResponseEntity.ok(new Response<String>());
	}
	
	/**
	 * Converte uma entidade disciplina para seu respectivo DTO
	 * 
	 * @param disciplina
	 * @return
	 */
	private DisciplinaDto converterDisciplinaDto(Disciplina disciplina) {
		DisciplinaDto disciplinaDto = new DisciplinaDto();
		
		disciplinaDto.setId(Optional.of(disciplina.getId()));
		disciplinaDto.setNome(disciplina.getNome());
		disciplinaDto.setCursoId(disciplina.getCurso().getId());
		
		return disciplinaDto;
	}
	
	/**
	 * Valida um aluno, verificando se ele é existente e válido no sistema
	 * 
	 * @param disciplinaDto
	 * @param result
	 */
	private void validarAluno(DisciplinaDto disciplinaDto, BindingResult result) {
		if (disciplinaDto.getCursoId() == null) {
			result.addError(new ObjectError("curso", "Curso não informado."));
			return;
		}
		
		log.info("Validando aluno id: {}", disciplinaDto.getCursoId());
		Optional<Curso> curso = this.cursoService.buscarPorId(disciplinaDto.getCursoId());
		if (!curso.isPresent()) {
			result.addError(new ObjectError("curso", "Curso não encontrado. ID inexistente."));
		}
	}
	
	/**
	 * Converte um disciplinaDto para uma entidade Disciplina
	 * 
	 * @param disciplinaDto
	 * @param result
	 * @return Disciplina
	 * @throws ParseException
	 */
	private Disciplina converterDtoParaDisciplina(DisciplinaDto disciplinaDto, BindingResult result) throws ParseException {
		Disciplina disciplina = new Disciplina();
		
		if (disciplinaDto.getId().isPresent()) {
			Optional<Disciplina> evt = this.disciplinaService.buscarPorId(disciplinaDto.getId().get());
			if (evt.isPresent()) {
				disciplina = evt.get();				
			} else {
				result.addError(new ObjectError("disciplina", "Disciplina não encontrado."));
			}
		} else {
			disciplina.setCurso(new Curso());
			disciplina.getCurso().setId(disciplinaDto.getCursoId());
		}
		
		disciplina.setNome(disciplinaDto.getNome());
		
		return disciplina;
	}
	
}
