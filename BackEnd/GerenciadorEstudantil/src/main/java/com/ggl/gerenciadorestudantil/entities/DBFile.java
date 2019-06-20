package com.ggl.gerenciadorestudantil.entities;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "files")
public class DBFile implements Serializable {

	private static final long serialVersionUID = -5721619065426051954L;
	
	private String id;
    private String fileName;
    private String fileType;
    private byte[] data;
    private Disciplina disciplina;

    public DBFile() {
    	
    }    		
    
    public DBFile(String fileName, String fileType, byte[] data, Disciplina disciplina) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
        this.disciplina = disciplina;
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
    
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="disciplina_id")
	public Disciplina getDisciplina() {
		return disciplina;
	}
	
	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

	@Override
	public String toString() {
		return "DBFile [id=" + id + ", fileName=" + fileName + ", fileType=" + fileType + ", disciplina=" + disciplina + "]";
	}
}
