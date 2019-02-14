package com.authentication.model.demo;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.authentication.dto.demo.FilesDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * @author TungBoom
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "files")
public class FilesEntity {
	
    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "file_id", nullable = false, updatable = false)
    private Long fileId;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "created_user", insertable=true, updatable=false)
    private String createdUser;
    
    @Column(name = "created_time", insertable=true, updatable=false)
    private Timestamp createdTime;
    
    public FilesDto toDto() {
		return new FilesDto(fileId, fileName, filePath, fileSize, createdUser, createdTime);
	}
    
	public FilesEntity(Long fileId, String fileName, String filePath, Long fileSize, String createdUser,
			Timestamp createdTime) {
		this.fileId = fileId;
		this.fileName = fileName;
		this.filePath = filePath;
		this.fileSize = fileSize;
		this.createdUser = createdUser;
		this.createdTime = createdTime;
	}
}
