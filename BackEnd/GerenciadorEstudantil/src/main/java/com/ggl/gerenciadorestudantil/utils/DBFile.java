package com.ggl.gerenciadorestudantil.utils;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "files")
public class DBFile {
    private String id;
    private String fileName;
    private String fileType;
    private byte[] data;

    public DBFile() {

    }

    public DBFile(String fileName, String fileType, byte[] data) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
    }

    @Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Lob
	@Column(name = "file_Name") 
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name = "file_Type")
	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	@Column(name = "data")
	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
    
    
}
