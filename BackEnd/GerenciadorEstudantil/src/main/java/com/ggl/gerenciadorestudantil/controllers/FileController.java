package com.ggl.gerenciadorestudantil.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ggl.gerenciadorestudantil.dtos.DBFileDto;
import com.ggl.gerenciadorestudantil.entities.DBFile;
import com.ggl.gerenciadorestudantil.response.Response;
import com.ggl.gerenciadorestudantil.response.UploadFileResponse;
import com.ggl.gerenciadorestudantil.services.impl.DBFileStorageService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class FileController {
	
	private static final Logger logger = LoggerFactory.getLogger(FileController.class);

	@Value("${paginacao.qtd_por_pagina}")
	private int qtdPorPagina;
	
    @Autowired
    private DBFileStorageService DBFileStorageService;

    @PostMapping("/uploadFile/{disciplinaId}")
    public UploadFileResponse uploadFile(@PathVariable("disciplinaId") int disciplinaId, @RequestParam("file") MultipartFile file) {
        DBFile dbFile = DBFileStorageService.storeFile(disciplinaId, file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(dbFile.getId())
                .toUriString();

        return new UploadFileResponse(dbFile.getFileName(), fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @PostMapping("/uploadMultipleFiles/{disciplinaId}")
    public List<UploadFileResponse> uploadMultipleFiles(@PathVariable("disciplinaId") int disciplinaId, @RequestParam("files") MultipartFile[] files) {    
    	return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(disciplinaId, file))
                .collect(Collectors.toList());
    }

    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("fileId") String fileId) {
        // Load file from database
        DBFile dbFile = DBFileStorageService.getFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
                .body(new ByteArrayResource(dbFile.getData()));
    }
    
    @GetMapping("/files/{disciplinaId}")
    public ResponseEntity<Response<Page<DBFileDto>>> listFile(@PathVariable("disciplinaId") int disciplinaId,
    		@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir) {
    	
    	@SuppressWarnings("deprecation")
		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
        // Load file from database
        Page<DBFile> files = DBFileStorageService.getAllFiles(disciplinaId, pageRequest);
        Page<DBFileDto> filesDto = files.map(f -> this.converterFileDto(f));
        
        Response<Page<DBFileDto>> response = new Response<Page<DBFileDto>>();
        response.setData(filesDto);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("files/{id}")
	public ResponseEntity<Response<String>> remover(@PathVariable("id") String id) {
		logger.info("Removendo arquivo: {}", id);
		Response<String> response = new Response<String>();
		DBFile dbFile = this.DBFileStorageService.getFile(id);

		if (dbFile == null) {
			logger.info("Erro ao remover devido ao aluno ID: {} ser inválido.", id);
			response.getErrors().add("Erro ao remover arquivo. Registro não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		this.DBFileStorageService.remover(id);
		return ResponseEntity.ok(new Response<String>());
	}
    
    private DBFileDto converterFileDto(DBFile file) {
    	DBFileDto fileDto = new DBFileDto();
		
    	fileDto.setId(Optional.of(file.getId()));
    	fileDto.setFileName(file.getFileName());
    	fileDto.setFileType(file.getFileType());
    	fileDto.setDisciplinaId(file.getDisciplina().getId());
		
		return fileDto;
	}
}
