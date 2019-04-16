package com.ggl.gerenciadorestudantil.repositories;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.ggl.gerenciadorestudantil.entities.Aluno;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AlunoRepositoryTest {

	@Autowired
	private AlunoRepository alunoRepository;
	
	private static final String NOME = "Gustavo Mannrich";
	
	@Before
	public void setUp() throws Exception {
		Aluno aluno = new Aluno();
		aluno.setNome(NOME);
		aluno.setSenha("1234");
		this.alunoRepository.save(aluno);
	}
	
	@After
	public final void tearDown() {
		this.alunoRepository.deleteAll();
	}
	
	@Test
	public void testBuscarPorNome() {
		Aluno aluno = this.alunoRepository.findByNome(NOME);
		
		assertEquals(NOME, aluno.getNome());
	}
}
