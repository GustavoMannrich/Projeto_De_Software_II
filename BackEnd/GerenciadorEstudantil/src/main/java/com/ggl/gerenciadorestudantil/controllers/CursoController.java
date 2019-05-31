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

import com.ggl.gerenciadorestudantil.dtos.CursoDto;
import com.ggl.gerenciadorestudantil.entities.Aluno;
import com.ggl.gerenciadorestudantil.entities.Curso;
import com.ggl.gerenciadorestudantil.response.Response;
import com.ggl.gerenciadorestudantil.services.AlunoService;
import com.ggl.gerenciadorestudantil.services.CursoService;

@RestController
@RequestMapping("/api/cursos")
@CrossOrigin(origins = "*")
public class CursoController {

	private static final Logger log = LoggerFactory.getLogger(CursoController.class);

	@Autowired
	private CursoService cursoService;
	
	@Autowired
	private AlunoService alunoService;
	
	@Value("${paginacao.qtd_por_pagina}")
	private int qtdPorPagina;
	
	/**
	 * Retorna a listagem de cursos de um aluno
	 * 
	 * @param alunoId
	 * @param pag
	 * @param ord
	 * @param dir
	 * @return ResponseEntity<Response<Page<CursoDto>>>
	 */
	@GetMapping(value = "/aluno/{alunoId}")
	public ResponseEntity<Response<Page<CursoDto>>> listarPorAluno(
			@PathVariable("alunoId") int alunoId,
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir) {
		log.info("Buscando cursos por ID do aluno: {}, página: {}", alunoId, pag);
		Response<Page<CursoDto>> response = new Response<Page<CursoDto>>();
		
		@SuppressWarnings("deprecation")
		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<Curso> cursos = this.cursoService.buscarPorAlunoId(alunoId, pageRequest);
		Page<CursoDto> cursosDto = cursos.map(evento -> this.converterCursoDto(evento));
		
		response.setData(cursosDto);
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Retorna um curso por ID
	 * 
	 * @param id
	 * @return ResponseEntity<Response<CursoDto>>
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<CursoDto>> listarPorId(@PathVariable("id") int id) {
		log.info("Buscando curso por ID: {}", id);
		Response<CursoDto> response = new Response<CursoDto>();
		Optional<Curso> curso = this.cursoService.buscarPorId(id);
		
		if (!curso.isPresent()) {
			log.info("Curso não encontrado para o ID: {}", id);
			response.getErrors().add("Curso não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}
		
		response.setData(this.converterCursoDto(curso.get()));
		
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Adiciona um novo curso
	 * 
	 * @param cursoDto
	 * @param result
	 * @return ResponseEntity<Response<CursoDto>>
	 * @throws ParseException
	 */
	@PostMapping
	public ResponseEntity<Response<CursoDto>> adicionar(@Valid @RequestBody CursoDto cursoDto,
														 BindingResult result) throws ParseException {
		log.info("Adicionando curso: {}", cursoDto.toString());
		Response<CursoDto> response = new Response<CursoDto>();
		validarAluno(cursoDto, result);
		Curso curso = this.converterDtoParaCurso(cursoDto, result);
		
		if (result.hasErrors()) {
			log.error("Erro validando curso: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
	
		curso = this.cursoService.persistir(curso);
		response.setData(this.converterCursoDto(curso));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Atualiza os dados de um curso
	 * 
	 * @param id
	 * @param eventoDto
	 * @param result
	 * @return ResponseEntity<Response<EventoDto>>
	 * @throws ParseException
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<CursoDto>> atualizar(@PathVariable("id") int id,
			@Valid @RequestBody CursoDto cursoDto, BindingResult result) throws ParseException {
		log.info("Atualizando curso: {}", cursoDto.toString());
		Response<CursoDto> response = new Response<CursoDto>();
		validarAluno(cursoDto, result);
		cursoDto.setId(Optional.of(id));
		Curso curso = this.converterDtoParaCurso(cursoDto, result);

		if (result.hasErrors()) {
			log.error("Erro validando curso: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		curso = this.cursoService.persistir(curso);
		response.setData(this.converterCursoDto(curso));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Remove um curso por ID.
	 * 
	 * @param id
	 * @return ResponseEntity<Response<Curso>>
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response<String>> remover(@PathVariable("id") int id) {
		log.info("Removendo evento: {}", id);
		Response<String> response = new Response<String>();
		Optional<Curso> curso = this.cursoService.buscarPorId(id);

		if (!curso.isPresent()) {
			log.info("Erro ao remover devido ao curso ID: {} ser inválido.", id);
			response.getErrors().add("Erro ao remover curso. Registro não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		this.cursoService.remover(id);
		return ResponseEntity.ok(new Response<String>());
	}
	
	/**
	 * Converte uma entidade curso para seu respectivo DTO
	 * 
	 * @param curso
	 * @return
	 */
	private CursoDto converterCursoDto(Curso curso) {
		CursoDto cursoDto = new CursoDto();
		
		cursoDto.setId(Optional.of(curso.getId()));
		cursoDto.setNome(curso.getNome());
		cursoDto.setAlunoId(curso.getAluno().getId());
		
		return cursoDto;
	}
	
	/**
	 * Valida um aluno, verificando se ele é existente e válido no sistema
	 * 
	 * @param eventoDto
	 * @param result
	 */
	private void validarAluno(CursoDto cursoDto, BindingResult result) {
		if (cursoDto.getAlunoId() == null) {
			result.addError(new ObjectError("aluno", "Aluno não informado."));
			return;
		}
		
		log.info("Validando aluno id: {}", cursoDto.getAlunoId());
		Optional<Aluno> aluno = this.alunoService.buscarPorId(cursoDto.getAlunoId());
		if (!aluno.isPresent()) {
			result.addError(new ObjectError("aluno", "Aluno não encontrado. ID inexistente."));
		}
	}
	
	/**
	 * Converte um cursoDto para uma entidade Curso
	 * 
	 * @param eventoDto
	 * @param result
	 * @return Evento
	 * @throws ParseException
	 */
	private Curso converterDtoParaCurso(CursoDto cursoDto, BindingResult result) throws ParseException {
		Curso curso = new Curso();
		
		if (cursoDto.getId().isPresent()) {
			Optional<Curso> evt = this.cursoService.buscarPorId(cursoDto.getId().get());
			if (evt.isPresent()) {
				curso = evt.get();				
			} else {
				result.addError(new ObjectError("curso", "Curso não encontrado."));
			}
		} else {
			curso.setAluno(new Aluno());
			curso.getAluno().setId(cursoDto.getAlunoId());
		}
		
		curso.setNome(cursoDto.getNome());
		
		return curso;
	}
	
}
