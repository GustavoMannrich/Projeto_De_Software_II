package com.ggl.gerenciadorestudantil.repositories;

import java.util.List;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.ggl.gerenciadorestudantil.entities.Disciplina;

@Transactional(readOnly = true)
@NamedQueries({@NamedQuery(name = "DisciplinaRepository.findByAlunoCursoId",
						   query = "SELECT a FROM disciplina a WHERE a.aluno_id = :alunoId AND a.curso_id = :cursoId")})

// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.named-queries
// @Query("select u from User u where u.firstname = :firstname or u.lastname = :lastname")

public interface DisciplinaRepository extends JpaRepository<Disciplina, Integer> {

	List<Disciplina> findByAlunoAndCursoId(@Param("alunoId") Integer alunoId, @Param("cursoId") Integer cursoId);

	Page<Disciplina> findByAlunoAndCursoId(@Param("alunoId") Integer alunoId, @Param("cursoId") Integer cursoId, Pageable pageable);
	
}
