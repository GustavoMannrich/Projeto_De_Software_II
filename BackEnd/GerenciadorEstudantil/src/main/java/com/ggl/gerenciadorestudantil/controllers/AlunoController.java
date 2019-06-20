package com.ggl.gerenciadorestudantil.controllers;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ggl.gerenciadorestudantil.dtos.AlunoDto;
import com.ggl.gerenciadorestudantil.entities.Aluno;
import com.ggl.gerenciadorestudantil.response.Response;
import com.ggl.gerenciadorestudantil.services.AlunoService;
import com.ggl.gerenciadorestudantil.utils.PasswordUtils;

@RestController
@RequestMapping("/api/alunos")
@CrossOrigin(origins = "*")
public class AlunoController {
	
	private static final Logger log = LoggerFactory.getLogger(AlunoController.class);
	
	@Autowired
	private AlunoService alunoService;

	/**
	 * Atualiza os dados de um aluno
	 * 
	 * @param id
	 * @param alunoDto
	 * @param result
	 * @return ResponseEntity<Response<AlunoDto>>
	 * @throws NoSuchAlgorithmException
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<AlunoDto>> atualizar(@PathVariable("id") int id, @Valid @RequestBody AlunoDto alunoDto, BindingResult result) throws NoSuchAlgorithmException {
		log.info("Atualizando aluno: {}", alunoDto.toString());
		Response<AlunoDto> response = new Response<AlunoDto>();
		
		Optional<Aluno> aluno = this.alunoService.buscarPorId(id);
		if (!aluno.isPresent())
			result.addError(new ObjectError("aluno", "Aluno não encontrado"));
		
		this.atualizarDadosAluno(aluno.get(), alunoDto, result);
		
		if (result.hasErrors()) {
			log.error("Erro validando aluno {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		this.alunoService.persistir(aluno.get());
		response.setData(this.converterAlunoDto(aluno.get()));
		
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Retorna um aluno por email
	 * 
	 * @param email
	 * @return ResponseEntity<Response<EventoDto>>
	 */
	@GetMapping(value = "/{email}")
	public ResponseEntity<Response<AlunoDto>> listarPorId(@PathVariable("email") String email) {
		log.info("Buscando aluno por email: {}", email);
		Response<AlunoDto> response = new Response<AlunoDto>();
		Optional<Aluno> aluno = this.alunoService.buscarPorEmail(email);
		
		if (!aluno.isPresent()) {
			log.info("Aluno não encontrado para o email: {}", email);
			response.getErrors().add("Aluno não encontrado para o email " + email);
			return ResponseEntity.badRequest().body(response);
		}
		
		response.setData(this.converterAlunoDto(aluno.get()));
		
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Atualiza os dados do aluno com base nos dados encontrados no DTO
	 * 
	 * @param aluno
	 * @param alunoDto
	 * @param result
	 * @throws NoSuchAlgorithmException
	 */
	private void atualizarDadosAluno(Aluno aluno, AlunoDto alunoDto, BindingResult result) throws NoSuchAlgorithmException {		
		if (!aluno.getEmail().equals(alunoDto.getEmail())) {
			this.alunoService.buscarPorEmail(alunoDto.getEmail()).ifPresent(a -> result.addError(new ObjectError("nome", "Email já existente.")));
			aluno.setEmail(alunoDto.getEmail());
		}
		
		aluno.setNome(alunoDto.getNome());		
		aluno.setReceber_notificacoes(alunoDto.getReceber_notificacoes());
			
		if (alunoDto.getSenha().isPresent())
			aluno.setSenha(PasswordUtils.gerarCrypt(alunoDto.getSenha().get()));
	}
	
	/**
	 * Retorna um DTO com os dados de um aluno
	 * 
	 * @param aluno
	 * @return AlunoDto
	 */
	private AlunoDto converterAlunoDto(Aluno aluno) {
		AlunoDto alunoDto = new AlunoDto();
		
		alunoDto.setId(aluno.getId());
		alunoDto.setEmail(aluno.getEmail());
		alunoDto.setNome(aluno.getNome());
				
		alunoDto.setReceber_notificacoes(aluno.getReceber_notificacoes());
			
		return alunoDto;	
	}
}
