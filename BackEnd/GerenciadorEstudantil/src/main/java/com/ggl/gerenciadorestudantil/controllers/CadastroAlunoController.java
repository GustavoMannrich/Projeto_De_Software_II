package com.ggl.gerenciadorestudantil.controllers;

import java.security.NoSuchAlgorithmException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ggl.gerenciadorestudantil.dtos.CadastroAlunoDto;
import com.ggl.gerenciadorestudantil.entities.Aluno;
import com.ggl.gerenciadorestudantil.response.Response;
import com.ggl.gerenciadorestudantil.services.AlunoService;
import com.ggl.gerenciadorestudantil.utils.PasswordUtils;

@RestController
@RequestMapping("/api/cadastrar-aluno")
@CrossOrigin(origins = "*")
public class CadastroAlunoController {

	private static final Logger log = LoggerFactory.getLogger(CadastroAlunoController.class);
	
	@Autowired
	private AlunoService alunoService;
	
	/**
	 * Cadastra um aluno no sistema 
	 * 
	 * @param cadastroAlunoDto
	 * @param result
	 * @return ResponseEntity<Response<CadastroAlunoDto>>
	 * @throws NoSuchAlgorithmException
	 */
	@PostMapping
	public ResponseEntity<Response<CadastroAlunoDto>> cadastrar(@Valid @RequestBody CadastroAlunoDto cadastroAlunoDto,
			BindingResult result) throws NoSuchAlgorithmException {
		log.info("Cadastrando aluno: {}", cadastroAlunoDto.toString());
		Response<CadastroAlunoDto> response = new Response<CadastroAlunoDto>();
		
		validarDadosExistentes(cadastroAlunoDto, result);
		Aluno aluno = this.converterDtoParaAluno(cadastroAlunoDto);
		
		if (result.hasErrors()) {
			log.error("Erro validando dados de cadastro de aluno: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		this.alunoService.persistir(aluno);
		response.setData(this.converterCadastroAlunoDto(aluno));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Verifica se o aluno já existe na base de dados
	 * 
	 * @param cadastroAlunoDto
	 * @param result
	 */
	private void validarDadosExistentes(CadastroAlunoDto cadastroAlunoDto, BindingResult result) {
		this.alunoService.buscarPorNome(cadastroAlunoDto.getNome()).ifPresent(aluno -> result.addError(new ObjectError("aluno", "Nome já existente.")));
	}
	
	/**
	 * Converte os dados do DTO para aluno
	 * 
	 * @param cadastroAlunoDto
	 * @return
	 */
	private Aluno converterDtoParaAluno(CadastroAlunoDto cadastroAlunoDto) {
		Aluno aluno = new Aluno();
		aluno.setNome(cadastroAlunoDto.getNome());
		aluno.setSenha(PasswordUtils.gerarCrypt(cadastroAlunoDto.getSenha()));
		
		return aluno;
	}
	
	private CadastroAlunoDto converterCadastroAlunoDto(Aluno aluno) {
		CadastroAlunoDto cadastroAlunoDto = new CadastroAlunoDto();
		cadastroAlunoDto.setId(aluno.getId());
		cadastroAlunoDto.setNome(aluno.getNome());
		
		return cadastroAlunoDto;
	}
}
