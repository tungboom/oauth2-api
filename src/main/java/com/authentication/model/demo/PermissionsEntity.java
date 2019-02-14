package com.authentication.model.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.authentication.dto.demo.PermissionsDto;

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
@Table(name = "permissions")
public class PermissionsEntity {
	
    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "permission_id", nullable = false, updatable = false)
    private Long permissionId;

    @Column(name = "permission_code")
    private String permissionCode;

    @Column(name = "permission_name")
    private String permissionName;

    @Column(name = "status")
    private Long status;
    
    @Column(name = "description")
    private String description;
    
    public PermissionsDto toDto() {
		return new PermissionsDto(permissionId, permissionCode, permissionName, status, description);
	}

	public PermissionsEntity(Long permissionId, String permissionCode, String permissionName, Long status, String description) {
		this.permissionId = permissionId;
		this.permissionCode = permissionCode;
		this.permissionName = permissionName;
		this.status = status;
		this.description = description;
	}
}
