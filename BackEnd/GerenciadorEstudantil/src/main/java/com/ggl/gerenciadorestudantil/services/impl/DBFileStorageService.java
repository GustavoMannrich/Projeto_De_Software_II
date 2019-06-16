package com.ggl.gerenciadorestudantil.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.ggl.gerenciadorestudantil.entities.Aluno;
import com.ggl.gerenciadorestudantil.entities.DBFile;
import com.ggl.gerenciadorestudantil.exceptions.FileStorageException;
import com.ggl.gerenciadorestudantil.exceptions.MyFileNotFoundException;
import com.ggl.gerenciadorestudantil.repositories.AlunoRepository;
import com.ggl.gerenciadorestudantil.repositories.DBFileRepository;

import java.io.IOException;
import java.util.Optional;

@Service
public class DBFileStorageService {
    @Autowired
    private DBFileRepository dbFileRepository;
    
    @Autowired
    private AlunoRepository alunoRepository;

    public DBFile storeFile(int alunoId, MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            Optional <Aluno> aluno = alunoRepository.findById(alunoId);
            
            DBFile dbFile = new DBFile(fileName, file.getContentType(), file.getBytes(), aluno.get());

            return dbFileRepository.save(dbFile);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public DBFile getFile(String fileId) {
        return dbFileRepository.findById(fileId)
                .orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));
    }
    
    public Page<DBFile> getAllFiles(int alunoId, PageRequest pageRequest) {
    	return dbFileRepository.findByAlunoId(alunoId, pageRequest);
    }
    
    public void remover(String id) {
		this.dbFileRepository.deleteById(id);
	}
}
