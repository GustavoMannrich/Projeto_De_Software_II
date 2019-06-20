package com.ggl.gerenciadorestudantil.dtos;

import java.util.Optional;

public class DBFileDto {

	private Optional<String> id = Optional.empty();
	private Integer disciplinaId;
    private String fileName;
    private String fileType;
    
	public Optional<String> getId() {
		return id;
	}
	public void setId(Optional<String> id) {
		this.id = id;
	}
	public Integer getDisciplinaId() {
		return disciplinaId;
	}
	public void setDisciplinaId(Integer disciplinaId) {
		this.disciplinaId = disciplinaId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	@Override
	public String toString() {
		return "DBFileDto [id=" + id + ", disciplinaId=" + disciplinaId + ", fileName=" + fileName + ", fileType=" + fileType
				+ "]";
	}	
}
