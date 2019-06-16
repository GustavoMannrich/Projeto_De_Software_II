package com.ggl.gerenciadorestudantil.dtos;

import java.util.Optional;

public class DBFileDto {

	private Optional<String> id = Optional.empty();
	private Integer alunoId;
    private String fileName;
    private String fileType;
    
	public Optional<String> getId() {
		return id;
	}
	public void setId(Optional<String> id) {
		this.id = id;
	}
	public Integer getAlunoId() {
		return alunoId;
	}
	public void setAlunoId(Integer alunoId) {
		this.alunoId = alunoId;
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
		return "DBFileDto [id=" + id + ", alunoId=" + alunoId + ", fileName=" + fileName + ", fileType=" + fileType
				+ "]";
	}	
}
