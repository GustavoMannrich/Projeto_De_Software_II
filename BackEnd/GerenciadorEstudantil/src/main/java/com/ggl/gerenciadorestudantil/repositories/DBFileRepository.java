package com.ggl.gerenciadorestudantil.repositories;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ggl.gerenciadorestudantil.entities.DBFile;

/*@Transactional(readOnly = true)
@NamedQueries({@NamedQuery(name = "DBFileRepository.findByAlunoId",
						   query = "SELECT a FROM files a "
						   		+ "INNER JOIN alunos b ON b.aluno_id = :alunoId")})*/

@Repository
public interface DBFileRepository extends JpaRepository<DBFile, String> {
	
	//Page<DBFile> findByAlunoId(@Param("aluno") Integer alunoId, Pageable pageable);
	
	//@Query(value = "SELECT a FROM files a INNER JOIN aluno b ON b.id = :alunoId", nativeQuery = true)
	//Page<DBFile> findByAlunoIdWithPagination(@Param("alunoId") Integer alunoId, Pageable pageable);
	
	@Query(value = "SELECT * FROM files a WHERE a.disciplina_Id = :disciplinaId", nativeQuery = true)
	Page<DBFile> findByDisciplinaId(@Param("disciplinaId") Integer disciplinaId, Pageable pageable);
}
