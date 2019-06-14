package com.ggl.gerenciadorestudantil.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ggl.gerenciadorestudantil.utils.DBFile;

@Repository
public interface DBFileRepository extends JpaRepository<DBFile, String> {

}
