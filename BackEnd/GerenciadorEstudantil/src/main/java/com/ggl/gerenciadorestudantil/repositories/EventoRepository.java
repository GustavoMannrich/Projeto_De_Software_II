package com.ggl.gerenciadorestudantil.repositories;

import java.util.List;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.ggl.gerenciadorestudantil.entities.Evento;

@Transactional(readOnly = true)
@NamedQueries({@NamedQuery(name = "EventoRepository.findByAlunoId",
						   query = "SELECT a FROM evento a WHERE a.aluno_id = :alunoId")})

public interface EventoRepository extends JpaRepository<Evento, Integer> {

	List<Evento> findByAlunoId(@Param("alunoId") Integer alunoId);

	Page<Evento> findByAlunoId(@Param("alunoId") Integer alunoId, Pageable pageable);

	List<Evento> findAll();
}
