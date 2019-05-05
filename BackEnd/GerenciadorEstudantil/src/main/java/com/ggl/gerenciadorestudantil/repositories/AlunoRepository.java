package com.ggl.gerenciadorestudantil.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import com.ggl.gerenciadorestudantil.entities.Aluno;

@Transactional(readOnly = true)
public interface AlunoRepository extends JpaRepository<Aluno, Integer> {

	Aluno findByNome(String nome);
	
	Aluno findByEmail(String email);
}
