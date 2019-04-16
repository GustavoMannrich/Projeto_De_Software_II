package com.ggl.gerenciadorestudantil.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.ggl.gerenciadorestudantil.entities.Aluno;
import com.ggl.gerenciadorestudantil.repositories.AlunoRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AlunoServiceTest {

	@MockBean
	private AlunoRepository alunoRepository;
	
	@Autowired
	private AlunoService alunoService;
	
	@Before
	public void setUp() throws Exception {
		BDDMockito.given(this.alunoRepository.save(Mockito.any(Aluno.class))).willReturn(new Aluno());
		BDDMockito.given(this.alunoRepository.findById(Mockito.anyInt())).willReturn(Optional.ofNullable(new Aluno()));
		BDDMockito.given(this.alunoRepository.findByNome(Mockito.anyString())).willReturn(new Aluno());
	}
	
	@Test
	public void testPersistirAluno() {
		Aluno aluno = this.alunoService.persistir(new Aluno());
		
		assertNotNull(aluno);
	}
	
	@Test
	public void testBuscarAlunoPorNome() {
		Optional<Aluno> aluno = this.alunoService.buscarPorNome("Lucas Reinert");
		
		assertTrue(aluno.isPresent());
	}
	
	@Test
	public void testBuscarAlunoPorId() {
		Optional<Aluno> aluno = this.alunoService.buscarPorId(1);
		
		assertTrue(aluno.isPresent());
	}
}
