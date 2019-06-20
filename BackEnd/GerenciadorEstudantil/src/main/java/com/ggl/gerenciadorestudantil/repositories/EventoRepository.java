package com.ggl.gerenciadorestudantil.repositories;

import java.util.List;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.ggl.gerenciadorestudantil.entities.Evento;

@Transactional(readOnly = true)
@NamedQueries({@NamedQuery(name = "EventoRepository.findByAlunoId",
						   query = "SELECT a FROM evento a WHERE a.aluno_id = :alunoId")})

public interface EventoRepository extends JpaRepository<Evento, Integer> {

	List<Evento> findByDisciplinaId(@Param("disciplinaId") Integer disciplinaId);

	Page<Evento> findByDisciplinaId(@Param("disciplinaId") Integer disciplinaId, Pageable pageable);

	@Query(value = "SELECT * FROM evento a "
			+ "INNER JOIN disciplina b ON b.id = a.disciplina_id "
			+ "INNER JOIN curso c ON c.id = b.curso_id "
			+ "INNER JOIN aluno d ON d.id = c.aluno_id "
			+ "WHERE d.id = :alunoId", nativeQuery = true)	
	List<Evento> findByAlunoId(@Param("alunoId") Integer alunoId);

	@Query(value = "SELECT * FROM evento a "
			+ "INNER JOIN disciplina b ON b.id = a.disciplina_id "
			+ "INNER JOIN curso c ON c.id = b.curso_id "
			+ "INNER JOIN aluno d ON d.id = c.aluno_id "
			+ "WHERE d.id = :alunoId", nativeQuery = true)	
	Page<Evento> findByAlunoId(@Param("alunoId") Integer alunoId, Pageable pageable);
	
	@Query(value = "SELECT * FROM evento a "
			+ "INNER JOIN disciplina b ON b.id = a.disciplina_id "
			+ "INNER JOIN curso c ON c.id = b.curso_id "
			+ "INNER JOIN aluno d ON d.id = c.aluno_id "
			+ "WHERE d.receber_notificacoes = true" ,nativeQuery = true)
	List<Evento> findEventosParaNotificar();
}
