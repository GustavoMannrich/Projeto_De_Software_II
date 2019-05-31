package com.ggl.gerenciadorestudantil.repositories;

import java.util.List;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.ggl.gerenciadorestudantil.entities.Curso;

@Transactional(readOnly = true)
@NamedQueries({@NamedQuery(name = "CursoRepository.findByAlunoId",
						   query = "SELECT a FROM curso a WHERE a.aluno_id = :alunoId")})

public interface CursoRepository extends JpaRepository<Curso, Integer> {

	List<Curso> findByAlunoId(@Param("alunoId") Integer alunoId);

	Page<Curso> findByAlunoId(@Param("alunoId") Integer alunoId, Pageable pageable);
	
}
